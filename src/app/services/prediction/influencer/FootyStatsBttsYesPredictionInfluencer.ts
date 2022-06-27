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

class FootyStatsBttsYesPredictionInfluencer implements BetResultInfluencer {
  // eslint-disable-next-line class-methods-use-this
  preCheck(ctx: BetPredictionContext): PreCheckReturn {
    if (ctx.bet !== Bet.BTTS_YES) {
      return PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET;
    }

    if (!ctx.match['BTTS Average']) {
      return PrecheckResult.NOT_ENOUGH_INFORMATION;
    }

    return PrecheckResult.OK;
  }

  // eslint-disable-next-line class-methods-use-this
  calculateInfluence(ctx: BetPredictionContext): BetInfluencerCalculation {
    return { amount: ctx.match['BTTS Average'] };
  }

  // eslint-disable-next-line class-methods-use-this
  influencerName(): string {
    return 'FootyStatsBttsYesPredictionInfluencer';
  }
}

export default FootyStatsBttsYesPredictionInfluencer;
