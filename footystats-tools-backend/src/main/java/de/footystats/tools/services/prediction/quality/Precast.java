package de.footystats.tools.services.prediction.quality;

import lombok.Getter;
import lombok.Setter;

public class Precast {
	@Getter
	@Setter
	private PredictionQualityRevision revision;

	@Getter
	@Setter
	private Long predictionsToAssess;
}
