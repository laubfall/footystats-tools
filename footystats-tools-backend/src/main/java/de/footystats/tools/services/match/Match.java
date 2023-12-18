package de.footystats.tools.services.match;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.footystats.tools.services.domain.Country;
import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PredictionResult;
import de.footystats.tools.services.prediction.quality.PredictionQualityRevision;
import de.footystats.tools.services.stats.MatchStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represents a match as shown in the match list.
 */
@Document
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndex(name = "unique", def = "{'dateUnix' : 1, 'dateGMT': 1, 'country': 1, 'league': 1, 'homeTeam': 1, 'awayTeam': 1, 'state': 1}")
public class Match {

	@Id
	private ObjectId id;
	@Indexed
	private Long dateUnix;
	@Indexed
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm'Z'")
	private LocalDateTime dateGMT;
	@Indexed
	@Schema(implementation = String.class)
	private Country country;
	private String league;
	@TextIndexed
	private String homeTeam;
	@TextIndexed
	private String awayTeam;
	private Integer goalsHomeTeam;
	private Integer goalsAwayTeam;
	@Indexed
	private MatchStatus state;
	private String footyStatsUrl;
	private PredictionResult o05;
	private PredictionResult o15;
	private PredictionResult bttsYes;
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
			default -> {
				return null;
			}
		}
	}
}
