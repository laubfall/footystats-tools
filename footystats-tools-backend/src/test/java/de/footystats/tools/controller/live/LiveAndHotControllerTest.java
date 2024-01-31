package de.footystats.tools.controller.live;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.footystats.tools.controller.BaseControllerTest;
import de.footystats.tools.services.match.Match;
import de.footystats.tools.services.match.MatchRepository;
import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.outcome.Ranking;
import de.footystats.tools.services.prediction.outcome.StatisticalResultOutcome;
import de.footystats.tools.services.prediction.outcome.StatisticalResultOutcomeService;
import de.footystats.tools.services.stats.MatchStatus;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
			.thenReturn(new StatisticalResultOutcome(Bet.OVER_ZERO_FIVE, 0.9, new Ranking(1, 34, true, true), null))
			.thenReturn(null, null, null);

		mockMvc.perform(RestDocumentationRequestBuilders
				.get("/match/liveandhot").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}
