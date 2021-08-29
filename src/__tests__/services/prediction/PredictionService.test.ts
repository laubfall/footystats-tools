import '@testing-library/jest-dom';
import prediction from '../../../app/services/prediction/PredictionService';
import { OVER } from '../../../app/types/prediction/Bet';
import BetPredictionContext from '../../../app/types/prediction/BetPredictionContext';

describe('PredictionService Tests', () => {
  it('Simple run', () => {
    const ctx: BetPredictionContext = {
      bet: OVER,
      match: {
        away: {
          leaguePosition: 3,
          team: 'Team 1',
          xG: 1,
          xGA: 1,
        },
        home: {
          leaguePosition: 5,
          team: 'Team 2',
          xG: 1,
          xGA: 1,
        },
        leagueTeamsCount: 20,
      },
    };
    const predictionResult = prediction(ctx);
    expect(predictionResult).not.toBeNull();
  });
});
