package de.footystats.tools.services.prediction.influencer;

import de.footystats.tools.services.prediction.PrecheckResult;

import static de.footystats.tools.services.prediction.Bet.OVER_TWO_FIVE;
import static de.footystats.tools.services.prediction.Bet.OVER_ZERO_FIVE;

public class FootyStatsOverFTPredictionInfluencer implements BetResultInfluencer {

	public PrecheckResult preCheck(BetPredictionContext ctx) {
		if (ctx.match().getOver05Average() == null) {
			return PrecheckResult.NOT_ENOUGH_INFORMATION;
		}

		return PrecheckResult.OK;
	}

	// eslint-disable-next-line class-methods-use-this
	public Integer calculateInfluence(BetPredictionContext ctx) {
		if (OVER_ZERO_FIVE.equals(ctx.bet())) {
			return ctx.match().getOver05Average();
		}

		if (OVER_TWO_FIVE.equals(ctx.bet())) {
			return ctx.match().getOver15Average();
		}

		return ctx.match().getOver25Average();
	}

	// eslint-disable-next-line class-methods-use-this
	public String influencerName() {
		return "FootyStatsOverFTPredictionInfluencer";
	}
}
