package de.ludwig.footystats.tools.backend.services.prediction;

import de.ludwig.footystats.tools.backend.services.prediction.influencer.BetPredictionContext;
import de.ludwig.footystats.tools.backend.services.stats.MatchStats;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@SpringBootTest
public class PredictionServiceTest {

	@Autowired
	private PredictionService predictionService;

	@Test
	public void analyze_o05_betOnThis(){
		var builder = MatchStats.builder().resultHomeTeamGoals(0).resultAwayTeamGoals(1).matchStatus(MatchStatus.complete);
		var bctx = new BetPredictionContext(builder.build(), null, null, Bet.OVER_ZERO_FIVE);
		PredictionAnalyze analyze = predictionService.analyze(bctx, true, true);
		Assertions.assertNotNull(analyze);
		Assertions.assertEquals(PredictionAnalyze.SUCCESS, analyze);

		builder = builder.resultHomeTeamGoals(1);
		bctx = new BetPredictionContext(builder.build(), null, null, Bet.OVER_ZERO_FIVE);
		analyze = predictionService.analyze(bctx, true, true);
		Assertions.assertEquals(PredictionAnalyze.SUCCESS, analyze);

		builder = builder.resultHomeTeamGoals(0).resultAwayTeamGoals(0);
		bctx = new BetPredictionContext(builder.build(), null, null, Bet.OVER_ZERO_FIVE);
		analyze = predictionService.analyze(bctx, true, true);
		Assertions.assertEquals(PredictionAnalyze.FAILED, analyze);
	}

	@Test
	public void analyze_o05_dontBetOnThis(){
		var builder = MatchStats.builder().resultHomeTeamGoals(0).resultAwayTeamGoals(0).matchStatus(MatchStatus.complete);
		var bctx = new BetPredictionContext(builder.build(), null, null, Bet.OVER_ZERO_FIVE);
		PredictionAnalyze analyze = predictionService.analyze(bctx, true, false);
		Assertions.assertNotNull(analyze);
		Assertions.assertEquals(PredictionAnalyze.SUCCESS, analyze);

		builder = builder.resultHomeTeamGoals(1);
		bctx = new BetPredictionContext(builder.build(), null, null, Bet.OVER_ZERO_FIVE);
		analyze = predictionService.analyze(bctx, true, false);
		Assertions.assertEquals(PredictionAnalyze.FAILED, analyze);

		builder = builder.resultAwayTeamGoals(1);
		bctx = new BetPredictionContext(builder.build(), null, null, Bet.OVER_ZERO_FIVE);
		analyze = predictionService.analyze(bctx, true, false);
		Assertions.assertEquals(PredictionAnalyze.FAILED, analyze);
	}

	public void analyze_bttsYes(){

	}
}
