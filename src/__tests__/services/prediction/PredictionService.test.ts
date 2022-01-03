import '@testing-library/jest-dom';
import prediction from '../../../app/services/prediction/PredictionService';
import { OVER } from '../../../app/types/prediction/Bet';
import BetPredictionContext from '../../../app/types/prediction/BetPredictionContext';
import mss from '../../TestUtils';

describe('PredictionService Tests', () => {
  it('Simple run', async () => {
    mss.readMatches(
      `${__dirname}/../../../../testdata/matches_expanded-1630235153-username.csv`
    );

    const matches = await mss.matchesByDay(new Date(2021, 7, 29));

    const ctx: BetPredictionContext = {
      bet: OVER,
      match: matches[0],
    };
    const predictionResult = prediction(ctx);
    expect(predictionResult).not.toBeNull();
    expect(predictionResult).toBe(3);
  });
});
