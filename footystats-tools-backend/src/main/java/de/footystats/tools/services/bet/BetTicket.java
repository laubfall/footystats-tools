package de.footystats.tools.services.bet;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import java.util.List;

/**
 * A ticket for a bet with a specific set of bet attributes.
 * These attributes are used to determine the bet series where the bet ticket should count against.
 */
@Document
@Getter
@Setter
public class BetTicket {
	private final List<BaseBetAttribute<?>> attributes;

	@Transient
	private AttributeSeries attributeSeries;

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

	private boolean evaluated;

	/**
	 * The id of the document that contains the match information. Document is of type Match.
	 */
	private ObjectId matchDocumentId;

	public BetTicket(ObjectId matchDocumentId, List<BaseBetAttribute<?>> attributes) {
		Assert.notNull(matchDocumentId, "Match document id must not be null");
		this.attributeSeries = new AttributeSeries(attributes);
		this.attributes = attributes;
		this.matchDocumentId = matchDocumentId;
	}
}
