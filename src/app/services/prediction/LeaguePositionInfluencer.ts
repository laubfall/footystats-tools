import { INFLUENCER_POINTS } from '../../constants';
import { BetType } from '../../types/prediction/Bet';
import BetPredictionContext from '../../types/prediction/BetPredictionContext';
import {
  BetResultInfluencer,
  BetInfluencerCalculation,
  NotExecutedCause,
  PreCheckReturn,
} from '../../types/prediction/BetResultInfluencer';

class LeaguePositionInfluencer implements BetResultInfluencer {
  // eslint-disable-next-line class-methods-use-this
  public preCheck(ctx: BetPredictionContext): PreCheckReturn {
    if (!ctx.teamStats || !ctx.leagueStats) {
      return NotExecutedCause.NOT_ENOUGH_INFORMATION;
    }

    return undefined;
  }

  /**
   * LeaguePositionInfluencer takes a look at the individual position of both teams in their current league.
   *
   * @param ctx Mandatory. bet ctx.
   * @returns Influencer result.
   */
  // eslint-disable-next-line class-methods-use-this
  public calculateInfluence(
    ctx: BetPredictionContext
  ): BetInfluencerCalculation {
    if (BetType.OVER === ctx.bet.betType) {
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

    const awayPosPerc = 100 - (100 * awayPos) / leagueStats.number_of_clubs;
    const homePosPerc = 100 - (100 * homePos) / leagueStats.number_of_clubs;

    return {
      amount: (INFLUENCER_POINTS * ((awayPosPerc + homePosPerc) / 2)) / 100,
    };
  }
}

export default LeaguePositionInfluencer;
