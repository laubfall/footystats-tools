package de.ludwig.footystats.tools.backend.controller.jobs;

import lombok.Getter;
import lombok.Setter;

public class JobInformation {
	/**
	 * A Spring batch job execution id.
	 */
	@Getter
	@Setter
	private Long jobId;
}
