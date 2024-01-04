package de.footystats.tools.services.stats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.footystats.tools.services.domain.DomainDataService;
import de.footystats.tools.services.match.Match;
import de.footystats.tools.services.match.MatchRepository;
import de.footystats.tools.services.prediction.quality.PredictionQualityRevision;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@DataMongoTest
@AutoConfigureDataMongo
@ContextConfiguration(classes = {MatchStatsServiceConfiguration.class})
class MatchStatsServiceTest {

	@Autowired
	private MatchStatsService matchStatsService;

	@Autowired
	private MatchStatsRepository matchStatsRepository;

	@Autowired
	private DomainDataService domainDataService;

	@Autowired
	private MatchRepository matchRepository;

	@BeforeEach
	public void cleanup() {
		matchStatsRepository.deleteAll();
	}

	@Test
	void upsert() {
		var germany = domainDataService.countryByNormalizedName("Germany");
		var time = System.currentTimeMillis();
		var builder = MatchStats.builder().country(germany).league("Bundesliga").dateUnix(time);
		var matchStats = builder.build();

		matchStatsService.upsert(matchStats);

		var persistedMatchStats = matchStatsRepository.findByCountry(germany);
		assertNotNull(persistedMatchStats);
		assertEquals(1, persistedMatchStats.size());

		matchStats = builder.matchFootyStatsURL("test").build();
		matchStatsService.upsert(matchStats);

		persistedMatchStats = matchStatsRepository.findByCountry(germany);
		assertNotNull(persistedMatchStats);
		assertEquals(1, persistedMatchStats.size());

		var loadedMatchStats = persistedMatchStats.get(0);
		assertEquals("test", loadedMatchStats.getMatchFootyStatsURL());
	}

	@Test
	void upsertTwoDifferentMatchStats() {
		var country = domainDataService.countryByNormalizedName("Germany");
		var localDateTime = LocalDateTime.of(2022, 9, 1, 12, 30);
		var builder = MatchStats.builder().country(country).league("Bundesliga")
			.dateUnix(localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()).dateGmt(localDateTime).homeTeam("Home Team")
			.awayTeam("Away Team");
		var matchStats = builder.build();

		matchStatsService.upsert(matchStats);

		builder.homeTeam("Home Team 2").awayTeam("Away Team 2");
		matchStats = builder.build();
		matchStatsService.upsert(matchStats);

		List<MatchStats> byCountry = matchStatsRepository.findByCountry(country);
		assertEquals(2, byCountry.size());
	}

	@Test
	void importMatchStats() {
		var time = System.currentTimeMillis();
		var france = domainDataService.countryByNormalizedName("France");
		var builder = MatchStats.builder().country(france).league("La Ligue").dateUnix(time).awayTeam("Team 1").homeTeam("Team 2")
			.dateGmt(LocalDateTime.now())
			.matchStatus(MatchStatus.incomplete);
		var matchStats = builder.build();

		matchStatsService.importMatchStats(matchStats);
		List<MatchStats> persistedMatchStats = matchStatsRepository.findByCountry(france);
		assertNotNull(persistedMatchStats);
		assertEquals(1, persistedMatchStats.size());
	}

	@Test
	void importMatchStatsTwice_expectMatchRevisionIsNotNull() {
		var time = System.currentTimeMillis();
		var france = domainDataService.countryByNormalizedName("France");
		var builder = MatchStats.builder().country(france).league("La Ligue").dateUnix(time).awayTeam("Team 1").homeTeam("Team 2")
			.dateGmt(LocalDateTime.now())
			.matchStatus(MatchStatus.incomplete);
		var matchStats = builder.build();

		matchStatsService.importMatchStats(matchStats);
		List<MatchStats> persistedMatchStats = matchStatsRepository.findByCountry(france);
		assertNotNull(persistedMatchStats);
		assertEquals(1, persistedMatchStats.size());

		Optional<Match> createdMatch = matchRepository.findOne(Example.of(Match.builder().awayTeam("Team 1").build()));
		assertTrue(createdMatch.isPresent());

		// Some fake revision and then the second import to ensure that the revision is still present
		Match match = createdMatch.get();
		match.setRevision(new PredictionQualityRevision(1));
		matchRepository.save(match);

		matchStatsService.importMatchStats(matchStats);
		createdMatch = matchRepository.findOne(Example.of(Match.builder().awayTeam("Team 1").build()));
		assertTrue(createdMatch.isPresent());
		assertNotNull(createdMatch.get().getRevision());
		assertEquals(createdMatch.get().getRevision().getRevision(), 1);
	}

	@Test
	void ignoreEsportsMatchIfConfigured() {
		var time = System.currentTimeMillis();
		var esports = domainDataService.countryByName("esports");
		var builder = MatchStats.builder().country(esports).league("La Ligue").dateUnix(time).awayTeam("Team 1").homeTeam("Team 2")
			.dateGmt(LocalDateTime.now())
			.matchStatus(MatchStatus.incomplete);
		var matchStats = builder.build();

		matchStatsService.importMatchStats(matchStats);
		List<MatchStats> persistedMatchStats = matchStatsRepository.findByCountry(esports);
		assertTrue(persistedMatchStats.isEmpty());
	}

	@Deprecated // See MatchStatsService.reimportMatchStats
	@Test
	void reimportMatchStats() {
		var denmark = domainDataService.countryByNormalizedName("Denmark");
		var time = System.currentTimeMillis();
		var builder = MatchStats.builder().country(denmark).league("La Ligue").dateUnix(time).awayTeam("Team 1").homeTeam("Team 2")
			.dateGmt(LocalDateTime.now())
			.resultAwayTeamGoals(1).resultHomeTeamGoals(1).matchStatus(MatchStatus.complete).bTTSAverage(50);
		var matchStats = builder.build();

		matchStatsService.importMatchStats(matchStats);
		var denmarkMatches = matchStatsRepository.findByCountry(denmark);
		assertNotNull(denmarkMatches);
		assertEquals(1, denmarkMatches.size());
		var match = denmarkMatches.get(0);
		assertEquals(50, match.getBTTSAverage());

		builder = builder.bTTSAverage(60);
		matchStats = builder.build();
		matchStatsService.upsert(matchStats);

		matchStatsService.reimportMatchStats();

		denmarkMatches = matchStatsRepository.findByCountry(denmark);
		assertNotNull(denmarkMatches);
		assertEquals(1, denmarkMatches.size());
		match = denmarkMatches.get(0);
		assertEquals(60, match.getBTTSAverage());
	}
}
