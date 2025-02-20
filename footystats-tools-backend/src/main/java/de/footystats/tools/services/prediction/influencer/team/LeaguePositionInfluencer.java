package de.footystats.tools.services.prediction.influencer.team;

import de.footystats.tools.services.prediction.PrecheckResult;
import de.footystats.tools.services.prediction.influencer.BetPredictionContext;
import de.footystats.tools.services.prediction.influencer.BetResultInfluencer;
import de.footystats.tools.services.stats.TeamStats;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract sealed class LeaguePositionInfluencer implements BetResultInfluencer permits AwayTeamLeaguePosInfluencer,
	HomeTeamLeaguePosInfluencer {

	/**
	 * Home or away team stats.
	 */
	private final boolean homeTeam;

	protected LeaguePositionInfluencer(boolean homeTeam) {
		this.homeTeam = homeTeam;
	}

	public PrecheckResult preCheck(BetPredictionContext ctx) {
		var teamStats = relevantTeamStats(ctx);
		var leagueStats = ctx.leagueStats();
		if (teamStats == null || leagueStats == null || leagueStats.getNumberOfClubs() == null
			|| teamStats.getLeaguePositionHome() == null || teamStats.getLeaguePositionAway() == null) {
			return PrecheckResult.NOT_ENOUGH_INFORMATION;
		}

		if (teamStats.getLeaguePositionHome() == 0 || teamStats.getLeaguePositionAway() == 0) {
			return PrecheckResult.NOT_ENOUGH_INFORMATION;
		}

		if (teamStats.getLeaguePositionAway() > leagueStats.getNumberOfClubs()
			|| teamStats.getLeaguePositionHome() > leagueStats.getNumberOfClubs()) {
			return PrecheckResult.INVALID_STATS;
		}

		if (teamStats.getMatchesPlayed() < 5) {
			return PrecheckResult.NOT_ENOUGH_INFORMATION;
		}

		return PrecheckResult.OK;
	}

	/**
	 * LeaguePositionInfluencer takes a look at the individual position of both teams in their current league.
	 *
	 * @param ctx Mandatory. bet ctx.
	 * @return Influencer result.
	 */
	public Integer calculateInfluence(
		BetPredictionContext ctx) {
		return this.overBet(ctx);
	}

	private Integer overBet(BetPredictionContext ctx) {
		var teamStats = relevantTeamStats(ctx);
		var leagueStats = ctx.leagueStats();
		var awayPos = teamStats.getLeaguePositionAway();
		var homePos = teamStats.getLeaguePositionHome();

		/*
		 * Position 1 is the best position, so the lower the position the better.
		 * Or in other words: The higher the position the worse and equal to 0 percent
		 * while the lowest position is 100 percent.
		 */
		var awayPercent = 100 - ((awayPos - 1) * 100 / (leagueStats.getNumberOfClubs() - 1));
		var homePercent = 100 - ((homePos - 1) * 100 / (leagueStats.getNumberOfClubs() - 1));

		return (awayPercent + homePercent) / 2;
	}

	private TeamStats relevantTeamStats(BetPredictionContext ctx) {
		return homeTeam ? ctx.homeTeamStats() : ctx.awayTeamStats();
	}
}
