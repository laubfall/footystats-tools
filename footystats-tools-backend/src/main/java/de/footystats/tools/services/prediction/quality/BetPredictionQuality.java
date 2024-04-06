package de.footystats.tools.services.prediction.quality;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.InfluencerPercentDistribution;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The Document that stores all information for a prediction with a specific prediction percent value.
 * <p>
 * Included are the influencer results of all influencer that were involved in computing the prediction percent value.
 */
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndex(name = "unique", def = "{'revision' : 1, 'bet': 1, 'predictionPercent': 1, 'revision.revision': 1}")
@CompoundIndex(name = "influencerDistribution", def = "{'influencerDistribution.influencerName' : 1, 'influencerDistribution.predictionPercent' : 1}")
@Document
@Builder(toBuilder = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
public class BetPredictionQuality implements IBetPredictionBaseData {

	@Id
	private String _id; // Mongo document id. Required in order to be able to delete this.

	private PredictionQualityRevision revision;

	private Bet bet;

	private Long betSucceeded; // PredictionAnalyze.SUCCESS either for bet or don't bet

	private Long betFailed; // PredictionAnalyze.FAILED either for bet or don't bet

	private Integer predictionPercent;

	private Long count; // Count of predictions for a bet with the same prediction percent value.

	private List<InfluencerPercentDistribution> influencerDistribution; // The results of all influencers that participate at the computation of the prediction percent value.
}
