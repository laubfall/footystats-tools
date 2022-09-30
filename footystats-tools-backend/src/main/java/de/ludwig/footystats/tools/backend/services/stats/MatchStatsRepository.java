package de.ludwig.footystats.tools.backend.services.stats;

import de.ludwig.footystats.tools.backend.model.MatchStats;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

public interface MatchStatsRepository extends MongoRepository<MatchStats, String>, PagingAndSortingRepository<MatchStats, String> {
    List<MatchStats> findByCountry(String country);

    // public async matchesByDay(day: Date)
    List<MatchStats> findMatchStatsByDateUnixBetween(long start, long end);

    Page<MatchStats> findMatchStatsByDateUnixBetween(long start, long end, Pageable pageable);

    //public async matchesByFilter
    Page<MatchStats> findMatchStatsByCountryAndLeagueAndDateUnixBetween(String country, String league, long start, long end, Pageable pageable);
}
