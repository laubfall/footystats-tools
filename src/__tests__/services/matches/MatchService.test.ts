import { readMatches } from '../../../app/services/matches/MatchService';

describe('Test the match service', () => {
  it('Load matches', () => {
    const result = readMatches(
      '../../../../testdata/matches_expanded-1630235153-username.csv'
    );

    expect(result).not.toBeNull();
    expect(result.length).toEqual(1);
  });
});
