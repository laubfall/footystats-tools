package de.ludwig.footystats.tools.backend.services.prediction.quality;

import de.ludwig.footystats.tools.backend.services.prediction.BetPredictionQuality;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@JsonComponent
@Document
public class PredictionQualityReport {
	@Id
	@Getter
	@Setter
	private String _id; // Mongo document id. Required in order to be able to delete this.

	@Indexed(unique = true)
	@Getter
	@Setter
	private PredictionQualityRevision revision;
	@Getter
	@Setter
	private List<BetPredictionQuality> measurements;

	public PredictionQualityReport(PredictionQualityRevision revision, List<BetPredictionQuality> measurements) {
		this.revision = revision;
		this.measurements = measurements;
	}
}
