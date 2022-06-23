import TeamStatsService from '../../../app/services/stats/TeamStatsService';
import TestUtils from '../../TestUtils';

const tss =
  TestUtils.inversifyContainer.get<TeamStatsService>(TeamStatsService);

describe('Test the stats service', () => {
  it('Load team stats', async () => {
    const result = tss.readTeamStats(
      `${__dirname}/../../../../testdata/germany-bundesliga-teams-2020-to-2021-stats.csv`
    );

    expect(result).not.toBeUndefined();
    expect(result.length).toEqual(1);
  });

  it('Load team stats and try to find it', async () => {
    tss.readTeamStats(
      `${__dirname}/../../../../testdata/germany-bundesliga-teams-2021-to-2022-stats.csv`
    );

    let result = await tss.latestThree('Borussia Dortmund', 'Germany', 2021);
    expect(result).toBeDefined();
    expect(result.length).toBe(2);

    result = await tss.latestThree('BVB 09 Borussia Dortmund', 'Germany', 2021);
    expect(result.length).toBe(2);

    result = await tss.latestThree('BVB 09 Borussia Dortmund', 'Germany', 2020);
    expect(result.length).toBe(1);

    result = await tss.latestThree('BVB 09 Borussia Dortmund', 'Germany', 2022);
    expect(result.length).toBe(2);
  });

  it('Load team stats with only a year as season and try to find it', async () => {
    tss.readTeamStats(
      `${__dirname}/../../../../testdata/south-korea-k-league-1-teams-2021-to-2021-stats.csv`
    );

    const result = await tss.latestThree('Gangwon FC', 'South Korea', 2020);
    expect(result).toBeDefined();
    expect(result.length).toBe(1);
  });
});
