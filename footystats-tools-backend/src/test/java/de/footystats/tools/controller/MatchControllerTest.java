package de.footystats.tools.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.footystats.tools.services.domain.DomainDataService;
import de.footystats.tools.services.match.Match;
import de.footystats.tools.services.match.MatchRepository;
import de.footystats.tools.services.prediction.InfluencerResult;
import de.footystats.tools.services.prediction.PrecheckResult;
import de.footystats.tools.services.prediction.PredictionAnalyze;
import de.footystats.tools.services.prediction.PredictionResult;
import de.footystats.tools.services.prediction.quality.PredictionQualityRevision;
import de.footystats.tools.services.stats.MatchStats;
import de.footystats.tools.services.stats.MatchStatsRepository;
import de.footystats.tools.services.stats.MatchStatus;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs(outputDir = "target/snippets")
class MatchControllerTest extends BaseControllerTest {

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private MatchStatsRepository matchStatsRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private DomainDataService domainDataService;

	@AfterEach
	public void cleanUp() {
		matchRepository.deleteAll();
	}

	@BeforeEach
	public void cleanup() {
		matchRepository.deleteAll();
		matchStatsRepository.deleteAll();
	}

	@Test
	void match_list_invalid_request() throws Exception {
		mockMvc.perform(RestDocumentationRequestBuilders
			.post("/match/list")).andExpect(status().is(415)).andDo(document("matchController/invalidrequest"));
	}

	@Test
	void listMatchesForCountryAndLeague() throws Exception {
		var date = LocalDateTime.of(2022, 8, 1, 13, 0);
		var country = domainDataService.countryByName("germany");
		var match = new Match();
		match.setFootyStatsUrl("http://www.footystats.org/someurl");
		match.setCountry(country);
		match.setLeague("Bundesliga");
		match.setRevision(PredictionQualityRevision.NO_REVISION);
		match.setDateGMT(date);
		match.setBttsYes(new PredictionResult(50, true, PredictionAnalyze.NOT_ANALYZED, Collections.emptyList()));
		match.setO05(new PredictionResult(50, true, PredictionAnalyze.NOT_ANALYZED, Collections.emptyList()));
		match.setO15(new PredictionResult(50, true, PredictionAnalyze.NOT_ANALYZED, Collections.emptyList()));
		matchRepository.insert(match);

		var request = new MatchController.ListMatchRequest();
		request.setCountry(List.of("germany"));
		request.setLeague(List.of("Bundesliga"));
		request.setStart(date.minusDays(1));
		request.setEnd(date.plusDays(1));
		var paging = Paging.builder().page(0).size(10).build();
		request.setPaging(paging);

		var json = objectMapper.writeValueAsString(request);
		mockMvc.perform(RestDocumentationRequestBuilders
				.post("/match/list").content(json).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("elements", Is.is(Matchers.hasSize(1))))
			.andExpect(jsonPath("totalElements", Is.is(1)));
	}

	@Test
	void listMatchesForAllCountries(@Autowired MockMvc mvc) throws Exception {
		var date = LocalDateTime.of(2022, 8, 1, 13, 0);
		var match = new Match();
		var country = domainDataService.countryByName("germany");
		match.setCountry(country);
		match.setLeague("Bundesliga");
		match.setRevision(new PredictionQualityRevision(1));
		match.setDateUnix(date.getLong(ChronoField.ERA));
		match.setDateGMT(date);
		match.setAwayTeam("Blah");
		match.setHomeTeam("Blub");
		match.setFootyStatsUrl("fjlksdjflkds");
		match.setBttsYes(new PredictionResult(40, true, PredictionAnalyze.SUCCESS, null));
		match.setO05(
			new PredictionResult(40, true, PredictionAnalyze.SUCCESS, List.of(new InfluencerResult("testInfluencer", 40, PrecheckResult.OK))));
		match.setState(MatchStatus.complete);
		match.setGoalsAwayTeam(2);
		match.setGoalsHomeTeam(1);
		matchRepository.insert(match);

		var request = new MatchController.ListMatchRequest();
		var paging = Paging.builder().page(0).size(10).build();
		request.setPaging(paging);

		var json = objectMapper.writeValueAsString(request);
		mvc.perform(RestDocumentationRequestBuilders
				.post("/match/list").content(json).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("totalElements", Is.is(1)))
			.andExpect(jsonPath("elements", Is.is(Matchers.hasSize(1))))
			.andDo(rh -> {
				var content = rh.getResponse().getContentAsString();
				System.out.println(content);
			});
	}

	@Test
	void reimport(@Autowired MockMvc mvc) throws Exception {
		var date = LocalDateTime.of(2022, 8, 1, 13, 0);
		var austria = domainDataService.countryByName("austria");
		MatchStats matchStats = MatchStats.builder().country(austria).homeTeam("Auffach").awayTeam("Oberau")
			.dateUnix(date.getLong(ChronoField.ERA))
			.dateGmt(date)
			.resultHomeTeamGoals(2).resultAwayTeamGoals(2).matchStatus(MatchStatus.complete).build();

		matchStatsRepository.insert(matchStats);

		mvc.perform(patch("/match/stats"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("job", Is.is("COMPLETED")))
			.andExpect(jsonPath("progressInPercent", Is.is(100)))
			.andExpect(jsonPath("currentReadCount", Is.is(1)))
			.andExpect(jsonPath("itemsToProcess", Is.is(1)))
			.andExpect(jsonPath("jobName", Is.is("reimportMatchStatsJob")))
			.andDo(rh -> {
				System.out.println(rh.getResponse().getContentAsString());
			});

		Optional<Match> updatedMatchOpt = matchRepository.findOne(Example.of(
			Match.builder().country(austria)
				.homeTeam(matchStats.getHomeTeam())
				.awayTeam(matchStats.getAwayTeam())
				.build()));
		Assertions.assertTrue(updatedMatchOpt.isPresent());
		var updatedMatch = updatedMatchOpt.get();
		Assertions.assertNotNull(updatedMatch.getBttsYes());
		Assertions.assertNotNull(updatedMatch.getO05());
		Assertions.assertNotNull(updatedMatch.getO15());
	}
}
