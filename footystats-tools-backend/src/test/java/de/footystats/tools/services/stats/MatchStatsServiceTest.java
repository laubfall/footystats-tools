package de.footystats.tools.services.stats;

import de.footystats.tools.services.domain.DomainDataService;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
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
		Assertions.assertNotNull(persistedMatchStats);
		Assertions.assertEquals(1, persistedMatchStats.size());

		matchStats = builder.matchFootyStatsURL("test").build();
		matchStatsService.upsert(matchStats);

		persistedMatchStats = matchStatsRepository.findByCountry(germany);
		Assertions.assertNotNull(persistedMatchStats);
		Assertions.assertEquals(1, persistedMatchStats.size());

		var loadedMatchStats = persistedMatchStats.get(0);
		Assertions.assertEquals("test", loadedMatchStats.getMatchFootyStatsURL());
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
		Assertions.assertEquals(2, byCountry.size());
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
		Assertions.assertNotNull(persistedMatchStats);
		Assertions.assertEquals(1, persistedMatchStats.size());
	}

	@Deprecated // See MatchStatsService
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
		Assertions.assertNotNull(denmarkMatches);
		Assertions.assertEquals(1, denmarkMatches.size());
		var match = denmarkMatches.get(0);
		Assertions.assertEquals(50, match.getBTTSAverage());

		builder = builder.bTTSAverage(60);
		matchStats = builder.build();
		matchStatsService.upsert(matchStats);

		matchStatsService.reimportMatchStats();

		denmarkMatches = matchStatsRepository.findByCountry(denmark);
		Assertions.assertNotNull(denmarkMatches);
		Assertions.assertEquals(1, denmarkMatches.size());
		match = denmarkMatches.get(0);
		Assertions.assertEquals(60, match.getBTTSAverage());
	}
}
