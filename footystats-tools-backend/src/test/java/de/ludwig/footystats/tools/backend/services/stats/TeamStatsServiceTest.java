package de.ludwig.footystats.tools.backend.services.stats;

import de.ludwig.footystats.tools.backend.FootystatsProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collection;

@ActiveProfiles("test")
@DataMongoTest
@AutoConfigureDataMongo
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
}
