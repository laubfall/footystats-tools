import fs from 'fs';
import path from 'path';
import {
  importFile,
  csvFileInformationByFileName,
  IMPORTED_PREFIX,
} from '../../../app/services/application/CsvFileService';
import { CsvFileType } from '../../../app/types/application/CsvFileType';
import MatchStats from '../../../app/types/stats/MatchStats';

describe('Tests for CsvFileService', () => {
  const pathToFileExpectedToBeRenamed = `${__dirname}/../../../../testdata/matches_expanded-1630235153-expectRenamed.csv`;

  afterAll(() => {
    const stats = fs.existsSync(
      pathToFileExpectedToBeRenamed + IMPORTED_PREFIX
    );
    if (stats) {
      fs.renameSync(
        pathToFileExpectedToBeRenamed + IMPORTED_PREFIX,
        pathToFileExpectedToBeRenamed
      );
    }
  });

  it('Get CSV File Information by filename - match stats', () => {
    const cfi = csvFileInformationByFileName(
      'france-ligue-1-matches-2020-to-2021-stats.csv'
    );

    expect(cfi).not.toBeNull();
    expect(cfi.type).toEqual(CsvFileType.MATCH_STATS);
    expect(cfi.country?.name).toEqual('france');
    expect(cfi.league?.name).toEqual('ligue-1');
    expect(cfi.season?.name).toEqual('2020-to-2021');
    expect(cfi.season?.yearFrom).toEqual('2020');
    expect(cfi.season?.yearTo).toEqual('2021');
  });

  it('Get CSV File Information by filename - league stats - league with one token', () => {
    const cfi = csvFileInformationByFileName(
      'scotland-premiership-league-2020-to-2021-stats'
    );

    expect(cfi).not.toBeNull();
    expect(cfi.type).toEqual(CsvFileType.LEAGUE_STATS);
    expect(cfi.country?.name).toEqual('scotland');
    expect(cfi.league?.name).toEqual('premiership');
    expect(cfi.season?.name).toEqual('2020-to-2021');
    expect(cfi.season?.yearFrom).toEqual('2020');
    expect(cfi.season?.yearTo).toEqual('2021');
  });

  it('Get CSV File Information by filename - match stats - league with three token', () => {
    const cfi = csvFileInformationByFileName(
      'india-indian-super-league-matches-2020-to-2021-stats.csv'
    );

    expect(cfi).not.toBeNull();
    expect(cfi.type).toEqual(CsvFileType.MATCH_STATS);
    expect(cfi.country?.name).toEqual('india');
    expect(cfi.league?.name).toEqual('indian-super-league');
    expect(cfi.season?.name).toEqual('2020-to-2021');
    expect(cfi.season?.yearFrom).toEqual('2020');
    expect(cfi.season?.yearTo).toEqual('2021');
  });

  it('Get matches CSV File Information', () => {
    const cfi = csvFileInformationByFileName(
      'matches_expanded-1636494163-laubfall.csv'
    );

    expect(cfi).not.toBeNull();
    expect(cfi.type).toEqual(CsvFileType.MATCH_STATS);
  });
  it('Import and expect File to be renamed', () => {
    importFile<MatchStats>(
      `${__dirname}/../../../../testdata/matches_expanded-1630235153-expectRenamed.csv`,
      true
    );

    const stats = fs.statSync(
      `${__dirname}/../../../../testdata/matches_expanded-1630235153-expectRenamed.csv.imported`
    );
    expect(stats).not.toBeNull();
  });
});
