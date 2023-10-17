package de.footystats.tools.services.prediction.influencer;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PrecheckResult;
import de.footystats.tools.services.stats.MatchStats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class XgOverZeroFiveInfluencerTest {

	@Test
	void prediction() {
		var influencer = new XgOverZeroFiveInfluencer();
		MatchStats matchStats = MatchStats.builder().awayTeamPreMatchxG(1.3f).homeTeamPreMatchxG(1.6f).build();
		var ctx = new BetPredictionContext(matchStats, null, null, null, Bet.OVER_ZERO_FIVE);
		PrecheckResult precheckResult = influencer.preCheck(ctx);
		Assertions.assertEquals(PrecheckResult.OK, precheckResult);
		Integer prediction = influencer.calculateInfluence(ctx);
		Assertions.assertNotNull(prediction);
	}

	@Test
	void prediction_max() {
		var influencer = new XgOverZeroFiveInfluencer();
		MatchStats matchStats = MatchStats.builder().awayTeamPreMatchxG(10.0f).homeTeamPreMatchxG(16.0f).build();
		var ctx = new BetPredictionContext(matchStats, null, null, null, Bet.OVER_ZERO_FIVE);
		PrecheckResult precheckResult = influencer.preCheck(ctx);
		Assertions.assertEquals(PrecheckResult.OK, precheckResult);
		Integer prediction = influencer.calculateInfluence(ctx);
		Assertions.assertNotNull(prediction);
		Assertions.assertEquals(100, prediction, "Far more xG than expected but we did not achieved 100 percent.");

		matchStats = MatchStats.builder().awayTeamPreMatchxG(2.0f).homeTeamPreMatchxG(2.0f).build();
		ctx = new BetPredictionContext(matchStats, null, null, null, Bet.OVER_ZERO_FIVE);
		precheckResult = influencer.preCheck(ctx);
		Assertions.assertEquals(PrecheckResult.OK, precheckResult);
		prediction = influencer.calculateInfluence(ctx);
		Assertions.assertNotNull(prediction);
		Assertions.assertEquals(100, prediction, "Influencer takes 2 as the max count of goals for achieving 100 percent");
	}

	@Test
	void prediction_min() {
		var influencer = new XgOverZeroFiveInfluencer();
		MatchStats matchStats = MatchStats.builder().awayTeamPreMatchxG(0.0f).homeTeamPreMatchxG(0.0f).build();
		var ctx = new BetPredictionContext(matchStats, null, null, null, Bet.OVER_ZERO_FIVE);
		PrecheckResult precheckResult = influencer.preCheck(ctx);
		Assertions.assertEquals(PrecheckResult.OK, precheckResult);
		Integer prediction = influencer.calculateInfluence(ctx);
		Assertions.assertNotNull(prediction);
		Assertions.assertEquals(0, prediction);
	}

	@Test
	void wrongbet() {
		var influencer = new XgOverZeroFiveInfluencer();
		MatchStats matchStats = MatchStats.builder().awayTeamPreMatchxG(1.3f).homeTeamPreMatchxG(1.6f).build();
		var ctx = new BetPredictionContext(matchStats, null, null, null, Bet.OVER_ONE_FIVE);
		PrecheckResult precheckResult = influencer.preCheck(ctx);
		Assertions.assertEquals(PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET, precheckResult);
	}

	@Test
	void xgForHomeAndAwayMissing() {
		var influencer = new XgOverZeroFiveInfluencer();
		MatchStats matchStats = MatchStats.builder().build();
		var ctx = new BetPredictionContext(matchStats, null, null, null, Bet.OVER_ZERO_FIVE);
		PrecheckResult precheckResult = influencer.preCheck(ctx);
		Assertions.assertEquals(PrecheckResult.NOT_ENOUGH_INFORMATION, precheckResult);
	}
}
