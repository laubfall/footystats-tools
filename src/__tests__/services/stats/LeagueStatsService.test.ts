import TestUtils from '../../TestUtils';

const lss = TestUtils.leagueStatsService;

describe('Test the stats service', () => {
  it('Load team stats', async () => {
    const result = lss.readLeagueStats(
      `${__dirname}/../../../../testdata/germany-bundesliga-league-2020-to-2021-stats.csv`
    );

    expect(result).not.toBeUndefined();
    expect(result.length).toEqual(1);

    const leagueStats = result[0];
    expect(leagueStats.number_of_clubs).toBeDefined();

    const ls = await lss.findeLeagueStatsBy('Bundesliga', '2020/2021');
    expect(ls).toBeDefined();
    expect(ls).not.toBeNull();
  });
});
