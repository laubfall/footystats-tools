package de.footystats.tools.controller.quality;

import de.footystats.tools.services.prediction.quality.view.BetPredictionQualityAllBetsAggregate;
import de.footystats.tools.services.prediction.quality.view.BetPredictionQualityBetAggregate;
import de.footystats.tools.services.prediction.quality.view.BetPredictionQualityInfluencerAggregate;
import java.util.List;
import java.util.Map;

/**
 * Just brackets around a minimal set of bet quality data. The structure represents the way how data is displayed inside the frontend. We have a list
 * with all bets and some information about how many of them were checked for quality.
 * <p>
 * Then there are graphs that show the percent distribution for on bet (depending on the choosen one from the list). And last detailed influencer
 * information that were involved while computing predictions for this bet.
 *
 * @param betPredictionResults              List for all types of bets we have quality measurements for.
 * @param betPredictionDistributions        List of percent distribution (for succeeded and failed bets) for one type of bet in case we want to bet.
 * @param betInfluencerPercentDistributions Influencer percent distributions keyed by influencer name. Only influencer for the chosen bet are
 *                                          included.
 */
public record Report(List<BetPredictionQualityAllBetsAggregate> betPredictionResults,
					 List<BetPredictionQualityBetAggregate> betPredictionDistributions,
					 Map<String, List<BetPredictionQualityInfluencerAggregate>> betInfluencerPercentDistributions,
					 Map<String, List<BetPredictionQualityInfluencerAggregate>> dontBetInfluencerPercentDistributions) {

}
