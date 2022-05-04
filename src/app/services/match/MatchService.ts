import { injectable } from 'inversify';
import path from 'path';
import cfg from '../../../config';
import Configuration from '../../types/application/Configuration';
import { NString, NDate } from '../../types/General';
import { MatchStats } from '../../types/stats/MatchStats';
import {
  DbStoreService,
  CursorModification,
  Result,
} from '../application/DbStoreService';
import Match, { IMatchService } from './IMatchService';
import prediction from '../prediction/PredictionService';
import { Bet } from '../../types/prediction/BetPredictionContext';

@injectable()
class MatchService implements IMatchService {
  readonly dbService: DbStoreService<Match>;

  constructor(configuration: Configuration) {
    this.dbService = new DbStoreService<Match>(
      path.join(configuration.databaseDirectory, cfg.matchDbFileName)
    );
    this.dbService.createUniqueIndex('unique');
    this.dbService.DB.ensureIndex({ fieldName: 'date_unix' });
    this.dbService.DB.ensureIndex({ fieldName: 'League' });
    this.dbService.DB.ensureIndex({ fieldName: 'Country' });
    this.dbService.DB.ensureIndex({ fieldName: 'Home Team' });
    this.dbService.DB.ensureIndex({ fieldName: 'Away Team' });
  }

  writeMatch(matchStats: MatchStats): void {
    const match: Match = {
      uniqueIdentifier: this.uniqueValue(matchStats),
      date_unix: matchStats.date_unix,
      date_GMT: matchStats.date_GMT,
      'Away Team': matchStats['Away Team'],
      'Home Team': matchStats['Home Team'],
      Country: matchStats.Country,
      League: matchStats.League,
      goalsAwayTeam: matchStats['Result - Away Team Goals'],
      goalsHomeTeam: matchStats['Result - Home Team Goals'],
      state: matchStats['Match Status'],
    };

    MatchService.calcPredictions(match, matchStats);
    this.dbService
      .asyncUpsert({ uniqueIdentifier: match.uniqueIdentifier }, match)
      .then(() => console.log('saved match'))
      .catch((reason) => console.log(`failed: ${reason}`));
  }

  static calcPredictions(n: Match, ms: MatchStats) {
    const predictionNumber = prediction({
      bet: Bet.OVER_ZERO_FIVE,
      match: ms,
      leagueStats: undefined,
      teamStats: undefined,
    });
    const bttsYesPredictionNumber = prediction({
      bet: Bet.BTTS_YES,
      match: ms,
      leagueStats: undefined,
      teamStats: undefined,
    });
    n.o05 = predictionNumber;
    n.bttsYes = bttsYesPredictionNumber;
  }

  matchesByFilter(
    country: NString,
    league: NString,
    from: NDate,
    until: NDate,
    cursorModification?: CursorModification[]
  ): Promise<Result<Match>> {
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

    if (from != null) {
      constraints.push({
        date_unix: {
          $gte:
            Date.UTC(
              from.getUTCFullYear(),
              from.getUTCMonth(),
              from.getUTCDate(),
              from.getUTCHours()
            ) / 1000,
        },
      });
    }

    if (until != null) {
      constraints.push({
        date_unix: {
          $lt:
            Date.UTC(
              until.getUTCFullYear(),
              until.getUTCMonth(),
              until.getUTCDate(),
              until.getUTCHours()
            ) / 1000,
        },
      });
    }

    let query = {};
    if (constraints.length > 0) {
      query = { $and: constraints };
    }

    return this.dbService.asyncFind(query, cursorModification);
  }

  // eslint-disable-next-line class-methods-use-this
  private uniqueValue(match: MatchStats) {
    return (
      match.date_unix.toString() +
      match.League +
      match['Home Team'] +
      match['Away Team']
    );
  }
}

export default MatchService;
