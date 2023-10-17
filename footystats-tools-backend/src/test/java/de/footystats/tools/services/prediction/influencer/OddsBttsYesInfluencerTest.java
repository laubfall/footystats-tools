package de.footystats.tools.services.prediction.influencer;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PrecheckResult;
import de.footystats.tools.services.stats.MatchStats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OddsBttsYesInfluencerTest {

	@Test
	void some_predictions() {
		var influencer = new OddsBttsYesInfluencer();
		var ms = new MatchStats();
		ms.setOddsBTTS_Yes(1f);
		var ctx = new BetPredictionContext(ms, null, null, null, Bet.BTTS_YES);
		var result = influencer.calculateInfluence(ctx);
		Assertions.assertEquals(100, result);

		ms.setOddsBTTS_Yes(2f);
		result = influencer.calculateInfluence(ctx);
		Assertions.assertEquals(50, result);

		ms.setOddsBTTS_Yes(3.0f);
		result = influencer.calculateInfluence(ctx);
		Assertions.assertEquals(0, result);
	}

	@Test
	void precheck_not_enough_information() {
		var influencer = new OddsBttsYesInfluencer();
		var ms = new MatchStats();
		ms.setOddsBTTS_Yes(0f);
		var ctx = new BetPredictionContext(ms, null, null, null, Bet.BTTS_YES);
		var result = influencer.preCheck(ctx);
		Assertions.assertEquals(PrecheckResult.NOT_ENOUGH_INFORMATION, result);

		ms.setOddsBTTS_Yes(0.9f);
		result = influencer.preCheck(ctx);
		Assertions.assertEquals(PrecheckResult.NOT_ENOUGH_INFORMATION, result);

		ms.setOddsBTTS_Yes(null);
		result = influencer.preCheck(ctx);
		Assertions.assertEquals(PrecheckResult.NOT_ENOUGH_INFORMATION, result);
	}
}
