package de.ludwig.footystats.tools.backend.services.prediction.quality;

public record BetPredictionQualityInfluencerAggregate(String influencerName, Integer predictionPercent, Long betSucceeded, Long betFailed, Long count) {

}
