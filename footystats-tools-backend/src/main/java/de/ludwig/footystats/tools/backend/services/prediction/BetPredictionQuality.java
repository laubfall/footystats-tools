package de.ludwig.footystats.tools.backend.services.prediction;

import lombok.*;
import org.springframework.boot.jackson.JsonComponent;

import java.util.ArrayList;
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
	private Long countAssessed = 0l; // Count of Matches a prediction was made for the chosen bet.
	@Getter
	@Setter
	private Long countSuccess = 0l; // PredictionAnalyze Success for bets you better bet on
	@Getter
	@Setter
	private Long countFailed = 0l; // PredictionAnalyse Failed for bets you better bet on
	@Getter
	@Setter
	private Long countSuccessDontBet = 0l;
	@Getter
	@Setter
	private Long countFailedDontBet = 0l;
	@Getter
	@Setter
	private List<BetPredictionDistribution> distributionBetOnThis = new ArrayList();
	@Getter
	@Setter
	private List<BetPredictionDistribution> distributionBetOnThisFailed = new ArrayList();
	@Getter
	@Setter
	private List<BetPredictionDistribution> distributionDontBetOnThis = new ArrayList();
	@Getter
	@Setter
	private List<BetPredictionDistribution> distributionDontBetOnThisFailed = new ArrayList();
	/**
	 * Contains the BetPredictionDistributions if a bet was successful no matter if footystats
	 * said bet on this or not.
	 */
	@Getter
	@Setter
	private List<BetPredictionDistribution> distributionBetSuccessful = new ArrayList();
}
