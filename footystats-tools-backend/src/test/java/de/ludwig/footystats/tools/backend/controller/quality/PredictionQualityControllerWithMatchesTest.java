package de.ludwig.footystats.tools.backend.controller.quality;

import de.ludwig.footystats.tools.backend.controller.Configuration;
import de.ludwig.footystats.tools.backend.services.csv.CsvFileService;
import de.ludwig.footystats.tools.backend.services.match.MatchRepository;
import de.ludwig.footystats.tools.backend.services.prediction.Bet;
import de.ludwig.footystats.tools.backend.services.prediction.quality.BetPredictionQuality;
import de.ludwig.footystats.tools.backend.services.prediction.quality.BetPredictionQualityRepository;
import de.ludwig.footystats.tools.backend.services.stats.MatchStats;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled("Disabled cause endpoints under test now starts a job and don't start a synchronous computation. Maybe replace this with a service test.")
@ActiveProfiles("test")
@WebMvcTest
@ContextConfiguration(classes = {Configuration.class})
@AutoConfigureDataMongo
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class PredictionQualityControllerWithMatchesTest {

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
	public void compute() throws Exception {

		List<MatchStats> matchStats = csvFileService.importFile(getClass().getResourceAsStream("matches_PredictionQualityReportWithMatchesTest.csv"), MatchStats.class);
		matchStats.forEach(matchStatsService::importMatchStats);

		mockMvc.perform(get("/predictionquality/compute"))
			.andExpect(status().isNoContent());
		mockMvc.perform(get("/predictionquality/latest/report/OVER_ZERO_FIVE"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults", hasSize(2))) // "Actually we do predictions for two bet types"
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults[0].bet", equalTo("OVER_ZERO_FIVE")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults[0].assessed", equalTo(4)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults[0].betSuccess", equalTo(4)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults[0].betFailed", equalTo(0)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults[1].bet", equalTo("BTTS_YES")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults[1].assessed", equalTo(4)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults[1].betSuccess", equalTo(3)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults[1].betFailed", equalTo(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.betInfluencerPercentDistributions.keys()", hasSize(2)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.betInfluencerPercentDistributions['OddsGoalsOverInfluencer']", hasSize(3)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.betInfluencerPercentDistributions['FootyStatsOverFTPredictionInfluencer']", hasSize(2)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.dontBetInfluencerPercentDistributions.keys()", empty()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionDistributions", hasSize(3)))
		;
	}

	@Test
	public void compute_and_test_influencer_aggregation() throws Exception {
		List<MatchStats> matchStats = csvFileService.importFile(getClass().getResourceAsStream("matches_ComputeAndTestInfluencerAggregation.csv"), MatchStats.class);
		matchStats.forEach(matchStatsService::importMatchStats);

		Assertions.assertEquals(3, matchRepository.count());

		mockMvc.perform(get("/predictionquality/compute"))
			.andExpect(status().isNoContent());

		mockMvc.perform(get("/predictionquality/latest/report/OVER_ZERO_FIVE"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults", hasSize(2)));

		Assertions.assertEquals(2, betPredictionQualityRepository.count(), "Two, cause one for btts_yes and one for over_zero_five.");
		var overZeroFiveQuality = new BetPredictionQuality();
		overZeroFiveQuality.setBet(Bet.OVER_ZERO_FIVE);
		Optional<BetPredictionQuality> optOverZeroFive = betPredictionQualityRepository.findOne(Example.of(overZeroFiveQuality));
		Assertions.assertTrue(optOverZeroFive.isPresent());
		overZeroFiveQuality = optOverZeroFive.get();
		Assertions.assertEquals(2, overZeroFiveQuality.getInfluencerDistribution().size(), "Thee matches all with the same stats results in a aggregated list of two influencers");
	}
}
