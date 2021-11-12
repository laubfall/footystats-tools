import { csvFileInformationByFileName } from '../../../app/services/application/CsvFileService';
import { CsvFileType } from '../../../app/types/application/CsvFileType';

describe('Tests for CsvFileService', () => {
  it('Get CSV File Information by filename - match stats', () => {
    const cfi = csvFileInformationByFileName(
      'france-ligue-1-matches-2020-to-2021-stats.csv'
    );

    expect(cfi).not.toBeNull();
    expect(cfi.type).toEqual(CsvFileType.MATCH);
    expect(cfi.country.name).toEqual('france');
    expect(cfi.league.name).toEqual('ligue-1');
    expect(cfi.season.name).toEqual('2020-to-2021');
    expect(cfi.season.yearFrom).toEqual('2020');
    expect(cfi.season.yearTo).toEqual('2021');
  });

  it('Get CSV File Information by filename - league stats - league with one token', () => {
    const cfi = csvFileInformationByFileName(
      'scotland-premiership-league-2020-to-2021-stats'
    );

    expect(cfi).not.toBeNull();
    expect(cfi.type).toEqual(CsvFileType.LEAGUE);
    expect(cfi.country.name).toEqual('scotland');
    expect(cfi.league.name).toEqual('premiership');
    expect(cfi.season.name).toEqual('2020-to-2021');
    expect(cfi.season.yearFrom).toEqual('2020');
    expect(cfi.season.yearTo).toEqual('2021');
  });

  it('Get CSV File Information by filename - match stats - league with three token', () => {
    const cfi = csvFileInformationByFileName(
      'india-indian-super-league-matches-2020-to-2021-stats.csv'
    );

    expect(cfi).not.toBeNull();
    expect(cfi.type).toEqual(CsvFileType.MATCH);
    expect(cfi.country.name).toEqual('india');
    expect(cfi.league.name).toEqual('indian-super-league');
    expect(cfi.season.name).toEqual('2020-to-2021');
    expect(cfi.season.yearFrom).toEqual('2020');
    expect(cfi.season.yearTo).toEqual('2021');
  });
});
