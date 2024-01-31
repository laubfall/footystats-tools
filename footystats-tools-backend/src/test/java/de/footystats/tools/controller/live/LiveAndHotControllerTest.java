package de.footystats.tools.controller.live;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.footystats.tools.controller.BaseControllerTest;
import de.footystats.tools.services.match.Match;
import de.footystats.tools.services.match.MatchRepository;
import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.outcome.InfluencerStatisticalResultOutcome;
import de.footystats.tools.services.prediction.outcome.Ranking;
import de.footystats.tools.services.prediction.outcome.StatisticalResultOutcome;
import de.footystats.tools.services.prediction.outcome.StatisticalResultOutcomeService;
import de.footystats.tools.services.stats.MatchStatus;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

class LiveAndHotControllerTest extends BaseControllerTest {

	@Autowired
	private MatchRepository matchRepository;

	@MockBean
	private StatisticalResultOutcomeService statisticalResultOutcomeService;

	@BeforeEach
	void cleanup() {
		matchRepository.deleteAll();
	}

	@Test
	void found_some_hot_bets() throws Exception {
		var country = domainDataService.countryByName("germany");
		var started = LocalDateTime.now().minusMinutes(30);
		long unix = started.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();
		matchRepository.save(
			Match.builder().state(MatchStatus.complete).country(country).dateGMT(started).dateUnix(unix)
				.awayTeam("away").homeTeam("home").build());
		matchRepository.save(
			Match.builder().state(MatchStatus.complete).country(country).dateGMT(started).dateUnix(unix)
				.awayTeam("away2").homeTeam("home").build());
		matchRepository.save(
			Match.builder().state(MatchStatus.complete).country(country).dateGMT(started).dateUnix(unix)
				.awayTeam("away3").homeTeam("home").build());
		matchRepository.save(
			Match.builder().state(MatchStatus.complete).country(country).dateGMT(started).dateUnix(unix)
				.awayTeam("away4").homeTeam("home").build());

		Mockito.when(statisticalResultOutcomeService.compute(Mockito.any(), Mockito.eq(Bet.OVER_ZERO_FIVE)))
			.thenReturn(new StatisticalResultOutcome(Bet.OVER_ZERO_FIVE, 0.9, new Ranking(1, 34, true, true), null), // hot
				new StatisticalResultOutcome(Bet.OVER_ZERO_FIVE, 0.9, new Ranking(1, 34, false, true), null), // not hot
				null, null);

		Mockito.when(statisticalResultOutcomeService.compute(Mockito.any(), Mockito.eq(Bet.OVER_ONE_FIVE)))
			.thenReturn(new StatisticalResultOutcome(Bet.OVER_ONE_FIVE, 0.94, new Ranking(1, 20, true, true), null), // hot
				null, null, null);

		mockMvc.perform(RestDocumentationRequestBuilders
				.get("/match/liveandhot").contentType(MediaType.APPLICATION_JSON))
			.andDo(rh -> System.out.println(rh.getResponse().getContentAsString()))
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].homeTeam", equalTo("home")))
			.andExpect(jsonPath("$[0].awayTeam", equalTo("away")))
			.andExpect(jsonPath("$[0].country", equalTo("germany")))
			.andExpect(jsonPath("$[0].hotBets", notNullValue()))
			.andExpect(jsonPath("$[0].hotBets", hasSize(2)))
			.andExpect(jsonPath("$[0].hotBets", containsInAnyOrder("OVER_ZERO_FIVE", "OVER_ONE_FIVE")))
			.andExpect(status().isOk());
	}

	@Test
	void hot_bet_but_match_lays_in_the_past() throws Exception {
		var country = domainDataService.countryByName("germany");
		var started = LocalDateTime.now().minusDays(1);
		long unix = started.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();
		matchRepository.save(
			Match.builder().state(MatchStatus.complete).country(country).dateGMT(started).dateUnix(unix)
				.awayTeam("away").homeTeam("home").build());

		Mockito.when(statisticalResultOutcomeService.compute(Mockito.any(), Mockito.eq(Bet.OVER_ZERO_FIVE)))
			.thenReturn(new StatisticalResultOutcome(Bet.OVER_ZERO_FIVE, 0.9,
				new Ranking(1, 34, true, true), null));

		mockMvc.perform(RestDocumentationRequestBuilders
				.get("/match/liveandhot").contentType(MediaType.APPLICATION_JSON))
			.andDo(rh -> System.out.println(rh.getResponse().getContentAsString()))
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$", empty()));
	}

	@Test
	void hot_bet_because_influencer_result() throws Exception {
		var country = domainDataService.countryByName("germany");
		var started = LocalDateTime.now().minusMinutes(30);
		long unix = started.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();
		matchRepository.save(
			Match.builder().state(MatchStatus.complete).country(country).dateGMT(started).dateUnix(unix)
				.awayTeam("somewhere").homeTeam("anywhere").build());

		Mockito.when(statisticalResultOutcomeService.compute(Mockito.any(), Mockito.eq(Bet.OVER_ONE_FIVE)))
			.thenReturn(new StatisticalResultOutcome(Bet.OVER_ONE_FIVE, 0.94, new Ranking(1, 20, false, true),
					List.of(new InfluencerStatisticalResultOutcome("someInfluencer", 0.9, new Ranking(2, 40, true, true)))), // hot
				new StatisticalResultOutcome(Bet.OVER_ONE_FIVE, 0.94, new Ranking(1, 20, false, true), // not hot
					List.of(new InfluencerStatisticalResultOutcome("someInfluencer", 0.9, new Ranking(2, 40, false, true)))),
				null, null);

		mockMvc.perform(RestDocumentationRequestBuilders
				.get("/match/liveandhot").contentType(MediaType.APPLICATION_JSON))
			.andDo(rh -> System.out.println(rh.getResponse().getContentAsString()))
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].homeTeam", equalTo("anywhere")))
			.andExpect(jsonPath("$[0].awayTeam", equalTo("somewhere")))
			.andExpect(jsonPath("$[0].country", equalTo("germany")))
			.andExpect(jsonPath("$[0].hotBets", notNullValue()))
			.andExpect(jsonPath("$[0].hotBets", hasSize(1)))
			.andExpect(jsonPath("$[0].hotBets", Matchers.contains("OVER_ONE_FIVE")))
			.andExpect(status().isOk());
	}
}
