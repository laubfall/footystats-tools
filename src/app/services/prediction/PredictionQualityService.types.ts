import { Bet } from '../../types/prediction/BetPredictionContext';
import { InfluencerName } from './PredictionService.types';
import { PrecheckResult } from '../../types/prediction/BetResultInfluencer';

export type Precast = {
	revision: PredictionQualityRevision;

	predictionsToAssess: number;
};

export type PredictionQualityReport = {
	revision: PredictionQualityRevision;
	measurements: BetPredictionQuality[];
};

export type BetPredictionDistribution = PredictionPercentDistribution[];

export type BetPredictionQuality = {
	bet: Bet;
	countAssessed: number; // Count of Matches a prediction was made for the chosen bet.
	countSuccess: number; // PredictionAnalyze Success for bets you better bet on
	countFailed: number; // PredictionAnalyse Failed for bets you better bet on
	countSuccessDontBet: number;
	countFailedDontBet: number;
	distributionBetOnThis?: BetPredictionDistribution; // Counted percentage values for bets fst told to bet on.
	distributionBetOnThisFailed?: BetPredictionDistribution; // Counted percentage values for bets fst told to bet on.
	distributionDontBetOnThis?: BetPredictionDistribution; // Counted percentage values for bets fst told to not bet on.
	distributionDontBetOnThisFailed?: BetPredictionDistribution; // Counted percentage values for bets fst told to not bet on.
	distributionBetSuccessful?: BetPredictionDistribution; // Regardless what fts said the bet would be won. So here are the counted percentages of betOnThis && success and dontBetOnThis && failed.
};

/**
 * Type to describe distribution of calculated prediction for a bet.
 */
export type PredictionPercentDistribution = {
	predictionPercent: number;
	count: number; // count of occourences of this prediction value (predictionPercent)
	influencerDistribution: InfluencerPercentDistribution[];
};

/**
 * Type to describe the distribution of calculated influence of single influencer.
 */
export type InfluencerPercentDistribution = {
	influencerName: InfluencerName;
	predictionPercent: number;
	precheckResult: PrecheckResult;
	count: number; // count of bets where the influencer calculated the same prediction in percent value (field predictionPercent)
};

export type PredictionQualityRevision = number;

export const NO_REVISION_SO_FAR = -1;
