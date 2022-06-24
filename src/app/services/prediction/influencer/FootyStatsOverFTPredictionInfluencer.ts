import {
  Bet,
  BetPredictionContext,
} from '../../../types/prediction/BetPredictionContext';
import {
  BetResultInfluencer,
  BetInfluencerCalculation,
  PreCheckReturn,
  PrecheckResult,
} from '../../../types/prediction/BetResultInfluencer';

class FootyStatsOverFTPredictionInfluencer implements BetResultInfluencer {
  // eslint-disable-next-line class-methods-use-this
  public preCheck(ctx: BetPredictionContext): PreCheckReturn {
    switch (ctx.bet) {
      case Bet.OVER_ZERO_FIVE:
      case Bet.OVER_ONE_FIVE:
        break;
      default:
        return PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET;
    }
    if (!ctx.match || !ctx.match['Over05 Average']) {
      return PrecheckResult.NOT_ENOUGH_INFORMATION;
    }

    return PrecheckResult.OK;
  }

  // eslint-disable-next-line class-methods-use-this
  calculateInfluence(ctx: BetPredictionContext): BetInfluencerCalculation {
    return { amount: ctx.match['Over05 Average'] };
  }
}

export default FootyStatsOverFTPredictionInfluencer;
