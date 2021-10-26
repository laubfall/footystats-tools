import config from '../../../config';
import LeagueStats from '../../types/stats/LeagueStats';
import { alreadyImported, importFile } from '../application/CsvFileService';
import { DbStoreService } from '../application/DbStoreService';

interface UniqueLeagueStats extends LeagueStats {
  unique: string;
}

export const dbService = new DbStoreService<UniqueLeagueStats>(
  `${config.db}leagueStatsNedb.data`
);
dbService.createUniqueIndex('unique');

// eslint-disable-next-line import/prefer-default-export
export function readLeagueStats(path: string): LeagueStats[] {
  if (alreadyImported(path)) {
    return [];
  }
  const leagueStats = importFile<LeagueStats>(path, false);

  const uniqueLeagueStats = leagueStats.map((t) => {
    return {
      ...t,
      unique: t.name + t.season,
    };
  });

  dbService.insertAll(uniqueLeagueStats);
  return leagueStats;
}
