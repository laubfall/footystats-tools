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
};

export type PredictionQualityRevision = number;

export const NO_REVISION_SO_FAR = -1;
