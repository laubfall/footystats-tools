package de.footystats.tools.services.stats;

import de.footystats.tools.services.domain.Country;
import java.util.Collection;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamStatsRepository extends MongoRepository<TeamStats, String> {

	Collection<TeamStats> findTeamStatsByCountryEqualsAndSeasonInAndTeamNameEquals(Country country, List<String> season, String teamName);
}
