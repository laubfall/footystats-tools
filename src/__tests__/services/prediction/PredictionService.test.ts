import '@testing-library/jest-dom';
import prediction from '../../../app/services/prediction/PredictionService';
import {
  Bet,
  BetPredictionContext,
} from '../../../app/types/prediction/BetPredictionContext';
import TestUtils from '../../TestUtils';

const mss = TestUtils.matchStatsService;

describe('PredictionService Tests', () => {
  it('Simple run', async () => {
    mss.readMatches(
      `${__dirname}/../../../../testdata/matches_expanded-1630235153-username.csv`
    );

    const matches = await mss.matchesByDay(new Date(2021, 7, 29));

    const ctx: BetPredictionContext = {
      bet: Bet.OVER_ZERO_FIVE,
      match: matches[0],
    };
    const predictionResult = prediction(ctx);
    expect(predictionResult).not.toBeNull();
    expect(predictionResult.betSuccessInPercent).toBe(76);
    expect(predictionResult.betOnThis).toBe(true);
    expect(predictionResult.analyzeResult).toEqual('SUCCESS');
  });

  it('Test O0.5 bet with NaN values in match stats', async () => {
    mss.readMatches(
      `${__dirname}/../../../../testdata/matches_expanded-check-O05-with-nan.csv`
    );

    const matches = await mss.matchesByDay(new Date(2021, 7, 29));

    const ctx: BetPredictionContext = {
      bet: Bet.OVER_ZERO_FIVE,
      match: matches[0],
    };
    const predictionResult = prediction(ctx);
    expect(predictionResult).not.toBeNull();
    expect(predictionResult.betSuccessInPercent).toBe(100);
    expect(predictionResult.betOnThis).toBe(true);
    expect(predictionResult.analyzeResult).toEqual('SUCCESS');
  });
});
