package de.footystats.tools.services.stats.batch;

import de.footystats.tools.services.match.Match;
import de.footystats.tools.services.match.MatchService;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class MatchWriter implements ItemWriter<Match> {

	private final MatchService matchService;

	public MatchWriter(MatchService matchService) {
		this.matchService = matchService;
	}

	@Override
	public void write(Chunk<? extends Match> chunk) {
		chunk.getItems().forEach(matchService::upsert);
	}
}
