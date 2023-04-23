package de.ludwig.footystats.tools.backend.services.prediction;

import java.util.List;

/**
 * Data about a match prediction.
 * @param betSuccessInPercent The predicted bet success in percent (only if analyzeResult is SUCCESSFUL).
 * @param betOnThis True if placing the bet is recommended, otherwise false.
 * @param analyzeResult Status of the prediction.
 * @param influencerDetailedResult Prediction of all influencer that did calculations for this match prediction.
 */
public record PredictionResult (Integer betSuccessInPercent,
                                boolean betOnThis,
                                PredictionAnalyze analyzeResult,
                                List<InfluencerResult> influencerDetailedResult){
}
