package de.ludwig.footystats.tools.backend.services.match;

import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityRevision;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface MatchRepository extends MongoRepository<Match, String> {
	Page<Match> findMatchesByCountryAndLeagueAndDateGMTBetween(String country, String league, LocalDateTime from,
			LocalDateTime until, PageRequest pageRequest);

	Page<Match> findMatchesByCountryInAndLeagueInAndDateGMTBetween(List<String> countries, List<String> leagues, LocalDateTime from, LocalDateTime until, PageRequest pageRequest);

	Page<Match> findMatchesByCountryInAndDateGMTBetween(List<String> countries, LocalDateTime from, LocalDateTime until, PageRequest pageRequest);

	Page<Match> findMatchesByLeagueInAndDateGMTBetween(List<String> leagues, LocalDateTime from, LocalDateTime until, PageRequest pageRequest);

	Page<Match> findMatchesByDateGMTBetween(LocalDateTime from, LocalDateTime until, PageRequest pageRequest);

	Page<Match> findMatchesByCountryIn(List<String> countries, PageRequest pageRequest);

	Page<Match> findMatchesByCountryInAndLeagueIn(List<String> countries, List<String> leagues, PageRequest pageRequest);

	Collection<Match> findMatchesByRevision(PredictionQualityRevision revision);

	Collection<Match> findMatchesByRevision_RevisionIsNull();

}
