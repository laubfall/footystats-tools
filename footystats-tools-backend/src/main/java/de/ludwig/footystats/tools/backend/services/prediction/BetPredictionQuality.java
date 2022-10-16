package de.ludwig.footystats.tools.backend.services.prediction;

import lombok.*;
import org.springframework.boot.jackson.JsonComponent;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@JsonComponent
@Builder(toBuilder = true)
public class BetPredictionQuality {
	@Getter
	@Setter
	private Bet bet;
	@Getter
	@Setter
	private Long countAssessed; // Count of Matches a prediction was made for the chosen bet.
	@Getter
	@Setter
	private Long countSuccess; // PredictionAnalyze Success for bets you better bet on
	@Getter
	@Setter
	private Long countFailed; // PredictionAnalyse Failed for bets you better bet on
	@Getter
	@Setter
	private Long countSuccessDontBet;
	@Getter
	@Setter
	private Long countFailedDontBet;
	@Getter
	@Setter
	private List<BetPredictionDistribution> distributionBetOnThis;
	@Getter
	@Setter
	private List<BetPredictionDistribution> distributionBetOnThisFailed;
	@Getter
	@Setter
	private List<BetPredictionDistribution> distributionDontBetOnThis;
	@Getter
	@Setter
	private List<BetPredictionDistribution> distributionDontBetOnThisFailed;
	@Getter
	@Setter
	private List<BetPredictionDistribution> distributionBetSuccessful;
}
