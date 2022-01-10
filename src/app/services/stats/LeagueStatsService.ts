import { injectable } from 'inversify';
import path from 'path';
import config from '../../../config';
import { LeagueStats } from '../../types/stats/LeagueStats';
import { alreadyImported, importFile } from '../application/CsvFileService';
import { DbStoreService } from '../application/DbStoreService';

interface UniqueLeagueStats extends LeagueStats {
  unique: string;
}

export interface ILeagueStatsService {
  findeLeagueStatsBy(name: string, season: string): Promise<LeagueStats>;
}

@injectable()
class LeagueStatsService implements ILeagueStatsService {
  readonly dbService: DbStoreService<UniqueLeagueStats>;

  constructor(databasePath: string) {
    this.dbService = new DbStoreService<UniqueLeagueStats>(
      path.join(databasePath, config.matchStatsDbFileName)
    );
    this.dbService.createUniqueIndex('unique');
  }

  public async findeLeagueStatsBy(
    name: string,
    season: string
  ): Promise<LeagueStats> {
    return this.dbService.asyncFindOne({ $and: [{ name }, { season }] });
  }

  public readLeagueStats(pathToLeagueStatsCsv: string): LeagueStats[] {
    if (alreadyImported(pathToLeagueStatsCsv)) {
      return [];
    }
    const leagueStats = importFile<LeagueStats>(pathToLeagueStatsCsv, false);

    const uniqueLeagueStats = leagueStats.map((t) => {
      return {
        ...t,
        unique: t.name + t.season,
      };
    });

    uniqueLeagueStats.forEach((ul) => {
      this.dbService.asyncUpsert({ unique: ul.unique }, { ...ul });
    });

    return leagueStats;
  }
}

export default LeagueStatsService;
