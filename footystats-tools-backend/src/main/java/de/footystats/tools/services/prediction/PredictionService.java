package de.footystats.tools.services.prediction;

import de.footystats.tools.services.prediction.influencer.BetPredictionContext;
import de.footystats.tools.services.prediction.influencer.BetResultInfluencer;
import de.footystats.tools.services.prediction.influencer.FootyStatsBttsYesPredictionInfluencer;
import de.footystats.tools.services.prediction.influencer.FootyStatsOverFTPredictionInfluencer;
import de.footystats.tools.services.prediction.influencer.OddsBttsYesInfluencer;
import de.footystats.tools.services.prediction.influencer.OddsGoalOverInfluencer;
import de.footystats.tools.services.prediction.influencer.XgHomeAndAwayInfluencer;
import de.footystats.tools.services.prediction.influencer.XgOverOneFiveInfluencer;
import de.footystats.tools.services.prediction.influencer.XgOverTwoFiveInfluencer;
import de.footystats.tools.services.prediction.influencer.XgOverZeroFiveInfluencer;
import de.footystats.tools.services.prediction.influencer.team.AwayTeamLeaguePosInfluencer;
import de.footystats.tools.services.prediction.influencer.team.AwayTeamWinOddsInfluencer;
import de.footystats.tools.services.prediction.influencer.team.HomeTeamLeaguePosInfluencer;
import de.footystats.tools.services.prediction.influencer.team.HomeTeamWinOddsInfluencer;
import de.footystats.tools.services.stats.MatchStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service to calculate the prediction for different bets for a match.
 */
@Slf4j
@Service
public class PredictionService {

	public static final int LOWER_EXCLUSIVE_BORDER_BET_ON_THIS = 50;

	private final Map<Bet, BetResultInfluencer[]> betResultInfluencerConfig = Map.of(
		Bet.BTTS_YES,
		new BetResultInfluencer[]{new OddsBttsYesInfluencer(), new FootyStatsBttsYesPredictionInfluencer(), new XgHomeAndAwayInfluencer()},
		Bet.OVER_ZERO_FIVE,
		new BetResultInfluencer[]{new OddsGoalOverInfluencer(), new XgOverZeroFiveInfluencer(), new FootyStatsOverFTPredictionInfluencer(), new AwayTeamLeaguePosInfluencer(), new HomeTeamLeaguePosInfluencer()},
		Bet.OVER_ONE_FIVE,
		new BetResultInfluencer[]{new OddsGoalOverInfluencer(), new XgOverOneFiveInfluencer(), new FootyStatsOverFTPredictionInfluencer(), new AwayTeamLeaguePosInfluencer(), new HomeTeamLeaguePosInfluencer()},
		Bet.OVER_TWO_FIVE,
		new BetResultInfluencer[]{new XgOverTwoFiveInfluencer(), new FootyStatsOverFTPredictionInfluencer(), new AwayTeamLeaguePosInfluencer(), new HomeTeamLeaguePosInfluencer()},
		Bet.HOME_WIN, new BetResultInfluencer[]{new HomeTeamWinOddsInfluencer()},
		Bet.AWAY_WIN, new BetResultInfluencer[]{new AwayTeamWinOddsInfluencer()}
	);

	private static PredictionAnalyze analyzeBttsYes(BetPredictionContext ctx) {
		if (ctx.match().getResultAwayTeamGoals() > 0 &&
			ctx.match().getResultHomeTeamGoals() > 0) {
			return PredictionAnalyze.SUCCESS;
		}

		return PredictionAnalyze.FAILED;
	}

	private static PredictionAnalyze analyzeOverTwoFive(BetPredictionContext ctx) {
		return analyzeOverXFive(ctx, 2);
	}

	private static PredictionAnalyze analyzeOverOneFive(BetPredictionContext ctx) {
		return analyzeOverXFive(ctx, 1);
	}

	private static PredictionAnalyze analyzeOverZeroFive(BetPredictionContext ctx) {
		return analyzeOverXFive(ctx, 0);
	}

	private static PredictionAnalyze analyzeOverXFive(BetPredictionContext ctx, int expectedGoals) {
		var goals = ctx.match().getResultAwayTeamGoals() +
			ctx.match().getResultHomeTeamGoals();
		if (goals > expectedGoals) {
			return PredictionAnalyze.SUCCESS;
		}

		return PredictionAnalyze.FAILED;
	}

	private static PredictionAnalyze analyzeTeamWin(BetPredictionContext ctx) {
		if (ctx.bet() == Bet.HOME_WIN) {
			if (ctx.match().getResultHomeTeamGoals() > ctx.match().getResultAwayTeamGoals()) {
				return PredictionAnalyze.SUCCESS;
			}
		} else if (ctx.bet() == Bet.AWAY_WIN) {
			if (ctx.match().getResultAwayTeamGoals() > ctx.match().getResultHomeTeamGoals()) {
				return PredictionAnalyze.SUCCESS;
			}
		}

		return PredictionAnalyze.FAILED;
	}

	/**
	 * Calculate if the prediction was correct.
	 *
	 * @param ctx                      Mandatory. The context to use.
	 * @param didPredictionCalculation True if the prediction was calculated.
	 * @return The result of the analysis.
	 */
	public final PredictionAnalyze analyze(
		BetPredictionContext ctx,
		boolean didPredictionCalculation) {
		if (!didPredictionCalculation) {
			return PredictionAnalyze.NOT_PREDICTED;
		}

		if (ctx.match().getMatchStatus() != MatchStatus.complete) {
			return PredictionAnalyze.NOT_COMPLETED;
		}

		return switch (ctx.bet()) {
			case OVER_ZERO_FIVE -> analyzeOverZeroFive(ctx);
			case OVER_ONE_FIVE -> analyzeOverOneFive(ctx);
			case OVER_TWO_FIVE -> analyzeOverTwoFive(ctx);
			case BTTS_YES -> analyzeBttsYes(ctx);
			case HOME_WIN, AWAY_WIN -> analyzeTeamWin(ctx);
			default -> PredictionAnalyze.NOT_ANALYZED;
		};
	}

	public PredictionResult prediction(BetPredictionContext ctx) {
		var result = 0;

		List<InfluencerResult> influencerDetailedResult = new ArrayList<>();

		var doneInfluencerCalculations = 0;

		BetResultInfluencer[] betResultInfluencer = betResultInfluencerConfig.get(ctx.bet());
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
		log.debug("Done prediction bet {} and match: {} - {} with result: {} and bet on this: {}", ctx.bet(),
			ctx.match().getAwayTeam(),
			ctx.match().getHomeTeam(), result,
			betOnThis);
		return new PredictionResult(result, betOnThis, analyze(ctx, doneInfluencerCalculations > 0),
			influencerDetailedResult);
	}
}
