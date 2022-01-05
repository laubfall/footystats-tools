import { includes } from 'lodash';
import { INFLUENCER_POINTS } from '../../constants';
import {
  BetPredictionContext,
  Bet,
} from '../../types/prediction/BetPredictionContext';
import {
  BetResultInfluencer,
  BetInfluencerCalculation,
  NotExecutedCause,
  PreCheckReturn,
} from '../../types/prediction/BetResultInfluencer';

class LeaguePositionDiffInfluencer implements BetResultInfluencer {
  // eslint-disable-next-line class-methods-use-this
  public preCheck(ctx: BetPredictionContext): PreCheckReturn {
    if (!ctx.teamStats || !ctx.leagueStats) {
      return NotExecutedCause.NOT_ENOUGH_INFORMATION;
    }

    return undefined;
  }

  // eslint-disable-next-line class-methods-use-this
  public calculateInfluence(
    ctx: BetPredictionContext
  ): BetInfluencerCalculation {
    if (includes([Bet.OVER_ONE_FIVE, Bet.OVER_ZERO_FIVE], ctx.bet)) {
      return this.overBet(ctx);
    }
    return {
      amount: 0,
      notExecutedCause: NotExecutedCause.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET,
    };
  }

  // eslint-disable-next-line class-methods-use-this
  private overBet(ctx: BetPredictionContext): BetInfluencerCalculation {
    const teamStats = {
      league_position_away: 0,
      league_position_home: 0,
      ...ctx.teamStats,
    };
    const leagueStats = { number_of_clubs: 0, ...ctx.leagueStats };
    const awayPos = teamStats.league_position_away;
    const homePos = teamStats.league_position_home;

    const diff = Math.abs(homePos - awayPos);

    // e.g. leagueCnt 20 h 1 a 10 = 9
    // 20 / 9
    const result = diff / leagueStats.number_of_clubs;
    return {
      amount: result * INFLUENCER_POINTS,
    };
  }
}

export default LeaguePositionDiffInfluencer;
