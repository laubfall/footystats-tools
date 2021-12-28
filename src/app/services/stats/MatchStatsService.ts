import { add } from 'date-fns';
import path from 'path';
import cfg from '../../../config';
import { MainProcessMessageCodes } from '../../types/application/MessageCodes';
import MatchStats from '../../types/stats/MatchStats';
import { alreadyImported, importFile } from '../application/CsvFileService';
import { DbStoreService } from '../application/DbStoreService';
import { msgSimpleMessage } from '../application/Ipc2RendererService';

interface UniqueMatch extends MatchStats {
  unique: string;
}

export interface IMatchStatsService {
  matchesByDay(day: Date): Promise<MatchStats[]>;
}

export class MatchStatsService implements IMatchStatsService {
  readonly dbService: DbStoreService<UniqueMatch>;

  constructor(databasePath: string) {
    this.dbService = new DbStoreService<UniqueMatch>(
      path.join(databasePath, cfg.matchStatsDbFileName)
    );
    this.dbService.createUniqueIndex('unique');
  }

  public readMatches(pathToMatchesCsv: string) {
    if (alreadyImported(pathToMatchesCsv)) {
      return;
    }

    const matches = importFile<MatchStats>(
      pathToMatchesCsv,
      cfg.markCsvFilesAsImported
    );
    const uniqueMatches: UniqueMatch[] = matches.map((m) => {
      return {
        ...m,
        unique:
          m.date_unix.toString() + m.League + m['Home Team'] + m['Away Team'],
      };
    });
    this.dbService.insertAll(uniqueMatches);
    msgSimpleMessage(MainProcessMessageCodes.MATCH_FILE_IMPORTED);
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
