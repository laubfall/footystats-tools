package de.footystats.tools.services.stats.batch;

import de.footystats.tools.services.match.Match;
import de.footystats.tools.services.match.MatchService;
import de.footystats.tools.services.stats.MatchStats;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class MatchStatsItemProcessor implements ItemProcessor<MatchStats, Match> {

	private final MatchService matchService;

	public MatchStatsItemProcessor(MatchService matchService) {
		this.matchService = matchService;
	}

	@Override
	public Match process(MatchStats item) throws Exception {
		return matchService.convert(item);
	}
}
