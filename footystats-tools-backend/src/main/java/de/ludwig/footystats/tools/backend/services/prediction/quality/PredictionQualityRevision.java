package de.ludwig.footystats.tools.backend.services.prediction.quality;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;

@AllArgsConstructor
@NoArgsConstructor
@JsonComponent
public class PredictionQualityRevision {
	public static final int NO_REVISION_SO_FAR = -1;
	@Getter
	@Setter
	private Integer revision;

	public static final PredictionQualityRevision NO_REVISION = new PredictionQualityRevision(NO_REVISION_SO_FAR);

}
