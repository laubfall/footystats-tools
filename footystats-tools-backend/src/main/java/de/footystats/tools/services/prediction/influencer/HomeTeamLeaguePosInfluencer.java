package de.footystats.tools.services.prediction.influencer;

public final class HomeTeamLeaguePosInfluencer extends LeaguePositionInfluencer {

	public HomeTeamLeaguePosInfluencer() {
		super(true);
	}

	@Override
	public String influencerName() {
		return "HomeTeamLeaguePosInfluencer";
	}
}
