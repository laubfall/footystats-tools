package de.footystats.tools.services.prediction.influencer.team;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PrecheckResult;
import de.footystats.tools.services.prediction.influencer.BetPredictionContext;

abstract class TeamWinOddsInfluencer extends TeamWinInfluencer {

	static float DIFF_RANGE = 4f;
	private final Bet teamBet;

	public TeamWinOddsInfluencer(Bet teamBet) {
		this.teamBet = teamBet;
	}

	@Override
	PrecheckResult betRelatedPreCheck(BetPredictionContext ctx) {
		var matchStats = ctx.match();
		if (matchStats.getOddsDraw() == null || matchStats.getOddsHomeWin() == null || matchStats.getOddsAwayWin() == null) {
			return PrecheckResult.NOT_ENOUGH_INFORMATION;
		}
		return PrecheckResult.OK;
	}

	@Override
	public Integer calculateInfluence(BetPredictionContext ctx) {
		Float oddsTeamWin = oddsTeamWin(ctx);
		Float oddsOtherTeam = oddsOtherTeam(ctx);

		var diff = oddsTeamWin - oddsOtherTeam;
		if (diff < 0) {
			return 0;
		}

		if (diff > DIFF_RANGE) {
			return 100;
		}

		// calculate the percentage of diff in the range of 0 to DIFF_RANGE
		return (int) ((diff / DIFF_RANGE) * 100);
	}

	private Float oddsTeamWin(BetPredictionContext ctx) {
		var matchStats = ctx.match();
		if (Bet.HOME_WIN.equals(teamBet)) {
			return matchStats.getOddsHomeWin();
		} else {
			return matchStats.getOddsAwayWin();
		}
	}

	private Float oddsOtherTeam(BetPredictionContext ctx) {
		var matchStats = ctx.match();
		if (Bet.HOME_WIN.equals(teamBet)) {
			return matchStats.getOddsAwayWin();
		} else {
			return matchStats.getOddsHomeWin();
		}
	}
}
