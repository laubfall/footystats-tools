package de.ludwig.footystats.tools.backend.services.stats;

import de.ludwig.footystats.tools.backend.FootystatsProperties;
import de.ludwig.footystats.tools.backend.services.csv.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collection;

@ActiveProfiles("test")
@DataMongoTest
@AutoConfigureDataMongo
@Import({Configuration.class})
@ContextConfiguration(classes = {TeamStatsServiceConfiguration.class})
public class TeamStatsServiceTest {

	@Autowired
	private TeamStatsRepository teamStatsRepository;

	@Autowired
	private TeamStatsService teamStatsService;

	@Test
	public void latestThree() {
		var teamStats = new TeamStats();
		teamStats.setCountry("Somecountry");
		teamStats.setSeason("2020/2021");
		teamStats.setTeamName("Some Team");
		teamStats.setCommonName("Someteam");

		teamStatsRepository.save(teamStats);

		Collection<TeamStats> result = teamStatsService.latestThree("Some Team", "Somecountry", 2021);
		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isEmpty());
	}

	@Test
	public void aggregate(){
		Collection<TeamStats> teamStats = teamStatsService.readTeamStats(getClass().getResourceAsStream("one-team-different-season-stats.csv"));
		Assertions.assertFalse(teamStats.isEmpty());
		Assertions.assertEquals(2, teamStats.size());

		TeamStats aggregate = teamStatsService.aggregate(teamStats);
		Assertions.assertEquals(7, aggregate.getWins_away());
		Assertions.assertEquals(1.6, aggregate.getPoints_per_game_home());
		Assertions.assertEquals(1.55, aggregate.getPoints_per_game());
		Assertions.assertEquals(45, aggregate.getGoals_scored());
		Assertions.assertNotNull(aggregate);
	}
}
