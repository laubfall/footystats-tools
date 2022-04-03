/* eslint-disable prettier/prettier */
import "reflect-metadata";
import { fromUnixTime } from 'date-fns';
import TestUtils from '../../TestUtils';


describe('Test the match service', () => {
  const mss = TestUtils.matchStatsService;

  it('Load matches', async () => {
    mss.readMatches(`${__dirname}/../../../../testdata/matches_expanded-1630235153-username.csv`);

    const result = await mss.matchesByDay(
      fromUnixTime(1630243800 - (60 * 60 * 2))
    );

    expect(result).not.toBeNull();
    expect(result.length).toEqual(1);
  });

  it('Load matches and do it again after a match is finished, ignore Esports matches', async () => {
    mss.readMatches(`${__dirname}/../../../../testdata/matches_expanded-1641239361-completed-match-test.csv`);

    let result = await mss.matchesByDay(new Date(2022,0,3));
    expect(result).not.toBeNull();
    expect(result.length).toEqual(1);
    let match = result[0];
    expect(match['Match Status']).toBe('incomplete');

    mss.readMatches(`${__dirname}/../../../../testdata/matches_expanded-1641328373-completed-match-test.csv`);
    result = await mss.matchesByDay(new Date(2022,0,3));
    expect(result).not.toBeNull();
    expect(result.length).toEqual(1); // file contains one esports match, we ignore this one

    // eslint-disable-next-line prefer-destructuring
    match = result[0];
    expect(match['Match Status']).toBe('complete');
  });
});
