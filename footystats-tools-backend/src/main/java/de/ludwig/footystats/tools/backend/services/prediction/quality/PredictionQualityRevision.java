package de.ludwig.footystats.tools.backend.services.prediction.quality;

import lombok.*;
import org.springframework.boot.jackson.JsonComponent;

@AllArgsConstructor
@NoArgsConstructor
public class PredictionQualityRevision {
	public static final int NO_REVISION_SO_FAR = -1;
	@Getter
	@Setter
	private Integer revision;

	public static final PredictionQualityRevision NO_REVISION = new PredictionQualityRevision(NO_REVISION_SO_FAR);

	public static final PredictionQualityRevision IN_RECOMPUTATION = new PredictionQualityRevision(-2);
}
