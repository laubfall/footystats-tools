package de.footystats.tools.services.prediction.influencer;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PrecheckResult;
import de.footystats.tools.services.stats.MatchStats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class XgHomeAndAwayInfluencerTest {

	public static Object[][] calculateInfluenceParams() {
		return new Object[][]{
			{1F, 3F, 66},
			{3F, 3F, 100},
			{4F, 4F, 100},
			{0F, 3F, 33},
			{3F, 0F, 33},
			{0.5F, 0.5F, 0},
			{1F, 0F, 0},
			{0F, 1F, 0},
			{2F, 2F, 66},
			{2F, 1F, 50},
			{1F, 2F, 50},
			{1F, 1F, 33},
			{0F, 2F, 16},
			{2F, 0F, 16},
			{0F, 1F, 0}
		};
	}

	static Object[][] precheckParams() {
		return new Object[][]{
			{1F, 3F, Bet.BTTS_YES, PrecheckResult.OK},
			{1F, 3F, Bet.OVER_ZERO_FIVE, PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET},
			{1F, 3F, Bet.OVER_ONE_FIVE, PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET},
			{null, 3F, Bet.BTTS_YES, PrecheckResult.NOT_ENOUGH_INFORMATION},
			{3F, null, Bet.BTTS_YES, PrecheckResult.NOT_ENOUGH_INFORMATION},
			{null, null, Bet.BTTS_YES, PrecheckResult.NOT_ENOUGH_INFORMATION},
			{0F, 0F, Bet.BTTS_YES, PrecheckResult.NOT_ENOUGH_INFORMATION},
		};
	}

	@ParameterizedTest
	@MethodSource("calculateInfluenceParams")
	void calculateInfluence(Float xgHomePreMatch, Float xgAwayPreMatch, int expectedResult) {
		var influencer = new XgHomeAndAwayInfluencer();
		MatchStats matchStats = MatchStats.builder().homeTeamPreMatchxG(xgHomePreMatch).awayTeamPreMatchxG(xgAwayPreMatch).build();
		var ctx = new BetPredictionContext(matchStats, null, null, null, null);
		var result = influencer.calculateInfluence(ctx);
		Assertions.assertEquals(expectedResult, result);

	}

	@ParameterizedTest
	@MethodSource("precheckParams")
	void precheck(Float xgHomePreMatch, Float xgAwayPreMatch, Bet bet, PrecheckResult expectedResult) {
		var influencer = new XgHomeAndAwayInfluencer();
		MatchStats matchStats = MatchStats.builder().homeTeamPreMatchxG(xgHomePreMatch).awayTeamPreMatchxG(xgAwayPreMatch).build();
		var ctx = new BetPredictionContext(matchStats, null, null, null, bet);
		var result = influencer.preCheck(ctx);
		Assertions.assertEquals(expectedResult, result);

	}
}
