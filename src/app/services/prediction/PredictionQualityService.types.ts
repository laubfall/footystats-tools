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
  countSuccess: number; // PredictionAnalyze Success for bets you better bet on
  countFailed: number; // PredictionAnalyse Failed for bets you better bet on
  countSuccessDontBet: number;
  countFailedDontBet: number;
  distributionBetOnThis?: PredictionPercentDistribution[]; // Counted percentage values for bets fst told to bet on.
  distributionDontBetOnThis?: PredictionPercentDistribution[]; // Counted percentage values for bets fst told to not bet on.
  distributionBetSuccessful?: PredictionPercentDistribution[]; // Regardless what fts said the bet would be won. So here are the counted percentages of betOnThis && success and dontBetOnThis && failed.
};

export type PredictionPercentDistribution = {
  predictionPercent: number;
  count: number;
};

export type PredictionQualityRevision = number;

export const NO_REVISION_SO_FAR = -1;
