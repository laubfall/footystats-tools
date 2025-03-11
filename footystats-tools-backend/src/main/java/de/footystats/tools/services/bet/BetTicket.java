package de.footystats.tools.services.bet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import java.util.List;

/**
 * A ticket for a bet with a specific set of bet attributes.
 * These attributes are used to determine the bet series where the bet ticket should count against.
 */
@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BetTicket {
	@Indexed
	private List<ObjectId> attributeIds;

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

	@Indexed
	private boolean evaluated;

	/**
	 * The id of the document that contains the match information. Document is of type Match.
	 */
	@Indexed
	private ObjectId matchDocumentId;

	public BetTicket(ObjectId matchDocumentId, AttributeSeries attributeSeries) {
		Assert.notNull(matchDocumentId, "Match document id must not be null");
		this.attributeIds = attributeSeries.computeAttributeIds();
		this.matchDocumentId = matchDocumentId;
	}
}
