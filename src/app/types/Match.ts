export default interface MatchStats {
  /**
   * Name of the team.
   */
  team: string;
  /**
   * Position of this team in the league / championship.
   */
  leaguePosition: number;
  xG: number;
  xGA: number;
}

export interface Match {
  home: MatchStats;
  away: MatchStats;
  /**
   * Count of teams that play in this league / championship.
   */
  leagueTeamsCount: number;
}
