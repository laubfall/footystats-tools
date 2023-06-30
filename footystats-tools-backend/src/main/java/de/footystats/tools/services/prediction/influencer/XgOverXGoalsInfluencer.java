package de.footystats.tools.services.prediction.influencer;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PrecheckResult;

public abstract class XgOverXGoalsInfluencer implements BetResultInfluencer {

	private int xgBase;

	private Bet validFor;

	public XgOverXGoalsInfluencer(int xgBase, Bet validFor) {
		this.xgBase = xgBase;
		this.validFor = validFor;
	}

	@Override
	public PrecheckResult preCheck(BetPredictionContext ctx) {
		if (!validFor.equals(ctx.bet())) {
			return PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET;
		}

		if (ctx.match().getAwayTeamPreMatchxG() == null && ctx.match().getHomeTeamPreMatchxG() == null) {
			return PrecheckResult.NOT_ENOUGH_INFORMATION;
		}

		return PrecheckResult.OK;
	}

	@Override
	public Integer calculateInfluence(BetPredictionContext ctx) {
		var match = ctx.match();
		var homeXg = match.getHomeTeamPreMatchxG() != null ? match.getHomeTeamPreMatchxG() : 0;
		var awayXg = match.getAwayTeamPreMatchxG() != null ? match.getAwayTeamPreMatchxG() : 0;

		var median = (homeXg + awayXg) / 2;
		if (median > xgBase) {
			return 100;
		}

		return (int) ((median / xgBase) * 100);
	}
}
