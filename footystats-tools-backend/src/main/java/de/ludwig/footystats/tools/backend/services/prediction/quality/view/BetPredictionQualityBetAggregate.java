package de.ludwig.footystats.tools.backend.services.prediction.quality.view;

import de.ludwig.footystats.tools.backend.services.prediction.Bet;
import de.ludwig.footystats.tools.backend.services.prediction.quality.IBetPredictionBaseData;

public class BetPredictionQualityBetAggregate implements IBetPredictionBaseData {
	private Bet bet;
	private Long betSucceeded;
	private Long betFailed;
	private Integer predictionPercent;

	@Override
	public Bet getBet() {
		return bet;
	}

	@Override
	public Long getBetSucceeded() {
		return betSucceeded;
	}

	@Override
	public Long getBetFailed() {
		return betFailed;
	}

	@Override
	public Integer getPredictionPercent() {
		return predictionPercent;
	}

	@Override
	public void setBet(Bet bet) {
		this.bet = bet;
	}

	@Override
	public void setBetSucceeded(Long betSucceeded) {
		this.betSucceeded = betSucceeded;
	}

	@Override
	public void setBetFailed(Long betFailed) {
		this.betFailed = betFailed;
	}

	@Override
	public void setPredictionPercent(Integer predictionPercent) {
		this.predictionPercent = predictionPercent;
	}
}
