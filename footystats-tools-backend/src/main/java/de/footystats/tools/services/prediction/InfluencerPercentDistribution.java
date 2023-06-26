package de.footystats.tools.services.prediction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Stores Information about the distribution of one influencer and its calculated value.
 * One calculated value can exist for multiple predictions.
 */
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
