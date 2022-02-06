/**
 * Classes and functions that are necessary to store csv footstats data into the DB.
 */

import path from 'path';
import { injectable } from 'inversify';
import {
  CsvFileInformation,
  csvFileInformationByFileName,
} from './CsvFileService';
import { CsvFileType } from '../../types/application/CsvFileType';
import { MatchStatsService } from '../stats/MatchStatsService';
import TeamStatsService from '../stats/TeamStatsService';
import LeagueStatsService from '../stats/LeagueStatsService';
import { AppControllService } from './AppControllService';

@injectable()
class CsvDataToDBService {
  constructor(
    private matchStatsService: MatchStatsService,
    private teamStatsService: TeamStatsService,
    private leagueStatsService: LeagueStatsService,
    private appControllService: AppControllService
  ) {}

  public storeCsvData(pathToFile: string): void {
    const fp = path.parse(pathToFile);

    const fi = csvFileInformationByFileName(fp.base);
    this.updateAppControllData(fi);
    console.log(`Try to store csv data from file: ${pathToFile}`);
    switch (fi.type) {
      case CsvFileType.LEAGUE_MATCH_STATS:
        this.leagueStatsService.readLeagueStats(pathToFile);
        break;
      case CsvFileType.MATCH_STATS:
        this.matchStatsService.readMatches(pathToFile);
        break;
      case CsvFileType.TEAM_STATS:
        this.teamStatsService.readTeamStats(pathToFile);
        break;
      default:
        break;
    }
  }

  private updateAppControllData(fi: CsvFileInformation): void {
    if (fi.type === CsvFileType.MATCH_STATS) {
      return; // these files don't provide relevant information that can be used as app controll data.
    }

    if (fi.country) {
      const league = fi.country.leagues?.[0]; // filename only contains one league name, so thats safe.
      if (league) {
        const season = league?.seasons[0]; // filename only contains one season, so thats safe.
        this.appControllService.addCountryLeagueAndSeason(
          fi.country.name,
          league.name,
          season?.name
        );
      }
    }
  }
}

export default CsvDataToDBService;
