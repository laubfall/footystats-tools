package de.ludwig.footystats.tools.backend.services.prediction;

import lombok.Builder;

public record InfluencerResult(String influencerName,
                               Float influencerPredictionValue,
                               PrecheckResult precheckResult) {
}
