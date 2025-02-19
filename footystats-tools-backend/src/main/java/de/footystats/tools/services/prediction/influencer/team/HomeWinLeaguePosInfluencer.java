package de.footystats.tools.services.prediction.influencer.team;

import de.footystats.tools.services.prediction.Bet;

public final class HomeWinLeaguePosInfluencer extends TeamWinLeaguePosInfluencer {
	public HomeWinLeaguePosInfluencer() {
		super(Bet.HOME_WIN);
	}

	@Override
	public String influencerName() {
		return "homeWinLeaguePosInfluencer";
	}
}
