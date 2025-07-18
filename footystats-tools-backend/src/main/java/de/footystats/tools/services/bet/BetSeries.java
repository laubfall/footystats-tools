package de.footystats.tools.services.bet;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BetSeries {
	@Id
	private ObjectId id;

	/**
	 * The attribute id of an AttributeSeries.
	 */
	@Indexed
	private ObjectId attributeSeriesId;

	@Indexed
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm'Z'")
	private LocalDateTime validFrom;

	@Indexed
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm'Z'")
	private LocalDateTime validUntil;

	private long successCount;

	private long failCount;

	private long successCountVirtual;

	private long failCountVirtual;

	/**
	 * Won money. Maybe negative.
	 */
	private double wonMoney;

	/**
	 * Won money for virtual bets.
	 */
	private double wonMoneyVirtual;

	/**
	 * Updates the bet series based on the given ticket.
	 * Be careful, method does not check if the tickets attributes are part of the series and
	 * it does not check if the ticket is already evaluated.
	 *
	 * @param ticket Mandatory. The bet ticket to evaluate.
	 */
	void evaluatedBetTicket(BetTicket ticket) {

		if (ticket.isWon()) {
			if (ticket.isVirtual()) {
				successCountVirtual++;
				wonMoneyVirtual += ticket.getWonMoney();
			} else {
				successCount++;
				wonMoney += ticket.getWonMoney();
			}
		} else {
			if (ticket.isVirtual()) {
				failCountVirtual++;
				wonMoneyVirtual += ticket.getWonMoney();
			} else {
				failCount++;
				wonMoney += ticket.getWonMoney();
			}
		}
	}
}
