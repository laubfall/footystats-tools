import { Bet } from '../../types/prediction/BetPredictionContext';
import { InfluencerName } from './PredictionService.types';
import { PrecheckResult } from '../../types/prediction/BetResultInfluencer';


/**
 * Type to describe the distribution of calculated influence of single influencer.
 */
export type InfluencerPercentDistribution = {
	influencerName: InfluencerName;
	precheckResult: PrecheckResult;
} & PercentDistribution;

export type PercentDistribution = {
	predictionPercent: number;
	count: number; // count of bets where the influencer calculated the same prediction in percent value (field predictionPercent)
};

export type PredictionQualityRevision = number;

export const NO_REVISION_SO_FAR = -1;
