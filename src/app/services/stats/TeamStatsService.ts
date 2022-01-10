import { injectable } from 'inversify';
import cfg from '../../../config';
import Configuration from '../../types/application/Configuration';
import TeamStats from '../../types/stats/TeamStats';
import { alreadyImported, importFile } from '../application/CsvFileService';
import { DbStoreService } from '../application/DbStoreService';

interface UniqueTeamStats extends TeamStats {
  unique: string;
}

@injectable()
export default class TeamStatsService {
  readonly dbService: DbStoreService<UniqueTeamStats>;

  constructor(configuration: Configuration) {
    this.dbService = new DbStoreService<UniqueTeamStats>(
      `${configuration.databaseDirectory}${cfg.teamStatsDbFileName}`
    );
    this.dbService.createUniqueIndex('unique');
  }

  public readTeamStats(path: string): TeamStats[] {
    if (alreadyImported(path)) {
      return [];
    }
    const teamStats = importFile<TeamStats>(path, false);

    const uniqueTeamStats = teamStats.map((t) => {
      return {
        ...t,
        unique: t.country + t.common_name + t.season,
      };
    });

    this.dbService.insertAll(uniqueTeamStats);
    return teamStats;
  }
}
