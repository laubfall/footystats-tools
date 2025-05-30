package de.footystats.tools.services.bet;

import de.footystats.tools.services.bet.attributes.Attribute;
import de.footystats.tools.services.bet.attributes.ChosenAttributeValue;
import de.footystats.tools.services.prediction.Bet;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BetTicketTest {

	@Test
	void constructor() {
		// Arrange
		ObjectId matchId = new ObjectId();
		var attributeSeries = new ArrayList<ChosenAttributeValue>();

		attributeSeries.add(new ChosenAttributeValue(Bet.BTTS_YES));
		attributeSeries.add(new ChosenAttributeValue(Attribute.ODDS, 1.5));

		// Act
		BetTicket betTicket = new BetTicket(matchId, attributeSeries);

		// Assert
		assertNotNull(betTicket);
		assertEquals(matchId, betTicket.getMatchDocumentId());
		assertEquals(2, betTicket.getChosenAttributeValues().size());
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
}
