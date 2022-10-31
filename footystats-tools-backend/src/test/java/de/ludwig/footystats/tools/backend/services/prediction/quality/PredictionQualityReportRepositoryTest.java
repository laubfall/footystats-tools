package de.ludwig.footystats.tools.backend.services.prediction.quality;

import de.ludwig.footystats.tools.backend.services.prediction.PredictionServiceConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;

@ActiveProfiles("test")
@DataMongoTest
@ContextConfiguration(classes = {PredictionServiceConfiguration.class})
@AutoConfigureDataMongo
public class PredictionQualityReportRepositoryTest {

	@Autowired
	private PredictionQualityReportRepository predictionQualityReportRepository;

	@Test
	public void findByRevision() {
		var revision = new PredictionQualityRevision(0);
		var report = new PredictionQualityReport(revision, Collections.emptyList());
		predictionQualityReportRepository.insert(report);

		var searchedReport = predictionQualityReportRepository.findByRevision(revision);
		Assertions.assertNotNull(searchedReport);
	}

	@Test
	public void revisionHasToBeUnique() {
		final var revision = new PredictionQualityRevision(0);
		var report = new PredictionQualityReport(revision, Collections.emptyList());
		predictionQualityReportRepository.insert(report);

		Assertions.assertThrows(RuntimeException.class, () -> predictionQualityReportRepository.insert(new PredictionQualityReport(revision, Collections.emptyList())));
	}
}
