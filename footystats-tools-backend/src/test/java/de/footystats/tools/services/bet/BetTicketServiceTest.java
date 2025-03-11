package de.footystats.tools.services.bet;

import de.footystats.tools.services.bet.attributes.Attributes;
import de.footystats.tools.services.bet.attributes.BetAttribute;
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
import java.util.stream.Stream;

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

	@Autowired
	private BetAttributeRepository betAttributeRepository;

	@Test
	public void place_a_ticket_without_existing_bet_series() {
		List<BaseBetAttribute<?>> attributes = List.of(new BetAttribute(Bet.OVER_ZERO_FIVE),
			new DoubleAttribute(1.5, Attributes.ODDS));

		var match = new Match();
		match.setId(new ObjectId());
		BetTicket ticket = betTicketService.placeBet(match, new AttributeSeries(attributes), false, 1.0);
		Assertions.assertNotNull(ticket);

		List<BetTicket> all = betTicketRepository.findAll();
		Assertions.assertEquals(1, all.size());
		ticket = all.getFirst();
		Assertions.assertEquals(2, ticket.getAttributeIds().size());

		List<BetSeries> allBetSeries = betSeriesRepository.findAll();
		// One bet series with both attributes and another one with only the betAttribute.
		Assertions.assertEquals(2, allBetSeries.size());

		var betSeries = betSeriesRepository.searchByAttributeIds(new AttributeSeries(attributes).computeAttributeIds());
		Assertions.assertNotNull(betSeries);

		betSeries = betSeriesRepository.searchByAttributeIds(
			new AttributeSeries(new BetAttribute(Bet.OVER_ZERO_FIVE)).computeAttributeIds());
		Assertions.assertNotNull(betSeries);
	}

	@Test
	public void referende_attribute_ids() {
		var betAttribute = betAttributeRepository.insert(new BetAttribute(Bet.BTTS_YES));
		var doubAttr1 = betAttributeRepository.insert(new DoubleAttribute(12.2, Attributes.ODDS));
		var doubAttr2 = betAttributeRepository.insert(new DoubleAttribute(3.2, Attributes.ODDS));

		var match = new Match();
		match.setId(new ObjectId());

		var allAttributes = betAttributeRepository.findAll();

		betTicketRepository.insert(new BetTicket(match.getId(), new AttributeSeries(List.of(betAttribute, doubAttr1))));

		var attrIds = allAttributes.stream().map(BaseBetAttribute::getId).toList();
		List<BetTicket> allByAttributeIds = betTicketRepository.findAllByAttributeIds(attrIds);
		Assertions.assertEquals(0, allByAttributeIds.size());


		allByAttributeIds = betTicketRepository.findAllByAttributeIds(
			Stream.of(betAttribute, doubAttr1).map(BaseBetAttribute::getId).toList());

		Assertions.assertEquals(1, allByAttributeIds.size());

		allByAttributeIds = betTicketRepository.findAllByAttributeIds(
			Stream.of(betAttribute, doubAttr2).map(BaseBetAttribute::getId).toList());

		Assertions.assertEquals(0, allByAttributeIds.size());

		allByAttributeIds = betTicketRepository.findAllByAttributeIds(
			Stream.of(betAttribute).map(BaseBetAttribute::getId).toList());

		Assertions.assertEquals(0, allByAttributeIds.size());
	}
}
