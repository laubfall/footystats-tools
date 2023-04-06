package de.ludwig.footystats.tools.backend.services.prediction.quality;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;

public class Precast {
	@Getter
	@Setter
	private PredictionQualityRevision revision;

	@Getter
	@Setter
	private Long predictionsToAssess;
}
