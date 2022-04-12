import {
  Bet,
  BetPredictionContext,
} from '../../types/prediction/BetPredictionContext';
import {
  BetResultInfluencer,
  BetInfluencerCalculation,
  PrecheckResult,
  PreCheckReturn,
} from '../../types/prediction/BetResultInfluencer';

class OddsGoalsOverInfluencer implements BetResultInfluencer {
  // eslint-disable-next-line class-methods-use-this
  public preCheck(ctx: BetPredictionContext): PreCheckReturn {
    switch (ctx.bet) {
      case Bet.OVER_ZERO_FIVE:
      case Bet.OVER_ONE_FIVE:
        break;
      default:
        return PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET;
    }
    if (!ctx.match) {
      return PrecheckResult.NOT_ENOUGH_INFORMATION;
    }

    return PrecheckResult.OK;
  }

  // eslint-disable-next-line class-methods-use-this
  public calculateInfluence(
    ctx: BetPredictionContext
  ): BetInfluencerCalculation {
    let amount = 0;
    // eslint-disable-next-line default-case
    switch (ctx.bet) {
      case Bet.OVER_ZERO_FIVE:
      case Bet.OVER_ONE_FIVE:
        amount = OddsGoalsOverInfluencer.calculateFor05And15(ctx);
        break;
    }

    return { amount };
  }

  private static calculateFor05And15(ctx: BetPredictionContext): number {
    const zeroFiveModifier = ctx.bet === Bet.OVER_ZERO_FIVE ? 0.2 : 0;
    const oddsOver15 = ctx.match.Odds_Over15; // Odds Over05 info does not exist in csv
    const diffOddToOdd2 = 2 - oddsOver15 - zeroFiveModifier; // e.g.: 2 - 1,3 = 0,7, the subtraction of 0.2 is cause of the stat
    if (diffOddToOdd2 < 0) {
      return 0; // odds for over 1.5 are so high that it seems to be impossible for even over 05
    }

    if (diffOddToOdd2 > 1) {
      return 100;
    }

    return 100 - 100 * diffOddToOdd2;
  }
}

export default OddsGoalsOverInfluencer;
