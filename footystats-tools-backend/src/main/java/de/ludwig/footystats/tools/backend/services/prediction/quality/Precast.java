package de.ludwig.footystats.tools.backend.services.prediction.quality;

import lombok.Getter;
import lombok.Setter;

public class Precast {
	@Getter
	@Setter
	private PredictionQualityRevision revision;

	@Getter
	@Setter
	private Integer predictionsToAssess;
}
