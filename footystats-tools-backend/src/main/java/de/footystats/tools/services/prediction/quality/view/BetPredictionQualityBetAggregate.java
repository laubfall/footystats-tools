package de.footystats.tools.services.prediction.quality.view;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.outcome.IRanked;
import de.footystats.tools.services.prediction.quality.IBetPredictionBaseData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BetPredictionQualityBetAggregate implements IBetPredictionBaseData, IRanked {

	private Bet bet;
	private Long betSucceeded;
	private Long betFailed;
	private Integer predictionPercent;

	@Override
	public Bet getBet() {
		return bet;
	}

	@Override
	public void setBet(Bet bet) {
		this.bet = bet;
	}

	@Override
	public Long getBetSucceeded() {
		return betSucceeded;
	}

	@Override
	public void setBetSucceeded(Long betSucceeded) {
		this.betSucceeded = betSucceeded;
	}

	@Override
	public Long getBetFailed() {
		return betFailed;
	}

	@Override
	public void setBetFailed(Long betFailed) {
		this.betFailed = betFailed;
	}

	@Override
	public Integer getPredictionPercent() {
		return predictionPercent;
	}

	@Override
	public void setPredictionPercent(Integer predictionPercent) {
		this.predictionPercent = predictionPercent;
	}

	@Override
	public Integer predictionPercent() {
		return predictionPercent;
	}

	@Override
	public Long betSucceeded() {
		return betSucceeded;
	}

	@Override
	public Long betFailed() {
		return betFailed;
	}
}
