/* eslint-disable prettier/prettier */
import { fromUnixTime } from 'date-fns';
import tmp from 'tmp';
import { MatchStatsService } from '../../../app/services/stats/MatchStatsService';

const mss = new MatchStatsService(tmp.dirSync({prefix:'MatchStatsServiceTest'}).name);

describe('Test the match service', () => {
  beforeAll(() => {
    mss.dbService.removeDb();
  })

  it('Load matches', async () => {
    mss.readMatches(
      '../../../../testdata/matches_expanded-1630235153-username.csv'
    );

    const result = await mss.matchesByDay(
      fromUnixTime(1630243800 - (60 * 60 * 2))
    );

    expect(result).not.toBeNull();
    expect(result.length).toEqual(1);
  });
});