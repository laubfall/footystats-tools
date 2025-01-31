package de.footystats.tools.services.prediction.influencer.team;

import de.footystats.tools.services.prediction.Bet;

public class HomeTeamWinOddsInfluencer extends TeamVictoryOddsInfluencer {
	public HomeTeamWinOddsInfluencer() {
		super(Bet.HOME_WIN);
	}

	@Override
	public String influencerName() {
		return "oddsHomeTeamWin";
	}
}
