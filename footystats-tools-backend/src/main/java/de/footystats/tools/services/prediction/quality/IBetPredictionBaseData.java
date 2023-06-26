package de.footystats.tools.services.prediction.quality;

import de.footystats.tools.services.prediction.Bet;

public interface IBetPredictionBaseData {
	Bet getBet();

	Long getBetSucceeded();

	Long getBetFailed();

	Integer getPredictionPercent();

	void setBet(Bet bet);

	void setBetSucceeded(Long betSucceeded);

	void setBetFailed(Long betFailed);

	void setPredictionPercent(Integer predictionPercent);
}
