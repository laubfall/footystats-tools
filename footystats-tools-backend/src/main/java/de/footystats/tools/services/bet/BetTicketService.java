package de.footystats.tools.services.bet;

import de.footystats.tools.services.ServiceException;
import de.footystats.tools.services.bet.attributes.BetAttribute;
import de.footystats.tools.services.match.Match;
import de.footystats.tools.services.stats.MatchStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BetTicketService {

	private final BetTicketRepository betTicketRepository;

	private final BetSeriesRepository betSeriesRepository;

	public BetTicketService(BetTicketRepository betTicketRepository, BetSeriesRepository betSeriesRepository) {
		this.betTicketRepository = betTicketRepository;
		this.betSeriesRepository = betSeriesRepository;
	}

	public BetTicket placeBet(List<BaseBetAttribute<?>> attributes, boolean virtual, double stake) {
		var result = hasExactlyOneBetAttribute(attributes);
		if (!result) {
			throw new ServiceException(ServiceException.Type.BET_TICKET_SERVICE_NO_BET_ATTRIBUTE);
		}

		prepareBetSeries(attributes);

		var ticket = new BetTicket(attributes);
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
		// Evaluate the bet tickets.
		// Update the bet tickets.
	}

	public BetSeries betSeriesByAttributes(List<BaseBetAttribute<?>> attributes) {
		List<BetSeries> possibleBetSeries = betSeriesRepository.findAll();

		Optional<BetSeries> matching = possibleBetSeries.stream().filter(
				series -> CollectionUtils.isEqualCollection(series.getAttributeSeries(), attributes))
			.findFirst();

		return matching.orElse(null);

	}

	private void prepareBetSeries(List<BaseBetAttribute<?>> attributes) {
		// Find the BetAttribute in the list
		BaseBetAttribute<?> betAttribute = attributes.stream()
			.filter(attribute -> attribute instanceof BetAttribute)
			.findFirst()
			.orElse(null);

		if (betAttribute == null) {
			// No need to throw exception, is done before.
			return;
		}

		// Create a new list without the BetAttribute
		List<BaseBetAttribute<?>> attributesWithoutBet = new ArrayList<>(attributes);
		attributesWithoutBet.remove(betAttribute);

		// Recursively check for BetSeries
		checkBetSeries((BetAttribute) betAttribute, attributesWithoutBet);
	}

	private void checkBetSeries(BetAttribute betAttribute, List<BaseBetAttribute<?>> attributes) {

		// Check if a BetSeries exists for the current list of attributes
		var fullAttributes = new ArrayList<>(attributes);
		fullAttributes.add(betAttribute);
		BetSeries betSeries = betSeriesByAttributes(fullAttributes);
		if (betSeries == null) {
			var series = new BetSeries(fullAttributes);
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

	/**
	 * Method that checks if the list of attributes contains exactly one BetAttribute. More would not make sense.
	 *
	 * @param attributes The list of attributes to check.
	 * @return True if the list contains exactly one BetAttribute, false otherwise.
	 */
	private boolean hasExactlyOneBetAttribute(List<BaseBetAttribute<?>> attributes) {
		return attributes.stream().filter(attribute -> attribute instanceof BetAttribute).count() == 1;
	}
}
