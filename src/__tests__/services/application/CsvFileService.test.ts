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
    expect(cfi.country?.leagues?.[0].name).toEqual('ligue-1');
    expect(cfi.country?.leagues?.[0].seasons?.[0].name).toEqual('2020-to-2021');
    expect(cfi.country?.leagues?.[0].seasons?.[0].yearFrom).toEqual('2020');
    expect(cfi.country?.leagues?.[0].seasons?.[0].yearTo).toEqual('2021');
  });

  it('Get CSV File Information by filename - league stats - league with one token', () => {
    const cfi = csvFileInformationByFileName(
      'scotland-premiership-league-2020-to-2021-stats'
    );

    expect(cfi).not.toBeNull();
    expect(cfi.type).toEqual(CsvFileType.LEAGUE_STATS);
    expect(cfi.country?.name).toEqual('scotland');
    expect(cfi.country?.leagues?.[0].name).toEqual('premiership');
    expect(cfi.country?.leagues?.[0].seasons?.[0].name).toEqual('2020-to-2021');
    expect(cfi.country?.leagues?.[0].seasons?.[0].yearFrom).toEqual('2020');
    expect(cfi.country?.leagues?.[0].seasons?.[0].yearTo).toEqual('2021');
  });

  it('Get CSV File Information by filename - match stats - league with three token', () => {
    const cfi = csvFileInformationByFileName(
      'india-indian-super-league-matches-2020-to-2021-stats.csv'
    );

    expect(cfi).not.toBeNull();
    expect(cfi.type).toEqual(CsvFileType.MATCH_STATS);
    expect(cfi.country?.name).toEqual('india');
    expect(cfi.country?.leagues?.[0].name).toEqual('indian-super-league');
    expect(cfi.country?.leagues?.[0].seasons?.[0].name).toEqual('2020-to-2021');
    expect(cfi.country?.leagues?.[0].seasons?.[0].yearFrom).toEqual('2020');
    expect(cfi.country?.leagues?.[0].seasons?.[0].yearTo).toEqual('2021');
  });

  it('Get matches CSV File Information', () => {
    const cfi = csvFileInformationByFileName(
      'matches_expanded-1636494163-laubfall.csv'
    );

    expect(cfi).not.toBeNull();
    expect(cfi.type).toEqual(CsvFileType.MATCH_STATS);
  });

  it('Get CSV File Information by filename - france ligue 1', () => {
    const cfi = csvFileInformationByFileName(
      'france-ligue-1-league-2020-to-2021-stats.csv'
    );
    expect(cfi).not.toBeNull();
    expect(cfi.type).toEqual(CsvFileType.LEAGUE_STATS);
    expect(cfi.country?.name).toEqual('france');
  });

  it('Get CSV File Information by filename - belgium pro league', () => {
    const cfi = csvFileInformationByFileName(
      'belgium-pro-league-teams-2020-to-2021-stats.csv'
    );
    expect(cfi).not.toBeNull();
    expect(cfi.type).toEqual(CsvFileType.TEAM_STATS);
    expect(cfi.country?.name).toEqual('belgium');
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
