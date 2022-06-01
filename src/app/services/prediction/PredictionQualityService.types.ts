import { Bet } from '../../types/prediction/BetPredictionContext';

export type Precast = {
  revision: PredictionQualityRevision;

  predictionsToAssess: number;
};

export type PredictionQualityReport = {
  revision: PredictionQualityRevision;
  measurements: BetPredictionQuality[];
};

export type BetPredictionQuality = {
  bet: Bet;
  countAssessed: number; // Count of Matches a prediction was made for the chosen bet.
  countSuccess: number; // PredictionAnalyze Success
  countFailed: number; // PredictionAnalyse Failed
};

export type PredictionQualityRevision = number;

export const NO_REVISION_SO_FAR = -1;
