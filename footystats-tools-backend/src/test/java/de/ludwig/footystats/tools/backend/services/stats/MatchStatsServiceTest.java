package de.ludwig.footystats.tools.backend.services.stats;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@ActiveProfiles("test")
@DataMongoTest
@AutoConfigureDataMongo
@ContextConfiguration(classes = {Configuration.class})
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
    public void importMatchStats(){
        var time = System.currentTimeMillis();
        var builder = MatchStats.builder().country("France").league("La Ligue").dateUnix(time).awayTeam("Team 1").homeTeam("Team 2").matchStatus(MatchStatus.incomplete);
        var matchStats = builder.build();

        matchStatsService.importMatchStats(matchStats);
        List<MatchStats> persistedMatchStats = matchStatsRepository.findByCountry("France");
    }
}
