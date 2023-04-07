package de.ludwig.footystats.tools.backend.controller.quality;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ludwig.footystats.tools.backend.controller.Configuration;
import de.ludwig.footystats.tools.backend.services.csv.CsvFileService;
import de.ludwig.footystats.tools.backend.services.prediction.Bet;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityReportRepository;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityService;
import de.ludwig.footystats.tools.backend.services.stats.MatchStats;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatsService;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Assert;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest
@ContextConfiguration(classes = { Configuration.class })
@AutoConfigureDataMongo
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class PredictionQualityControllerWithMatchesTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CsvFileService<MatchStats> csvFileService;

	@Autowired
	private MatchStatsService matchStatsService;

	@Test
	public void compute() throws Exception {

		List<MatchStats> matchStats = csvFileService.importFile(getClass().getResourceAsStream("matches_PredictionQualityReportWithMatchesTest.csv"), MatchStats.class);
		matchStats.forEach(matchStatsService::importMatchStats);

		mockMvc.perform(get("/predictionquality/compute"))
			.andExpect(status().isNoContent());
		mockMvc.perform(get("/predictionquality/latest/report/OVER_ZERO_FIVE"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults", hasSize(4)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults[0].bet", equalTo("OVER_ZERO_FIVE")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults[0].assessed", equalTo(4)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults[0].betSuccess", equalTo(4)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults[0].betFailed", equalTo(0)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults[2].bet", equalTo("BTTS_YES")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults[2].assessed", equalTo(4)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults[2].betSuccess", equalTo(3)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults[2].betFailed", equalTo(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.influencerPercentDistributions.keys()", hasSize(2)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionDistributions", hasSize(3)))
		;


	}
}
