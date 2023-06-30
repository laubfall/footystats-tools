package de.footystats.tools.services.prediction.influencer;

import de.footystats.tools.services.prediction.Bet;

public class XgOverOneFiveInfluencer extends XgOverXGoalsInfluencer{
	public XgOverOneFiveInfluencer() {
		super(3, Bet.OVER_ONE_FIVE);
	}

	@Override
	public String influencerName() {
		return "XgOverOneFiveInfluencer";
	}
}
