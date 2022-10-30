package de.ludwig.footystats.tools.backend.services.prediction;

public record InfluencerResult(String influencerName,
		Float influencerPredictionValue,
		PrecheckResult precheckResult) {
}
