package de.ludwig.footystats.tools.backend.services.prediction;

import de.ludwig.footystats.tools.backend.services.stats.MatchStatus;
import de.ludwig.footystats.tools.backend.services.prediction.influencer.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PredictionService {

	private final BetResultInfluencer[] betResultInfluencer = {
			new OddsBttsYesInfluencer(),
			new OddsGoalOverInfluencer(),
			new FootyStatsBttsYesPredictionInfluencer(),
			new FootyStatsOverFTPredictionInfluencer()
	};

	public PredictionAnalyze analyze(
			BetPredictionContext ctx,
			boolean didPredictionCalculation,
			boolean betOnThis) {
		if (didPredictionCalculation == false) {
			return PredictionAnalyze.NOT_PREDICTED;
		}

		if (ctx.match().getMatchStatus() != MatchStatus.complete) {
			return PredictionAnalyze.NOT_COMPLETED;
		}

		switch (ctx.bet()) {
			case OVER_ZERO_FIVE: {
				var goals = ctx.match().getResultAwayTeamGoals() +
						ctx.match().getResultHomeTeamGoals();
				if (goals > 0 && betOnThis) {
					return PredictionAnalyze.SUCCESS;
				}

				return PredictionAnalyze.FAILED;
			}
			case BTTS_YES: {
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
			default:
				return PredictionAnalyze.NOT_ANALYZED;
		}
	}

	public PredictionResult prediction(BetPredictionContext ctx) {
		var result = 0F;

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
				influencerDetailedResult.add(new InfluencerResult(influencer.influencerName(), 0F, preCheckResult));
			}
		}
		;

		if (doneInfluencerCalculations > 0) {
			result = Math.round(result / doneInfluencerCalculations);
		}
		var betOnThis = result > 50;
		return new PredictionResult(result, betOnThis, analyze(ctx, doneInfluencerCalculations > 0, betOnThis),
				influencerDetailedResult);
	}
}
