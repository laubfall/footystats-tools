import { add } from 'date-fns';
import MatchStats from '../../types/stats/MatchStats';
import { alreadyImported, importFile } from '../application/CsvFileService';
import { DbStoreService } from '../application/DbStoreService';
import config from '../../../config';

interface UniqueMatch extends MatchStats {
  unique: string;
}

export const dbService = new DbStoreService<UniqueMatch>(
  `${config.db}matchNedb.data`
);
dbService.createUniqueIndex('unique');

export function readMatches(path: string) {
  if (alreadyImported(path)) {
    return;
  }

  const matches = importFile<MatchStats>(path, false);
  const uniqueMatches: UniqueMatch[] = matches.map((m) => {
    return {
      ...m,
      unique:
        m.date_unix.toString() + m.League + m['Home Team'] + m['Away Team'],
    };
  });
  dbService.insertAll(uniqueMatches);
}

export async function matchesByDay(day: Date): Promise<MatchStats[]> {
  const end = add(day, { days: 1 });
  const startSec = Math.floor(day.getTime() / 1000);
  const endSec = Math.floor(end.getTime() / 1000);
  return dbService.find({
    $and: [{ date_unix: { $gte: startSec } }, { date_unix: { $lt: endSec } }],
  });
}

export default matchesByDay;
