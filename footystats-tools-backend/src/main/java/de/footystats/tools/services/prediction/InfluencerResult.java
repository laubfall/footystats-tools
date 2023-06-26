package de.footystats.tools.services.prediction;

/**
 * Stores the result of an influencer that did some computation for one match.
 * @param influencerName Name of the influencer.
 * @param influencerPredictionValue The computed value.
 * @param precheckResult The precheck result.
 */
public record InfluencerResult(String influencerName,
		Integer influencerPredictionValue,
		PrecheckResult precheckResult) {

	public InfluencerResult(String influencerName, Integer influencerPredictionValue, PrecheckResult precheckResult) {
		this.influencerName = influencerName;

		if(influencerPredictionValue == null || influencerPredictionValue < 0 || influencerPredictionValue > 100){
			throw new IllegalArgumentException("InfluencerPredictionValue has to be between 0 and 100.");
		}
		this.influencerPredictionValue = influencerPredictionValue;
		this.precheckResult = precheckResult;
	}
}
