package de.ludwig.footystats.tools.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityReportRepository;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityRevision;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityService;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
import org.json.JSONArray;
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
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.JsonPathResultMatchersDsl;
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
	private PredictionQualityService predictionQualityService;

	@Autowired
	private PredictionQualityReportRepository qualityReportRepository;

	@Test
	public void compute() throws Exception {
		mockMvc.perform(RestDocumentationRequestBuilders.get("/predictionquality/compute"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.revision", IsNull.notNullValue()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.revision.revision", Matchers.equalTo(0)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.measurements", Matchers.empty()))
		;
	}

	@Test
	public void latestReport() throws Exception {
		mockMvc.perform(RestDocumentationRequestBuilders.get("/predictionquality/latest/report").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
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
