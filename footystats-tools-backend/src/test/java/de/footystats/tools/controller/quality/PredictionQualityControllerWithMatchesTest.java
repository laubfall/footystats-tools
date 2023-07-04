package de.footystats.tools.controller.quality;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.footystats.tools.controller.BaseControllerTest;
import de.footystats.tools.services.csv.CsvFileService;
import de.footystats.tools.services.match.MatchRepository;
import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.quality.BetPredictionQuality;
import de.footystats.tools.services.prediction.quality.BetPredictionQualityRepository;
import de.footystats.tools.services.stats.MatchStats;
import de.footystats.tools.services.stats.MatchStatsService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.data.domain.Example;
import org.springframework.test.web.servlet.MockMvc;

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

		List<MatchStats> matchStats = csvFileService.importFile(getClass().getResourceAsStream("matches_PredictionQualityReportWithMatchesTest.csv"),
			MatchStats.class);
		matchStats.forEach(matchStatsService::importMatchStats);

		mockMvc.perform(get("/predictionquality/compute"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.jobId", equalTo(1)));
		mockMvc.perform(get("/predictionquality/latest/report/OVER_ZERO_FIVE"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.betPredictionResults", hasSize(3))) // "Actually we do predictions for two bet types"
			.andExpect(jsonPath("$.betPredictionResults[0].bet", equalTo("OVER_ZERO_FIVE")))
			.andExpect(jsonPath("$.betPredictionResults[0].assessed", equalTo(4)))
			.andExpect(jsonPath("$.betPredictionResults[0].betSuccess", equalTo(4)))
			.andExpect(jsonPath("$.betPredictionResults[0].betFailed", equalTo(0)))
			.andExpect(jsonPath("$.betPredictionResults[2].bet", equalTo("BTTS_YES")))
			.andExpect(jsonPath("$.betPredictionResults[2].assessed", equalTo(4)))
			.andExpect(jsonPath("$.betPredictionResults[2].betSuccess", equalTo(3)))
			.andExpect(jsonPath("$.betPredictionResults[2].betFailed", equalTo(1)))
			.andExpect(jsonPath("$.betInfluencerPercentDistributions.keys()", hasSize(3)))
			.andExpect(jsonPath("$.betInfluencerPercentDistributions['OddsGoalsOverInfluencer']", hasSize(3)))
			.andExpect(jsonPath("$.betInfluencerPercentDistributions['FootyStatsOverFTPredictionInfluencer']", hasSize(2)))
			.andExpect(jsonPath("$.dontBetInfluencerPercentDistributions.keys()", empty()))
			.andExpect(jsonPath("$.betPredictionDistributions", hasSize(3)))
		;
	}

	@Test
	void compute_and_test_influencer_aggregation() throws Exception {
		List<MatchStats> matchStats = csvFileService.importFile(getClass().getResourceAsStream("matches_ComputeAndTestInfluencerAggregation.csv"),
			MatchStats.class);
		matchStats.forEach(matchStatsService::importMatchStats);

		Assertions.assertEquals(3, matchRepository.count());

		mockMvc.perform(get("/predictionquality/compute"))
			.andExpect(status().isOk());

		mockMvc.perform(get("/predictionquality/latest/report/OVER_ZERO_FIVE"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.betPredictionResults", hasSize(3)));

		Assertions.assertEquals(3, betPredictionQualityRepository.count(), "Two, cause one for btts_yes and one for over_zero_five.");
		var overZeroFiveQuality = new BetPredictionQuality();
		overZeroFiveQuality.setBet(Bet.OVER_ZERO_FIVE);
		Optional<BetPredictionQuality> optOverZeroFive = betPredictionQualityRepository.findOne(Example.of(overZeroFiveQuality));
		Assertions.assertTrue(optOverZeroFive.isPresent());
		overZeroFiveQuality = optOverZeroFive.get();
		Assertions.assertEquals(3, overZeroFiveQuality.getInfluencerDistribution().size(),
			"Thee matches all with the same stats results in a aggregated list of two influencers");
	}
}
