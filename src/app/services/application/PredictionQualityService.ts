/* eslint-disable class-methods-use-this */
import { injectable } from 'inversify';
import path from 'path';
import { DbStoreService } from './DbStoreService';
import MatchService from '../match/MatchService';
import Configuration from '../../types/application/Configuration';
import cfg from '../../../config';

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

  async latestRevision(): Promise<PredictionQualityRevision> {
    const report = await this.dbService.asyncFindOne({}, [
      {
        modification: 'sort',
        parameter: {
          revision: 1,
        },
      },
    ]);

    return Promise.resolve(report.revision);
  }

  precast(revision?: number): Precast {
    throw new Error('Method not implemented.');
  }

  computeQuality(revision?: number): PredictionQualityReport {
    throw new Error('Method not implemented.');
  }
}

export interface IPredictionQualityService {
  latestRevision(): Promise<PredictionQualityRevision>;

  precast(revision?: PredictionQualityRevision): Precast;

  computeQuality(revision?: PredictionQualityRevision): PredictionQualityReport;
}

export type Precast = {
  revision: PredictionQualityRevision;

  predictionsToAssess: number;
};

export type PredictionQualityReport = {
  revision: PredictionQualityRevision;
};

export type PredictionQualityRevision = number;
