package de.footystats.tools.services.bet;

import de.footystats.tools.services.bet.attributes.Attribute;
import de.footystats.tools.services.bet.attributes.AttributeSeries;
import de.footystats.tools.services.bet.attributes.BaseBetAttribute;
import de.footystats.tools.services.bet.attributes.BetAttribute;
import de.footystats.tools.services.bet.attributes.DoubleAttribute;
import de.footystats.tools.services.prediction.Bet;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BetTicketTest {

	@Test
	void constructor() {
		// Arrange
		ObjectId matchId = new ObjectId();
		AttributeSeries attributeSeries = new AttributeSeries();
		List<BaseBetAttribute<?>> attributes = new ArrayList<>();

		BetAttribute betAttribute = new BetAttribute(Bet.BTTS_NO);
		betAttribute.setId(new ObjectId());
		attributes.add(betAttribute);

		BaseBetAttribute<?> otherAttribute = new DoubleAttribute(1.0, Attribute.ODDS);
		otherAttribute.setId(new ObjectId());
		attributes.add(otherAttribute);

		attributeSeries.setAttributes(attributes);

		// Act
		BetTicket betTicket = new BetTicket(matchId, attributeSeries);

		// Assert
		assertNotNull(betTicket);
		assertEquals(matchId, betTicket.getMatchDocumentId());
		assertEquals(2, betTicket.getChosenAttributeValues().size());
		assertEquals(betAttribute.getId(), betTicket.getChosenAttributeValues().get(0).getAttributeId());
		assertEquals(otherAttribute.getId(), betTicket.getChosenAttributeValues().get(1).getAttributeId());
		assertFalse(betTicket.isWon());
		assertFalse(betTicket.isEvaluated());
	}

	@Test
	void getWonMoney_whenWon() {
		// Arrange
		BetTicket betTicket = new BetTicket();
		betTicket.setWon(true);
		betTicket.setStake(10.0);
		betTicket.setOdds(2.0);

		// Act
		double wonMoney = betTicket.getWonMoney();

		// Assert
		assertEquals(10.0, wonMoney); // (10 * 2) - 10 = 10
	}

	@Test
	void getWonMoney_whenLost() {
		// Arrange
		BetTicket betTicket = new BetTicket();
		betTicket.setWon(false);
		betTicket.setStake(10.0);
		betTicket.setOdds(2.0);

		// Act
		double wonMoney = betTicket.getWonMoney();

		// Assert
		assertEquals(-10.0, wonMoney); // -10 als Verlust
	}

	@Test
	void constructorWithNullMatchId() {
		// Arrange
		AttributeSeries attributeSeries = new AttributeSeries();
		var betAttribute = new BetAttribute(Bet.AWAY_WIN);
		betAttribute.setId(ObjectId.get());
		attributeSeries.setAttributes(List.of(betAttribute));

		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> new BetTicket(null, attributeSeries));
	}
}
