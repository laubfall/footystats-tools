package de.footystats.tools.services.prediction;

/**
 * Result of a precheck for a BetResultInfluencer.
 */
public enum PrecheckResult {
	// The current BetResult Influencer misses some information inside the BetPredictionContext
	// in order to do a proper calculation.
	NOT_ENOUGH_INFORMATION,
	// Some relevant stats for the influencer are invalid or are invalid in combination with other stats.
	INVALID_STATS,
	// Error while doing calculation
	EXCEPTION,
	// Everything is fine, we can calculate a prediction value
	OK,
	;
}
