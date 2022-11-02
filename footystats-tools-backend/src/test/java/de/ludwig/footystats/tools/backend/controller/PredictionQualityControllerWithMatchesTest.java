package de.ludwig.footystats.tools.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest
@ContextConfiguration(classes = { Configuration.class })
@AutoConfigureDataMongo
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class PredictionQualityControllerWithMatchesTest {
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PredictionQualityReportRepository qualityReportRepository;

	@Autowired
	private CsvFileService<MatchStats> csvFileService;

	@Autowired
	private MatchStatsService matchStatsService;

	@Test
	public void compute() throws Exception {

		List<MatchStats> matchStats = csvFileService.importFile(getClass().getResourceAsStream("matches_PredictionQualityReportWithMatchesTest.csv"), MatchStats.class);
		matchStats.forEach(matchStatsService::importMatchStats);

		mockMvc.perform(RestDocumentationRequestBuilders.get("/predictionquality/compute"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.revision", IsNull.notNullValue()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.revision.revision", Matchers.equalTo(0)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.measurements", Matchers.hasSize(2)));

		var reportCnt = qualityReportRepository.count();
		Assertions.assertEquals(1, reportCnt);

		var firstReport = qualityReportRepository.findTopByOrderByRevisionDesc();
		Assertions.assertNotNull(firstReport);
		Assertions.assertEquals(2, firstReport.getMeasurements().size());

		var optBetOverZeroFive = firstReport.getMeasurements().stream().filter(bpq -> Bet.OVER_ZERO_FIVE.equals(bpq.getBet())).findAny();
		Assertions.assertTrue(optBetOverZeroFive.isPresent());

		var betOverZeroFive = optBetOverZeroFive.get();
		Assertions.assertEquals(4l, betOverZeroFive.getCountAssessed());
	}
}
