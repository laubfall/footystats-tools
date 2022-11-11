package de.ludwig.footystats.tools.backend.services.stats;

import de.ludwig.footystats.tools.backend.FootystatsProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;
import java.util.List;

@ActiveProfiles("test")
@DataMongoTest
@AutoConfigureDataMongo
@Import({FootystatsProperties.class})
public class TeamStatsRepositoryTest {

	@Autowired
	private TeamStatsRepository teamStatsRepository;

	@Test
	public void findByCountryTeamAndSeason(){
		var teamStats = new TeamStats();
		teamStats.setCountry("Somecountry");
		teamStats.setSeason("2020/2021");
		teamStats.setTeamName("Some Team");
		teamStats.setCommonName("Someteam");

		teamStatsRepository.save(teamStats);

		Collection<TeamStats> result = teamStatsRepository.findTeamStatsByCountryEqualsAndSeasonInAndTeamNameEquals("Somecountry", List.of("2020/2021"), "Some Team");
		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isEmpty());
	}
}
