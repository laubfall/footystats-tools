package de.footystats.tools.services.prediction.influencer.team;

import de.footystats.tools.services.prediction.Bet;

public class AwayTeamWinOddsInfluencer extends TeamVictoryOddsInfluencer {
	public AwayTeamWinOddsInfluencer() {
		super(Bet.AWAY_WIN);
	}

	@Override
	public String influencerName() {
		return "oddsAwayTeamWin";
	}
}
