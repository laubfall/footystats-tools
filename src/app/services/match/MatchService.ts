import { injectable } from 'inversify';
import log from 'electron-log';
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
import prediction from '../prediction/PredictionService';
import Match, { IMatchService } from './IMatchService';
import { Bet } from '../../types/prediction/BetPredictionContext';
import { PredictionAnalyze } from '../prediction/IPredictionService';
import {
  NO_REVISION_SO_FAR,
  PredictionQualityRevision,
} from '../prediction/PredictionQualityService.types';

@injectable()
class MatchService implements IMatchService {
  readonly dbService: DbStoreService<Match>;

  constructor(configuration: Configuration) {
    this.dbService = new DbStoreService<Match>(
      path.join(configuration.databaseDirectory, cfg.matchDbFileName)
    );
    this.dbService.createUniqueIndex('uniqueIdentifier');
    this.dbService.DB.ensureIndex({ fieldName: 'date_unix' });
    this.dbService.DB.ensureIndex({ fieldName: 'League' });
    this.dbService.DB.ensureIndex({ fieldName: 'Country' });
    this.dbService.DB.ensureIndex({ fieldName: 'Home Team' });
    this.dbService.DB.ensureIndex({ fieldName: 'Away Team' });
    this.dbService.DB.ensureIndex({ fieldName: 'revision' });
    this.dbService.DB.ensureIndex({ fieldName: 'o05.betSuccessInPercent' });
    this.dbService.DB.ensureIndex({ fieldName: 'o05.betOnThis' });
    this.dbService.DB.ensureIndex({ fieldName: 'o05.analyzeResult' });
    this.dbService.DB.ensureIndex({ fieldName: 'bttsYes.betSuccessInPercent' });
    this.dbService.DB.ensureIndex({ fieldName: 'bttsYes.betOnThis' });
    this.dbService.DB.ensureIndex({ fieldName: 'bttsYes.analyzeResult' });
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
      footyStatsUrl: matchStats['Match FootyStats URL'],
      revision: NO_REVISION_SO_FAR, // you can't search for undefined so set a value.
    };

    MatchService.calcPredictions(match, matchStats);
    this.dbService
      .asyncUpsert({ uniqueIdentifier: match.uniqueIdentifier }, match)
      .then(() => log.debug('saved match'))
      .catch((reason) => log.error(`failed: ${reason}`));
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

  matchesByFilterExt(
    country: NString,
    league: NString,
    from: NDate,
    until: NDate,
    bet: Bet,
    predictionAnalyze: PredictionAnalyze,
    cursorModification?: CursorModification[]
  ): Promise<Result<Match>> {
    const constraints = MatchService.matchFilterConstraints(
      country,
      league,
      from,
      until
    );

    let query = {};
    if (constraints.length > 0) {
      query = { $and: constraints };
    }

    return this.dbService.asyncFind(query, cursorModification);
  }

  matchesByRevision(
    revisionP?: PredictionQualityRevision,
    cursorModification?: CursorModification[]
  ): Promise<Result<Match>> {
    return this.dbService.asyncFind(
      { revision: revisionP },
      cursorModification
    );
  }

  matchesByFilter(
    country: NString,
    league: NString,
    from: NDate,
    until: NDate,
    cursorModification?: CursorModification[]
  ): Promise<Result<Match>> {
    const constraints = MatchService.matchFilterConstraints(
      country,
      league,
      from,
      until
    );

    let query = {};
    if (constraints.length > 0) {
      query = { $and: constraints };
    }

    return this.dbService.asyncFind(query, cursorModification);
  }

  update(match: Match) {
    this.dbService.DB.update(
      { uniqueIdentifier: match.uniqueIdentifier },
      match
    );
  }

  private static matchFilterConstraints(
    country: string | null,
    league: string | null,
    from: Date | null,
    until: Date | null
  ): any[] {
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
    return constraints;
  }

  // eslint-disable-next-line class-methods-use-this
  private uniqueValue(matchStats: MatchStats) {
    return `${matchStats.date_unix.toString()}${matchStats.League}${
      matchStats['Home Team']
    }${matchStats['Away Team']}`;
  }
}

export default MatchService;
