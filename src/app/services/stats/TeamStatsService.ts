import { injectable } from 'inversify';
import path from 'path';
import { getYear } from 'date-fns';
import { meanBy } from 'lodash';
import cfg from '../../../config';
import Configuration from '../../types/application/Configuration';
import { TeamStats } from '../../types/stats/TeamStats';
import { importFile } from '../application/CsvFileService';
import { DbStoreService } from '../application/DbStoreService';

export type UniqueTeamStats = {
  unique: string;
} & TeamStats;

export interface IIpcTeamStatsService {
  latestThree(
    team: string,
    country: string,
    year?: number
  ): Promise<UniqueTeamStats[]>;
}

@injectable()
export default class TeamStatsService implements IIpcTeamStatsService {
  readonly dbService: DbStoreService<UniqueTeamStats>;

  constructor(configuration: Configuration) {
    this.dbService = new DbStoreService<UniqueTeamStats>(
      path.join(configuration.databaseDirectory, cfg.teamStatsDbFileName)
    );
    this.dbService.createUniqueIndex('unique');
  }

  public readTeamStats(importFilePath: string): TeamStats[] {
    const teamStats = importFile<TeamStats>(
      importFilePath,
      cfg.markCsvFilesAsImported
    );

    const uniqueTeamStats = teamStats.map((t) => {
      return {
        ...t,
        unique: t.country + t.common_name + t.season,
      };
    });

    this.dbService.insertAll(uniqueTeamStats);
    return teamStats;
  }

  public async latestThree(
    team: string,
    country: string,
    year?: number
  ): Promise<UniqueTeamStats[]> {
    const baseYear = year || getYear(new Date());

    const season = [
      `${baseYear - 1}/${baseYear}`,
      `${baseYear - 2}/${baseYear - 1}`,
      `${baseYear}/${baseYear + 1}`,
      baseYear,
      baseYear + 1,
      baseYear - 1,
    ];

    const result: UniqueTeamStats[] = (await this.dbService.asyncFind({
      $and: [
        { country },
        { $or: [{ team_name: team }, { common_name: team }] },
        { season: { $in: season } },
      ],
    })) as UniqueTeamStats[];

    return Promise.resolve(result);
  }

