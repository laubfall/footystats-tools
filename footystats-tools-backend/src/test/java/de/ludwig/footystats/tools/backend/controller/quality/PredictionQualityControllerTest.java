package de.ludwig.footystats.tools.backend.controller.quality;

import de.ludwig.footystats.tools.backend.controller.BaseControllerTest;
import de.ludwig.footystats.tools.backend.services.match.MatchRepository;
import de.ludwig.footystats.tools.backend.services.prediction.quality.BetPredictionQualityRepository;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(outputDir = "target/snippets")
class PredictionQualityControllerTest extends BaseControllerTest {

	@Autowired
	private MockMvc mockMvc;

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
		mockMvc.perform(RestDocumentationRequestBuilders.get("/predictionquality/latest/report/BTTS_YES")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults", IsNull.notNullValue()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults.size()", Matchers.equalTo(2)));
		mockMvc.perform(RestDocumentationRequestBuilders.get("/predictionquality/compute"))
			.andExpect(status().isOk());
		mockMvc.perform(RestDocumentationRequestBuilders.get("/predictionquality/latest/report/BTTS_YES")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults", IsNull.notNullValue()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.betPredictionResults.size()", Matchers.equalTo(2)));
	}
}
