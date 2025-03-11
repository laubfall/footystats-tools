package de.footystats.tools.services.bet;

import de.footystats.tools.services.bet.attributes.BetAttribute;
import de.footystats.tools.services.match.Match;
import de.footystats.tools.services.stats.MatchStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BetTicketService {

	private final BetTicketRepository betTicketRepository;

	private final BetSeriesRepository betSeriesRepository;

	private final BetAttributeRepository betAttributeRepository;

	public BetTicketService(BetTicketRepository betTicketRepository, BetSeriesRepository betSeriesRepository, BetAttributeRepository betAttributeRepository) {
		this.betTicketRepository = betTicketRepository;
		this.betSeriesRepository = betSeriesRepository;
		this.betAttributeRepository = betAttributeRepository;
	}

	public BetTicket placeBet(Match match, AttributeSeries attributeSeries, boolean virtual, double stake) {
		Assert.notNull(match, "Match must not be null");
		Assert.notNull(match.getId(), "Match id must not be null");

		prepareBetSeries(attributeSeries);

		var ticket = new BetTicket(match.getId(), attributeSeries);
		ticket.setVirtual(virtual);
		ticket.setStake(stake);

		betTicketRepository.insert(ticket);

		return ticket;
	}

	public void evaluateMatchingBetTickets(Match completedMatch) {
		if (!MatchStatus.complete.equals(completedMatch.getState())) {
			return;
		}
		// Find all bet tickets that match the completed match.
		var betsForMatch = betTicketRepository.findAllByMatchDocumentIdAndEvaluatedIsFalse(completedMatch.getId());
		for (BetTicket forMatch : betsForMatch) {
			List<BaseBetAttribute<?>> matchBetAttributes = betAttributeRepository.findAllById(
				forMatch.getAttributeIds());

			// Evaluate the bet tickets.
			// Update the bet tickets.
		}

	}

	private void prepareBetSeries(AttributeSeries attributeSeries) {
		// Find the BetAttribute in the list
		var betAttribute = attributeSeries.findBetAttribute();

		// Create a new list without the BetAttribute
		List<BaseBetAttribute<?>> attributesWithoutBet = new ArrayList<>(attributeSeries.getAttributes());
		attributesWithoutBet.remove(betAttribute);

		// Recursively check for BetSeries
		checkBetSeries(betAttribute, attributesWithoutBet);
	}

	private void checkBetSeries(BetAttribute betAttribute, List<BaseBetAttribute<?>> attributes) {

		// Check if a BetSeries exists for the current list of attributes
		var fullAttributes = new ArrayList<>(attributes);
		fullAttributes.add(betAttribute);
		var attributeSeries = new AttributeSeries(fullAttributes);
		BetSeries betSeries = betSeriesRepository.searchByAttributeIds(attributeSeries.computeAttributeIds());
		if (betSeries == null) {
			var series = new BetSeries(attributeSeries);
			series.setValidFrom(LocalDateTime.now());
			betSeriesRepository.insert(series);
			log.info("Created new BetSeries: {}", series);
			if (fullAttributes.size() == 1) {
				return;
			}
		}

		// Recursively check for BetSeries with one less element
		for (int i = 0; i < attributes.size(); i++) {
			List<BaseBetAttribute<?>> reducedAttributes = new ArrayList<>(attributes);
			reducedAttributes.remove(i);
			checkBetSeries(betAttribute, reducedAttributes);
		}
	}
}
