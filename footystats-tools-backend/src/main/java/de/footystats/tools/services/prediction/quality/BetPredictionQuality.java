package de.footystats.tools.services.prediction.quality;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.InfluencerPercentDistribution;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * The Document that stores all information for a prediction with a specific prediction percent value.
 *
 * Included are the influencer results of all influencer that were involved in computing the prediction percent value.
 */
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndexes({
	@CompoundIndex(name = "unique", def = "{'revision' : 1, 'bet': 1, 'predictionPercent': 1, 'revision.revision': 1}"),
	@CompoundIndex(name = "influencerDistribution", def = "{'influencerDistribution.influencerName' : 1, 'influencerDistribution.predictionPercent' : 1}")
})
@Document
@Builder(toBuilder = true, access = AccessLevel.PUBLIC)
public class BetPredictionQuality implements IBetPredictionBaseData {
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
	private Long count; // Count of predictions for a bet with the same prediction percent value.
	@Getter
	@Setter
	private List<InfluencerPercentDistribution> influencerDistribution; // The results of all influencers that participate at the computation of the prediction percent value.
}
