/* eslint-disable prettier/prettier */
import { fromUnixTime } from 'date-fns';
import {
  matchesByDay,
  readMatches,
} from '../../../app/services/matches/MatchService';

describe('Test the match service', () => {
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
