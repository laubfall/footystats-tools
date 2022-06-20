/**
 * Classes and functions that are necessary to store csv footstats data into the DB.
 */

import path from 'path';
import { injectable } from 'inversify';
import log from 'electron-log';
import {
  alreadyImported,
  csvFileInformationByFileName,
} from './CsvFileService';
import { CsvFileType } from '../../types/application/CsvFileType';
import { MatchStatsService } from '../stats/MatchStatsService';
import TeamStatsService from '../stats/TeamStatsService';
import LeagueStatsService from '../stats/LeagueStatsService';

@injectable()
class CsvDataToDBService {
  constructor(
    private matchStatsService: MatchStatsService,
    private teamStatsService: TeamStatsService,
    private leagueStatsService: LeagueStatsService
  ) {}

  public storeCsvData(pathToFile: string): void {
    const fp = path.parse(pathToFile);

    if (alreadyImported(pathToFile)) {
      return;
    }

    if (pathToFile.endsWith('.csv') === false) {
      log.info(`File does not have csv extension: ${fp.name}`);
      return;
    }

    const fi = csvFileInformationByFileName(fp.base);
    log.debug(`Try to store csv data from file: ${pathToFile}`);
    switch (fi.type) {
      case CsvFileType.LEAGUE_STATS:
        this.leagueStatsService.readLeagueStats(pathToFile);
        break;
      case CsvFileType.MATCH_STATS:
        this.matchStatsService.readMatches(pathToFile);
        break;
      case CsvFileType.TEAM_STATS:
        this.teamStatsService.readTeamStats(pathToFile);
        break;
      default:
        log.warn(
          `Don't know how to store csv data for file type ${fi.type} of file ${pathToFile}`
        );
        break;
    }
  }
}

export default CsvDataToDBService;
