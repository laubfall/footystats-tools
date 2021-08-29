import { INFLUENCER_POINTS } from '../../constants';
import { BetType } from '../../types/Bet';
import BetPredictionContext from '../../types/prediction/BetPredictionContext';
import {
  BetInfluencerCalculation,
  NotExecutedCause,
} from '../../types/prediction/BetResultInfluencer';

function preCheck(ctx: BetPredictionContext): NotExecutedCause {
  if (
    !ctx.match?.away?.leaguePosition ||
    !ctx.match?.home?.leaguePosition ||
    !ctx.match?.leagueTeamsCount
  ) {
    return NotExecutedCause.NOT_ENOUGH_INFORMATION;
  }

  return NotExecutedCause.EXECUTED;
}

function overBet(ctx: BetPredictionContext): BetInfluencerCalculation {
  const preCheckResult = preCheck(ctx);
  if (preCheckResult !== NotExecutedCause.EXECUTED) {
    return { amount: 0, notExecutedCause: preCheckResult };
  }

  const awayPos = ctx.match.away.leaguePosition;
  const homePos = ctx.match.home.leaguePosition;

  const awayPosPerc = 100 - (100 * awayPos) / ctx.match.leagueTeamsCount;
  const homePosPerc = 100 - (100 * homePos) / ctx.match.leagueTeamsCount;

  return {
    amount: (INFLUENCER_POINTS * ((awayPosPerc + homePosPerc) / 2)) / 100,
  };
}

/**
 * LeaguePositionInfluencer takes a look at the individual position of both teams in their current league.
 *
 * @param ctx Mandatory. bet ctx.
 * @returns Influencer result.
 */
export default function calculateInfluence(
  ctx: BetPredictionContext
): BetInfluencerCalculation {
  if (BetType.OVER === ctx.bet.betType) {
    return overBet(ctx);
  }
  return {
    amount: 0,
    notExecutedCause: NotExecutedCause.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET,
  };
}
