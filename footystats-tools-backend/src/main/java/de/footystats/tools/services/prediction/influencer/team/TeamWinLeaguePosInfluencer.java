package de.footystats.tools.services.prediction.influencer.team;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PrecheckResult;
import de.footystats.tools.services.prediction.influencer.BetPredictionContext;

/**
 * Influencer that takes a look at the league position of both teams.
 * It calculates the influence based on the league position of the teams and set both result in relation.
 */
public abstract class TeamWinLeaguePosInfluencer extends TeamWinInfluencer {

	private final AwayTeamLeaguePosInfluencer awayTeamLeaguePosInfluencer;

	private final HomeTeamLeaguePosInfluencer homeTeamLeaguePosInfluencer;
	private final Bet teamBet;

	public TeamWinLeaguePosInfluencer(Bet teamBet) {
		this.teamBet = teamBet;
		awayTeamLeaguePosInfluencer = new AwayTeamLeaguePosInfluencer();
		homeTeamLeaguePosInfluencer = new HomeTeamLeaguePosInfluencer();
	}

	@Override
	PrecheckResult betRelatedPreCheck(BetPredictionContext ctx) {
		PrecheckResult precheckResult = awayTeamLeaguePosInfluencer.preCheck(ctx);
		if (precheckResult != PrecheckResult.OK) {
			return precheckResult;
		}
		precheckResult = homeTeamLeaguePosInfluencer.preCheck(ctx);
		if (precheckResult != PrecheckResult.OK) {
			return precheckResult;
		}
		return ctx.homeTeamStats() == null || ctx.awayTeamStats() == null ? PrecheckResult.NOT_ENOUGH_INFORMATION : PrecheckResult.OK;
	}

	@Override
	public Integer calculateInfluence(BetPredictionContext ctx) {
		var awayInf = awayTeamLeaguePosInfluencer.calculateInfluence(ctx);
		var homeInf = homeTeamLeaguePosInfluencer.calculateInfluence(ctx);

		var result = 0;
		if (Bet.HOME_WIN.equals(teamBet)) {
			result = homeInf - awayInf;
		} else if (Bet.AWAY_WIN.equals(teamBet)) {
			result = awayInf - homeInf;
		}

		return Math.max(result, 0);
	}
}
