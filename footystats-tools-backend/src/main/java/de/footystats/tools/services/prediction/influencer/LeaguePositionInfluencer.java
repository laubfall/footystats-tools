package de.footystats.tools.services.prediction.influencer;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PrecheckResult;
import org.apache.commons.lang3.ArrayUtils;

public class LeaguePositionInfluencer implements BetResultInfluencer {
	public PrecheckResult preCheck(BetPredictionContext ctx) {

		if (ArrayUtils.contains(new Bet[] { Bet.OVER_ONE_FIVE, Bet.OVER_ZERO_FIVE }, ctx.bet()) == false) {
			return PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET;
		}

		if (ctx.teamStats() == null || ctx.leagueStats() == null) {
			return PrecheckResult.NOT_ENOUGH_INFORMATION;
		}

		return PrecheckResult.OK;
	}

	/**
	 * LeaguePositionInfluencer takes a look at the individual position of both
	 * teams in their current league.
	 *
	 * @param ctx Mandatory. bet ctx.
	 * @returns Influencer result.
	 */
	public Integer calculateInfluence(
			BetPredictionContext ctx) {
		return this.overBet(ctx);
	}

	private Integer overBet(BetPredictionContext ctx) {
		var teamStats = ctx.teamStats();
		/*
		 * var leagueStats = {number_of_clubs:0, ...ctx.leagueStats() };
		 * const awayPos = teamStats.league_position_away; // TODO teamStats ist aktull
		 * ein Array (auch im alten Code). Das hier kann so also nicht stimmen.
		 * const homePos = teamStats.league_position_home;
		 *
		 * const awayPosPerc = 100 - (100 * awayPos) / leagueStats.number_of_clubs;
		 * const homePosPerc = 100 - (100 * homePos) / leagueStats.number_of_clubs;
		 *
		 * return (INFLUENCER_POINTS * ((awayPosPerc + homePosPerc) / 2)) / 100;
		 */
		return 0;
	}

	public String influencerName() {
		return "LeaguePositionInfluencer";
	}
}
