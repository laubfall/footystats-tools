package de.footystats.tools.services.prediction.outcome;

/**
 * Implemented by pojos that can be ranked in terms of the statistical outcome of a bet prediction.
 */
public interface IRanked {

	Integer predictionPercent();

	Long betSucceeded();

	Long betFailed();
}
