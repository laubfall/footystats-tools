import { readTeamStats } from '../../../app/services/stats/TeamStatsService';

describe('Test the stats service', () => {
  it('Load team stats', () => {
    const result = readTeamStats(
      '../../../../testdata/germany-bundesliga-teams-2020-to-2021-stats.csv'
    );

    expect(result).not.toBeNull();
    expect(result.length).toEqual(1);
  });
});
