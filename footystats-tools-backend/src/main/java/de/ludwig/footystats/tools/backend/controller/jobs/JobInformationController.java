package de.ludwig.footystats.tools.backend.controller.jobs;

import de.ludwig.footystats.tools.backend.services.prediction.quality.BetPredictionQualityJobService;
import org.springframework.batch.core.JobExecution;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/job")
public class JobInformationController {
	private BetPredictionQualityJobService betPredictionQualityJobService;

	public JobInformationController(BetPredictionQualityJobService betPredictionQualityJobService) {
		this.betPredictionQualityJobService = betPredictionQualityJobService;
	}

	@GetMapping("/running")
	public JobInformation running(@PathVariable String jobName) {
		// TODO replace with a generic service.
		final JobExecution jobExecution = betPredictionQualityJobService.running();
		if(jobExecution == null){
			return null;
		}

		var ji = new JobInformation();
		ji.setJobId(jobExecution.getJobInstance().getInstanceId());
		return ji;
	}
}
