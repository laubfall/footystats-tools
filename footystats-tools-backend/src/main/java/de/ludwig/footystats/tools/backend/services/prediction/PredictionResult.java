package de.ludwig.footystats.tools.backend.services.prediction;

import java.util.List;

public record PredictionResult (Float betSuccessInPercent,
                                boolean betOnThis,
                                PredictionAnalyze analyzeResult,
                                List<InfluencerResult> influencerDetailedResult){
}