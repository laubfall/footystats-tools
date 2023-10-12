package de.footystats.tools.services.stats;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.services.domain.Country;
import de.footystats.tools.services.domain.DomainDataService;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataMongoTest
@AutoConfigureDataMongo
@Import({FootystatsProperties.class, DomainDataService.class})
class TeamStatsRepositoryTest {

	@Autowired
	private TeamStatsRepository teamStatsRepository;

	@Autowired
	private DomainDataService domainDataService;

	@Test
	void findByCountryTeamAndSeason() {
		Country germany = domainDataService.countryByNormalizedName("Germany");
		var teamStats = new TeamStats();
		teamStats.setCountry(germany);
		teamStats.setSeason("2020/2021");
		teamStats.setTeamName("Some Team");
		teamStats.setCommonName("Someteam");

		teamStatsRepository.save(teamStats);

		Collection<TeamStats> result = teamStatsRepository.findTeamStatsByCountryEqualsAndSeasonInAndTeamNameEquals(germany,
			List.of("2020/2021"), "Some Team");
		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isEmpty());
	}
}
