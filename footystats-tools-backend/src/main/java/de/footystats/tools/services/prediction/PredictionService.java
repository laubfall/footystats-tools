package de.footystats.tools.services.prediction;

import de.footystats.tools.services.prediction.influencer.*;
import de.footystats.tools.services.stats.MatchStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PredictionService {
	public static final int LOWER_EXCLUSIVE_BORDER_BET_ON_THIS = 50;

	private final BetResultInfluencer[] betResultInfluencer = {
		new OddsBttsYesInfluencer(),
		new OddsGoalOverInfluencer(),
		new FootyStatsBttsYesPredictionInfluencer(),
		new FootyStatsOverFTPredictionInfluencer(),
		new XgOverZeroFiveInfluencer(),
		new XgOverOneFiveInfluencer()
	};

	public PredictionAnalyze analyze(
		BetPredictionContext ctx,
		boolean didPredictionCalculation,
		boolean betOnThis) {
		if (!didPredictionCalculation) {
			return PredictionAnalyze.NOT_PREDICTED;
		}

		if (ctx.match().getMatchStatus() != MatchStatus.complete) {
			return PredictionAnalyze.NOT_COMPLETED;
		}

		switch (ctx.bet()) {
			case OVER_ZERO_FIVE: {
				var goals = ctx.match().getResultAwayTeamGoals() +
					ctx.match().getResultHomeTeamGoals();
				if ((goals > 0 && betOnThis) || (goals == 0 && !betOnThis)) {
					return PredictionAnalyze.SUCCESS;
				}

				return PredictionAnalyze.FAILED;
			}
			case OVER_ONE_FIVE: {
				var goals = ctx.match().getResultAwayTeamGoals() +
					ctx.match().getResultHomeTeamGoals();
				if ((goals > 1 && betOnThis) || (goals <= 1 && !betOnThis)) {
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
