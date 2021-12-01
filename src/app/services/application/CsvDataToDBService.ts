/**
 * Classes and functions that are necessary to store csv footstats data into the DB.
 */

import path from 'path';
import { csvFileInformationByFileName } from './CsvFileService';
import { CsvFileType } from '../../types/application/CsvFileType';
import { MatchStatsService } from '../stats/MatchStatsService';
import Configuration from '../../types/application/Configuration';

class CsvDataToDBService {
  private matchStatsService;

  constructor(configuration: Configuration) {
    this.matchStatsService = new MatchStatsService(
      configuration.databaseDirectory
    );
  }

  public storeCsvData(pathToFile: string): void {
    const fp = path.parse(pathToFile);

    const fi = csvFileInformationByFileName(fp.base);
    console.log(`Try to store csv data from file: ${pathToFile}`);
    switch (fi.type) {
      case CsvFileType.LEAGUE_MATCH_STATS:
        break;
      case CsvFileType.MATCH_STATS:
        this.matchStatsService.readMatches(pathToFile);
        break;
      default:
        break;
    }
  }

  public static addNewTeam(
    commonName: string,
    country: string,
    league: string
  ) {}
}

export default CsvDataToDBService;
