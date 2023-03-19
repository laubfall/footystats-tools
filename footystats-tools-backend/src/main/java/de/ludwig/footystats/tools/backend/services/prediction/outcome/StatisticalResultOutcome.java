package de.ludwig.footystats.tools.backend.services.prediction.outcome;

import java.util.List;

/**
 * Shows the statistical possibility that a bet with a given prediction will be successful.
 */
public record StatisticalResultOutcome(Double betStatisticalSuccess, List<InfluencerStatisticalResultOutcome> influencerStatisticalResultOutcomes) {
}
