package de.ludwig.footystats.tools.backend.services.match;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@ActiveProfiles("test")
@DataMongoTest
@AutoConfigureDataMongo
@ContextConfiguration(classes = {MatchServiceConfiguration.class})
public class MatchServiceTest {

	@Autowired
	private MatchService matchService;

	@Autowired
	private MatchRepository matchRepository;


	@Test
	public void upsertTwoDifferentMatches(){
		var country = "Fantasia";
		var now = LocalDateTime.now();
		var matchBuilder = Match.builder().country(country).dateUnix(now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()).dateGMT(now).awayTeam("Team Away").homeTeam("Team Home");

		matchService.upsert(matchBuilder.build());

		matchBuilder.awayTeam("Team Away 2").homeTeam("Team Home 2");

		matchService.upsert(matchBuilder.build());

		List<Match> fantasiaMatches = matchRepository.findAll(Example.of(Match.builder().country(country).build()));
		Assertions.assertEquals(2, fantasiaMatches.size());
	}

	@Test
	public void findByCustomQuery() {
		var country = "Heureka";
		var now = LocalDateTime.now();
		var matchBuilder = Match.builder().country(country).dateUnix(now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()).dateGMT(now).awayTeam("Team Away").homeTeam("Team Home");

		matchService.upsert(matchBuilder.build());

		matchBuilder.awayTeam("Team Away 2").homeTeam("Team Home 2");

		matchService.upsert(matchBuilder.build());

		var page = PageRequest.of(0, 10);
		Page<Match> matches = matchService.find(MatchSearch.builder().countries(List.of(country)).pageable(page).build());
		Assertions.assertNotNull(matches);
		Assertions.assertEquals(2, matches.getNumberOfElements());

		var nowTomorrow = now.plusDays(1);
		matches = matchService.find(MatchSearch.builder().start(nowTomorrow).pageable(page).build());
		Assertions.assertTrue(matches.isEmpty());

		var nowYesterday = now.minusDays(1);
		matches = matchService.find(MatchSearch.builder().start(nowYesterday).pageable(page).build());
		Assertions.assertEquals(2, matches.getNumberOfElements());

		page = PageRequest.of(0, 1);
		matches = matchService.find(MatchSearch.builder().start(nowYesterday).pageable(page).build());
		Assertions.assertEquals(1, matches.getNumberOfElements());
		Assertions.assertEquals(2, matches.getTotalElements());
	}

	@Test
	public void findAllByCustomQuery(){
		var result = matchService.find(MatchSearch.builder().pageable(PageRequest.of(0, 10)).build());
		Assertions.assertNotNull(result);
	}
}
