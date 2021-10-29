/* eslint-disable prettier/prettier */
import { fromUnixTime } from 'date-fns';
import {
  dbService,
  matchesByDay,
  readMatches,
} from '../../../app/services/stats/MatchStatsService';

describe('Test the match service', () => {
  beforeAll(() => {
    dbService.removeDb();
  })

  it('Load matches', async () => {
    readMatches(
      '../../../../testdata/matches_expanded-1630235153-username.csv'
    );

    const result = await matchesByDay(
      fromUnixTime(1630243800 - (60 * 60 * 2))
    );

    expect(result).not.toBeNull();
    expect(result.length).toEqual(1);
  });
});