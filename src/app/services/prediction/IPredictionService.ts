import BetPredictionContext from '../../types/prediction/BetPredictionContext';

export default interface IPredictionService {
  prediction(ctx: BetPredictionContext): Promise<number>;
}
