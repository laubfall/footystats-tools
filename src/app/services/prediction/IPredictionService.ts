import { BetPredictionContext } from '../../types/prediction/BetPredictionContext';

export default interface IPredictionService {
  prediction(ctx: BetPredictionContext): Promise<number>;

  analyze(
    ctx: BetPredictionContext,
    didPredictionCalculation: boolean,
    betOnThis: boolean
  ): PredictionAnalyze;
}

export type PredictionResult = {
  betSuccessInPercent: number;
  betOnThis: boolean;
  analyzeResult: PredictionAnalyze;
};

export type PredictionAnalyze =
  | 'SUCCESS'
  | 'CLOSE'
  | 'FAILED'
  | 'NOT_COMPLETED'
  | 'NOT_ANALYZED'
  | 'NOT_PREDICTED';
