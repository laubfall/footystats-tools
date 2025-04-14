package de.footystats.tools.services.bet;

import de.footystats.tools.services.bet.attributes.AttributeSeries;
import de.footystats.tools.services.bet.attributes.AttributeService;
import de.footystats.tools.services.bet.attributes.Attributes;
import de.footystats.tools.services.bet.attributes.BaseBetAttribute;
import de.footystats.tools.services.bet.attributes.BetAttribute;
import de.footystats.tools.services.bet.attributes.BetAttributeRepository;
import de.footystats.tools.services.bet.attributes.ChosenAttributeValue;
import de.footystats.tools.services.match.Match;
import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PredictionAnalyze;
import de.footystats.tools.services.stats.MatchStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class BetTicketService {

	private final BetTicketRepository betTicketRepository;

	private final BetSeriesRepository betSeriesRepository;

	private final BetAttributeRepository betAttributeRepository;
	private final AttributeService attributeService;

	public BetTicketService(BetTicketRepository betTicketRepository, BetSeriesRepository betSeriesRepository, BetAttributeRepository betAttributeRepository, AttributeService attributeService) {
		this.betTicketRepository = betTicketRepository;
		this.betSeriesRepository = betSeriesRepository;
		this.betAttributeRepository = betAttributeRepository;
		this.attributeService = attributeService;
	}

	public BetTicket placeBet(Match match, Collection<ChosenAttributeValue> betValues, boolean virtual, double stake) {
		Assert.notNull(match, "Match must not be null");
		Assert.notNull(match.getId(), "Match id must not be null");

		var attributeSeries = by(betValues);
		prepareBetSeries(attributeSeries);

		var ticket = new BetTicket(match.getId(), attributeSeries);
		ticket.setVirtual(virtual);
		ticket.setStake(stake);

		betTicketRepository.insert(ticket);

		return ticket;
	}

	@Transactional
	public void evaluateMatchingBetTickets(Match completedMatch) {
		if (!MatchStatus.complete.equals(completedMatch.getState())) {
			return;
		}
		// Find all bet tickets that match the completed match.
		var betsForMatch = betTicketRepository.findAllByMatchDocumentIdAndEvaluatedIsFalse(completedMatch.getId());
		for (BetTicket ticket : betsForMatch) {

			// Update the bet tickets.
			var betWon = wonBet(ticket, completedMatch);
			ticket.setWon(betWon);
			ticket.setEvaluated(true);
			betTicketRepository.save(ticket);


			// Update the bet series with matching bet attributes.
			AttributeSeries matchingSeries = attributeService.byChosenValues(ticket.getChosenAttributeValues());
			// Todo Find all bet series that match at least a subset of the bet attributes.
			//betSeriesRepository.searchByAttributeIds(ticket.getAttributeIds());
		}
	}

	private boolean wonBet(BetTicket betTicket, Match completedMatch) {
		BetAttribute bet = betAttributeRepository.findByNameAndIdIn(Attributes.BET_ATTRIBUTE,
			betTicket.getChosenAttributeValues().stream().map(
				ChosenAttributeValue::getAttributeId).toList(), BetAttribute.class);

		if (Bet.OVER_ONE_FIVE.equals(bet.getValue())) {
			return PredictionAnalyze.SUCCESS.equals(completedMatch.getO05().analyzeResult());
		}

		return false;
	}

	private AttributeSeries by(Collection<ChosenAttributeValue> betValues) {
		return attributeService.byChosenValues(new ArrayList<>(betValues));
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
		fullAttributes.addFirst(betAttribute);
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
