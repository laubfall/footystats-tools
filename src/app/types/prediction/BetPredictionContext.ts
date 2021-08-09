import { BetTypeDefinition } from '../Bet';
import { Match } from '../Match';

export default interface BetPredictionContext {
  match: Match;
  bet: BetTypeDefinition;
}
