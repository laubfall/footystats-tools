import {
  Bet,
  BetPredictionContext,
} from '../../types/prediction/BetPredictionContext';
import {
  BetResultInfluencer,
  BetInfluencerCalculation,
  PreCheckReturn,
  PrecheckResult,
} from '../../types/prediction/BetResultInfluencer';

class OddsBttsYesInfluencer implements BetResultInfluencer {
  // eslint-disable-next-line class-methods-use-this
  preCheck(ctx: BetPredictionContext): PreCheckReturn {
    if (ctx.bet !== Bet.BTTS_YES) {
      return PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET;
    }

    if (!ctx.match.Odds_BTTS_Yes) {
      return PrecheckResult.NOT_ENOUGH_INFORMATION;
    }

    return PrecheckResult.OK;
  }

  // eslint-disable-next-line class-methods-use-this
  calculateInfluence(ctx: BetPredictionContext): BetInfluencerCalculation {
    // odds between 1 and 3 are relevant, other odds are extremas.
    return { amount: 100 * (100 - (100 * 2 - ctx.match.Odds_BTTS_Yes) / 2) };
  }
}

export default OddsBttsYesInfluencer;
