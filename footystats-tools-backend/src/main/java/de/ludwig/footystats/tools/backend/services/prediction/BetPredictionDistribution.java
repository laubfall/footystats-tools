package de.ludwig.footystats.tools.backend.services.prediction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@JsonComponent
public class BetPredictionDistribution {
	@Getter
	@Setter
	private Float predictionPercent;
	@Getter
	@Setter
	private Long count;
	@Getter
	@Setter
	private List<InfluencerPercentDistribution> influencerDistribution;
}
