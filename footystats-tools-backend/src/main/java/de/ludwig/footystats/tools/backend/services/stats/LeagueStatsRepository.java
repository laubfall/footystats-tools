package de.ludwig.footystats.tools.backend.services.stats;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LeagueStatsRepository extends MongoRepository<LeagueStats, String> {
	LeagueStats findByNameAndSeason(String name, String season);
}
