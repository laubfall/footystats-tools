package de.footystats.tools.services.bet;

import de.footystats.tools.services.bet.attributes.Attribute;
import de.footystats.tools.services.bet.attributes.AttributeSeries;
import de.footystats.tools.services.bet.attributes.AttributeSeriesRepository;
import de.footystats.tools.services.bet.attributes.AttributeSeriesService;
import de.footystats.tools.services.bet.attributes.BaseBetAttribute;
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
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

		var ticket = new BetTicket(match.getId(), betValues);
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
			final List<AttributeSeries> matchingSeries = attributeService.byChosenValues(
				ticket.getChosenAttributeValues());
			for (AttributeSeries sery : matchingSeries) {
				var mainSeries = betSeriesRepository.findBetSeriesByAttributeSeriesId(sery.getId());
				mainSeries = safeGet(mainSeries, sery);
				mainSeries.evaluatedBetTicket(ticket);
				betSeriesRepository.save(mainSeries);

				// Find all bet series that match at least a subset of the bet attributes.
				for (List<BaseBetAttribute<?>> generateCombination : sery.generateCombinations()) {
					var existsMaybeSeries = new AttributeSeries();
					existsMaybeSeries.setAttributes(generateCombination);
					existsMaybeSeries = attributeSeriesRepository.findByAttributeIds(
						existsMaybeSeries.getAttributeIds());
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

		Optional<ChosenAttributeValue> first = betTicket.getChosenAttributeValues().stream().filter(
			chosenValue -> chosenValue.getChosenAttribute().equals(Attribute.BET_ATTRIBUTE)).findFirst();

		Assert.isTrue(first.isPresent(), "Bet ticket must contain a bet attribute");

		var bet = first.get().getBet();

		var predictionResult = completedMatch.forBet(bet);

		Assert.notNull(predictionResult, "Match must contain a prediction result for the bet: " + bet);

		return PredictionAnalyze.SUCCESS.equals(predictionResult.analyzeResult());
	}
}
