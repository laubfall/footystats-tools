package de.footystats.tools.services.prediction.outcome;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PredictionResult;
import de.footystats.tools.services.prediction.quality.BetPredictionQualityRepository;
import de.footystats.tools.services.prediction.quality.PredictionQualityRevision;
import de.footystats.tools.services.prediction.quality.PredictionQualityService;
import de.footystats.tools.services.prediction.quality.view.BetPredictionQualityBetAggregate;
import de.footystats.tools.services.prediction.quality.view.PredictionQualityViewService;
import java.util.ArrayList;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = {StatisticalResultOutcomeService.class})
class StatisticalResultOutcomeServiceTest {

	@Autowired
	private StatisticalResultOutcomeService statisticalResultOutcomeService;

	@MockBean
	private PredictionQualityViewService predictionQualityViewService;

	@MockBean
	private BetPredictionQualityRepository betPredictionAggregateRepository;

	@MockBean
	private PredictionQualityService predictionQualityService;

	static Object[][] calcBetRankingsParams() {
		var intValues = IntStream.rangeClosed(1, 100).boxed().toArray();
		var result = new Object[intValues.length][3];

		for (int i = 0; i < intValues.length; i++) {
			result[i][0] = intValues[i];
			result[i][1] = false;
			result[i][2] = false;
		}

		for (int i = 100; i > 90; i--) {
			result[i - 1][1] = true;
		}

		for (int i = 100; i > 80; i--) {
			result[i - 1][2] = true;
		}
		return result;
	}

	@BeforeEach
	void setUp() {
		reset(predictionQualityViewService);
	}

	@ParameterizedTest
	@MethodSource("calcBetRankingsParams")
	void calcBetRankings(int predictedResult, boolean best10percent, boolean best20percent) {
		final var bet = Bet.OVER_ONE_FIVE;
		final var revision = new PredictionQualityRevision(1);
		var betAggregates = new ArrayList<BetPredictionQualityBetAggregate>();
		for (int i = 100; i > 50; i--) {
			betAggregates.add(new BetPredictionQualityBetAggregate(bet, (long) (i - 1), (long) (100 - i - 1), i));
		}

		when(predictionQualityViewService.betPredictionQuality(bet, revision)).thenReturn(betAggregates);

		var dontBetAggregates = new ArrayList<BetPredictionQualityBetAggregate>();
		for (int i = 50; i > 0; i--) {
			betAggregates.add(new BetPredictionQualityBetAggregate(bet, (long) (i), (long) (50 - i), i));
		}

		when(predictionQualityViewService.dontBetPredictionQuality(bet, revision)).thenReturn(dontBetAggregates);
		Ranking ranking = statisticalResultOutcomeService.calcBetRanking(bet, new PredictionResult(predictedResult, true, null, null), revision);
		Assertions.assertNotNull(ranking);
		Assertions.assertEquals(ranking.best10Percent(), best10percent);
		Assertions.assertEquals(ranking.best20Percent(), best20percent);
	}
}
