package de.ludwig.footystats.tools.backend.services.prediction.quality;

import de.ludwig.footystats.tools.backend.services.prediction.Bet;
import de.ludwig.footystats.tools.backend.services.prediction.InfluencerPercentDistribution;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityRevision;
import lombok.*;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@JsonComponent
@Document
@Builder(toBuilder = true)
public class BetPredictionAggregate {
	@Id
	@Getter
	@Setter
	private String _id; // Mongo document id. Required in order to be able to delete this.
	@Getter
	@Setter
	private PredictionQualityRevision revision;
	@Getter
	@Setter
	private Bet bet;
	@Getter
	@Setter
	private Long betSucceeded; // PredictionAnalyze.SUCCESS either for bet or don't bet
	@Getter
	@Setter
	private Long betFailed; // PredictionAnalyze.FAILED either for bet or don't bet
	@Getter
	@Setter
	private Integer predictionPercent;
	@Getter
	@Setter
	private Long count;
	@Getter
	@Setter
	private List<InfluencerPercentDistribution> influencerDistribution;
}
