export default interface MatchStats {
  /**
   * Name of the team.
   */
  team: string;
  /**
   * Position of this team in the current league / championship.
   */
  leaguePosition: number;
  /**
   * Time played in seconds.
   */
  playedTime: number;
  /**
   * Expected goals.
   */
  xG: number;
  /**
   * Expected goals shot by the other team.
   */
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
