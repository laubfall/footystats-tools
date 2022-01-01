import LeagueStats from '../stats/LeagueStats';
import MatchStats from '../stats/MatchStats';
import TeamStats from '../stats/TeamStats';
import { BetTypeDefinition } from './Bet';

export default interface BetPredictionContext {
  match: MatchStats;
  teamStats?: TeamStats;
  leagueStats?: LeagueStats;
  bet: BetTypeDefinition;
}
