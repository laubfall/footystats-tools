package de.ludwig.footystats.tools.backend.services.prediction.quality.view;

public record BetPredictionQualityInfluencerAggregate(String influencerName, Integer predictionPercent, Long betSucceeded, Long betFailed, Long count) {

}
