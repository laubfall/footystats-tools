import { add } from 'date-fns';
import cfg from '../../../config';
import MatchStats from '../../types/stats/MatchStats';
import { alreadyImported, importFile } from '../application/CsvFileService';
import { DbStoreService } from '../application/DbStoreService';

interface UniqueMatch extends MatchStats {
  unique: string;
}

export class MatchStatsService {
  readonly dbService: DbStoreService<UniqueMatch>;

  constructor(databasePath: string) {
    this.dbService = new DbStoreService<UniqueMatch>(
      `${databasePath}${cfg.matchStatsDbFileName}`
    );
    this.dbService.createUniqueIndex('unique');
  }

  public readMatches(path: string) {
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
    this.dbService.insertAll(uniqueMatches);
  }

  public async matchesByDay(day: Date): Promise<MatchStats[]> {
    const end = add(day, { days: 1 });
    const startSec = Math.floor(day.getTime() / 1000);
    const endSec = Math.floor(end.getTime() / 1000);
    return this.dbService.find({
      $and: [{ date_unix: { $gte: startSec } }, { date_unix: { $lt: endSec } }],
    });
  }
}

export default MatchStatsService;
