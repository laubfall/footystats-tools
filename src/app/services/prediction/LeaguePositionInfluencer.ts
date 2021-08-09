import { INFLUENCER_POINTS } from '../../constants';
import { BetType } from '../../types/Bet';
import BetPredictionContext from '../../types/prediction/BetPredictionContext';
import {
  BetInfluencerCalculation,
  NotExecutedCause,
} from '../../types/prediction/BetResultInflunencer';

function overBet(ctx: BetPredictionContext): number {
  const awayPos = ctx.match.away.leaguePosition;
  const homePos = ctx.match.home.leaguePosition;

  const awayPosPerc = 100 - (100 * awayPos) / ctx.match.leagueTeamsCount;
  const homePosPerc = 100 - (100 * homePos) / ctx.match.leagueTeamsCount;

  return (INFLUENCER_POINTS * ((awayPosPerc + homePosPerc) / 2)) / 100;
}

export default function calculateInfluence(
  ctx: BetPredictionContext
): BetInfluencerCalculation {
  if (BetType.OVER === ctx.bet.betType) {
    const amount = overBet(ctx);
    return {
      amount,
    };
  }
  return {
    amount: 0,
    notExecutedCause: NotExecutedCause.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET,
  };
}
