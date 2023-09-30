package de.footystats.tools.services.match;

import de.footystats.tools.services.domain.DomainDataService;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataMongoTest(includeFilters = {@Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {Converter.class})})
@AutoConfigureDataMongo
@Import({MatchServiceConfiguration.class})
class MatchServiceTest {

	@Autowired
	private MatchService matchService;

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private DomainDataService domainDataService;

	static Stream<Pair<String, String>> multipleSearchTerms() {
		return Stream.of(
			Pair.of("Yolo", "Yala"),
			Pair.of("yolo", "yala"),
			Pair.of("YOLO", "YALA"),
			Pair.of("yOlO", "yAlA")
		);
	}

	@BeforeEach
	void cleanUp() {
		matchRepository.deleteAll();
	}

	@Test
	void upsertTwoDifferentMatches() {
		var country = domainDataService.countryByName("germany");
		var now = LocalDateTime.now();
		var matchBuilder = Match.builder().country(country).dateUnix(now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()).dateGMT(now)
			.awayTeam("Team Away").homeTeam("Team Home");

		matchService.upsert(matchBuilder.build());

		matchBuilder.awayTeam("Team Away 2").homeTeam("Team Home 2");

		matchService.upsert(matchBuilder.build());

		List<Match> fantasiaMatches = matchRepository.findAll(Example.of(Match.builder().country(country).build()));
		Assertions.assertEquals(2, fantasiaMatches.size());
	}

	@Test
	void findByCustomQuery() {
		var country = domainDataService.countryByName("germany");
		var now = LocalDateTime.now();
		var matchBuilder = Match.builder().country(country).dateUnix(now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()).dateGMT(now)
			.awayTeam("Team Away").homeTeam("Team Home");

		matchService.upsert(matchBuilder.build());

		matchBuilder.awayTeam("Team Away 2").homeTeam("Team Home 2");

		matchService.upsert(matchBuilder.build());

		var page = PageRequest.of(0, 10);
		Page<Match> matches = matchService.find(
			MatchSearch.builder().countries(List.of(country.getCountryNameByFootystats())).pageable(page).build());
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
	void findAllByCustomQuery() {
		var result = matchService.find(MatchSearch.builder().pageable(PageRequest.of(0, 10)).build());
		Assertions.assertNotNull(result);
	}

	@ParameterizedTest
	@ValueSource(strings = {"Yala", "yala", "YALA", "yAlA", "Yolo", "yolo", "YOLO", "yOlO"})
	void findByTeamNameFullTextQuery(String searchTerm) {

		var country = domainDataService.countryByName("germany");
		var now = LocalDateTime.now();
		var matchBuilder = Match.builder().country(country).dateUnix(now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()).dateGMT(now)
			.awayTeam("Yolo").homeTeam("Yala");

		matchService.upsert(matchBuilder.build());

		var result = matchService.find(MatchSearch.builder().fullTextSearchTerms(List.of(searchTerm)).pageable(PageRequest.of(0, 10)).build());
		Assertions.assertNotNull(result);
		Assertions.assertEquals(1, result.getTotalElements());
	}

	@ParameterizedTest
	@MethodSource("multipleSearchTerms")
	void findByTeamNameFullTextQueryMultipleSearchTerms(Pair<String, String> searchTerms) {

		var country = domainDataService.countryByName("germany");
		var now = LocalDateTime.now();
		var matchBuilder = Match.builder().country(country).dateUnix(now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()).dateGMT(now)
			.awayTeam("Yolo").homeTeam("other");
		matchService.upsert(matchBuilder.build());

		// Create another match with home team "Yala" the way as before.
		matchBuilder = Match.builder().country(country).dateUnix(now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()).dateGMT(now)
			.awayTeam("other").homeTeam("Yala");
		matchService.upsert(matchBuilder.build());

		var result = matchService.find(
			MatchSearch.builder().fullTextSearchTerms(List.of(searchTerms.getLeft(), searchTerms.getRight())).pageable(PageRequest.of(0, 10))
				.build());
		Assertions.assertNotNull(result);
		Assertions.assertEquals(2, result.getTotalElements());
	}
}
