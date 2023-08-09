package de.footystats.tools.services.prediction.quality.batch;

import de.footystats.tools.services.match.Match;
import de.footystats.tools.services.match.MatchRepository;
import de.footystats.tools.services.stats.MatchStatus;
import de.footystats.tools.spring.batch.TrackItemCountExecutionListener;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

@Component
public class ComputeStepListener extends TrackItemCountExecutionListener {

	private final MatchRepository matchRepository;

	public ComputeStepListener(MatchRepository matchRepository) {
		this.matchRepository = matchRepository;
	}

	@Override
	public Long count() {
		return matchRepository.count(Example.of(Match.builder().state(MatchStatus.complete).build()));
	}
}
