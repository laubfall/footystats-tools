package de.ludwig.footystats.tools.backend.services.stats;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@ActiveProfiles("test")
@DataMongoTest
@AutoConfigureDataMongo
@ContextConfiguration(classes = {MatchStatsServiceConfiguration.class})
public class MatchStatsServiceTest {

    @Autowired
    private MatchStatsService matchStatsService;

    @Autowired
    private MatchStatsRepository matchStatsRepository;

    @BeforeEach
    private void cleanup(){
        matchStatsRepository.deleteAll();
    }

    @Test
    public void upsert() {

        var time = System.currentTimeMillis();
        var builder = MatchStats.builder().country("Germany").league("Bundesliga").dateUnix(time);
        var matchStats = builder.build();

        matchStatsService.upsert(matchStats);

        var persistedMatchStats = matchStatsRepository.findByCountry("Germany");
        Assertions.assertNotNull(persistedMatchStats);
        Assertions.assertEquals(1, persistedMatchStats.size());

        matchStats = builder.matchFootyStatsURL("test").build();
        matchStatsService.upsert(matchStats);

        persistedMatchStats = matchStatsRepository.findByCountry("Germany");
        Assertions.assertNotNull(persistedMatchStats);
        Assertions.assertEquals(1, persistedMatchStats.size());

        var loadedMatchStats = persistedMatchStats.get(0);
        Assertions.assertEquals("test", loadedMatchStats.getMatchFootyStatsURL());
    }

	@Test
	public void upsertTwoDifferentMatchStats(){
		var country = "Fantasia";
		var localDateTime = LocalDateTime.of(2022, 9, 1, 12, 30);
		var builder = MatchStats.builder().country(country).league("Bundesliga").dateUnix(localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()).dateGmt(localDateTime).homeTeam("Home Team").awayTeam("Away Team");
		var matchStats = builder.build();

		matchStatsService.upsert(matchStats);

		builder.homeTeam("Home Team 2").awayTeam("Away Team 2");
		matchStats = builder.build();
		matchStatsService.upsert(matchStats);

		List<MatchStats> byCountry = matchStatsRepository.findByCountry(country);
		Assertions.assertEquals(2, byCountry.size());
	}

    @Test
    public void importMatchStats(){
        var time = System.currentTimeMillis();
        var builder = MatchStats.builder().country("France").league("La Ligue").dateUnix(time).awayTeam("Team 1").homeTeam("Team 2").matchStatus(MatchStatus.incomplete);
        var matchStats = builder.build();

        matchStatsService.importMatchStats(matchStats);
        List<MatchStats> persistedMatchStats = matchStatsRepository.findByCountry("France");
    }
}
