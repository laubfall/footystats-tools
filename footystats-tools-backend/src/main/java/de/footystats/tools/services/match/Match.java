package de.footystats.tools.services.match;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.footystats.tools.services.domain.Country;
import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PredictionResult;
import de.footystats.tools.services.prediction.quality.PredictionQualityRevision;
import de.footystats.tools.services.stats.MatchStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder()
@AllArgsConstructor
@NoArgsConstructor
@JsonComponent
@CompoundIndex(name = "unique", def = "{'dateUnix' : 1, 'dateGMT': 1, 'country': 1, 'league': 1, 'homeTeam': 1, 'awayTeam': 1, 'state': 1}")
public class Match {

	@Getter
	@Setter
	private Long dateUnix;
	@Getter
	@Setter
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm'Z'")
	private LocalDateTime dateGMT;
	@Getter
	@Setter
	private Country country;
	@Getter
	@Setter
	private String league;
	@TextIndexed
	@Getter
	@Setter
	private String homeTeam;
	@TextIndexed
	@Getter
	@Setter
	private String awayTeam;
	@Getter
	@Setter
	private Integer goalsHomeTeam;
	@Getter
	@Setter
	private Integer goalsAwayTeam;
	@Getter
	@Setter
	private MatchStatus state;
	@Getter
	@Setter
	private String footyStatsUrl;
	@Getter
	@Setter
	private PredictionResult o05;
	@Getter
	@Setter
	private PredictionResult o15;
	@Getter
	@Setter
	private PredictionResult bttsYes;
	@Getter
	@Setter
	private PredictionQualityRevision revision;

	/**
	 * Access to prediction results via a given bet type.
	 *
	 * @param bet Mandatory. The type of bet you want to get the prediction results for.
	 * @return null if there are no prediction results otherwise prediction results for the given bet type.
	 */
	public final PredictionResult forBet(Bet bet) {
		switch (bet) {
			case OVER_ZERO_FIVE -> {
				return o05;
			}
			case OVER_ONE_FIVE -> {
				return o15;
			}
			case BTTS_YES -> {
				return bttsYes;
			}
			case BTTS_NO -> {
				return null;
			}
			default -> {
				return null;
			}
		}
	}
}
