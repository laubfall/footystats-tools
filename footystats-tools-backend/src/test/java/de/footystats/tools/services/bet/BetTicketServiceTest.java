package de.footystats.tools.services.bet;

import de.footystats.tools.services.bet.attributes.Attributes;
import de.footystats.tools.services.bet.attributes.BetAttribute;
import de.footystats.tools.services.bet.attributes.BetAttributeRepository;
import de.footystats.tools.services.bet.attributes.ChosenAttributeValue;
import de.footystats.tools.services.bet.attributes.DoubleAttribute;
import de.footystats.tools.services.match.Match;
import de.footystats.tools.services.prediction.Bet;
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

	@Test
	void place_a_ticket_without_existing_bet_series() {

		var match = new Match();
		match.setId(new ObjectId());

		//bet_OVER_ZERO_FIVE,double_1.0_ODDS
		var attrBetOver05 = betAttributeRepository.findByUniqueName(BetAttribute.uniqueName(Bet.OVER_ZERO_FIVE),
			BetAttribute.class);
		var attrOdds1 = betAttributeRepository.findByUniqueName(DoubleAttribute.uniqueName(1.0, Attributes.ODDS),
			DoubleAttribute.class);

		BetTicket ticket = betTicketService.placeBet(match,
			List.of(new ChosenAttributeValue(attrBetOver05.getId(), attrBetOver05.getValue().name()),
				new ChosenAttributeValue(attrOdds1.getId(), attrOdds1.getValue())),
			false, 1.0);
		Assertions.assertNotNull(ticket);

		List<BetTicket> all = betTicketRepository.findAll();
		Assertions.assertEquals(1, all.size());
		ticket = all.getFirst();
		Assertions.assertEquals(2, ticket.getChosenAttributeValues().size());

		List<BetSeries> allBetSeries = betSeriesRepository.findAll();
		// One bet series with both attributes and another one with only the betAttribute.
		Assertions.assertEquals(2, allBetSeries.size());


		var betSeries = betSeriesRepository.searchByAttributeIds(List.of(attrBetOver05.getId(), attrOdds1.getId()));
		Assertions.assertNotNull(betSeries);

		betSeries = betSeriesRepository.searchByAttributeIds(List.of(attrBetOver05.getId()));
		Assertions.assertNotNull(betSeries);
	}
}
