import '@testing-library/jest-dom';
import TestUtils from '../../TestUtils';
import Match from '../../../app/services/match/IMatchService';
import { Bet } from '../../../app/types/prediction/BetPredictionContext';

const mss = TestUtils.matchStatsService;
const ms = TestUtils.matchService;

describe('PredictionQualityService Tests', () => {
  it('Do prediction quality calculation', async () => {
    await mss.readMatches(
      `${__dirname}/../../../../testdata/predictionQualityService/matches_expanded-01.csv`
    );

    const precast = await TestUtils.predictionQualityService.precast();
    expect(precast).toBeDefined();
    expect(precast.revision).toBe(0);
    expect(precast.predictionsToAssess).toBe(1);

    let report = await TestUtils.predictionQualityService.computeQuality();
    expect(report).toBeDefined();
    expect(report.revision).toBe(0);
    expect(report.measurements).toBeDefined();
    expect(report.measurements.length).toBeGreaterThan(0);

    let measureO05 = report.measurements.find(
      (m) => m.bet === Bet.OVER_ZERO_FIVE
    );
    expect(measureO05).toBeDefined();
    expect(measureO05?.countAssessed).toBe(1);

    const matches = await ms.matchesByRevision(0);
    const match: Match = matches[0] as Match;
    expect(match).toBeDefined();
    expect(match.revision).toBe(0);

    await mss.readMatches(
      `${__dirname}/../../../../testdata/predictionQualityService/matches_expanded-02.csv`
    );
    report = await TestUtils.predictionQualityService.computeQuality();
    expect(report).toBeDefined();
    expect(report.revision).toBe(0);
    expect(report.measurements).toBeDefined();
    expect(report.measurements.length).toBeGreaterThan(0);

    measureO05 = report.measurements.find((m) => m.bet === Bet.OVER_ZERO_FIVE);
    expect(measureO05).toBeDefined();
    expect(measureO05?.countAssessed).toBe(2);

    const r = await TestUtils.predictionQualityService.dbService.asyncFind({
      revision: 0,
    });
    expect(r.length).toBe(1);
  });
});
