package de.footystats.tools.services.bet;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.footystats.tools.services.bet.attributes.AttributeSeries;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BetSeries {
	@Indexed
	private List<ObjectId> attributeIds;

	@Indexed
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm'Z'")
	private LocalDateTime validFrom;

	@Indexed
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm'Z'")
	private LocalDateTime validUntil;

	private long successCount;

	private long failCount;

	private double wonMoney;

	public BetSeries(AttributeSeries attributeSeries) {
		this.attributeIds = attributeSeries.computeAttributeIds();
	}
}
