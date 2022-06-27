import { NDate, NString } from '../../types/General';
import { MatchStats, MatchStatus } from '../../types/stats/MatchStats';
import { CursorModification, Result } from '../application/DbStoreService';
import { Bet } from '../../types/prediction/BetPredictionContext';
import { PredictionQualityRevision } from '../prediction/PredictionQualityService.types';
import {
  PredictionAnalyze,
  PredictionResult,
} from '../prediction/PredictionService.types';
// eslint-disable-next-line import/no-cycle

export interface IMatchService {
  writeMatch(matchStats: MatchStats): void;

  matchesByFilter(
    country: NString[],
    league: NString[],
    from: NDate,
    until: NDate,
    cursorModification?: CursorModification[]
  ): Promise<Result<Match>>;

  matchesByFilterExt(
    country: NString[],
    league: NString[],
    from: NDate,
    until: NDate,
    bet: Bet,
    predictionAnalyze: PredictionAnalyze,
    cursorModification?: CursorModification[]
  ): Promise<Result<Match>>;

  matchesByRevision(
    revision?: PredictionQualityRevision,
    cursorModification?: CursorModification[]
  ): Promise<Result<Match>>;

  calculatePrediction(bet: Bet, ms: MatchStats): Promise<PredictionResult>;
}

type Match = {
  uniqueIdentifier: string; // combination of date_unix, league, away and home team
  date_unix: number;
  date_GMT: string;
  Country: string;
  League: string;
  'Home Team': string;
  'Away Team': string;
  goalsHomeTeam: number;
  goalsAwayTeam: number;
  state: MatchStatus;
  footyStatsUrl: string;
  o05?: PredictionResult;
  bttsYes?: PredictionResult;
  revision?: PredictionQualityRevision;
};

export default Match;
