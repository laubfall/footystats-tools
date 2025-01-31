package de.footystats.tools.services.prediction.influencer.team;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PrecheckResult;
import de.footystats.tools.services.prediction.influencer.BetPredictionContext;
import de.footystats.tools.services.prediction.influencer.BetResultInfluencer;
import org.springframework.util.Assert;

abstract class TeamWinInfluencer implements BetResultInfluencer {
	private final Bet teamBet;

	public TeamWinInfluencer(Bet teamBet) {
		Assert.notNull(teamBet, "teamBet must not be null");
		Assert.isTrue(teamBet == Bet.HOME_WIN || teamBet == Bet.AWAY_WIN, "teamBet must be HOME_WIN or AWAY_WIN");
		this.teamBet = teamBet;
	}

	@Override
	public final PrecheckResult preCheck(BetPredictionContext ctx) {
		if (!teamBet.equals(ctx.bet())) {
			return PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET;
		}
		return betRelatedPreCheck(ctx);
	}

	abstract PrecheckResult betRelatedPreCheck(BetPredictionContext ctx);
}
