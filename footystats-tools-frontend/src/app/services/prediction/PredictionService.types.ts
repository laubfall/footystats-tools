import { PrecheckResult } from '../../types/prediction/BetResultInfluencer';

export type PredictionResult = {
	betSuccessInPercent: number;
	betOnThis: boolean;
	analyzeResult: PredictionAnalyze;
	influencerDetailedResult: InfluencerResult[];
};
export type InfluencerName = string;
export type InfluencerResult = {
	influencerName: InfluencerName;
	influencerPredictionValue?: number;
	precheckResult: PrecheckResult;
};
export type PredictionAnalyze =
	| 'SUCCESS'
	| 'CLOSE'
	| 'FAILED'
	| 'NOT_COMPLETED'
	| 'NOT_ANALYZED'
	| 'NOT_PREDICTED';
