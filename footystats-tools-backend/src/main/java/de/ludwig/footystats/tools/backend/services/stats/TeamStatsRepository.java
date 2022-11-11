package de.ludwig.footystats.tools.backend.services.stats;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface TeamStatsRepository extends MongoRepository<TeamStats, String> {
	Collection<TeamStats> findTeamStatsByCountryEqualsAndSeasonInAndTeamNameEquals(String country, List<String> season, String teamName);
}
