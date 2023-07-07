package de.footystats.tools.services.stats.batch;

import de.footystats.tools.services.stats.MatchStatsRepository;
import de.footystats.tools.spring.batch.TrackItemCountExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class MatchStatsToProcessBeforeStart extends TrackItemCountExecutionListener {

	private final MatchStatsRepository matchStatsRepository;

	public MatchStatsToProcessBeforeStart(MatchStatsRepository matchStatsRepository) {
		this.matchStatsRepository = matchStatsRepository;
	}

	@Override
	public Long count() {
		return matchStatsRepository.count();
	}
}
