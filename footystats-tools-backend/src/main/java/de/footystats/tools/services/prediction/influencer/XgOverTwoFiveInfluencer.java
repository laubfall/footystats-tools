package de.footystats.tools.services.prediction.influencer;

import de.footystats.tools.services.prediction.Bet;

public class XgOverTwoFiveInfluencer extends XgOverXGoalsInfluencer {

	public XgOverTwoFiveInfluencer() {
		super(4, Bet.OVER_TWO_FIVE);
	}

	@Override
	public String influencerName() {
		return "XgOverTwoFiveInfluencer";
	}

}
