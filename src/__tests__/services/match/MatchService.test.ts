/* eslint-disable prettier/prettier */
import "reflect-metadata";
import TestUtils from '../../TestUtils';
import {importFile} from "../../../app/services/application/CsvFileService";
import {MatchStats} from "../../../app/types/stats/MatchStats";


describe('Test the match service', () => {
  const {matchService} = TestUtils;

  it('Create some match db entries', async () => {
    const matchStats = importFile<MatchStats>(`${__dirname}/../../../../testdata/matches_expanded-1630235153-matchService.csv`, false);
    matchStats.forEach(ms => matchService.writeMatch(ms));

    const matches = await matchService.matchesByFilter('Germany', 'Bundesliga', null, null);
    expect(matches).toBeDefined();
    expect(matches.length).toBe(5)
  });
});
