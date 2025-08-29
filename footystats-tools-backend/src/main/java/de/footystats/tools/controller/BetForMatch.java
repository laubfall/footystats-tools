package de.footystats.tools.controller;

import de.footystats.tools.services.bet.BetTicket;
import de.footystats.tools.services.prediction.Bet;

import java.util.List;

/**
 * Additional information about bets (placed by user) for a match.
 */
public record BetForMatch(Bet placedBet, boolean won, boolean evaluated) {

	/**
	 * Creates a list of BetForMatch from a list of BetTickets.
	 *
	 * @param betTickets the bet tickets
	 * @return the list of BetForMatch
	 */
	public static List<BetForMatch> fromBetTickets(List<BetTicket> betTickets) {
		if (betTickets == null) {
			return List.of();
		}
		return betTickets.stream()
			.map(bt -> new BetForMatch(bt.fromChosenAttribute(), bt.isWon(), bt.isEvaluated()))
			.toList();
	}
}
