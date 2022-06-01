import 'reflect-metadata';
import { injectable } from 'inversify';
import path from 'path';
import log from 'electron-log';
import config from '../../../config';
import { LeagueStats } from '../../types/stats/LeagueStats';
import { importFile } from '../application/CsvFileService';
import { DbStoreService } from '../application/DbStoreService';
import Configuration from '../../types/application/Configuration';

interface UniqueLeagueStats extends LeagueStats {
  unique: string;
}

export interface ILeagueStatsService {
  findeLeagueStatsBy(name: string, season: string): Promise<LeagueStats>;
}

@injectable()
class LeagueStatsService implements ILeagueStatsService {
  readonly dbService: DbStoreService<UniqueLeagueStats>;

  constructor(configuration: Configuration) {
    this.dbService = new DbStoreService<UniqueLeagueStats>(
      path.join(configuration.databaseDirectory, config.leagueStatsDbFileName)
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
    const leagueStats = importFile<LeagueStats>(
      pathToLeagueStatsCsv,
      config.markCsvFilesAsImported
    );

    const uniqueLeagueStats = leagueStats.map((t) => {
      return {
        ...t,
        unique: t.name + t.season,
      };
    });

    uniqueLeagueStats.forEach((ul) => {
      this.dbService
        .asyncUpsert({ unique: ul.unique }, { ...ul })
        .catch((reason) =>
          log.error(`Failed to insert league stats to db`, reason)
        );
    });

    return leagueStats;
  }
}

export default LeagueStatsService;
