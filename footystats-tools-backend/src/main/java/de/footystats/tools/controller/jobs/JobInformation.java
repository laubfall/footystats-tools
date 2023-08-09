package de.footystats.tools.controller.jobs;

import de.footystats.tools.spring.batch.TrackItemCountExecutionListener;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;

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

	@Getter
	@Setter
	private Status job;

	@Getter
	@Setter
	private Integer progressInPercent;

	@Getter
	@Setter
	private Long itemsToProcess;

	@Getter
	@Setter
	private Long currentReadCount;

	@Getter
	@Setter
	private String jobName;

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
		ji.itemsToProcess = TrackItemCountExecutionListener.countOfItemsToProcess(jobExecution);
		ji.jobName = jobExecution.getJobInstance().getJobName();
		if (ji.itemsToProcess == 0) {
			ji.progressInPercent = 0;
			ji.currentReadCount = 0L;
			return ji;
		}

		Optional<StepExecution> first = jobExecution.getStepExecutions().stream().findFirst();
		var currentReadCount = 0L;
		if (first.isPresent()) {
			currentReadCount = first.get().getReadCount();
		}
		ji.currentReadCount = currentReadCount;
		ji.progressInPercent = (int) (((float) currentReadCount / (float) ji.itemsToProcess) * 100);

		switch (jobExecution.getStatus()) {
			case STARTING,
				STARTED,
				STOPPING -> ji.setJob(Status.RUNNING);
			case COMPLETED,
				STOPPED,
				FAILED,
				ABANDONED,
				UNKNOWN -> ji.setJob(Status.COMPLETED);
		}

		return ji;
	}
}
