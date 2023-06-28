package de.footystats.tools.services.prediction.influencer;

import de.footystats.tools.services.prediction.PrecheckResult;

public class XgAOverZeroFiveInfluencer implements BetResultInfluencer {
	@Override
	public PrecheckResult preCheck(BetPredictionContext ctx) {
		return null;
	}

	@Override
	public Integer calculateInfluence(BetPredictionContext ctx) {
		return null;
	}

	@Override
	public String influencerName() {
		return null;
	}
}
