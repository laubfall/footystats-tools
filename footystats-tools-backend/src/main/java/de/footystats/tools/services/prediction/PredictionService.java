package de.footystats.tools.services.prediction;

import de.footystats.tools.services.prediction.influencer.AwayTeamLeaguePosInfluencer;
import de.footystats.tools.services.prediction.influencer.BetPredictionContext;
import de.footystats.tools.services.prediction.influencer.BetResultInfluencer;
import de.footystats.tools.services.prediction.influencer.FootyStatsBttsYesPredictionInfluencer;
import de.footystats.tools.services.prediction.influencer.FootyStatsOverFTPredictionInfluencer;
import de.footystats.tools.services.prediction.influencer.HomeTeamLeaguePosInfluencer;
import de.footystats.tools.services.prediction.influencer.OddsBttsYesInfluencer;
import de.footystats.tools.services.prediction.influencer.OddsGoalOverInfluencer;
import de.footystats.tools.services.prediction.influencer.XgHomeAndAwayInfluencer;
import de.footystats.tools.services.prediction.influencer.XgOverOneFiveInfluencer;
import de.footystats.tools.services.prediction.influencer.XgOverZeroFiveInfluencer;
import de.footystats.tools.services.stats.MatchStatus;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service to calculate the prediction for different bets for a match.
 */
@Service
public class PredictionService {

	public static final int LOWER_EXCLUSIVE_BORDER_BET_ON_THIS = 50;

	private final BetResultInfluencer[] betResultInfluencer = {
		new OddsBttsYesInfluencer(),
		new OddsGoalOverInfluencer(),
		new FootyStatsBttsYesPredictionInfluencer(),
		new FootyStatsOverFTPredictionInfluencer(),
		new XgOverZeroFiveInfluencer(),
		new XgOverOneFiveInfluencer(),
		new AwayTeamLeaguePosInfluencer(),
		new HomeTeamLeaguePosInfluencer(),
		new XgHomeAndAwayInfluencer()
	};

	private static PredictionAnalyze analyzeBttsYes(BetPredictionContext ctx, boolean betOnThis) {
		if ((ctx.match().getResultAwayTeamGoals() > 0 &&
			ctx.match().getResultHomeTeamGoals() > 0 &&
			betOnThis) ||
			((ctx.match().getResultAwayTeamGoals() == 0 ||
				ctx.match().getResultHomeTeamGoals() == 0) &&
				!betOnThis)) {
			return PredictionAnalyze.SUCCESS;
		}

		return PredictionAnalyze.FAILED;
	}

	private static PredictionAnalyze analyzeOverOneFive(BetPredictionContext ctx, boolean betOnThis) {
		var goals = ctx.match().getResultAwayTeamGoals() +
			ctx.match().getResultHomeTeamGoals();
		if ((goals > 1 && betOnThis) || (goals <= 1 && !betOnThis)) {
			return PredictionAnalyze.SUCCESS;
		}

		return PredictionAnalyze.FAILED;
	}

	private static PredictionAnalyze analyzeOverZeroFive(BetPredictionContext ctx, boolean betOnThis) {
		var goals = ctx.match().getResultAwayTeamGoals() +
			ctx.match().getResultHomeTeamGoals();
		if ((goals > 0 && betOnThis) || (goals == 0 && !betOnThis)) {
			return PredictionAnalyze.SUCCESS;
		}

		return PredictionAnalyze.FAILED;
	}

	/**
	 * Calculate if the prediction was correct.
	 *
	 * @param ctx                      Mandatory. The context to use.
	 * @param didPredictionCalculation True if the prediction was calculated.
	 * @param betOnThis                True if prediction said bet on this bet, otherwise false.
	 * @return The result of the analysis.
	 */
	public final PredictionAnalyze analyze(
		BetPredictionContext ctx,
		boolean didPredictionCalculation,
		boolean betOnThis) {
		if (!didPredictionCalculation) {
			return PredictionAnalyze.NOT_PREDICTED;
		}

		if (ctx.match().getMatchStatus() != MatchStatus.complete) {
			return PredictionAnalyze.NOT_COMPLETED;
		}

		return switch (ctx.bet()) {
			case OVER_ZERO_FIVE -> analyzeOverZeroFive(ctx, betOnThis);
			case OVER_ONE_FIVE -> analyzeOverOneFive(ctx, betOnThis);
			case BTTS_YES -> analyzeBttsYes(ctx, betOnThis);
			default -> PredictionAnalyze.NOT_ANALYZED;
		};
	}

	public PredictionResult prediction(BetPredictionContext ctx) {
		var result = 0;

		List<InfluencerResult> influencerDetailedResult = new ArrayList<>();

		var doneInfluencerCalculations = 0;

		for (BetResultInfluencer influencer : betResultInfluencer) {

			var preCheckResult = influencer.preCheck(ctx);
			if (preCheckResult == PrecheckResult.OK) {
				var predictionInfluence = influencer.calculateInfluence(ctx);
				result += predictionInfluence;
				doneInfluencerCalculations += 1;
				influencerDetailedResult
					.add(new InfluencerResult(influencer.influencerName(), predictionInfluence, preCheckResult));
			} else if (preCheckResult == PrecheckResult.NOT_ENOUGH_INFORMATION ||
				preCheckResult == PrecheckResult.EXCEPTION) {
				influencerDetailedResult.add(new InfluencerResult(influencer.influencerName(), 0, preCheckResult));
			}
		}

		if (doneInfluencerCalculations > 0) {
			result = result / doneInfluencerCalculations;
		}
		var betOnThis = result > LOWER_EXCLUSIVE_BORDER_BET_ON_THIS;
		return new PredictionResult(result, betOnThis, analyze(ctx, doneInfluencerCalculations > 0, betOnThis),
			influencerDetailedResult);
	}
}
