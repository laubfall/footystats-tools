import {
  dbService,
  readTeamStats,
} from '../../../app/services/stats/TeamStatsService';

describe('Test the stats service', () => {
  beforeAll(() => {
    dbService.removeDb();
  });

  it('Load team stats', () => {
    const result = readTeamStats(
      '../../../../testdata/germany-bundesliga-teams-2020-to-2021-stats.csv'
    );

    expect(result).not.toBeUndefined();
    expect(result.length).toEqual(1);
  });
});
