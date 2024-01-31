package de.footystats.tools.controller.live;

import de.footystats.tools.services.domain.Country;
import de.footystats.tools.services.prediction.Bet;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Class used to represent a live match with hot bets.
 *
 * @param start    start time of the match (UTC / GMT)
 * @param homeTeam name of the home team
 * @param awayTeam name of the away team
 * @param country  country of the match
 * @param hotBets  set of hot bets for the match. Hot bets are those with a high statistical result outcome.
 */
public record LiveAndHotMatches(LocalDateTime start, String homeTeam, String awayTeam, Country country, Set<Bet> hotBets) {

}
