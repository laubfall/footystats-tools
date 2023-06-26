package de.footystats.tools.services.match;

import de.footystats.tools.services.stats.MatchStatus;
import de.footystats.tools.services.prediction.quality.PredictionQualityRevision;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchRepository extends MongoRepository<Match, String> {

	Page<Match> findMatchesByStateAndRevision(MatchStatus state, PredictionQualityRevision revision, PageRequest pageRequest);

	Page<Match> findMatchesByStateAndRevision_RevisionIsNull(MatchStatus state, PageRequest pageRequest);

	Page<Match> findMatchesByState(MatchStatus state, PageRequest pageRequest);

}
