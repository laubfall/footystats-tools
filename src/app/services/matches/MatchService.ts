import { add } from 'date-fns';
import Match from '../../types/matches/Match';
import { alreadyImported, importFile } from '../application/CsvFileService';
import { DbStoreService } from '../application/DbStoreService';

const dbService = new DbStoreService<Match>(
  'C:/Users/Daniel/Desktop/matchNedb.data'
);

export function readMatches(path: string) {
  if (alreadyImported(path)) {
    return;
  }

  const matches = importFile<Match>(path, false);
  dbService.insertAll(matches);
}

export async function matchesByDay(day: Date): Promise<Match[]> {
  const end = add(day, { days: 1 });
  const startSec = Math.floor(day.getTime() / 1000);
  const endSec = Math.floor(end.getTime() / 1000);
  return dbService.find({
    $and: [{ date_unix: { $gte: startSec } }, { date_unix: { $lt: endSec } }],
  });
}

export default matchesByDay;
