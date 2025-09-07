package de.footystats.tools.controller.placebet;

import de.footystats.tools.controller.BaseControllerTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PlaceBetControllerTest extends BaseControllerTest {

	@Test
	void available_bet_options() throws Exception {
		ResultActions result = mockMvc.perform(RestDocumentationRequestBuilders
				.get("/placebet/available-options").accept(MediaType.APPLICATION_JSON).contentType(
					MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.options").isArray())
			.andExpect(jsonPath("$.options.length()", Matchers.equalTo(12)))
			.andDo(document("placeBetController/availableBetOptions"));
	}
}
