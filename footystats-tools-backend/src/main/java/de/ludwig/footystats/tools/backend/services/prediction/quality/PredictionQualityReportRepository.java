package de.ludwig.footystats.tools.backend.services.prediction.quality;

import org.springframework.data.mongodb.repository.MongoRepository;

@Deprecated
public interface PredictionQualityReportRepository extends MongoRepository<PredictionQualityReport, String> {
	PredictionQualityReport findTopByOrderByRevisionDesc();

	PredictionQualityReport findByRevision(PredictionQualityRevision revision);
}
