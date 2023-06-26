package de.footystats.tools.services.prediction.quality.batch;

import de.footystats.tools.services.prediction.quality.PredictionQualityService;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class ComputeJobListener implements JobExecutionListener {

	private final PredictionQualityService predictionQualityService;

	public ComputeJobListener(PredictionQualityService predictionQualityService) {
		this.predictionQualityService = predictionQualityService;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(BatchStatus.COMPLETED.equals(jobExecution.getStatus())) {
			predictionQualityService.markMatchesWithRevisionOnCompute();
		}
	}
}
