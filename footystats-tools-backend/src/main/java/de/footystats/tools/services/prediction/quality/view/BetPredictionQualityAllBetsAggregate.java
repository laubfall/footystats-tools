package de.footystats.tools.services.prediction.quality.view;

import de.footystats.tools.services.prediction.Bet;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Aggregated counts independent of the prediction percent values.
 *
 * @param bet            Type of bet counts are computed for.
 * @param assessed       Total count of all predictions for this bet type.
 * @param betSuccess     Count successful bets.
 * @param betFailed      Count failed bets.
 * @param dontBetSuccess Count of successful bets but prediction was "don't bet".
 * @param dontBetFailed  Count of failed bets but prediction was "don't bet" (so this is the positive case: don't bet and bet failed indeed).
 */
public record BetPredictionQualityAllBetsAggregate(@Schema(enumAsRef = true, implementation = Bet.class) Bet bet,
                                                   Long assessed, Long betSuccess, Long betFailed,
                                                   Long dontBetSuccess,
                                                   Long dontBetFailed) {

}
