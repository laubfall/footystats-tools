package de.footystats.tools.controller.quality;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.footystats.tools.controller.BaseControllerTest;
import de.footystats.tools.services.match.MatchRepository;
import de.footystats.tools.services.prediction.quality.BetPredictionQualityRepository;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

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
			.andExpect(jsonPath("$.betPredictionResults", IsNull.notNullValue(Integer.class)))
			.andExpect(jsonPath("$.betPredictionResults.size()", Matchers.equalTo(3)));
		mockMvc.perform(RestDocumentationRequestBuilders.get("/predictionquality/compute"))
			.andExpect(status().isOk())
			// checking for a specific id is unstable on github actions. So we just check for not null.
			.andExpect(jsonPath("$.jobId", notNullValue()));
		mockMvc.perform(RestDocumentationRequestBuilders.get("/predictionquality/latest/report/BTTS_YES")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.betPredictionResults", IsNull.notNullValue()))
			.andExpect(jsonPath("$.betPredictionResults.size()", Matchers.equalTo(3)));
	}
}
