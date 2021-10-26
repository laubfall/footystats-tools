import config from '../../../config';
import TeamStats from '../../types/stats/TeamStats';
import { alreadyImported, importFile } from '../application/CsvFileService';
import { DbStoreService } from '../application/DbStoreService';

interface UniqueTeamStats extends TeamStats {
  unique: string;
}

export const dbService = new DbStoreService<UniqueTeamStats>(
  `${config.db}teamStatsNedb.data`
);
dbService.createUniqueIndex('unique');

// eslint-disable-next-line import/prefer-default-export
export function readTeamStats(path: string): TeamStats[] {
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

  dbService.insertAll(uniqueTeamStats);
  return teamStats;
}
