package de.footystats.tools.services.prediction.influencer.team;

public final class AwayTeamLeaguePosInfluencer extends LeaguePositionInfluencer {

	public AwayTeamLeaguePosInfluencer() {
		super(false);
	}

	@Override
	public String influencerName() {
		return "AwayTeamLeaguePosInfluencer";
	}
}
