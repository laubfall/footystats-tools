/* eslint-disable class-methods-use-this */
import { injectable } from 'inversify';
import path from 'path';
import log from 'electron-log';
import { DbStoreService } from '../application/DbStoreService';
import Configuration from '../../types/application/Configuration';
import cfg from '../../../config';
import {
	BetPredictionQuality,
	InfluencerPercentDistribution,
	NO_REVISION_SO_FAR,
	Precast,
	PredictionPercentDistribution,
	PredictionQualityReport,
	PredictionQualityRevision,
} from './PredictionQualityService.types';
import MatchService from '../match/MatchService';
import Match from '../match/IMatchService';
import { Bet } from '../../types/prediction/BetPredictionContext';
import { PredictionResult } from './PredictionService.types';

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

	async latestReport(): Promise<PredictionQualityReport | undefined> {
		const lr = await this.latestRevision();
		if (lr === NO_REVISION_SO_FAR) {
			return Promise.resolve(undefined);
		}
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

		if (report === null) {
			return Promise.resolve(NO_REVISION_SO_FAR);
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

		let latestRevision = await this.latestRevision();
		let report: PredictionQualityReport;
		if (latestRevision === NO_REVISION_SO_FAR) {
			latestRevision = 0;
			report = { revision: latestRevision, measurements: [] };
			this.dbService.insert(report);
		} else {
			report = await this.dbService.asyncFindOne({
				revision: latestRevision,
			});

			if (report === undefined) {
				log.info(
					`Inserted new report with revision: ${latestRevision}`
				);
				report = { revision: latestRevision, measurements: [] };
				this.dbService.insert(report);
			}
		}

		result.forEach((match) => {
			const msm = this.measure(match);
			this.merge(report, msm);

			// update match with revision number
			match.revision = latestRevision;
			this.matchService.update(match);
		});
		await this.dbService.DB.update(
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
			if (nextRevision === NO_REVISION_SO_FAR) {
				nextRevision = 0;
			}
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
		if (match.o05 && relevantPredictionResult(match.o05)) {
			const m = {
				bet: Bet.OVER_ZERO_FIVE,
				countAssessed: 1,
				countFailed:
					match.o05?.analyzeResult === 'FAILED' && match.o05.betOnThis
						? 1
						: 0,
				countSuccess:
					match.o05?.analyzeResult === 'SUCCESS' &&
					match.o05.betOnThis
						? 1
						: 0,
				countFailedDontBet:
					match.o05?.analyzeResult === 'FAILED' &&
					match.o05.betOnThis === false
						? 1
						: 0,
				countSuccessDontBet:
					match.o05?.analyzeResult === 'SUCCESS' &&
					match.o05.betOnThis === false
						? 1
						: 0,
				influencerDistribution: [],
			};
			this.addDistribution(m, match.o05);
			measurements.push(m);
		}

		if (match.bttsYes && relevantPredictionResult(match.bttsYes)) {
			const m = {
				bet: Bet.BTTS_YES,
				countAssessed: 1,
				countFailed:
					match.bttsYes?.analyzeResult === 'FAILED' &&
					match.bttsYes.betOnThis
						? 1
						: 0,
				countSuccess:
					match.bttsYes?.analyzeResult === 'SUCCESS' &&
					match.bttsYes.betOnThis
						? 1
						: 0,
				countFailedDontBet:
					match.bttsYes?.analyzeResult === 'FAILED' &&
					match.bttsYes.betOnThis === false
						? 1
						: 0,
				countSuccessDontBet:
					match.bttsYes?.analyzeResult === 'SUCCESS' &&
					match.bttsYes.betOnThis === false
						? 1
						: 0,
				influencerDistribution: [],
			};
			this.addDistribution(m, match.bttsYes);
			measurements.push(m);
		}

		return measurements;
	}

	private addDistribution(
		quality: BetPredictionQuality,
		prediction: PredictionResult
	) {
		const dist = {
			predictionPercent: prediction.betSuccessInPercent,
			count: 1,
			influencerDistribution: this.addInfluencerDistribution(prediction),
		};

		if (
			prediction.betOnThis === true &&
			prediction.analyzeResult === 'SUCCESS'
		) {
			quality.distributionBetOnThis = [dist];
		} else if (
			prediction.betOnThis === false &&
			prediction.analyzeResult === 'SUCCESS'
		) {
			quality.distributionDontBetOnThis = [dist];
		}

		if (prediction.betOnThis && prediction.analyzeResult === 'FAILED') {
			quality.distributionBetOnThisFailed = [dist];
		}

		if (
			prediction.betOnThis === false &&
			prediction.analyzeResult === 'FAILED'
		) {
			quality.distributionDontBetOnThisFailed = [dist];
		}

		if (
			(prediction.betOnThis === true &&
				prediction.analyzeResult === 'SUCCESS') ||
			(prediction.betOnThis === false &&
				prediction.analyzeResult === 'FAILED')
		) {
			quality.distributionBetSuccessful = [dist];
		}
	}

	private addInfluencerDistribution(
		prediction: PredictionResult
	): InfluencerPercentDistribution[] {
		return prediction.influencerDetailedResult.map((id) => {
			return {
				count: 1,
				influencerName: id.influencerName,
				predictionPercent: id.influencerPredictionValue,
				precheckResult: id.precheckResult,
			} as InfluencerPercentDistribution;
		});
	}

	private merge(
		report: PredictionQualityReport,
		measurements: BetPredictionQuality[]
	) {
		measurements.forEach((msm) => {
			let reportMsm = report.measurements.find(
				(rMsm) => rMsm.bet === msm.bet
			);
			if (!reportMsm) {
				reportMsm = {
					...msm,
				};
				report.measurements.push(reportMsm);
			} else {
				reportMsm.countAssessed += msm.countAssessed;
				reportMsm.countSuccess += msm.countSuccess;
				reportMsm.countFailed += msm.countFailed;
				reportMsm.countSuccessDontBet += msm.countSuccessDontBet;
				reportMsm.countFailedDontBet += msm.countFailedDontBet;
				this.mergeDistributions(reportMsm, msm);
			}
		});
	}

	private mergeDistributions(
		fullReport: BetPredictionQuality,
		matchReport: BetPredictionQuality
	) {
		if (matchReport.distributionBetOnThis) {
			this.mergeDistribution(
				fullReport.distributionBetOnThis ||
					(fullReport.distributionBetOnThis = []),
				matchReport.distributionBetOnThis
			);
		}

		if (matchReport.distributionDontBetOnThis) {
			this.mergeDistribution(
				fullReport.distributionDontBetOnThis ||
					(fullReport.distributionDontBetOnThis = []),
				matchReport.distributionDontBetOnThis
			);
		}

		if (matchReport.distributionBetSuccessful) {
			this.mergeDistribution(
				fullReport.distributionBetSuccessful ||
					(fullReport.distributionBetSuccessful = []),
				matchReport.distributionBetSuccessful
			);
		}

		if (matchReport.distributionBetOnThisFailed) {
			this.mergeDistribution(
				fullReport.distributionBetOnThisFailed ||
					(fullReport.distributionBetOnThisFailed = []),
				matchReport.distributionBetOnThisFailed
			);
		}

		if (matchReport.distributionDontBetOnThisFailed) {
			this.mergeDistribution(
				fullReport.distributionDontBetOnThisFailed ||
					(fullReport.distributionDontBetOnThisFailed = []),
				matchReport.distributionDontBetOnThisFailed
			);
		}
	}

	private mergeDistribution(
		target: PredictionPercentDistribution[],
		source: PredictionPercentDistribution[]
	) {
		source.forEach((sp) => {
			const idx = target.findIndex(
				(tp) => tp.predictionPercent === sp.predictionPercent
			);
			if (idx >= 0) {
				target[idx].count += sp.count;
				this.mergeInfluencerDistribution(target[idx], sp);
			} else {
				sp.influencerDistribution = [];
				target.push(sp);
			}
		});
	}

	private mergeInfluencerDistribution(
		target: PredictionPercentDistribution,
		source: PredictionPercentDistribution
	) {
		source.influencerDistribution.forEach((sId) => {
			if (!target.influencerDistribution) {
				target.influencerDistribution = [];
			}
			const idx = target.influencerDistribution.findIndex(
				(tId) =>
					tId.influencerName === sId.influencerName &&
					tId.predictionPercent === sId.predictionPercent &&
					tId.precheckResult === sId.precheckResult
			);

			if (idx >= 0) {
				target.influencerDistribution[idx].count += sId.count;
			} else {
				target.influencerDistribution.push(sId);
			}
		});
	}

	async recomputeQuality(revision: number): Promise<PredictionQualityReport> {
		const report = await this.dbService.asyncFindOne({ revision });
		if (!report) {
			return Promise.reject(
				new Error('There is no report for the given revision number.')
			);
		}

		const result: Match[] = (await this.matchService.matchesByRevision(
			revision
		)) as Match[];

		report.measurements = [];

		result.forEach((match) => {
			const msm = this.measure(match);
			this.merge(report, msm);
		});
		await this.dbService.DB.update({ revision }, { ...report });

		return Promise.resolve(report);
	}
}

export interface IPredictionQualityService {
	latestRevision(): Promise<PredictionQualityRevision>;

	precast(revision?: PredictionQualityRevision): Promise<Precast>;

	computeQuality(): Promise<PredictionQualityReport>;

	recomputeQuality(revision: number): Promise<PredictionQualityReport>;

	latestReport(): Promise<PredictionQualityReport | undefined>;
}
