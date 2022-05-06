import { NDate, NString } from '../../types/General';
import { MatchStats, MatchStatus } from '../../types/stats/MatchStats';
import { CursorModification, Result } from '../application/DbStoreService';
import { PredictionResult } from '../prediction/IPredictionService';

export interface IMatchService {
  writeMatch(matchStats: MatchStats): void;

  matchesByFilter(
    country: NString,
    league: NString,
    from: NDate,
    until: NDate,
    cursorModification?: CursorModification[]
  ): Promise<Result<Match>>;
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
};

export default Match;
