package de.footystats.tools.services.bet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BetSeriesTest {
	@Test
	void evaluatedBetTicket() {
		// Arrange
		var betSeries = new BetSeries();
		var ticket = new BetTicket();
		ticket.setEvaluated(true);
		ticket.setWon(true);
		ticket.setStake(10.0);
		ticket.setOdds(2.0);

		// Act
		betSeries.evaluatedBetTicket(ticket);

		// Assert
		Assertions.assertEquals(1, betSeries.getSuccessCount());
		Assertions.assertEquals(10.0, betSeries.getWonMoney());
	}

	@Test
	void evaluateVirtualBetTicket() {
		// Arrange
		var betSeries = new BetSeries();
		var ticket = new BetTicket();
		ticket.setEvaluated(true);
		ticket.setWon(true);
		ticket.setStake(10.0);
		ticket.setOdds(2.0);
		ticket.setVirtual(true);

		// Act
		betSeries.evaluatedBetTicket(ticket);

		// Assert
		Assertions.assertEquals(1, betSeries.getSuccessCountVirtual());
		Assertions.assertEquals(10.0, betSeries.getWonMoneyVirtual());
	}

	@Test
	void do_nothing_when_not_evaluated() {
		// Arrange
		var betSeries = new BetSeries();
		var ticket = new BetTicket();
		ticket.setEvaluated(false);
		ticket.setWon(true);
		ticket.setStake(10.0);
		ticket.setOdds(2.0);

		// Act
		betSeries.evaluatedBetTicket(ticket);

		// Assert
		Assertions.assertEquals(0, betSeries.getSuccessCount());
		Assertions.assertEquals(0.0, betSeries.getWonMoney());
	}

	@Test
	void evaluateMultipleTickets() {
		// Arrange
		var betSeries = new BetSeries();
		var ticket1 = new BetTicket();
		ticket1.setEvaluated(true);
		ticket1.setWon(true);
		ticket1.setStake(10.0);
		ticket1.setOdds(2.0);

		var ticket2 = new BetTicket();
		ticket2.setEvaluated(true);
		ticket2.setWon(true);
		ticket2.setStake(5.0);
		ticket2.setOdds(3.0);

		// Act
		betSeries.evaluatedBetTicket(ticket1);
		betSeries.evaluatedBetTicket(ticket2);

		// Assert
		Assertions.assertEquals(2, betSeries.getSuccessCount());
		Assertions.assertEquals(0, betSeries.getFailCount());
		Assertions.assertEquals(20.0, betSeries.getWonMoney());
	}

	@Test
	void evaluateNotWonTicket() {
		// Arrange
		var betSeries = new BetSeries();
		var ticket = new BetTicket();
		ticket.setEvaluated(true);
		ticket.setWon(false);
		ticket.setStake(10.0);
		ticket.setOdds(2.0);

		// Act
		betSeries.evaluatedBetTicket(ticket);

		// Assert
		Assertions.assertEquals(0, betSeries.getSuccessCount());
		Assertions.assertEquals(1, betSeries.getFailCount());
		Assertions.assertEquals(-10.0, betSeries.getWonMoney());
	}
}
