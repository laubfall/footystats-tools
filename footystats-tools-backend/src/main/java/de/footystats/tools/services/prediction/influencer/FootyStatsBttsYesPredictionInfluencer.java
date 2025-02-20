package de.footystats.tools.services.prediction.influencer;

import de.footystats.tools.services.prediction.PrecheckResult;

public class FootyStatsBttsYesPredictionInfluencer implements BetResultInfluencer {
	public PrecheckResult preCheck(BetPredictionContext ctx) {
		if (ctx.match().getBTTSAverage() == null) {
			return PrecheckResult.NOT_ENOUGH_INFORMATION;
		}

		return PrecheckResult.OK;
	}

	// eslint-disable-next-line class-methods-use-this
	public Integer calculateInfluence(BetPredictionContext ctx) {
		return ctx.match().getBTTSAverage();
	}

	// eslint-disable-next-line class-methods-use-this
	public String influencerName() {
		return "FootyStatsBttsYesPredictionInfluencer";
	}
}
