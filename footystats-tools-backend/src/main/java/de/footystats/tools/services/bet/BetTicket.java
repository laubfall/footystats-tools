package de.footystats.tools.services.bet;

import de.footystats.tools.FootystatsRuntimeException;
import de.footystats.tools.services.bet.attributes.ChosenAttributeValue;
import de.footystats.tools.services.prediction.Bet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
	@Id
	private ObjectId id;

	private List<ChosenAttributeValue> chosenAttributeValues;

	/**
	 * True if the bet was not placed for real. Used to track bets you don't want to place but
	 * you want to know if it would have been successful or not.
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

	/**
	 * The odds of the bet ticket.
	 */
	private double odds;

	/**
	 * True if the bet ticket was evaluated (i.e. the match was played and the bet tickets result is written to the bet series).
	 */
	@Indexed
	private boolean evaluated;

	/**
	 * The id of the document that contains the match information. Document is of type Match.
	 */
	@Indexed
	private ObjectId matchDocumentId;

	public BetTicket(ObjectId matchDocumentId, Collection<ChosenAttributeValue> chosenAttributeValues) {
		Assert.notNull(matchDocumentId, "Match document id must not be null");

		this.chosenAttributeValues = new ArrayList<>(chosenAttributeValues);

		this.matchDocumentId = matchDocumentId;
	}

	public double getWonMoney() {
		return won ? ((stake * odds) - stake) : -stake;
	}

	public Bet fromChosenAttribute() {
		Optional<Bet> bet = getChosenAttributeValues().stream().filter(
			cav -> cav.getBetOpt().isPresent()).findFirst().map(
			ChosenAttributeValue::getBet);

		if (bet.isEmpty()) {
			throw new FootystatsRuntimeException(new FootystatsRuntimeException.ExceptionDetail(
				FootystatsRuntimeException.Type.DATA_INTEGRITY, this.getClass(), "bet",
				"BetTicket does not contain a Bet attribute."));
		}

		return bet.get();
	}
}
