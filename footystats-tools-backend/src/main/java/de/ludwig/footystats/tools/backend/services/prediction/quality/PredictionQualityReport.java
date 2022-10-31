package de.ludwig.footystats.tools.backend.services.prediction.quality;

import de.ludwig.footystats.tools.backend.services.prediction.BetPredictionQuality;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@JsonComponent
@Document
public class PredictionQualityReport {
	@Indexed(unique = true)
	@Getter
	@Setter
	private PredictionQualityRevision revision;
	@Getter
	@Setter
	private List<BetPredictionQuality> measurements;
}
