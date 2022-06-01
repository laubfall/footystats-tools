import '@testing-library/jest-dom';
import TestUtils from '../../TestUtils';
import Match from '../../../app/services/match/IMatchService';

const mss = TestUtils.matchStatsService;
const ms = TestUtils.matchService;

describe('PredictionQualityService Tests', () => {
  it('Do prediction quality calculation', async () => {
    await mss.readMatches(
      `${__dirname}/../../../../testdata/matches_expanded-1630235153-username.csv`
    );

    const precast = await TestUtils.predictionQualityService.precast();
    expect(precast).toBeDefined();
    expect(precast.revision).toBe(0);
    expect(precast.predictionsToAssess).toBe(1);

    const report = await TestUtils.predictionQualityService.computeQuality();
    expect(report).toBeDefined();

    const matches = await ms.matchesByRevision(0);
    const match: Match = matches[0] as Match;
    expect(match).toBeDefined();
    expect(match.revision).toBe(0);
  });
});
