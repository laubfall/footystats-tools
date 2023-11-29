package de.footystats.tools.services.prediction.quality.view;

import de.footystats.tools.services.prediction.outcome.IRanked;

/**
 * Stores aggregated distribution data for one influencer.
 *
 * @param influencerName    Name of the infuencer that did the computation.
 * @param predictionPercent The calculated value (0-100).
 * @param betSucceeded      Count of successful predictions when this influencer with this specific value was involved.
 * @param betFailed         Same as betSucceeded but when prediction failed.
 */
public record BetPredictionQualityInfluencerAggregate(String influencerName, Integer predictionPercent, Long betSucceeded, Long betFailed) implements
	IRanked {

}
