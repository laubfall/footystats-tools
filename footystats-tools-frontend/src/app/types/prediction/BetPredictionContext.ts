import { LeagueStats } from "../stats/LeagueStats";
import { MatchStats } from "../stats/MatchStats";
import TeamStats from "../stats/TeamStats";

export type BetPredictionContext = {
	match: MatchStats;
	teamStats?: TeamStats[];
	leagueStats?: LeagueStats;
	bet: Bet;
};

export enum Bet {
	OVER_ZERO_FIVE,
	OVER_ONE_FIVE,
	BTTS_YES,
	BTTS_NO,
}
