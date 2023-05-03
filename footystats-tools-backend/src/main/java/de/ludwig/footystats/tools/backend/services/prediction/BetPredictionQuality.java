package de.ludwig.footystats.tools.backend.services.prediction;

import lombok.*;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Deprecated
public class BetPredictionQuality {
	@Getter
	@Setter
	private Bet bet;
	@Getter
	@Setter
	private Long countAssessed = 0L; // Count of Matches a prediction was made for the chosen bet.
	@Getter
	@Setter
	private Long countSuccess = 0L; // PredictionAnalyze Success for bets you better bet on
	@Getter
	@Setter
	private Long countFailed = 0L; // PredictionAnalyse Failed for bets you better bet on
	@Getter
	@Setter
	private Long countSuccessDontBet = 0L;
	@Getter
	@Setter
	private Long countFailedDontBet = 0L;
	@Getter
	@Setter
	private List<BetPredictionDistribution> distributionBetOnThis = new ArrayList<>();
	@Getter
	@Setter
	private List<BetPredictionDistribution> distributionBetOnThisFailed = new ArrayList<>();
	@Getter
	@Setter
	private List<BetPredictionDistribution> distributionDontBetOnThis = new ArrayList<>();
	@Getter
	@Setter
	private List<BetPredictionDistribution> distributionDontBetOnThisFailed = new ArrayList<>();
	/**
	 * Contains the BetPredictionDistributions if a bet was successful no matter if footystats
	 * said bet on this or not.
	 */
	@Getter
	@Setter
	private List<BetPredictionDistribution> distributionBetSuccessful = new ArrayList<>();

	@Transient
	public List<InfluencerPercentDistribution> allDistributions() {
		var result = new ArrayList<InfluencerPercentDistribution>();
		Supplier<List<InfluencerPercentDistribution>> resultSupplier = () -> result;
		distributionBetOnThis.stream().map(BetPredictionDistribution::getInfluencerDistribution).collect(resultSupplier, List::addAll, List::addAll);
		distributionBetOnThisFailed.stream().map(BetPredictionDistribution::getInfluencerDistribution).collect(resultSupplier, List::addAll, List::addAll);
		distributionDontBetOnThis.stream().map(BetPredictionDistribution::getInfluencerDistribution).collect(resultSupplier, List::addAll, List::addAll);
		distributionDontBetOnThisFailed.stream().map(BetPredictionDistribution::getInfluencerDistribution).collect(resultSupplier, List::addAll, List::addAll);
		return result;
	}
}
