export type PercentDistribution = {
	predictionPercent: number;
	count: number; // count of bets where the influencer calculated the same prediction in percent value (field predictionPercent)
};

export type PredictionQualityRevision = number;

export const NO_REVISION_SO_FAR = -1;
