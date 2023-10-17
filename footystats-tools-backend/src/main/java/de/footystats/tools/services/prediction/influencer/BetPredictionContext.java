package de.footystats.tools.services.prediction.influencer;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.stats.LeagueStats;
import de.footystats.tools.services.stats.MatchStats;
import de.footystats.tools.services.stats.TeamStats;

/**
 * Context for a bet prediction.
 *
 * @param match
 * @param homeTeamStats
 * @param awayTeamStats
 * @param leagueStats
 * @param bet
 */
public record BetPredictionContext(MatchStats match,
								   TeamStats homeTeamStats,
								   TeamStats awayTeamStats,
								   LeagueStats leagueStats,
								   Bet bet) {

}
