package de.footystats.tools.services.bet;

import de.footystats.tools.services.bet.attributes.Attributes;
import de.footystats.tools.services.bet.attributes.BetAttribute;
import de.footystats.tools.services.bet.attributes.DoubleAttribute;
import de.footystats.tools.services.prediction.Bet;
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
public class BetTicketServiceTest {
	@Autowired
	private BetTicketService betTicketService;

	@Autowired
	private BetSeriesRepository betSeriesRepository;

	@Autowired
	private BetTicketRepository betTicketRepository;

	@Test
	public void place_a_ticket_without_existing_bet_series() {
		List<BaseBetAttribute<?>> attributes = List.of(new BetAttribute(Bet.OVER_ZERO_FIVE),
			new DoubleAttribute(1.5, Attributes.ODDS));

		BetTicket ticket = betTicketService.placeBet(attributes, false, 1.0);
		Assertions.assertNotNull(ticket);

		List<BetTicket> all = betTicketRepository.findAll();
		Assertions.assertEquals(1, all.size());
		ticket = all.getFirst();
		Assertions.assertEquals(2, ticket.getAttributeSeries().size());

		List<BetSeries> allBetSeries = betSeriesRepository.findAll();
		// One bet series with both attributes and another one with only the betAttribute.
		Assertions.assertEquals(2, allBetSeries.size());

		var betSeries = betTicketService.betSeriesByAttributes(attributes);
		Assertions.assertNotNull(betSeries);

		betSeries = betTicketService.betSeriesByAttributes(
			List.of(new BetAttribute(Bet.OVER_ZERO_FIVE)));
		Assertions.assertNotNull(betSeries);
	}
}
