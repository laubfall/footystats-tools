package de.ludwig.footystats.tools.backend.services.prediction;

import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityRevision;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@JsonComponent
public class BetPredictionDistribution {
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
