package de.ludwig.footystats.tools.backend.services.prediction.influencer;

import de.ludwig.footystats.tools.backend.model.LeagueStats;
import de.ludwig.footystats.tools.backend.model.MatchStats;
import de.ludwig.footystats.tools.backend.model.TeamStats;
import de.ludwig.footystats.tools.backend.services.prediction.Bet;

import java.util.List;

public record BetPredictionContext(MatchStats match,
                                   List<TeamStats> teamStats,
                                   LeagueStats leagueStats,
                                   Bet bet) {
}
