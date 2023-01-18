package de.ludwig.footystats.tools.backend.services.prediction.influencer;

import de.ludwig.footystats.tools.backend.services.prediction.Bet;
import de.ludwig.footystats.tools.backend.services.prediction.PrecheckResult;

public class OddsBttsYesInfluencer implements BetResultInfluencer {
	private static final float UPPER_ODDS_BOUND = 3f;

	private static final float LOWER_ODDS_BOUND = 1f;

	private static final float CALCULATION_BASE = UPPER_ODDS_BOUND - LOWER_ODDS_BOUND;

	public PrecheckResult preCheck(BetPredictionContext ctx) {

		if (ctx.bet() != Bet.BTTS_YES) {
			return PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET;
		}

		var odds = ctx.match().getOddsBTTS_Yes();
		// Odds with a value lower then 0 won't occure inside a footystats csv but odds
		// with value of 0 can.
		// So be safe and ignore those matches. Beside that odds lower then 1 are not
		// normal and will never
		// occur in bet plattforms.
		if (odds == null || odds < LOWER_ODDS_BOUND) {
			return PrecheckResult.NOT_ENOUGH_INFORMATION;
		}

		return PrecheckResult.OK;
	}

	// eslint-disable-next-line class-methods-use-this
	public Float calculateInfluence(BetPredictionContext ctx) {
		// odds between 1 and 3 are relevant, other odds are extremas.
		if (ctx.match().getOddsBTTS_Yes() > UPPER_ODDS_BOUND) {
			return 0F;
		}

		return (1 - ((ctx.match().getOddsBTTS_Yes() - LOWER_ODDS_BOUND) / CALCULATION_BASE)) * 100;
	}

	// eslint-disable-next-line class-methods-use-this
	public String influencerName() {
		return "OddsBttsYesInfluencer";
	}
}
