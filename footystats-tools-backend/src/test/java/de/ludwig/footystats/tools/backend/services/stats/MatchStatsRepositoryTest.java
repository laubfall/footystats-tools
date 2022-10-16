package de.ludwig.footystats.tools.backend.services.stats;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@ActiveProfiles("test")
@DataMongoTest
@ContextConfiguration(classes = {Configuration.class})
@AutoConfigureDataMongo
public class MatchStatsRepositoryTest {

    @Autowired
    private MatchStatsRepository matchStatsRepository;

    @Test
    public void findByDate_unix(){
        var now = LocalDateTime.of(2022, Month.SEPTEMBER, 16, 0, 0).atZone(ZoneId.systemDefault()).toInstant();
        var matchStats = MatchStats.builder().dateUnix(now.toEpochMilli()).country("Germany").league("Bundesliga").build();
        matchStatsRepository.insert(matchStats);

        var result = matchStatsRepository.findMatchStatsByDateUnixBetween(now.minus(1, ChronoUnit.DAYS).toEpochMilli(), now.plus(1, ChronoUnit.DAYS).toEpochMilli());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());

        result = matchStatsRepository.findMatchStatsByDateUnixBetween(now.plus(1, ChronoUnit.DAYS).toEpochMilli(), now.plus(2, ChronoUnit.DAYS).toEpochMilli());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());


        Page<MatchStats> paged = matchStatsRepository.findMatchStatsByDateUnixBetween(now.minus(1, ChronoUnit.DAYS).toEpochMilli(), now.plus(2, ChronoUnit.DAYS).toEpochMilli(), PageRequest.of(0, 3));
        Assertions.assertEquals(1, paged.getTotalPages());
    }

    @Test
    public void findByCountryLeagueAndDateUnix(){
        var now = new Date();
        var matchStats = MatchStats.builder().dateUnix(now.getTime()).country("Germany").league("2. Bundesliga").build();
        matchStatsRepository.insert(matchStats);

        Page<MatchStats> paged = matchStatsRepository.findMatchStatsByCountryAndLeagueAndDateUnixBetween("Germany", "2. Bundesliga", DateUtils.addDays(now, -1).getTime(), DateUtils.addDays(now, 2).getTime(), PageRequest.of(0, 10));
        Assertions.assertEquals(1, paged.getTotalPages());
    }
}
