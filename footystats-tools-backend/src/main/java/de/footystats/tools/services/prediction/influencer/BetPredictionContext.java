package de.footystats.tools.services.prediction.influencer;

import de.footystats.tools.services.stats.LeagueStats;
import de.footystats.tools.services.stats.MatchStats;
import de.footystats.tools.services.stats.TeamStats;
import de.footystats.tools.services.prediction.Bet;

import java.util.List;

public record BetPredictionContext(MatchStats match,
                                   List<TeamStats> teamStats,
                                   LeagueStats leagueStats,
                                   Bet bet) {
}
