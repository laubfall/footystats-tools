package de.footystats.tools.services.bet;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * A ticket for a bet with a specific set of bet attributes.
 * These attributes are used to determine the bet series where the bet ticket should count against.
 */
@Document
@Getter
@Setter
public class BetTicket {
	private final List<BaseBetAttribute<?>> attributeSeries;

	/**
	 * If true the outcome of the bet should be involved in computing the total bet series (e.g. won money/bets).
	 */
	private boolean virtual;

	/**
	 * If true the bet ticket is a winning ticket.
	 */
	private boolean won;

	/**
	 * The stake of the bet ticket.
	 */
	private double stake;

	private double wonMoney = 0;

	public BetTicket(List<BaseBetAttribute<?>> attributeSeries) {
		this.attributeSeries = attributeSeries;
	}
}
