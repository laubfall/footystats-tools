import 'reflect-metadata';
import { add } from 'date-fns';
import { injectable } from 'inversify';
import path from 'path';
import cfg from '../../../config';
import Configuration from '../../types/application/Configuration';
import { MainProcessMessageCodes } from '../../types/application/MessageCodes';
import MatchStats from '../../types/stats/MatchStats';
import { alreadyImported, importFile } from '../application/CsvFileService';
import { DbStoreService } from '../application/DbStoreService';
import { msgSimpleMessage } from '../application/gui/IpcMain2Renderer';
import { NDate, NString } from '../../types/General';

interface UniqueMatch extends MatchStats {
  unique: string;
}

export interface IMatchStatsService {
  matchesByDay(day: Date): Promise<MatchStats[]>;

  matchesByFilter(
    country: NString,
    league: NString,
    from: NDate,
    until: NDate
  ): Promise<MatchStats[]>;
}

@injectable()
export class MatchStatsService implements IMatchStatsService {
  readonly dbService: DbStoreService<UniqueMatch>;

  constructor(configuration: Configuration) {
    this.dbService = new DbStoreService<UniqueMatch>(
      path.join(configuration.databaseDirectory, cfg.matchStatsDbFileName)
    );
    this.dbService.createUniqueIndex('unique');
  }

  public async matchesByFilter(
    country: NString,
    league: NString,
    from: NDate,
    until: NDate
  ): Promise<MatchStats[]> {
    const constraints = [];
    if (country != null) {
      constraints.push({
        Country: country,
      });
    }

    if (league != null) {
      constraints.push({
        League: league,
      });
    }

    let query = {};
    if (constraints.length > 0) {
      query = { $and: constraints };
    }

    return this.dbService.asyncFind(query);
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
        unique: this.uniqueValue(m),
      };
    });

    uniqueMatches.forEach((um) => {
      this.dbService.asyncUpsert({ unique: this.uniqueValue(um) }, { ...um });
    });

    msgSimpleMessage(MainProcessMessageCodes.MATCH_FILE_IMPORTED);
  }

  public async matchesByDay(day: Date): Promise<MatchStats[]> {
    const end = add(day, { days: 1 });
    const startSec = Math.floor(day.getTime() / 1000);
    const endSec = Math.floor(end.getTime() / 1000);
    return this.dbService.asyncFind({
      $and: [{ date_unix: { $gte: startSec } }, { date_unix: { $lt: endSec } }],
    });
  }

  // eslint-disable-next-line class-methods-use-this
  private uniqueValue(matchStats: MatchStats) {
    return (
      matchStats.date_unix.toString() +
      matchStats.League +
      matchStats['Home Team'] +
      matchStats['Away Team']
    );
  }
}

export default MatchStatsService;
