package de.ludwig.footystats.tools.backend.services.match;

import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityRevision;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.Collection;

public interface MatchRepository extends MongoRepository<Match, String>, PagingAndSortingRepository<Match, String> {
    Page<Match> findMatchesByCountryAndLeagueAndDateGMTBetween(String country, String league, LocalDateTime from, LocalDateTime until, PageRequest pageRequest);

	Collection<Match> findMatchesByRevision_Revision(PredictionQualityRevision revision);
}
