package de.footystats.tools.services.prediction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Stores Information about the distribution of one influencer and its calculated value. One calculated value can exist for multiple predictions.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfluencerPercentDistribution {

	private Integer predictionPercent;

	// Count of successful predictions for a bet with the same influencer prediction percent value.
	private Long betSucceeded;

	// Count of failed predictions for a bet with the same influencer prediction percent value.
	private Long betFailed;

	private String influencerName;

	private PrecheckResult precheckResult; // why?
}
