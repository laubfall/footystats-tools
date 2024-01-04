package de.footystats.tools.services.match;

import de.footystats.tools.services.domain.Country;
import de.footystats.tools.services.prediction.quality.PredictionQualityRevision;
import de.footystats.tools.services.stats.MatchStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for {@link Match} entities.
 * <p>
 * Don't get  confused about unused method, they are maybe used by spring batch jobs via reflection.
 */
public interface MatchRepository extends MongoRepository<Match, String> {

	Page<Match> findMatchesByStateAndRevision(MatchStatus state, PredictionQualityRevision revision, PageRequest pageRequest);

	Page<Match> findMatchesByStateAndRevision_RevisionIsNull(MatchStatus state, PageRequest pageRequest);

	Page<Match> findMatchesByState(MatchStatus state, PageRequest pageRequest);

	Match findByDateUnixAndCountryAndLeagueAndHomeTeamAndAwayTeam(Long dateUnix, Country country, String league, String homeTeam, String awayTeam);
}
