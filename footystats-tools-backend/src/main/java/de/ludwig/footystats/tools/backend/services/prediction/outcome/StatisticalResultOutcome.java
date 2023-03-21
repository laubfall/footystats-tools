package de.ludwig.footystats.tools.backend.services.prediction.outcome;

import de.ludwig.footystats.tools.backend.services.prediction.Bet;

import java.util.List;

/**
 * Shows the statistical possibility that a bet with a given prediction will be successful.
 */
public record StatisticalResultOutcome(Bet bet, Double betStatisticalSuccess, List<InfluencerStatisticalResultOutcome> influencerStatisticalResultOutcomes) {
}
