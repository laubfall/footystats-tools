package de.footystats.tools.services.prediction.outcome;

/**
 * Describes the statistical outcome for a successful bet for one influencer and its computed bet prediction.
 *
 * @param influencerName Name of the influencer.
 * @param statisticalOutcomeBetSuccess Possibility in percent that the bet is successful for a computed bet prediction
 *                                     by this influencer.
 */
public record InfluencerStatisticalResultOutcome(String influencerName, Double statisticalOutcomeBetSuccess) {
}
