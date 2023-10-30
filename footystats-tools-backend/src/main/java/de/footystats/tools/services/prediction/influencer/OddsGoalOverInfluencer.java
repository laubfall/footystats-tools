package de.footystats.tools.services.prediction.influencer;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PrecheckResult;

public class OddsGoalOverInfluencer implements BetResultInfluencer {

	// eslint-disable-next-line class-methods-use-this
	public PrecheckResult preCheck(BetPredictionContext ctx) {
		switch (ctx.bet()) {
			case OVER_ZERO_FIVE, OVER_ONE_FIVE:
				break;
			default:
				return PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET;
		}
		if (ctx.match() == null || ctx.match().getOddsOver15() == null) {
			return PrecheckResult.NOT_ENOUGH_INFORMATION;
		}

		return PrecheckResult.OK;
	}

	// eslint-disable-next-line class-methods-use-this
	public Integer calculateInfluence(
		BetPredictionContext ctx
	) {
		return switch (ctx.bet()) {
			case OVER_ZERO_FIVE, OVER_ONE_FIVE -> calculateFor05And15(ctx);
			default -> 0;
			// eslint-disable-next-line default-case
		};
	}

	private Integer calculateFor05And15(BetPredictionContext ctx) {
		var zeroFiveModifier = ctx.bet() == Bet.OVER_ZERO_FIVE ? 0.4 : 0.2;
		var oddsOver15 = ctx.match().getOddsOver15(); // Odds Over05 info does not exist in csv
		var diffOddToOdd2 = 2 - oddsOver15 - zeroFiveModifier; // e.g.: 2 - 1,3 = 0,7, the subtraction of 0.2 is cause of the stat
		if (diffOddToOdd2 < 0) {
			return 0; // odds for over 1.5 are so high that it seems to be impossible for even over 05
		}

		if (diffOddToOdd2 > 1) {
			return 100;
		}

		return (int) (100 * diffOddToOdd2);
	}

	// eslint-disable-next-line class-methods-use-this
	public String influencerName() {
		return "OddsGoalsOverInfluencer";
	}
}
