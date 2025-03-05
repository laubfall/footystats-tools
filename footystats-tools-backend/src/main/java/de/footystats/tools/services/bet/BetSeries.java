package de.footystats.tools.services.bet;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Getter
@Setter
@ToString
public class BetSeries {
	private final List<BaseBetAttribute<?>> attributeSeries;

	@Indexed
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm'Z'")
	private LocalDateTime validFrom;

	@Indexed
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm'Z'")
	private LocalDateTime validUntil;

	public BetSeries(List<BaseBetAttribute<?>> attributeSeries) {
		this.attributeSeries = attributeSeries;
	}
}
