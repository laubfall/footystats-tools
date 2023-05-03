package de.ludwig.footystats.tools.backend.services.prediction.quality;

import org.springframework.batch.core.JobExecution;

public interface IBetPredictionQualityJobService {
	JobExecution startJob();

	JobExecution running();
}
