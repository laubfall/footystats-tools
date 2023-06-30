package de.footystats.tools.services.prediction.influencer;

import de.footystats.tools.services.prediction.Bet;

public class XgOverZeroFiveInfluencer extends XgOverXGoalsInfluencer {

	private static final Integer XG_BASE = 2;

	public XgOverZeroFiveInfluencer() {
		super(XG_BASE, Bet.OVER_ZERO_FIVE);
	}

	@Override
	public String influencerName() {
		return "XgOverZeroFiveInfluencer";
	}
}
