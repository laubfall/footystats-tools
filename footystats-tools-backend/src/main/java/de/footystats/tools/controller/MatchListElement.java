package de.footystats.tools.controller;

import de.footystats.tools.services.bet.BetTicket;
import de.footystats.tools.services.match.Match;
import de.footystats.tools.services.prediction.outcome.StatisticalResultOutcome;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Class used to represent a match with statistical result outcomes.
 */
public class MatchListElement extends Match implements Serializable {

	@Getter
	@Setter
	private List<StatisticalResultOutcome> statisticalResultOutcome;

	/**
	 * List of bets placed by the user for this match.
	 */
	@Getter
	@Setter
	private List<BetForMatch> placedBets;

	public void convertToPlacedBets(List<BetTicket> tickets) {
		this.placedBets = BetForMatch.fromBetTickets(tickets);
	}
}
