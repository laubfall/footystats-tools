import tmp from 'tmp';
import TeamStatsService from '../../../app/services/stats/TeamStatsService';

const tss = new TeamStatsService(
  tmp.dirSync({ prefix: 'TeamStatsServiceTest' }).name
);

describe('Test the stats service', () => {
  beforeAll(() => {
    tss.dbService.removeDb();
  });

  it('Load team stats', async () => {
    const result = tss.readTeamStats(
      '../../../../testdata/germany-bundesliga-teams-2020-to-2021-stats.csv'
    );

    expect(result).not.toBeUndefined();
    expect(result.length).toEqual(1);
  });
});
