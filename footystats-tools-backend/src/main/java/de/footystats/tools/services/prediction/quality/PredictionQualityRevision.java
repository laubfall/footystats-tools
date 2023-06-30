package de.footystats.tools.services.prediction.quality;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Domain object representing a specific version for a {@link BetPredictionQuality}.
 */
@AllArgsConstructor
@NoArgsConstructor
public class PredictionQualityRevision {
	public static final int NO_REVISION_SO_FAR = -1;

	/**
	 * The version number.
	 */
	@Getter
	@Setter
	private Integer revision;

	public static final PredictionQualityRevision NO_REVISION = new PredictionQualityRevision(NO_REVISION_SO_FAR);

	/**
	 * Internal revision to show that a prediction quality report was is the result of a full recompute. Matches are
	 * also marked with this revision if they had no revision before.
	 */
	public static final PredictionQualityRevision IN_RECOMPUTATION = new PredictionQualityRevision(-2);

	@Override
	public String toString() {
		return "PredictionQualityRevision{" +
			"revision=" + revision +
			'}';
	}
}
