/* eslint-disable class-methods-use-this */
import { injectable } from 'inversify';
import path from 'path';
import { DbStoreService } from '../application/DbStoreService';
import Configuration from '../../types/application/Configuration';
import cfg from '../../../config';
import {
  BetPredictionQuality,
  NO_REVISION_SO_FAR,
  Precast,
  PredictionQualityReport,
  PredictionQualityRevision,
} from './PredictionQualityService.types';
import MatchService from '../match/MatchService';
import Match from '../match/IMatchService';
import { PredictionResult } from './IPredictionService';
import { Bet } from '../../types/prediction/BetPredictionContext';

@injectable()
export class PredictionQualityService implements IPredictionQualityService {
  readonly dbService: DbStoreService<PredictionQualityReport>;

  constructor(
    configuration: Configuration,
    private matchService: MatchService
  ) {
    this.dbService = new DbStoreService(
      path.join(
        configuration.databaseDirectory,
        cfg.predictionQualityDbFileName
      )
    );
    this.dbService.createUniqueIndex('revision');
  }

  async latestReport(): Promise<PredictionQualityReport> {
    const lr = await this.latestRevision();
    return this.dbService.asyncFindOne({ revision: lr });
  }

  async latestRevision(): Promise<PredictionQualityRevision> {
    const report = await this.dbService.asyncFindOne({}, [
      {
        modification: 'sort',
        parameter: {
          revision: 1,
        },
      },
    ]);

    if (!report) {
      return Promise.resolve(0);
    }

    return Promise.resolve(report.revision);
  }

  async precast(revision?: PredictionQualityRevision): Promise<Precast> {
    const result = await this.matchService.matchesByRevision(
      revision || NO_REVISION_SO_FAR
    );
    const nextRevision = await this.nextRevision(revision);
    return Promise.resolve({
      revision: nextRevision,
      predictionsToAssess: result.length,
    });
  }

  async computeQuality(): Promise<PredictionQualityReport> {
    const result: Match[] = (await this.matchService.matchesByRevision(
      NO_REVISION_SO_FAR
    )) as Match[];
    const latestRevision = await this.latestRevision();

    let report = await this.dbService.asyncFindOne({
      revision: latestRevision,
    });

    if (!report) {
      report = { revision: latestRevision, measurements: [] };
      this.dbService.insert(report);
    }

    result.forEach((match) => {
      const msm = this.measure(match);
      this.merge(report, msm);

      // update match with revision number
      match.revision = latestRevision;
      this.matchService.update(match);
    });
    await this.dbService.asyncUpsert(
      { revision: latestRevision },
      { ...report }
    );

    return Promise.resolve(report);
  }

  async nextRevision(
    revision?: PredictionQualityRevision
  ): Promise<PredictionQualityRevision> {
    let nextRevision = -1;
    if (revision) {
      nextRevision = revision + 1;
    } else {
      nextRevision = await this.latestRevision();
    }

    return nextRevision;
  }

  measure(match: Match): BetPredictionQuality[] {
    const measurements: BetPredictionQuality[] = [];
    const relevantPredictionResult = (prediction?: PredictionResult) => {
      return (
        prediction !== undefined &&
        (prediction.analyzeResult === 'SUCCESS' ||
          prediction?.analyzeResult === 'FAILED')
      );
    };
    if (relevantPredictionResult(match.o05)) {
      measurements.push({
        bet: Bet.OVER_ZERO_FIVE,
        countAssessed: 1,
        countFailed: match.o05?.analyzeResult === 'FAILED' ? 1 : 0,
        countSuccess: match.o05?.analyzeResult === 'SUCCESS' ? 1 : 0,
      });
    }

    if (relevantPredictionResult(match.bttsYes)) {
      measurements.push({
        bet: Bet.BTTS_YES,
        countAssessed: 1,
        countFailed: match.o05?.analyzeResult === 'FAILED' ? 1 : 0,
        countSuccess: match.o05?.analyzeResult === 'SUCCESS' ? 1 : 0,
      });
    }

    return measurements;
  }

  private merge(
    report: PredictionQualityReport,
    measurements: BetPredictionQuality[]
  ) {
    measurements.forEach((msm) => {
      let reportMsm = report.measurements.find((rMsm) => rMsm.bet === msm.bet);
      if (!reportMsm) {
        reportMsm = {
          ...msm,
        };
        report.measurements.push(reportMsm);
      } else {
        reportMsm.countAssessed += msm.countAssessed;
        reportMsm.countSuccess += msm.countSuccess;
        reportMsm.countFailed += msm.countFailed;
      }
    });
  }
}

export interface IPredictionQualityService {
  latestRevision(): Promise<PredictionQualityRevision>;

  precast(revision?: PredictionQualityRevision): Promise<Precast>;

  computeQuality(): Promise<PredictionQualityReport>;

  latestReport(): Promise<PredictionQualityReport>;
}
