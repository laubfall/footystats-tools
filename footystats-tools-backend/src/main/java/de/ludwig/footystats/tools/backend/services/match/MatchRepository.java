package de.ludwig.footystats.tools.backend.services.match;

import de.ludwig.footystats.tools.backend.model.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;

public interface MatchRepository extends MongoRepository<Match, String>, PagingAndSortingRepository<Match, String> {
    Page<Match> findMatchesByCountryAndLeagueAndDateGMTBetween(String country, String league, LocalDateTime from, LocalDateTime until, PageRequest pageRequest);
}
