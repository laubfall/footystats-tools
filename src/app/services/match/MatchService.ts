import { injectable } from 'inversify';
import log from 'electron-log';
import path from 'path';
import { getYear } from 'date-fns';
import cfg from '../../../config';
import Configuration from '../../types/application/Configuration';
import { NDate, NString } from '../../types/General';
import { MatchStats } from '../../types/stats/MatchStats';
import {
  CursorModification,
  DbStoreService,
  Result,
} from '../application/DbStoreService';
import prediction from '../prediction/PredictionService';
import Match, { IMatchService } from './IMatchService';
import { Bet } from '../../types/prediction/BetPredictionContext';
import {
  NO_REVISION_SO_FAR,
  PredictionQualityRevision,
} from '../prediction/PredictionQualityService.types';
import TeamStatsService from '../stats/TeamStatsService';
import {
  PredictionAnalyze,
  PredictionResult,
} from '../prediction/PredictionService.types';

@injectable()
class MatchService implements IMatchService {
  readonly dbService: DbStoreService<Match>;

  constructor(
    configuration: Configuration,
    private teamStatsService: TeamStatsService
  ) {
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

  async writeMatch(matchStats: MatchStats) {
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
      o05: await this.calculatePrediction(Bet.OVER_ZERO_FIVE, matchStats),
      bttsYes: await this.calculatePrediction(Bet.BTTS_YES, matchStats),
    };

    this.dbService
      .asyncUpsert({ uniqueIdentifier: match.uniqueIdentifier }, match)
      .then(() => log.debug(`saved match ${match.uniqueIdentifier}`))
      .catch((reason) =>
        log.error(`failed saving match: ${reason} ${match.uniqueIdentifier}`)
      );
  }

  matchesByFilterExt(
    country: NString[],
    league: NString[],
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
    country: NString[],
    league: NString[],
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
    country: NString[] | null,
    league: NString[] | null,
    from: Date | null,
    until: Date | null
  ): any[] {
    const constraints = [];
    if (country != null && country.length > 0) {
      constraints.push({
        Country: { $in: country },
      });
    }

    if (league != null && league.length > 0) {
      constraints.push({
        League: { $in: league },
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

  async calculatePrediction(
    bet: Bet,
    ms: MatchStats
  ): Promise<PredictionResult> {
    const teamStats = await this.teamStatsService.latestThree(
      ms['Home Team'],
      ms.Country,
      getYear(new Date())
    );

    teamStats.push(
      ...(await this.teamStatsService.latestThree(
        ms['Away Team'],
        ms.Country,
        getYear(new Date())
      ))
    );

    const predictionNumber = prediction({
      bet,
      match: ms,
      leagueStats: undefined,
      teamStats,
    });
    return Promise.resolve(predictionNumber);
  }
}

export default MatchService;