  /**
   * Do an aggregation of all stats (sum up and divide by the count of given stats).
   * @param stats Mandatory. Method assumes that all stats are for one team and one league. Otherwise method will work as well but aggregation dont make sense on stats of different teams.
   * @return aggregated stats or undefined if parameter stats is empty.
   */
  static aggregate(stats: UniqueTeamStats[]): UniqueTeamStats | undefined {
    if (stats.length === 0) {
      return undefined;
    }

    const relevantStats = stats.filter(
      (uts) =>
        uts.matches_played > cfg.stats.ignoreTeamStatsWithGamesPlayedLowerThen
    );
    if (relevantStats.length === 0) {
      return undefined;
    }

    return {
      unique: relevantStats[0].unique,
      team_name: relevantStats[0].team_name,
      common_name: relevantStats[0].common_name,
      season: relevantStats[0].season,
      country: relevantStats[0].country,
      matches_played: meanBy(relevantStats, 'matches_played'),
      matches_played_home: meanBy(relevantStats, 'matches_played_home'),
      matches_played_away: meanBy(relevantStats, 'matches_played_away'),
      suspended_matches: meanBy(relevantStats, 'suspended_matches'),
      wins: meanBy(relevantStats, 'wins'),
      wins_home: meanBy(relevantStats, 'wins_home'),
      wins_away: meanBy(relevantStats, 'wins_away'),
      draws: meanBy(relevantStats, 'draws'),
      draws_home: meanBy(relevantStats, 'draws_home'),
      draws_away: meanBy(relevantStats, 'draws_away'),
      losses: meanBy(relevantStats, 'losses'),
      losses_home: meanBy(relevantStats, 'losses_home'),
      losses_away: meanBy(relevantStats, 'losses_away'),
      points_per_game: meanBy(relevantStats, 'points_per_game'),
      points_per_game_home: meanBy(relevantStats, 'points_per_game_home'),
      points_per_game_away: meanBy(relevantStats, 'points_per_game_away'),
      league_position: meanBy(relevantStats, 'league_position'),
      league_position_home: meanBy(relevantStats, 'league_position_home'),
      league_position_away: meanBy(relevantStats, 'league_position_away'),
      performance_rank: meanBy(relevantStats, 'performance_rank'),
      goals_scored: meanBy(relevantStats, 'goals_scored'),
      goals_conceded: meanBy(relevantStats, 'goals_conceded'),
      goal_difference: meanBy(relevantStats, 'goal_difference'),
      total_goal_count: meanBy(relevantStats, 'total_goal_count'),
      total_goal_count_home: meanBy(relevantStats, 'total_goal_count_home'),
      total_goal_count_away: meanBy(relevantStats, 'total_goal_count_away'),
      goals_scored_home: meanBy(relevantStats, 'goals_scored_home'),
      goals_scored_away: meanBy(relevantStats, 'goals_scored_away'),
      goals_conceded_home: meanBy(relevantStats, 'goals_conceded_home'),
      goals_conceded_away: meanBy(relevantStats, 'goals_conceded_away'),
      goal_difference_home: meanBy(relevantStats, 'goal_difference_home'),
      goal_difference_away: meanBy(relevantStats, 'goal_difference_away'),
      minutes_per_goal_scored: meanBy(relevantStats, 'minutes_per_goal_scored'),
      minutes_per_goal_scored_home: meanBy(
        relevantStats,
        'minutes_per_goal_scored_home'
      ),
      minutes_per_goal_scored_away: meanBy(
        relevantStats,
        'minutes_per_goal_scored_away'
      ),
      minutes_per_goal_conceded: meanBy(
        relevantStats,
        'minutes_per_goal_conceded'
      ),
      minutes_per_goal_conceded_home: meanBy(
        relevantStats,
        'minutes_per_goal_conceded_home'
      ),
      minutes_per_goal_conceded_away: meanBy(
        relevantStats,
        'minutes_per_goal_conceded_away'
      ),
      clean_sheets: meanBy(relevantStats, 'clean_sheets'),
      clean_sheets_home: meanBy(relevantStats, 'clean_sheets_home'),
      clean_sheets_away: meanBy(relevantStats, 'clean_sheets_away'),
      btts_count: meanBy(relevantStats, 'btts_count'),
      btts_count_home: meanBy(relevantStats, 'btts_count_home'),
      btts_count_away: meanBy(relevantStats, 'btts_count_away'),
      fts_count: meanBy(relevantStats, 'fts_count'),
      fts_count_home: meanBy(relevantStats, 'fts_count_home'),
      fts_count_away: meanBy(relevantStats, 'fts_count_away'),
      first_team_to_score_count: meanBy(
        relevantStats,
        'first_team_to_score_count'
      ),
      first_team_to_score_count_home: meanBy(
        relevantStats,
        'first_team_to_score_count_home'
      ),
      first_team_to_score_count_away: meanBy(
        relevantStats,
        'first_team_to_score_count_away'
      ),
      corners_total: meanBy(relevantStats, 'corners_total'),
      corners_total_home: meanBy(relevantStats, 'corners_total_home'),
      corners_total_away: meanBy(relevantStats, 'corners_total_away'),
      cards_total: meanBy(relevantStats, 'cards_total'),
      cards_total_home: meanBy(relevantStats, 'cards_total_home'),
      cards_total_away: meanBy(relevantStats, 'cards_total_away'),
      average_possession: meanBy(relevantStats, 'average_possession'),
      average_possession_home: meanBy(relevantStats, 'average_possession_home'),
      average_possession_away: meanBy(relevantStats, 'average_possession_away'),
      shots: meanBy(relevantStats, 'shots'),
      shots_home: meanBy(relevantStats, 'shots_home'),
      shots_away: meanBy(relevantStats, 'shots_away'),
      shots_on_target: meanBy(relevantStats, 'shots_on_target'),
      shots_on_target_home: meanBy(relevantStats, 'shots_on_target_home'),
      shots_on_target_away: meanBy(relevantStats, 'shots_on_target_away'),
      shots_off_target: meanBy(relevantStats, 'shots_off_target'),
      shots_off_target_home: meanBy(relevantStats, 'shots_off_target_home'),
      shots_off_target_away: meanBy(relevantStats, 'shots_off_target_away'),
      fouls: meanBy(relevantStats, 'fouls'),
      fouls_home: meanBy(relevantStats, 'fouls_home'),
      fouls_away: meanBy(relevantStats, 'fouls_away'),
      goals_scored_half_time: meanBy(relevantStats, 'goals_scored_half_time'),
      goals_scored_half_time_home: meanBy(
        relevantStats,
        'goals_scored_half_time_home'
      ),
      goals_scored_half_time_away: meanBy(
        relevantStats,
        'goals_scored_half_time_away'
      ),
      goals_conceded_half_time: meanBy(
        relevantStats,
        'goals_conceded_half_time'
      ),
      goals_conceded_half_time_home: meanBy(
        relevantStats,
        'goals_conceded_half_time_home'
      ),
      goals_conceded_half_time_away: meanBy(
        relevantStats,
        'goals_conceded_half_time_away'
      ),
      goal_difference_half_time: meanBy(
        relevantStats,
        'goal_difference_half_time'
      ),
      goal_difference_half_time_home: meanBy(
        relevantStats,
        'goal_difference_half_time_home'
      ),
      goal_difference_half_time_away: meanBy(
        relevantStats,
        'goal_difference_half_time_away'
      ),
      leading_at_half_time: meanBy(relevantStats, 'leading_at_half_time'),
      leading_at_half_time_home: meanBy(
        relevantStats,
        'leading_at_half_time_home'
      ),
      leading_at_half_time_away: meanBy(
        relevantStats,
        'leading_at_half_time_away'
      ),
      draw_at_half_time: meanBy(relevantStats, 'draw_at_half_time'),
      draw_at_half_time_home: meanBy(relevantStats, 'draw_at_half_time_home'),
      draw_at_half_time_away: meanBy(relevantStats, 'draw_at_half_time_away'),
      losing_at_half_time: meanBy(relevantStats, 'losing_at_half_time'),
      losing_at_half_time_home: meanBy(
        relevantStats,
        'losing_at_half_time_home'
      ),
      losing_at_half_time_away: meanBy(
        relevantStats,
        'losing_at_half_time_away'
      ),
      points_per_game_half_time: meanBy(
        relevantStats,
        'points_per_game_half_time'
      ),
      points_per_game_half_time_home: meanBy(
        relevantStats,
        'points_per_game_half_time_home'
      ),
      points_per_game_half_time_away: meanBy(
        relevantStats,
        'points_per_game_half_time_away'
      ),
      average_total_goals_per_match: meanBy(
        relevantStats,
        'average_total_goals_per_match'
      ),
      average_total_goals_per_match_home: meanBy(
        relevantStats,
        'average_total_goals_per_match_home'
      ),
      average_total_goals_per_match_away: meanBy(
        relevantStats,
        'average_total_goals_per_match_away'
      ),
      goals_scored_per_match: meanBy(relevantStats, 'goals_scored_per_match'),
      goals_scored_per_match_home: meanBy(
        relevantStats,
        'goals_scored_per_match_home'
      ),
      goals_scored_per_match_away: meanBy(
        relevantStats,
        'goals_scored_per_match_away'
      ),
      goals_conceded_per_match: meanBy(
        relevantStats,
        'goals_conceded_per_match'
      ),
      goals_conceded_per_match_home: meanBy(
        relevantStats,
        'goals_conceded_per_match_home'
      ),
      goals_conceded_per_match_away: meanBy(
        relevantStats,
        'goals_conceded_per_match_away'
      ),
      total_goals_per_match_half_time: meanBy(
        relevantStats,
        'total_goals_per_match_half_time'
      ),
      total_goals_per_match_half_time_home: meanBy(
        relevantStats,
        'total_goals_per_match_half_time_home'
      ),
      total_goals_per_match_half_time_away: meanBy(
        relevantStats,
        'total_goals_per_match_half_time_away'
      ),
      goals_scored_per_match_half_time: meanBy(
        relevantStats,
        'goals_scored_per_match_half_time'
      ),
      goals_scored_per_match_half_time_home: meanBy(
        relevantStats,
        'goals_scored_per_match_half_time_home'
      ),
      goals_scored_per_match_half_time_away: meanBy(
        relevantStats,
        'goals_scored_per_match_half_time_away'
      ),
      goals_conceded_per_match_half_time: meanBy(
        relevantStats,
        'goals_conceded_per_match_half_time'
      ),
      goals_conceded_per_match_half_time_home: meanBy(
        relevantStats,
        'goals_conceded_per_match_half_time_home'
      ),
      goals_conceded_per_match_half_time_away: meanBy(
        relevantStats,
        'goals_conceded_per_match_half_time_away'
      ),
      over05_count: meanBy(relevantStats, 'over05_count'),
      over15_count: meanBy(relevantStats, 'over15_count'),
      over25_count: meanBy(relevantStats, 'over25_count'),
      over35_count: meanBy(relevantStats, 'over35_count'),
      over45_count: meanBy(relevantStats, 'over45_count'),
      over55_count: meanBy(relevantStats, 'over55_count'),
      over05_count_home: meanBy(relevantStats, 'over05_count_home'),
      over15_count_home: meanBy(relevantStats, 'over15_count_home'),
      over25_count_home: meanBy(relevantStats, 'over25_count_home'),
      over35_count_home: meanBy(relevantStats, 'over35_count_home'),
      over45_count_home: meanBy(relevantStats, 'over45_count_home'),
      over55_count_home: meanBy(relevantStats, 'over55_count_home'),
      over05_count_away: meanBy(relevantStats, 'over05_count_away'),
      over15_count_away: meanBy(relevantStats, 'over15_count_away'),
      over25_count_away: meanBy(relevantStats, 'over25_count_away'),
      over35_count_away: meanBy(relevantStats, 'over35_count_away'),
      over45_count_away: meanBy(relevantStats, 'over45_count_away'),
      over55_count_away: meanBy(relevantStats, 'over55_count_away'),
      under05_count: meanBy(relevantStats, 'under05_count'),
      under15_count: meanBy(relevantStats, 'under15_count'),
      under25_count: meanBy(relevantStats, 'under25_count'),
      under35_count: meanBy(relevantStats, 'under35_count'),
      under45_count: meanBy(relevantStats, 'under45_count'),
      under55_count: meanBy(relevantStats, 'under55_count'),
      under05_count_home: meanBy(relevantStats, 'under05_count_home'),
      under15_count_home: meanBy(relevantStats, 'under15_count_home'),
      under25_count_home: meanBy(relevantStats, 'under25_count_home'),
      under35_count_home: meanBy(relevantStats, 'under35_count_home'),
      under45_count_home: meanBy(relevantStats, 'under45_count_home'),
      under55_count_home: meanBy(relevantStats, 'under55_count_home'),
      under05_count_away: meanBy(relevantStats, 'under05_count_away'),
      under15_count_away: meanBy(relevantStats, 'under15_count_away'),
      under25_count_away: meanBy(relevantStats, 'under25_count_away'),
      under35_count_away: meanBy(relevantStats, 'under35_count_away'),
      under45_count_away: meanBy(relevantStats, 'under45_count_away'),
      under55_count_away: meanBy(relevantStats, 'under55_count_away'),
      over05_percentage: meanBy(relevantStats, 'over05_percentage'),
      over15_percentage: meanBy(relevantStats, 'over15_percentage'),
      over25_percentage: meanBy(relevantStats, 'over25_percentage'),
      over35_percentage: meanBy(relevantStats, 'over35_percentage'),
      over45_percentage: meanBy(relevantStats, 'over45_percentage'),
      over55_percentage: meanBy(relevantStats, 'over55_percentage'),
      over05_percentage_home: meanBy(relevantStats, 'over05_percentage_home'),
      over15_percentage_home: meanBy(relevantStats, 'over15_percentage_home'),
      over25_percentage_home: meanBy(relevantStats, 'over25_percentage_home'),
      over35_percentage_home: meanBy(relevantStats, 'over35_percentage_home'),
      over45_percentage_home: meanBy(relevantStats, 'over45_percentage_home'),
      over55_percentage_home: meanBy(relevantStats, 'over55_percentage_home'),
      over05_percentage_away: meanBy(relevantStats, 'over05_percentage_away'),
      over15_percentage_away: meanBy(relevantStats, 'over15_percentage_away'),
      over25_percentage_away: meanBy(relevantStats, 'over25_percentage_away'),
      over35_percentage_away: meanBy(relevantStats, 'over35_percentage_away'),
      over45_percentage_away: meanBy(relevantStats, 'over45_percentage_away'),
      over55_percentage_away: meanBy(relevantStats, 'over55_percentage_away'),
      under05_percentage: meanBy(relevantStats, 'under05_percentage'),
      under15_percentage: meanBy(relevantStats, 'under15_percentage'),
      under25_percentage: meanBy(relevantStats, 'under25_percentage'),
      under35_percentage: meanBy(relevantStats, 'under35_percentage'),
      under45_percentage: meanBy(relevantStats, 'under45_percentage'),
      under55_percentage: meanBy(relevantStats, 'under55_percentage'),
      under05_percentage_home: meanBy(relevantStats, 'under05_percentage_home'),
      under15_percentage_home: meanBy(relevantStats, 'under15_percentage_home'),
      under25_percentage_home: meanBy(relevantStats, 'under25_percentage_home'),
      under35_percentage_home: meanBy(relevantStats, 'under35_percentage_home'),
      under45_percentage_home: meanBy(relevantStats, 'under45_percentage_home'),
      under55_percentage_home: meanBy(relevantStats, 'under55_percentage_home'),
      under05_percentage_away: meanBy(relevantStats, 'under05_percentage_away'),
      under15_percentage_away: meanBy(relevantStats, 'under15_percentage_away'),
      under25_percentage_away: meanBy(relevantStats, 'under25_percentage_away'),
      under35_percentage_away: meanBy(relevantStats, 'under35_percentage_away'),
      under45_percentage_away: meanBy(relevantStats, 'under45_percentage_away'),
      under55_percentage_away: meanBy(relevantStats, 'under55_percentage_away'),
      over05_count_half_time: meanBy(relevantStats, 'over05_count_half_time'),
      over15_count_half_time: meanBy(relevantStats, 'over15_count_half_time'),
      over25_count_half_time: meanBy(relevantStats, 'over25_count_half_time'),
      over05_count_half_time_home: meanBy(
        relevantStats,
        'over05_count_half_time_home'
      ),
      over15_count_half_time_home: meanBy(
        relevantStats,
        'over15_count_half_time_home'
      ),
      over25_count_half_time_home: meanBy(
        relevantStats,
        'over25_count_half_time_home'
      ),
      over05_count_half_time_away: meanBy(
        relevantStats,
        'over05_count_half_time_away'
      ),
      over15_count_half_time_away: meanBy(
        relevantStats,
        'over15_count_half_time_away'
      ),
      over25_count_half_time_away: meanBy(
        relevantStats,
        'over25_count_half_time_away'
      ),
      over05_half_time_percentage: meanBy(
        relevantStats,
        'over05_half_time_percentage'
      ),
      over15_half_time_percentage: meanBy(
        relevantStats,
        'over15_half_time_percentage'
      ),
      over25_half_time_percentage: meanBy(
        relevantStats,
        'over25_half_time_percentage'
      ),
      over05_half_time_percentage_home: meanBy(
        relevantStats,
        'over05_half_time_percentage_home'
      ),
      over15_half_time_percentage_home: meanBy(
        relevantStats,
        'over15_half_time_percentage_home'
      ),
      over25_half_time_percentage_home: meanBy(
        relevantStats,
        'over25_half_time_percentage_home'
      ),
      over05_half_time_percentage_away: meanBy(
        relevantStats,
        'over05_half_time_percentage_away'
      ),
      over15_half_time_percentage_away: meanBy(
        relevantStats,
        'over15_half_time_percentage_away'
      ),
      over25_half_time_percentage_away: meanBy(
        relevantStats,
        'over25_half_time_percentage_away'
      ),
      win_percentage: meanBy(relevantStats, 'win_percentage'),
      win_percentage_home: meanBy(relevantStats, 'win_percentage_home'),
      win_percentage_away: meanBy(relevantStats, 'win_percentage_away'),
      home_advantage_percentage: meanBy(
        relevantStats,
        'home_advantage_percentage'
      ),
      clean_sheet_percentage: meanBy(relevantStats, 'clean_sheet_percentage'),
      clean_sheet_percentage_home: meanBy(
        relevantStats,
        'clean_sheet_percentage_home'
      ),
      clean_sheet_percentage_away: meanBy(
        relevantStats,
        'clean_sheet_percentage_away'
      ),
      btts_percentage: meanBy(relevantStats, 'btts_percentage'),
      btts_percentage_home: meanBy(relevantStats, 'btts_percentage_home'),
      btts_percentage_away: meanBy(relevantStats, 'btts_percentage_away'),
      fts_percentage: meanBy(relevantStats, 'fts_percentage'),
      fts_percentage_home: meanBy(relevantStats, 'fts_percentage_home'),
      fts_percentage_away: meanBy(relevantStats, 'fts_percentage_away'),
      first_team_to_score_percentage: meanBy(
        relevantStats,
        'first_team_to_score_percentage'
      ),
      first_team_to_score_percentage_home: meanBy(
        relevantStats,
        'first_team_to_score_percentage_home'
      ),
      first_team_to_score_percentage_away: meanBy(
        relevantStats,
        'first_team_to_score_percentage_away'
      ),
      clean_sheet_half_time: meanBy(relevantStats, 'clean_sheet_half_time'),
      clean_sheet_half_time_home: meanBy(
        relevantStats,
        'clean_sheet_half_time_home'
      ),
      clean_sheet_half_time_away: meanBy(
        relevantStats,
        'clean_sheet_half_time_away'
      ),
      clean_sheet_half_time_percentage: meanBy(
        relevantStats,
        'clean_sheet_half_time_percentage'
      ),
      clean_sheet_half_time_percentage_home: meanBy(
        relevantStats,
        'clean_sheet_half_time_percentage_home'
      ),
      clean_sheet_half_time_percentage_away: meanBy(
        relevantStats,
        'clean_sheet_half_time_percentage_away'
      ),
      fts_half_time: meanBy(relevantStats, 'fts_half_time'),
      fts_half_time_home: meanBy(relevantStats, 'fts_half_time_home'),
      fts_half_time_away: meanBy(relevantStats, 'fts_half_time_away'),
      fts_half_time_percentage: meanBy(
        relevantStats,
        'fts_half_time_percentage'
      ),
      fts_half_time_percentage_home: meanBy(
        relevantStats,
        'fts_half_time_percentage_home'
      ),
      fts_half_time_percentage_away: meanBy(
        relevantStats,
        'fts_half_time_percentage_away'
      ),
      btts_half_time: meanBy(relevantStats, 'btts_half_time'),
      btts_half_time_home: meanBy(relevantStats, 'btts_half_time_home'),
      btts_half_time_away: meanBy(relevantStats, 'btts_half_time_away'),
      btts_half_time_percentage: meanBy(
        relevantStats,
        'btts_half_time_percentage'
      ),
      btts_half_time_percentage_home: meanBy(
        relevantStats,
        'btts_half_time_percentage_home'
      ),
      btts_half_time_percentage_away: meanBy(
        relevantStats,
        'btts_half_time_percentage_away'
      ),
      leading_at_half_time_percentage: meanBy(
        relevantStats,
        'leading_at_half_time_percentage'
      ),
      leading_at_half_time_percentage_home: meanBy(
        relevantStats,
        'leading_at_half_time_percentage_home'
      ),
      leading_at_half_time_percentage_away: meanBy(
        relevantStats,
        'leading_at_half_time_percentage_away'
      ),
      draw_at_half_time_percentage: meanBy(
        relevantStats,
        'draw_at_half_time_percentage'
      ),
      draw_at_half_time_percentage_home: meanBy(
        relevantStats,
        'draw_at_half_time_percentage_home'
      ),
      draw_at_half_time_percentage_away: meanBy(
        relevantStats,
        'draw_at_half_time_percentage_away'
      ),
      losing_at_half_time_percentage: meanBy(
        relevantStats,
        'losing_at_half_time_percentage'
      ),
      losing_at_half_time_percentage_home: meanBy(
        relevantStats,
        'losing_at_half_time_percentage_home'
      ),
      losing_at_half_time_percentage_away: meanBy(
        relevantStats,
        'losing_at_half_time_percentage_away'
      ),
      corners_per_match: meanBy(relevantStats, 'corners_per_match'),
      corners_per_match_home: meanBy(relevantStats, 'corners_per_match_home'),
      corners_per_match_away: meanBy(relevantStats, 'corners_per_match_away'),
      cards_per_match: meanBy(relevantStats, 'cards_per_match'),
      cards_per_match_home: meanBy(relevantStats, 'cards_per_match_home'),
      cards_per_match_away: meanBy(relevantStats, 'cards_per_match_away'),
      over65_corners_percentage: meanBy(
        relevantStats,
        'over65_corners_percentage'
      ),
      over75_corners_percentage: meanBy(
        relevantStats,
        'over75_corners_percentage'
      ),
      over85_corners_percentage: meanBy(
        relevantStats,
        'over85_corners_percentage'
      ),
      over95_corners_percentage: meanBy(
        relevantStats,
        'over95_corners_percentage'
      ),
      over105_corners_percentage: meanBy(
        relevantStats,
        'over105_corners_percentage'
      ),
      over115_corners_percentage: meanBy(
        relevantStats,
        'over115_corners_percentage'
      ),
      over125_corners_percentage: meanBy(
        relevantStats,
        'over125_corners_percentage'
      ),
      over135_corners_percentage: meanBy(
        relevantStats,
        'over135_corners_percentage'
      ),
      xg_for_avg_overall: meanBy(relevantStats, 'xg_for_avg_overall'),
      xg_for_avg_home: meanBy(relevantStats, 'xg_for_avg_home'),
      xg_for_avg_away: meanBy(relevantStats, 'xg_for_avg_away'),
      xg_against_avg_overall: meanBy(relevantStats, 'xg_against_avg_overall'),
      xg_against_avg_home: meanBy(relevantStats, 'xg_against_avg_home'),
      xg_against_avg_away: meanBy(relevantStats, 'xg_against_avg_away'),
      prediction_risk: meanBy(relevantStats, 'prediction_risk'),
      goals_scored_min_0_to_10: meanBy(
        relevantStats,
        'goals_scored_min_0_to_10'
      ),
      goals_scored_min_11_to_20: meanBy(
        relevantStats,
        'goals_scored_min_11_to_20'
      ),
      goals_scored_min_21_to_30: meanBy(
        relevantStats,
        'goals_scored_min_21_to_30'
      ),
      goals_scored_min_31_to_40: meanBy(
        relevantStats,
        'goals_scored_min_31_to_40'
      ),
      goals_scored_min_41_to_50: meanBy(
        relevantStats,
        'goals_scored_min_41_to_50'
      ),
      goals_scored_min_51_to_60: meanBy(
        relevantStats,
        'goals_scored_min_51_to_60'
      ),
      goals_scored_min_61_to_70: meanBy(
        relevantStats,
        'goals_scored_min_61_to_70'
      ),
      goals_scored_min_71_to_80: meanBy(
        relevantStats,
        'goals_scored_min_71_to_80'
      ),
      goals_scored_min_81_to_90: meanBy(
        relevantStats,
        'goals_scored_min_81_to_90'
      ),
      goals_conceded_min_0_to_10: meanBy(
        relevantStats,
        'goals_conceded_min_0_to_10'
      ),
      goals_conceded_min_11_to_20: meanBy(
        relevantStats,
        'goals_conceded_min_11_to_20'
      ),
      goals_conceded_min_21_to_30: meanBy(
        relevantStats,
        'goals_conceded_min_21_to_30'
      ),
      goals_conceded_min_31_to_40: meanBy(
        relevantStats,
        'goals_conceded_min_31_to_40'
      ),
      goals_conceded_min_41_to_50: meanBy(
        relevantStats,
        'goals_conceded_min_41_to_50'
      ),
      goals_conceded_min_51_to_60: meanBy(
        relevantStats,
        'goals_conceded_min_51_to_60'
      ),
      goals_conceded_min_61_to_70: meanBy(
        relevantStats,
        'goals_conceded_min_61_to_70'
      ),
      goals_conceded_min_71_to_80: meanBy(
        relevantStats,
        'goals_conceded_min_71_to_80'
      ),
      goals_conceded_min_81_to_90: meanBy(
        relevantStats,
        'goals_conceded_min_81_to_90'
      ),
      draw_percentage_overall: meanBy(relevantStats, 'draw_percentage_overall'),
      draw_percentage_home: meanBy(relevantStats, 'draw_percentage_home'),
      draw_percentage_away: meanBy(relevantStats, 'draw_percentage_away'),
      loss_percentage_ovearll: meanBy(relevantStats, 'loss_percentage_ovearll'),
      loss_percentage_home: meanBy(relevantStats, 'loss_percentage_home'),
      loss_percentage_away: meanBy(relevantStats, 'loss_percentage_away'),
      over145_corners_percentage: meanBy(
        relevantStats,
        'over145_corners_percentage'
      ),
    };
  }
}
