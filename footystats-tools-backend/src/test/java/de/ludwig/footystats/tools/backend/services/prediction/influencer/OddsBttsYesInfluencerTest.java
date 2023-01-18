package de.ludwig.footystats.tools.backend.services.prediction.influencer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.ludwig.footystats.tools.backend.services.prediction.Bet;
import de.ludwig.footystats.tools.backend.services.prediction.PrecheckResult;
import de.ludwig.footystats.tools.backend.services.stats.MatchStats;

public class OddsBttsYesInfluencerTest {
	@Test
	public void some_predictions() {
		var influencer = new OddsBttsYesInfluencer();
		var ms = new MatchStats();
		ms.setOddsBTTS_Yes(1f);
		var ctx = new BetPredictionContext(ms, null, null, Bet.BTTS_YES);
		var result = influencer.calculateInfluence(ctx);
		Assertions.assertEquals(100f, Math.floor(result));

		ms.setOddsBTTS_Yes(2f);
		result = influencer.calculateInfluence(ctx);
		Assertions.assertEquals(50f, Math.floor(result));

		ms.setOddsBTTS_Yes(3.0f);
		result = influencer.calculateInfluence(ctx);
		Assertions.assertEquals(0f, result);
	}

	@Test
	public void precheck_not_enough_information() {
		var influencer = new OddsBttsYesInfluencer();
		var ms = new MatchStats();
		ms.setOddsBTTS_Yes(0f);
		var ctx = new BetPredictionContext(ms, null, null, Bet.BTTS_YES);
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
