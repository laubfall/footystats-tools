package de.footystats.tools.services.prediction.influencer;

public class XgOverOneFiveInfluencer extends XgOverXGoalsInfluencer {

	public XgOverOneFiveInfluencer() {
		super(3);
	}

	@Override
	public String influencerName() {
		return "XgOverOneFiveInfluencer";
	}
}
