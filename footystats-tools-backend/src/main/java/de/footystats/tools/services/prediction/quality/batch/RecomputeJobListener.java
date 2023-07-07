package de.footystats.tools.services.prediction.quality.batch;

import de.footystats.tools.services.match.Match;
import de.footystats.tools.services.match.MatchRepository;
import de.footystats.tools.services.prediction.quality.BetPredictionQualityRepository;
import de.footystats.tools.services.prediction.quality.PredictionQualityRevision;
import de.footystats.tools.services.prediction.quality.PredictionQualityService;
import de.footystats.tools.services.stats.MatchStatus;
import de.footystats.tools.spring.batch.TrackItemCountExecutionListener;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

@Component
public class RecomputeJobListener extends TrackItemCountExecutionListener implements JobExecutionListener {

	private final PredictionQualityService predictionQualityService;

	private final BetPredictionQualityRepository betPredictionQualityRepository;

	private final MatchRepository matchRepository;

	public RecomputeJobListener(PredictionQualityService predictionQualityService, BetPredictionQualityRepository betPredictionQualityRepository,
		MatchRepository matchRepository) {
		this.predictionQualityService = predictionQualityService;
		this.betPredictionQualityRepository = betPredictionQualityRepository;
		this.matchRepository = matchRepository;
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		super.beforeJob(jobExecution);
		// Cleanup old recomputations that were not lifted to a new revision.
		betPredictionQualityRepository.deleteByRevisionEquals(PredictionQualityRevision.IN_RECOMPUTATION);
	}

	@Override
	public Long count() {
		return matchRepository.count(Example.of(Match.builder().state(MatchStatus.complete).build()));
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (BatchStatus.COMPLETED.equals(jobExecution.getStatus())) {
			predictionQualityService.revisionUpdateOnRecompute();
		}
	}
}
