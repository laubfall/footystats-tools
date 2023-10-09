package de.footystats.tools.services.stats;

import de.footystats.tools.services.domain.Country;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchStatsRepository extends MongoRepository<MatchStats, String> {

	List<MatchStats> findByCountry(Country country);

	// public async matchesByDay(day: Date)
	List<MatchStats> findMatchStatsByDateUnixBetween(long start, long end);

	Page<MatchStats> findMatchStatsByDateUnixBetween(long start, long end, Pageable pageable);

	// public async matchesByFilter
	Page<MatchStats> findMatchStatsByCountryAndLeagueAndDateUnixBetween(Country country, String league, long start,
		long end, Pageable pageable);
}
