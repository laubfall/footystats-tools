import { BetPredictionContext } from '../../types/prediction/BetPredictionContext';
import {
  BetResultInfluencer,
  NotExecutedCause,
} from '../../types/prediction/BetResultInfluencer';
import LeaguePositionDiffInfluencer from './LeaguePositionDiffInfluencer';
import LeaguePositionInfluencer from './LeaguePositionInfluencer';
import OddsGoalsOverInfluencer from './OddsGoalsOverInfluencer';

/**
 * All known prediction influencer
 */
const betResultInfluencer: BetResultInfluencer[] = [
  new LeaguePositionInfluencer(),
  new LeaguePositionDiffInfluencer(),
  new OddsGoalsOverInfluencer(),
];

export default function prediction(ctx: BetPredictionContext): number {
  let result = 0;

  betResultInfluencer.forEach((influencer) => {
    const preCheckResult = influencer.preCheck(ctx);
    if (preCheckResult !== NotExecutedCause.NOT_ENOUGH_INFORMATION) {
      const predictionInfluence = influencer.calculateInfluence(ctx);
      if (!predictionInfluence.notExecutedCause) {
        result += predictionInfluence.amount;
      }
    }
  });

  return Math.round(result);
}
