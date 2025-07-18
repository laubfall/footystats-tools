package de.footystats.tools.services.bet;

import de.footystats.tools.services.bet.attributes.Attribute;
import de.footystats.tools.services.bet.attributes.AttributeSeries;
import de.footystats.tools.services.bet.attributes.AttributeSeriesRepository;
import de.footystats.tools.services.bet.attributes.BetAttribute;
import de.footystats.tools.services.bet.attributes.BetAttributeRepository;
import de.footystats.tools.services.bet.attributes.ChosenAttributeValue;
import de.footystats.tools.services.bet.attributes.DoubleAttribute;
import de.footystats.tools.services.match.Match;
import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PredictionAnalyze;
import de.footystats.tools.services.prediction.PredictionResult;
import de.footystats.tools.services.stats.MatchStatus;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("test")
@DataMongoTest
@AutoConfigureDataMongo
@Import({BetTicktServiceConfiguration.class})
class BetTicketServiceTest {
	@Autowired
	private BetTicketService betTicketService;

	@Autowired
	private BetSeriesRepository betSeriesRepository;

	@Autowired
	private BetTicketRepository betTicketRepository;

	@Autowired
	private BetAttributeRepository betAttributeRepository;
	
	@Autowired
	private AttributeSeriesRepository attributeSeriesRepository;

	@Test
	void place_a_ticket_without_existing_bet_series() {

		var match = new Match();
		match.setId(new ObjectId());
		match.setState(MatchStatus.complete);
		match.setO05(new PredictionResult(100, true, PredictionAnalyze.SUCCESS, List.of()));

		//bet_OVER_ZERO_FIVE,double_1.0_ODDS
		var attrBetOver05 = betAttributeRepository.findByUniqueName(BetAttribute.uniqueName(Bet.OVER_ZERO_FIVE),
			BetAttribute.class);
		var attrOdds1 = betAttributeRepository.findByUniqueName(DoubleAttribute.uniqueName(1.0, Attribute.ODDS),
			DoubleAttribute.class);

		BetTicket ticket = betTicketService.placeBet(match,
			List.of(new ChosenAttributeValue(Bet.OVER_ZERO_FIVE),
				new ChosenAttributeValue(Attribute.ODDS, 1.0)),
			false, 2.0, 1.5);
		Assertions.assertNotNull(ticket);

		List<BetTicket> all = betTicketRepository.findAll();
		Assertions.assertEquals(1, all.size());
		ticket = all.getFirst();
		Assertions.assertEquals(2, ticket.getChosenAttributeValues().size());

		betTicketService.evaluateMatchingBetTickets(match);

		List<BetSeries> o05series = betSeriesRepository.findAll();
		// Actually with the current config there are 3 series created, for three attribute series that match the bet ticket.
		// The series are:
		// 1. bet_OVER_ZERO_FIVE
		// 2. bet_OVER_ZERO_FIVE,double_1.0_ODDS
		// 3. bet_OVER_ZERO_FIVE,range_1.0-2.0_ODDS
		Assertions.assertEquals(3, o05series.size());

		o05series.forEach(series -> {
			Assertions.assertEquals(1L, series.getSuccessCount());
			Assertions.assertEquals(0L, series.getFailCount());
			Assertions.assertEquals(1.0, series.getWonMoney());
		});

		betTicketService.placeBet(match,
			List.of(new ChosenAttributeValue(Bet.OVER_ZERO_FIVE)), false, 2.0, 1.5);

		betTicketService.evaluateMatchingBetTickets(match);
		AttributeSeries o05attrSeries = attributeSeriesRepository.findByAttributeIds(
			List.of(attrBetOver05.getId()));
		var o05seriesOnlyBetAttribute = betSeriesRepository.findBetSeriesByAttributeSeriesId(o05attrSeries);
		Assertions.assertEquals(2L, o05seriesOnlyBetAttribute.getSuccessCount());
		Assertions.assertEquals(0L, o05seriesOnlyBetAttribute.getFailCount());
		Assertions.assertEquals(2.0, o05seriesOnlyBetAttribute.getWonMoney());

		var allAttributeSeries = attributeSeriesRepository.findByAttributeIds(
			List.of(attrBetOver05.getId(), attrOdds1.getId()));
		var allAttributeBetSeries = betSeriesRepository.findBetSeriesByAttributeSeriesId(allAttributeSeries);
		// No change in the series, still 1 success because the second ticket was only for the bet attribute.
		Assertions.assertEquals(1L, allAttributeBetSeries.getSuccessCount());
		Assertions.assertEquals(0L, allAttributeBetSeries.getFailCount());
		Assertions.assertEquals(1.0, allAttributeBetSeries.getWonMoney());
	}
}
