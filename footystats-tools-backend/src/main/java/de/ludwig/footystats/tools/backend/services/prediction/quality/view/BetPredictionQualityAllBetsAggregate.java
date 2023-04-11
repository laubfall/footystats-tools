package de.ludwig.footystats.tools.backend.services.prediction.quality.view;

import de.ludwig.footystats.tools.backend.services.prediction.Bet;

/**
 * Aggregated counts independent of the prediction percent values.
 *
 * @param bet Type of bet counts are computed for.
 * @param assessed Total count of all predictions for this bet type.
 * @param betSuccess Count successful bets.
 * @param betFailed Count failed bets.
 * @param dontBetSuccess Count success don't bet.
 * @param dontBetFailed Count failed don't bet.
 */
public record BetPredictionQualityAllBetsAggregate(Bet bet, Long assessed, Long betSuccess, Long betFailed, Long dontBetSuccess,
												   Long dontBetFailed) {

}
