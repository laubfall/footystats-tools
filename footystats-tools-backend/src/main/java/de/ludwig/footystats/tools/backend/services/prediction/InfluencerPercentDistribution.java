package de.ludwig.footystats.tools.backend.services.prediction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class InfluencerPercentDistribution
{
	@Getter
	@Setter
	private Integer predictionPercent;
	@Getter
	@Setter
	private Long betSucceeded;
	@Getter
	@Setter
	private Long betFailed;
	@Getter
	@Setter
	private String influencerName;
	@Getter
	@Setter
	private PrecheckResult precheckResult; // why?
}
