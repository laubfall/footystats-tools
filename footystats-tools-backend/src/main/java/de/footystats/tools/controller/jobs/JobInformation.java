package de.footystats.tools.controller.jobs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.batch.core.JobExecution;

/**
 * Classic java bean used to transfer via rest.
 */
public class JobInformation {

	/**
	 * A Spring batch job execution id.
	 */
	@Getter
	@Setter
	private Long jobId;

	/**
	 * Convert a job execution instance to an instance of this type.
	 *
	 * @param jobExecution Convert this.
	 * @return null if jobExecution is null, otherwise an instance of type {@link JobInformation}.
	 */
	public static JobInformation convert(JobExecution jobExecution) {
		if (jobExecution == null) {
			return null;
		}

		var ji = new JobInformation();
		ji.setJobId(jobExecution.getJobInstance().getInstanceId());
		return ji;
	}
}
