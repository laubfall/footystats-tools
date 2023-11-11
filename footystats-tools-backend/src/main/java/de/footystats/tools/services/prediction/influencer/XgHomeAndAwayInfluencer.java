package de.footystats.tools.services.prediction.influencer;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PrecheckResult;

/**
 * Influencer for the home and away xG values relevant for BTTS Yes bet.
 */
public class XgHomeAndAwayInfluencer implements BetResultInfluencer {

	private static final Float MAX_INFLUENCE_VAL = 3F;

	@Override
	public PrecheckResult preCheck(BetPredictionContext ctx) {
		if (!Bet.BTTS_YES.equals(ctx.bet())) {
			return PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET;
		}

		Float homeTeamPreMatchxG = ctx.match().getHomeTeamPreMatchxG();
		Float awayTeamPreMatchxG = ctx.match().getAwayTeamPreMatchxG();

		if (homeTeamPreMatchxG == null || awayTeamPreMatchxG == null) {
			return PrecheckResult.NOT_ENOUGH_INFORMATION;
		}

		if (homeTeamPreMatchxG == 0F && awayTeamPreMatchxG == 0F) {
			return PrecheckResult.NOT_ENOUGH_INFORMATION;
		}

		return PrecheckResult.OK;
	}

	@Override
	public Integer calculateInfluence(BetPredictionContext ctx) {
		Float homeTeamPreMatchxG = ctx.match().getHomeTeamPreMatchxG();
		Float awayTeamPreMatchxG = ctx.match().getAwayTeamPreMatchxG();

		if (homeTeamPreMatchxG < 1) {
			awayTeamPreMatchxG -= 1;
		}

		if (awayTeamPreMatchxG < 1) {
			homeTeamPreMatchxG -= 1;
		}

		if (awayTeamPreMatchxG > MAX_INFLUENCE_VAL) {
			awayTeamPreMatchxG = MAX_INFLUENCE_VAL;
		}

		if (homeTeamPreMatchxG > MAX_INFLUENCE_VAL) {
			homeTeamPreMatchxG = MAX_INFLUENCE_VAL;
		}

		var result = homeTeamPreMatchxG / 2F + awayTeamPreMatchxG / 2F;
		if (result < 0) {
			result = 0;
		}
		return (int) ((result / MAX_INFLUENCE_VAL) * 100);
	}

	@Override
	public String influencerName() {
		return "xgHomeAndAwayInfluencer";
	}
}
