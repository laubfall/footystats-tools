package de.footystats.tools.services.prediction.influencer;

import de.footystats.tools.services.prediction.PrecheckResult;

public abstract class XgOverXGoalsInfluencer implements BetResultInfluencer {

	private final int xgBase;

	protected XgOverXGoalsInfluencer(int xgBase) {
		this.xgBase = xgBase;
	}

	@Override
	public PrecheckResult preCheck(BetPredictionContext ctx) {
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
