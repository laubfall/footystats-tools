package de.footystats.tools.controller.jobs;

import java.util.List;
import java.util.Objects;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/job")
public class JobInformationController {

	private final JobExplorer jobExplorer;

	public JobInformationController(JobExplorer jobExplorer) {
		this.jobExplorer = jobExplorer;
	}

	@GetMapping("/running/{jobName}")
	public JobInformation running(@PathVariable String jobName) {
		JobInstance lastJobInstance = jobExplorer.getLastJobInstance(jobName);
		if (lastJobInstance == null) {
			return null;
		}

		JobExecution lastJobExecution = jobExplorer.getLastJobExecution(lastJobInstance);
		return JobInformation.convert(lastJobExecution);
	}

	@GetMapping("/allrunning")
	public List<JobInformation> allRunning() {
		return jobExplorer.getJobNames().stream().map(jobExplorer::getLastJobInstance).map(jobExplorer::getLastJobExecution)
			.filter(Objects::nonNull)
			.map(JobInformation::convert).toList();
	}
}
