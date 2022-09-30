import { BetPredictionContext } from '../../types/prediction/BetPredictionContext';
import { PredictionAnalyze } from './PredictionService.types';

export default interface IPredictionService {
  prediction(ctx: BetPredictionContext): Promise<number>;

  analyze(
    ctx: BetPredictionContext,
    didPredictionCalculation: boolean,
    betOnThis: boolean
  ): PredictionAnalyze;
}
