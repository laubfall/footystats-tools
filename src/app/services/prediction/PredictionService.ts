import BetPredictionContext from '../../types/prediction/BetPredictionContext';
import BetResultInfluencer from '../../types/prediction/BetResultInfluencer';
import calculateInfluenceLeaguePosition from './LeaguePositionInfluencer';

const betResultInfluencer: BetResultInfluencer[] = [
  { calculateInfluence: calculateInfluenceLeaguePosition },
];

export default function prediction(ctx: BetPredictionContext): number {
  let result = 0;

  betResultInfluencer.forEach((influencer) => {
    const predictionInfluence = influencer.calculateInfluence(ctx);
    if (!predictionInfluence.notExecutedCause) {
      result += predictionInfluence.amount;
    }
  });

  return result;
}
