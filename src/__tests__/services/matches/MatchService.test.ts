import { fromUnixTime } from 'date-fns';
import storeMock from '../../__utilities__/StorageServiceMock';
import {
  matchesByDay,
  readMatches,
} from '../../../app/services/matches/MatchService';

describe('Test the match service', () => {
  it('Load matches', () => {
    readMatches(
      '../../../../testdata/matches_expanded-1630235153-username.csv'
    );

    const store = storeMock;
    const matches = store.store.mock.calls[0][1];
    store.load.mockImplementation((key: string) => matches);

    const result = matchesByDay(fromUnixTime(1630243800));

    expect(result).not.toBeNull();
    expect(result.length).toEqual(1);
  });
});
