package de.ludwig.footystats.tools.backend.services.stats;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LeagueMatchStatsRepository extends MongoRepository<LeagueMatchStats, String> {
	List<LeagueMatchStats> findLeagueMatchStatsByHomeTeamNameAndAwayTeamName(String homeTeamName, String awayTeamName);
}
