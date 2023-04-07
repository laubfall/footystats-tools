package de.ludwig.footystats.tools.backend.controller.quality;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ludwig.footystats.tools.backend.controller.Configuration;
import de.ludwig.footystats.tools.backend.services.match.MatchRepository;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityReportRepository;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityRevision;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest
@ContextConfiguration(classes = {Configuration.class})
@AutoConfigureDataMongo
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class PredictionQualityControllerTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private PredictionQualityReportRepository predictionQualityReportRepository;

	@BeforeEach
	public void cleanup() {
		matchRepository.deleteAll();
		predictionQualityReportRepository.deleteAll();
	}

	@Test
	public void compute() throws Exception {
		mockMvc.perform(RestDocumentationRequestBuilders.get("/predictionquality/latest/report/BTTS_YES")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults", IsNull.notNullValue()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults.size()", Matchers.equalTo(4)));
		mockMvc.perform(RestDocumentationRequestBuilders.get("/predictionquality/compute"))
			.andExpect(status().isNoContent());
		mockMvc.perform(RestDocumentationRequestBuilders.get("/predictionquality/latest/report/BTTS_YES")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults", IsNull.notNullValue()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults.size()", Matchers.equalTo(4)));
	}

	@Test
	public void precast() throws Exception {
		var revision = new PredictionQualityRevision(0);
		var requestBody = objectMapper.writeValueAsString(revision);
		mockMvc.perform(RestDocumentationRequestBuilders.post("/predictionquality/precast")
				.content(requestBody)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.predictionsToAssess", Matchers.equalTo(0)))
			.andExpect(status().isOk());
	}
}
