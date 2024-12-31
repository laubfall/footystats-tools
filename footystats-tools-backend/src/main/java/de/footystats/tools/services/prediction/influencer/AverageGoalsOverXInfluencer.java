package de.footystats.tools.services.prediction.influencer;

import de.footystats.tools.services.prediction.PrecheckResult;
import de.footystats.tools.services.stats.MatchStats;

import static de.footystats.tools.services.prediction.Bet.OVER_ONE_FIVE;
import static de.footystats.tools.services.prediction.Bet.OVER_TWO_FIVE;
import static de.footystats.tools.services.prediction.Bet.OVER_ZERO_FIVE;

public class AverageGoalsOverXInfluencer implements BetResultInfluencer {

	private static final Float MAX_EXPECTED_AVERAGE_GOALS = 6f;

	public PrecheckResult preCheck(BetPredictionContext ctx) {
		switch (ctx.bet()) {
			case OVER_ZERO_FIVE, OVER_ONE_FIVE, OVER_TWO_FIVE:
				break;
			default:
				return PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET;
		}

		final MatchStats match = ctx.match();
		if (match == null || match.getAverageGoals() == null) {
			return PrecheckResult.NOT_ENOUGH_INFORMATION;
		}
		return PrecheckResult.OK;
	}

	@Override
	public Integer calculateInfluence(BetPredictionContext ctx) {
		final MatchStats match = ctx.match();
		if (match.getAverageGoals() > MAX_EXPECTED_AVERAGE_GOALS) {
			return 100;
		}

		if (match.getAverageGoals() < 1) {
			return 0;
		}

		if (OVER_ZERO_FIVE.equals(ctx.bet())) {
			return o05(match.getAverageGoals());
		}

		if (OVER_ONE_FIVE.equals(ctx.bet())) {
			return o15(match.getAverageGoals());
		}

		if (OVER_TWO_FIVE.equals(ctx.bet())) {
			return o25(match.getAverageGoals());
		}

		throw new IllegalArgumentException("Bet not supported");
	}

	private Integer o05(Float averageGoals) {
		int perc = avgToMaxPerc(averageGoals);
		return addBonus(1.5f, averageGoals, perc);
	}

	private Integer o15(Float averageGoals) {
		int perc = avgToMaxPerc(averageGoals);
		return addBonus(2.5f, averageGoals, perc);
	}

	private Integer o25(Float averageGoals) {
		int perc = avgToMaxPerc(averageGoals);
		return addBonus(3.5f, averageGoals, perc);
	}

	private Integer addBonus(Float requestedAverageGoals, Float averageGoals, int avgToMaxPerc) {
		if (averageGoals >= requestedAverageGoals) {
			// add 10% to a maxium of 100%
			return Math.min(100, avgToMaxPerc + 10);
		}

		return avgToMaxPerc;
	}

	private Integer avgToMaxPerc(Float averageGoals) {
		return (int) ((averageGoals / MAX_EXPECTED_AVERAGE_GOALS) * 100);
	}

	@Override
	public String influencerName() {
		return "averageGoalsOverXInfluencer";
	}
}
