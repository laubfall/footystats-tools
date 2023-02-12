package de.ludwig.footystats.tools.backend.services.prediction.influencer;

import de.ludwig.footystats.tools.backend.services.prediction.PrecheckResult;

/**
 * Implementations calculate the possibility that a specific bet will be successful.
 * That is the influence of the total result of all influencer.
 */
public interface BetResultInfluencer {
	/**
	 * Implementations check the given context for stats that are necessary in order to compute the influence.
	 *
	 * @param ctx Mandatory.
	 * @return The Precheckresult.
	 */
    PrecheckResult preCheck(BetPredictionContext ctx);

	/**
	 * The influence or the possibility of bet success in percent (0-100).
	 * @param ctx Mandatory.
	 * @return See description.
	 */
    Integer calculateInfluence(BetPredictionContext ctx);

	/**
	 * A descriptive name for the specific influencer.
	 * @return See description.
	 */
    String influencerName();
}
