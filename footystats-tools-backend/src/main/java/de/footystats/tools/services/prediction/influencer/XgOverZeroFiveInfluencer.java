package de.footystats.tools.services.prediction.influencer;

public class XgOverZeroFiveInfluencer extends XgOverXGoalsInfluencer {

	private static final Integer XG_BASE = 2;

	public XgOverZeroFiveInfluencer() {
		super(XG_BASE);
	}

	@Override
	public String influencerName() {
		return "XgOverZeroFiveInfluencer";
	}
}
