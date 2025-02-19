package de.footystats.tools.services.prediction.influencer.team;

import de.footystats.tools.services.prediction.Bet;

public class AwayWinLeaguePosInfluencer extends TeamWinLeaguePosInfluencer {
	public AwayWinLeaguePosInfluencer() {
		super(Bet.AWAY_WIN);
	}

	@Override
	public String influencerName() {
		return "awayWinLeaguePosInfluencer";
	}
}
