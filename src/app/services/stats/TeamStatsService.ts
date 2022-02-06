import { injectable } from 'inversify';
import path from 'path';
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
      path.join(configuration.databaseDirectory, cfg.teamStatsDbFileName)
    );
    this.dbService.createUniqueIndex('unique');
  }

  public readTeamStats(importFilePath: string): TeamStats[] {
    if (alreadyImported(importFilePath)) {
      return [];
    }
    const teamStats = importFile<TeamStats>(
      importFilePath,
      cfg.markCsvFilesAsImported
    );

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
