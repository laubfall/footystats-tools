import { BetPredictionContext } from '../../types/prediction/BetPredictionContext';
import {
  BetResultInfluencer,
  PrecheckResult,
} from '../../types/prediction/BetResultInfluencer';
import FootyStatsBttsYesPredictionInfluencer from './FootyStatsBttsYesPredictionInfluencer';
import FootyStatsOverFTPredictionInfluencer from './FootyStatsOverFTPredictionInfluencer';
import LeaguePositionDiffInfluencer from './LeaguePositionDiffInfluencer';
import LeaguePositionInfluencer from './LeaguePositionInfluencer';
import OddsBttsYesInfluencer from './OddsBttsYesInfluencer';
import OddsGoalsOverInfluencer from './OddsGoalsOverInfluencer';

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

export default function prediction(ctx: BetPredictionContext): number {
  let result = 0;

  let doneInfluencerCalculations = 0;
  betResultInfluencer.forEach((influencer) => {
    const preCheckResult = influencer.preCheck(ctx);
    if (preCheckResult === PrecheckResult.OK) {
      const predictionInfluence = influencer.calculateInfluence(ctx);
      if (!predictionInfluence.notExecutedCause) {
        result += predictionInfluence.amount;
        doneInfluencerCalculations += 1;
      }
    }
  });

  return Math.round(result / doneInfluencerCalculations);
}

export type PredictionResult = {
  betSuccessInPercent: number;
  betOnThis: boolean;
};

export enum PredictionSuccess {
  SUCCESS,
  CLOSE,
  FAILED,
}
