package de.footystats.tools.services.prediction.influencer;

import static de.footystats.tools.services.prediction.Bet.OVER_TWO_FIVE;
import static de.footystats.tools.services.prediction.Bet.OVER_ZERO_FIVE;

import de.footystats.tools.services.prediction.PrecheckResult;

public class FootyStatsOverFTPredictionInfluencer implements BetResultInfluencer {

	public PrecheckResult preCheck(BetPredictionContext ctx) {
		switch (ctx.bet()) {
			case OVER_ZERO_FIVE, OVER_ONE_FIVE, OVER_TWO_FIVE:
				break;
			default:
				return PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET;
		}
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
