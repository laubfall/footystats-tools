package de.footystats.tools.services.bet;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
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

	private double wonMoney;
}
