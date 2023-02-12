package de.ludwig.footystats.tools.backend.services.prediction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;

@AllArgsConstructor
@NoArgsConstructor
@JsonComponent
public class InfluencerPercentDistribution
{
	@Getter
	@Setter
	private Integer predictionPercent;
	@Getter
	@Setter
	private Long count;
	@Getter
	@Setter
	private String influencerName;
	@Getter
	@Setter
	private PrecheckResult precheckResult;
}
