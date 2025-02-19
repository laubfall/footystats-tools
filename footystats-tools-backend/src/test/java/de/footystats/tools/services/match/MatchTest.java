package de.footystats.tools.services.match;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PredictionResult;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.modelmapper.internal.util.Assert;

public class MatchTest {
	@ParameterizedTest
	@EnumSource(Bet.class)
	public void prediction_for_bet(Bet bet) {
		var match = new Match();
		PredictionResult predictionResult = match.forBet(bet);
		Assert.isNull(predictionResult, "Should always return something (even if it's a null).");
	}
}
