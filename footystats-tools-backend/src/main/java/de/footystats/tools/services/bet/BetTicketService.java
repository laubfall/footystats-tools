package de.footystats.tools.services.bet;

import de.footystats.tools.services.bet.attributes.Attribute;
import de.footystats.tools.services.bet.attributes.AttributeSeries;
import de.footystats.tools.services.bet.attributes.AttributeSeriesRepository;
import de.footystats.tools.services.bet.attributes.AttributeSeriesService;
import de.footystats.tools.services.bet.attributes.BaseBetAttribute;
import de.footystats.tools.services.bet.attributes.BetAttribute;
import de.footystats.tools.services.bet.attributes.BetAttributeRepository;
import de.footystats.tools.services.bet.attributes.ChosenAttributeValue;
import de.footystats.tools.services.match.Match;
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

	private final AttributeSeriesService attributeService;

	private final AttributeSeriesRepository attributeSeriesRepository;

	public BetTicketService(BetTicketRepository betTicketRepository, BetSeriesRepository betSeriesRepository, BetAttributeRepository betAttributeRepository, AttributeSeriesService attributeService, AttributeSeriesRepository attributeSeriesRepository) {
		this.betTicketRepository = betTicketRepository;
		this.betSeriesRepository = betSeriesRepository;
		this.betAttributeRepository = betAttributeRepository;
		this.attributeService = attributeService;
		this.attributeSeriesRepository = attributeSeriesRepository;
	}

	public BetTicket placeBet(Match match, Collection<ChosenAttributeValue> betValues, boolean virtual, double stake, double odds) {
		Assert.notNull(match, "Match must not be null");
		Assert.notNull(match.getId(), "Match id must not be null");

		var attributeSeries = by(betValues);

		var ticket = new BetTicket(match.getId(), attributeSeries);
		ticket.setVirtual(virtual);
		ticket.setStake(stake);
		ticket.setOdds(odds);

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
			final AttributeSeries matchingSeries = attributeService.byChosenValues(ticket.getChosenAttributeValues());
			var mainSeries = betSeriesRepository.findBetSeriesByAttributeSeriesId(matchingSeries.getId());
			mainSeries = safeGet(mainSeries, matchingSeries);
			mainSeries.evaluatedBetTicket(ticket);
			betSeriesRepository.save(mainSeries);

			// Find all bet series that match at least a subset of the bet attributes.
			for (List<BaseBetAttribute<?>> generateCombination : matchingSeries.generateCombinations()) {
				var existsMaybeSeries = new AttributeSeries();
				existsMaybeSeries.setAttributes(generateCombination);
				existsMaybeSeries = attributeSeriesRepository.searchByAttributeIds(existsMaybeSeries.getAttributeIds());
				BetSeries subsequentBetSeries = betSeriesRepository.findBetSeriesByAttributeSeriesId(
					existsMaybeSeries.getId());

				if (existsMaybeSeries != null) {
					subsequentBetSeries = safeGet(subsequentBetSeries, existsMaybeSeries);
					subsequentBetSeries.evaluatedBetTicket(ticket);
					betSeriesRepository.save(subsequentBetSeries);
				}
			}
		}
	}

	private BetSeries safeGet(BetSeries mainSeries, AttributeSeries matchingSeries) {
		if (mainSeries == null) {
			mainSeries = new BetSeries();
			mainSeries.setAttributeSeriesId(matchingSeries.getId());
			mainSeries.setValidFrom(LocalDateTime.now());
			betSeriesRepository.insert(mainSeries);
			log.info("Created new bet series: {}", mainSeries);
		}
		return mainSeries;
	}

	private boolean wonBet(BetTicket betTicket, Match completedMatch) {
		BetAttribute bet = betAttributeRepository.findByNameAndIdIn(Attribute.BET_ATTRIBUTE,
			betTicket.getChosenAttributeValues().stream().map(ChosenAttributeValue::getAttributeId).toList(),
			BetAttribute.class);

		return PredictionAnalyze.SUCCESS.equals(completedMatch.forBet(bet.getValue()).analyzeResult());
	}

	private AttributeSeries by(Collection<ChosenAttributeValue> betValues) {
		return attributeService.byChosenValues(new ArrayList<>(betValues));
	}
}
