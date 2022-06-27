import {
  Bet,
  BetPredictionContext,
} from '../../types/prediction/BetPredictionContext';
import {
  BetResultInfluencer,
  PrecheckResult,
} from '../../types/prediction/BetResultInfluencer';
import FootyStatsBttsYesPredictionInfluencer from './influencer/FootyStatsBttsYesPredictionInfluencer';
import FootyStatsOverFTPredictionInfluencer from './influencer/FootyStatsOverFTPredictionInfluencer';
import LeaguePositionDiffInfluencer from './influencer/LeaguePositionDiffInfluencer';
import LeaguePositionInfluencer from './influencer/LeaguePositionInfluencer';
import OddsBttsYesInfluencer from './influencer/OddsBttsYesInfluencer';
import OddsGoalsOverInfluencer from './influencer/OddsGoalsOverInfluencer';
import {
  InfluencerResult,
  PredictionAnalyze,
  PredictionResult,
} from './PredictionService.types';

/**
 * All known prediction influencer
 */
const betResultInfluencer: BetResultInfluencer[] = [
  new LeaguePositionInfluencer(),
  new LeaguePositionDiffInfluencer(),
  new OddsGoalsOverInfluencer(),
  new FootyStatsOverFTPredictionInfluencer(),
  new OddsBttsYesInfluencer(),
  new FootyStatsBttsYesPredictionInfluencer(),
];

function analyze(
  ctx: BetPredictionContext,
  didPredictionCalculation: boolean,
  betOnThis: boolean
): PredictionAnalyze {
  if (didPredictionCalculation === false) {
    return 'NOT_PREDICTED';
  }

  if (ctx.match['Match Status'] !== 'complete') {
    return 'NOT_COMPLETED';
  }

  switch (ctx.bet) {
    case Bet.OVER_ZERO_FIVE: {
      const goals =
        ctx.match['Result - Away Team Goals'] +
        ctx.match['Result - Home Team Goals'];
      if (goals > 0 && betOnThis) {
        return 'SUCCESS';
      }

      return 'FAILED';
    }
    case Bet.BTTS_YES: {
      if (
        (ctx.match['Result - Away Team Goals'] > 0 &&
          ctx.match['Result - Home Team Goals'] > 0 &&
          betOnThis) ||
        ((ctx.match['Result - Away Team Goals'] === 0 ||
          ctx.match['Result - Home Team Goals'] === 0) &&
          !betOnThis)
      ) {
        return 'SUCCESS';
      }

      return 'FAILED';
    }
    default:
      return 'NOT_ANALYZED';
  }
}

export default function prediction(
  ctx: BetPredictionContext
): PredictionResult {
  let result = 0;

  const influencerDetailedResult: InfluencerResult[] = [];

  let doneInfluencerCalculations = 0;
  betResultInfluencer.forEach((influencer) => {
    const preCheckResult = influencer.preCheck(ctx);
    if (preCheckResult === PrecheckResult.OK) {
      const predictionInfluence = influencer.calculateInfluence(ctx);
      result += predictionInfluence.amount;
      doneInfluencerCalculations += 1;
      influencerDetailedResult.push({
        precheckResult: preCheckResult,
        influencerPredictionValue: predictionInfluence.amount,
        influencerName: influencer.influencerName(),
      });
    } else if (
      preCheckResult === PrecheckResult.NOT_ENOUGH_INFORMATION ||
      preCheckResult === PrecheckResult.EXCEPTION
    ) {
      influencerDetailedResult.push({
        influencerName: influencer.influencerName(),
        precheckResult: preCheckResult,
      });
    }
  });

  if (doneInfluencerCalculations > 0) {
    result = Math.round(result / doneInfluencerCalculations);
  }
  const betOnThis = result > 50;
  return {
    betSuccessInPercent: result,
    betOnThis,
    analyzeResult: analyze(ctx, doneInfluencerCalculations > 0, betOnThis),
    influencerDetailedResult,
  };
}
