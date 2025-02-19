package de.footystats.tools.controller.quality;

import de.footystats.tools.controller.BaseControllerTest;
import de.footystats.tools.services.csv.CsvFileService;
import de.footystats.tools.services.match.MatchRepository;
import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.quality.BetPredictionQuality;
import de.footystats.tools.services.prediction.quality.BetPredictionQualityRepository;
import de.footystats.tools.services.stats.MatchStats;
import de.footystats.tools.services.stats.MatchStatsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.data.domain.Example;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(outputDir = "target/snippets")
class PredictionQualityControllerWithMatchesTest extends BaseControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CsvFileService<MatchStats> csvFileService;

	@Autowired
	private MatchStatsService matchStatsService;

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private BetPredictionQualityRepository betPredictionQualityRepository;

	@BeforeEach
	public void cleanup() {
		matchRepository.deleteAll();
		betPredictionQualityRepository.deleteAll();
	}

	@Test
	void compute() throws Exception {

		List<MatchStats> matchStats = csvFileService.importFile(
			getClass().getResourceAsStream("matches_PredictionQualityReportWithMatchesTest.csv"),
			MatchStats.class);
		matchStats.forEach(matchStatsService::importMatchStats);

		mockMvc.perform(get("/predictionquality/compute"))
			.andExpect(status().isOk())
			// checking for a specific id is unstable on github actions. So we just check for not null.
			.andExpect(jsonPath("$.jobId", notNullValue(Integer.class)));
		mockMvc.perform(get("/predictionquality/latest/report/OVER_ZERO_FIVE"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.betPredictionResults",
				hasSize(6))) // Count of bets we did quality computations for the predictions.
			.andExpect(jsonPath("$.betPredictionResults[0].bet", equalTo("OVER_ZERO_FIVE")))
			.andExpect(jsonPath("$.betPredictionResults[0].assessed", equalTo(4)))
			.andExpect(jsonPath("$.betPredictionResults[0].betSuccess", equalTo(4)))
			.andExpect(jsonPath("$.betPredictionResults[0].betFailed", equalTo(0)))
			.andExpect(jsonPath("$.betPredictionResults[1].bet", equalTo("OVER_ONE_FIVE")))
			.andExpect(jsonPath("$.betPredictionResults[1].assessed", equalTo(4)))
			.andExpect(jsonPath("$.betPredictionResults[1].betSuccess", equalTo(3)))
			.andExpect(jsonPath("$.betPredictionResults[1].dontBetSuccess",
				equalTo(1))) // One match was expected to fail against this bet
			.andExpect(jsonPath("$.betPredictionResults[1].betFailed", equalTo(0)))
			.andExpect(jsonPath("$.betPredictionResults[2].bet", equalTo("OVER_TWO_FIVE")))
			.andExpect(jsonPath("$.betPredictionResults[2].assessed", equalTo(4)))
			.andExpect(jsonPath("$.betPredictionResults[2].betSuccess", equalTo(1)))
			.andExpect(jsonPath("$.betPredictionResults[2].betFailed", equalTo(3)))
			.andExpect(jsonPath("$.betInfluencerPercentDistributions.keys()", hasSize(3)))
			.andExpect(jsonPath("$.betInfluencerPercentDistributions['OddsGoalsOverInfluencer']", hasSize(3)))
			.andExpect(
				jsonPath("$.betInfluencerPercentDistributions['FootyStatsOverFTPredictionInfluencer']", hasSize(2)))
			// Because we have no league stats
			.andExpect(jsonPath("$.betInfluencerPercentDistributions['HomeTeamLeaguePosInfluencer']").doesNotExist())
			.andExpect(jsonPath("$.betInfluencerPercentDistributions['AwayTeamLeaguePosInfluencer']").doesNotExist())
			.andExpect(jsonPath("$.betPredictionDistributions", hasSize(3)))
		;
	}

	@Test
	void compute_and_test_influencer_aggregation() throws Exception {
		List<MatchStats> matchStats = csvFileService.importFile(
			getClass().getResourceAsStream("matches_ComputeAndTestInfluencerAggregation.csv"),
			MatchStats.class);
		matchStats.forEach(matchStatsService::importMatchStats);

		Assertions.assertEquals(3, matchRepository.count());

		mockMvc.perform(get("/predictionquality/compute"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.jobId", notNullValue(Integer.class)));

		mockMvc.perform(get("/predictionquality/latest/report/OVER_ZERO_FIVE"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.betPredictionResults", hasSize(6)));

		Assertions.assertEquals(6, betPredictionQualityRepository.count(),
			"Six, because this is the count of active bets (Bet.activeBets)");
		var overZeroFiveQuality = new BetPredictionQuality();
		overZeroFiveQuality.setBet(Bet.OVER_ZERO_FIVE);
		Optional<BetPredictionQuality> optOverZeroFive = betPredictionQualityRepository.findOne(
			Example.of(overZeroFiveQuality));
		Assertions.assertTrue(optOverZeroFive.isPresent());
		overZeroFiveQuality = optOverZeroFive.get();
		Assertions.assertEquals(3, overZeroFiveQuality.getInfluencerDistribution().size(),
			"Thee matches all with the same stats results in a aggregated list of two influencers");
	}
}
