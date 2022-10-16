package de.ludwig.footystats.tools.backend.services.prediction.influencer;

import de.ludwig.footystats.tools.backend.services.stats.LeagueStats;
import de.ludwig.footystats.tools.backend.services.stats.MatchStats;
import de.ludwig.footystats.tools.backend.services.stats.TeamStats;
import de.ludwig.footystats.tools.backend.services.prediction.Bet;

import java.util.List;

public record BetPredictionContext(MatchStats match,
                                   List<TeamStats> teamStats,
                                   LeagueStats leagueStats,
                                   Bet bet) {
}
