import { injectable } from 'inversify';
import path from 'path';
import { getYear } from 'date-fns';
import cfg from '../../../config';
import Configuration from '../../types/application/Configuration';
import TeamStats from '../../types/stats/TeamStats';
import { importFile } from '../application/CsvFileService';
import { DbStoreService } from '../application/DbStoreService';

export interface UniqueTeamStats extends TeamStats {
  unique: string;
}

export interface IIpcTeamStatsService {
  latestThree(
    team: string,
    country: string,
    year?: number
  ): Promise<UniqueTeamStats[]>;
}

@injectable()
export default class TeamStatsService implements IIpcTeamStatsService {
  readonly dbService: DbStoreService<UniqueTeamStats>;

  constructor(configuration: Configuration) {
    this.dbService = new DbStoreService<UniqueTeamStats>(
      path.join(configuration.databaseDirectory, cfg.teamStatsDbFileName)
    );
    this.dbService.createUniqueIndex('unique');
  }

  public readTeamStats(importFilePath: string): TeamStats[] {
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

  public async latestThree(
    team: string,
    country: string,
    year?: number
  ): Promise<UniqueTeamStats[]> {
    const baseYear = year || getYear(new Date());

    const season = [
      `${baseYear - 1}/${baseYear}`,
      `${baseYear - 2}/${baseYear - 1}`,
      `${baseYear}/${baseYear + 1}`,
      baseYear,
      baseYear + 1,
      baseYear - 1,
    ];

    const result: UniqueTeamStats[] = (await this.dbService.asyncFind({
      $and: [
        { country },
        { $or: [{ team_name: team }, { common_name: team }] },
        { season: { $in: season } },
      ],
    })) as UniqueTeamStats[];

    return Promise.resolve(result);
  }
}
