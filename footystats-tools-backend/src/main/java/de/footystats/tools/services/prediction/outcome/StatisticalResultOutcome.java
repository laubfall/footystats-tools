package de.footystats.tools.services.prediction.outcome;

import de.footystats.tools.services.prediction.Bet;
import java.io.Serializable;
import java.util.List;

/**
 * Shows the statistical possibility that a bet with a given prediction will be successful.
 *
 * @param bet                                 The bet the prediction was made for.
 * @param betStatisticalSuccess               The statistical possibility that the bet will be successful.
 * @param influencerStatisticalResultOutcomes The statistical possibility of the calculated possibilities of every influencer that was involved in the
 *                                            prediction.
 */
public record StatisticalResultOutcome(Bet bet, Double betStatisticalSuccess, Ranking ranking,
									   List<InfluencerStatisticalResultOutcome> influencerStatisticalResultOutcomes) implements Serializable {

}
