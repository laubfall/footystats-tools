import TeamStatsService from '../../../app/services/stats/TeamStatsService';

const tss = new TeamStatsService('inMemory');

describe('Test the stats service', () => {
  it('Load team stats', async () => {
    const result = tss.readTeamStats(
      `${__dirname}/../../../../testdata/germany-bundesliga-teams-2020-to-2021-stats.csv`
    );

    expect(result).not.toBeUndefined();
    expect(result.length).toEqual(1);
  });
});
