import { BetPredictionContext } from '../../types/prediction/BetPredictionContext';
import {
  BetInfluencerCalculation,
  NotExecutedCause,
  PreCheckReturn,
} from '../../types/prediction/BetResultInfluencer';

class OddsGoalsOverInfluencer {
  // eslint-disable-next-line class-methods-use-this
  public preCheck(ctx: BetPredictionContext): PreCheckReturn {
    if (!ctx.match) {
      return NotExecutedCause.NOT_ENOUGH_INFORMATION;
    }
    return undefined;
  }

  // eslint-disable-next-line class-methods-use-this
  public calculateInfluence(
    ctx: BetPredictionContext
  ): BetInfluencerCalculation {
    return { amount: 3 };
  }
}

export default OddsGoalsOverInfluencer;
