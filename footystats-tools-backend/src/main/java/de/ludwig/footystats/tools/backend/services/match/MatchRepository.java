package de.ludwig.footystats.tools.backend.services.match;

import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityRevision;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchRepository extends MongoRepository<Match, String> {

	Page<Match> findMatchesByStateAndRevision(MatchStatus state, PredictionQualityRevision revision, PageRequest pageRequest);

	Page<Match> findMatchesByStateAndRevision_RevisionIsNull(MatchStatus state, PageRequest pageRequest);

	Page<Match> findMatchesByState(MatchStatus state, PageRequest pageRequest);

}
