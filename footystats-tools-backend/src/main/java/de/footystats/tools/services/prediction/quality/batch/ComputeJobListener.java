package de.footystats.tools.services.prediction.quality.batch;

import de.footystats.tools.services.match.Match;
import de.footystats.tools.services.match.MatchRepository;
import de.footystats.tools.services.prediction.quality.PredictionQualityService;
import de.footystats.tools.services.stats.MatchStatus;
import de.footystats.tools.spring.batch.TrackItemCountExecutionListener;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

@Component
public class ComputeJobListener extends TrackItemCountExecutionListener implements JobExecutionListener {

	private final PredictionQualityService predictionQualityService;

	private final MatchRepository matchRepository;

	public ComputeJobListener(PredictionQualityService predictionQualityService, MatchRepository matchRepository) {
		this.predictionQualityService = predictionQualityService;
		this.matchRepository = matchRepository;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (BatchStatus.COMPLETED.equals(jobExecution.getStatus())) {
			predictionQualityService.markMatchesWithRevisionOnCompute();
		}
	}

	@Override
	public Long count() {
		return matchRepository.count(Example.of(Match.builder().state(MatchStatus.complete).revision(null).build()));
	}
}
