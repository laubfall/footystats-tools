/* eslint-disable prettier/prettier */
import { fromUnixTime } from 'date-fns';
import mss from '../../TestUtils';

describe('Test the match service', () => {

  it('Load matches', async () => {
    mss.readMatches(`${__dirname}/../../../../testdata/matches_expanded-1630235153-username.csv`);

    const result = await mss.matchesByDay(
      fromUnixTime(1630243800 - (60 * 60 * 2))
    );

    expect(result).not.toBeNull();
    expect(result.length).toEqual(1);
  });

  it('Load matches and do it again after a match is finished', async () => {

  })
});
