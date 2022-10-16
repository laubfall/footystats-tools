package de.ludwig.footystats.tools.backend.services.prediction.quality;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PredictionQualityReportRepository extends MongoRepository<PredictionQualityReport, String> {
	PredictionQualityReport findTopByOrderByRevisionDesc();

	PredictionQualityReport findByRevision_Revision(PredictionQualityRevision revision);
}
