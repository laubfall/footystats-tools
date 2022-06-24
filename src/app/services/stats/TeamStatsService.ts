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

    return {
      unique: stats[0].unique,
      team_name: stats[0].team_name,
      common_name: stats[0].common_name,
      season: stats[0].season,
      country: stats[0].country,
      matches_played: meanBy(stats, 'matches_played'),
      matches_played_home: meanBy(stats, 'matches_played_home'),
      matches_played_away: meanBy(stats, 'matches_played_away'),
      suspended_matches: meanBy(stats, 'suspended_matches'),
      wins: meanBy(stats, 'wins'),
      wins_home: meanBy(stats, 'wins_home'),
      wins_away: meanBy(stats, 'wins_away'),
      draws: meanBy(stats, 'draws'),
      draws_home: meanBy(stats, 'draws_home'),
      draws_away: meanBy(stats, 'draws_away'),
      losses: meanBy(stats, 'losses'),
      losses_home: meanBy(stats, 'losses_home'),
      losses_away: meanBy(stats, 'losses_away'),
      points_per_game: meanBy(stats, 'points_per_game'),
      points_per_game_home: meanBy(stats, 'points_per_game_home'),
      points_per_game_away: meanBy(stats, 'points_per_game_away'),
      league_position: meanBy(stats, 'league_position'),
      league_position_home: meanBy(stats, 'league_position_home'),
      league_position_away: meanBy(stats, 'league_position_away'),
      performance_rank: meanBy(stats, 'performance_rank'),
      goals_scored: meanBy(stats, 'goals_scored'),
      goals_conceded: meanBy(stats, 'goals_conceded'),
      goal_difference: meanBy(stats, 'goal_difference'),
      total_goal_count: meanBy(stats, 'total_goal_count'),
      total_goal_count_home: meanBy(stats, 'total_goal_count_home'),
      total_goal_count_away: meanBy(stats, 'total_goal_count_away'),
      goals_scored_home: meanBy(stats, 'goals_scored_home'),
      goals_scored_away: meanBy(stats, 'goals_scored_away'),
      goals_conceded_home: meanBy(stats, 'goals_conceded_home'),
      goals_conceded_away: meanBy(stats, 'goals_conceded_away'),
      goal_difference_home: meanBy(stats, 'goal_difference_home'),
      goal_difference_away: meanBy(stats, 'goal_difference_away'),
      minutes_per_goal_scored: meanBy(stats, 'minutes_per_goal_scored'),
      minutes_per_goal_scored_home: meanBy(
        stats,
        'minutes_per_goal_scored_home'
      ),
      minutes_per_goal_scored_away: meanBy(
        stats,
        'minutes_per_goal_scored_away'
      ),
      minutes_per_goal_conceded: meanBy(stats, 'minutes_per_goal_conceded'),
      minutes_per_goal_conceded_home: meanBy(
        stats,
        'minutes_per_goal_conceded_home'
      ),
      minutes_per_goal_conceded_away: meanBy(
        stats,
        'minutes_per_goal_conceded_away'
      ),
      clean_sheets: meanBy(stats, 'clean_sheets'),
      clean_sheets_home: meanBy(stats, 'clean_sheets_home'),
      clean_sheets_away: meanBy(stats, 'clean_sheets_away'),
      btts_count: meanBy(stats, 'btts_count'),
      btts_count_home: meanBy(stats, 'btts_count_home'),
      btts_count_away: meanBy(stats, 'btts_count_away'),
      fts_count: meanBy(stats, 'fts_count'),
      fts_count_home: meanBy(stats, 'fts_count_home'),
      fts_count_away: meanBy(stats, 'fts_count_away'),
      first_team_to_score_count: meanBy(stats, 'first_team_to_score_count'),
      first_team_to_score_count_home: meanBy(
        stats,
        'first_team_to_score_count_home'
      ),
      first_team_to_score_count_away: meanBy(
        stats,
        'first_team_to_score_count_away'
      ),
      corners_total: meanBy(stats, 'corners_total'),
      corners_total_home: meanBy(stats, 'corners_total_home'),
      corners_total_away: meanBy(stats, 'corners_total_away'),
      cards_total: meanBy(stats, 'cards_total'),
      cards_total_home: meanBy(stats, 'cards_total_home'),
      cards_total_away: meanBy(stats, 'cards_total_away'),
      average_possession: meanBy(stats, 'average_possession'),
      average_possession_home: meanBy(stats, 'average_possession_home'),
      average_possession_away: meanBy(stats, 'average_possession_away'),
      shots: meanBy(stats, 'shots'),
      shots_home: meanBy(stats, 'shots_home'),
      shots_away: meanBy(stats, 'shots_away'),
      shots_on_target: meanBy(stats, 'shots_on_target'),
      shots_on_target_home: meanBy(stats, 'shots_on_target_home'),
      shots_on_target_away: meanBy(stats, 'shots_on_target_away'),
      shots_off_target: meanBy(stats, 'shots_off_target'),
      shots_off_target_home: meanBy(stats, 'shots_off_target_home'),
      shots_off_target_away: meanBy(stats, 'shots_off_target_away'),
      fouls: meanBy(stats, 'fouls'),
      fouls_home: meanBy(stats, 'fouls_home'),
      fouls_away: meanBy(stats, 'fouls_away'),
      goals_scored_half_time: meanBy(stats, 'goals_scored_half_time'),
      goals_scored_half_time_home: meanBy(stats, 'goals_scored_half_time_home'),
      goals_scored_half_time_away: meanBy(stats, 'goals_scored_half_time_away'),
      goals_conceded_half_time: meanBy(stats, 'goals_conceded_half_time'),
      goals_conceded_half_time_home: meanBy(
        stats,
        'goals_conceded_half_time_home'
      ),
      goals_conceded_half_time_away: meanBy(
        stats,
        'goals_conceded_half_time_away'
      ),
      goal_difference_half_time: meanBy(stats, 'goal_difference_half_time'),
      goal_difference_half_time_home: meanBy(
        stats,
        'goal_difference_half_time_home'
      ),
      goal_difference_half_time_away: meanBy(
        stats,
        'goal_difference_half_time_away'
      ),
      leading_at_half_time: meanBy(stats, 'leading_at_half_time'),
      leading_at_half_time_home: meanBy(stats, 'leading_at_half_time_home'),
      leading_at_half_time_away: meanBy(stats, 'leading_at_half_time_away'),
      draw_at_half_time: meanBy(stats, 'draw_at_half_time'),
      draw_at_half_time_home: meanBy(stats, 'draw_at_half_time_home'),
      draw_at_half_time_away: meanBy(stats, 'draw_at_half_time_away'),
      losing_at_half_time: meanBy(stats, 'losing_at_half_time'),
      losing_at_half_time_home: meanBy(stats, 'losing_at_half_time_home'),
      losing_at_half_time_away: meanBy(stats, 'losing_at_half_time_away'),
      points_per_game_half_time: meanBy(stats, 'points_per_game_half_time'),
      points_per_game_half_time_home: meanBy(
        stats,
        'points_per_game_half_time_home'
      ),
      points_per_game_half_time_away: meanBy(
        stats,
        'points_per_game_half_time_away'
      ),
      average_total_goals_per_match: meanBy(
        stats,
        'average_total_goals_per_match'
      ),
      average_total_goals_per_match_home: meanBy(
        stats,
        'average_total_goals_per_match_home'
      ),
      average_total_goals_per_match_away: meanBy(
        stats,
        'average_total_goals_per_match_away'
      ),
      goals_scored_per_match: meanBy(stats, 'goals_scored_per_match'),
      goals_scored_per_match_home: meanBy(stats, 'goals_scored_per_match_home'),
      goals_scored_per_match_away: meanBy(stats, 'goals_scored_per_match_away'),
      goals_conceded_per_match: meanBy(stats, 'goals_conceded_per_match'),
      goals_conceded_per_match_home: meanBy(
        stats,
        'goals_conceded_per_match_home'
      ),
      goals_conceded_per_match_away: meanBy(
        stats,
        'goals_conceded_per_match_away'
      ),
      total_goals_per_match_half_time: meanBy(
        stats,
        'total_goals_per_match_half_time'
      ),
      total_goals_per_match_half_time_home: meanBy(
        stats,
        'total_goals_per_match_half_time_home'
      ),
      total_goals_per_match_half_time_away: meanBy(
        stats,
        'total_goals_per_match_half_time_away'
      ),
      goals_scored_per_match_half_time: meanBy(
        stats,
        'goals_scored_per_match_half_time'
      ),
      goals_scored_per_match_half_time_home: meanBy(
        stats,
        'goals_scored_per_match_half_time_home'
      ),
      goals_scored_per_match_half_time_away: meanBy(
        stats,
        'goals_scored_per_match_half_time_away'
      ),
      goals_conceded_per_match_half_time: meanBy(
        stats,
        'goals_conceded_per_match_half_time'
      ),
      goals_conceded_per_match_half_time_home: meanBy(
        stats,
        'goals_conceded_per_match_half_time_home'
      ),
      goals_conceded_per_match_half_time_away: meanBy(
        stats,
        'goals_conceded_per_match_half_time_away'
      ),
      over05_count: meanBy(stats, 'over05_count'),
      over15_count: meanBy(stats, 'over15_count'),
      over25_count: meanBy(stats, 'over25_count'),
      over35_count: meanBy(stats, 'over35_count'),
      over45_count: meanBy(stats, 'over45_count'),
      over55_count: meanBy(stats, 'over55_count'),
      over05_count_home: meanBy(stats, 'over05_count_home'),
      over15_count_home: meanBy(stats, 'over15_count_home'),
      over25_count_home: meanBy(stats, 'over25_count_home'),
      over35_count_home: meanBy(stats, 'over35_count_home'),
      over45_count_home: meanBy(stats, 'over45_count_home'),
      over55_count_home: meanBy(stats, 'over55_count_home'),
      over05_count_away: meanBy(stats, 'over05_count_away'),
      over15_count_away: meanBy(stats, 'over15_count_away'),
      over25_count_away: meanBy(stats, 'over25_count_away'),
      over35_count_away: meanBy(stats, 'over35_count_away'),
      over45_count_away: meanBy(stats, 'over45_count_away'),
      over55_count_away: meanBy(stats, 'over55_count_away'),
      under05_count: meanBy(stats, 'under05_count'),
      under15_count: meanBy(stats, 'under15_count'),
      under25_count: meanBy(stats, 'under25_count'),
      under35_count: meanBy(stats, 'under35_count'),
      under45_count: meanBy(stats, 'under45_count'),
      under55_count: meanBy(stats, 'under55_count'),
      under05_count_home: meanBy(stats, 'under05_count_home'),
      under15_count_home: meanBy(stats, 'under15_count_home'),
      under25_count_home: meanBy(stats, 'under25_count_home'),
      under35_count_home: meanBy(stats, 'under35_count_home'),
      under45_count_home: meanBy(stats, 'under45_count_home'),
      under55_count_home: meanBy(stats, 'under55_count_home'),
      under05_count_away: meanBy(stats, 'under05_count_away'),
      under15_count_away: meanBy(stats, 'under15_count_away'),
      under25_count_away: meanBy(stats, 'under25_count_away'),
      under35_count_away: meanBy(stats, 'under35_count_away'),
      under45_count_away: meanBy(stats, 'under45_count_away'),
      under55_count_away: meanBy(stats, 'under55_count_away'),
      over05_percentage: meanBy(stats, 'over05_percentage'),
      over15_percentage: meanBy(stats, 'over15_percentage'),
      over25_percentage: meanBy(stats, 'over25_percentage'),
      over35_percentage: meanBy(stats, 'over35_percentage'),
      over45_percentage: meanBy(stats, 'over45_percentage'),
      over55_percentage: meanBy(stats, 'over55_percentage'),
      over05_percentage_home: meanBy(stats, 'over05_percentage_home'),
      over15_percentage_home: meanBy(stats, 'over15_percentage_home'),
      over25_percentage_home: meanBy(stats, 'over25_percentage_home'),
      over35_percentage_home: meanBy(stats, 'over35_percentage_home'),
      over45_percentage_home: meanBy(stats, 'over45_percentage_home'),
      over55_percentage_home: meanBy(stats, 'over55_percentage_home'),
      over05_percentage_away: meanBy(stats, 'over05_percentage_away'),
      over15_percentage_away: meanBy(stats, 'over15_percentage_away'),
      over25_percentage_away: meanBy(stats, 'over25_percentage_away'),
      over35_percentage_away: meanBy(stats, 'over35_percentage_away'),
      over45_percentage_away: meanBy(stats, 'over45_percentage_away'),
      over55_percentage_away: meanBy(stats, 'over55_percentage_away'),
      under05_percentage: meanBy(stats, 'under05_percentage'),
      under15_percentage: meanBy(stats, 'under15_percentage'),
      under25_percentage: meanBy(stats, 'under25_percentage'),
      under35_percentage: meanBy(stats, 'under35_percentage'),
      under45_percentage: meanBy(stats, 'under45_percentage'),
      under55_percentage: meanBy(stats, 'under55_percentage'),
      under05_percentage_home: meanBy(stats, 'under05_percentage_home'),
      under15_percentage_home: meanBy(stats, 'under15_percentage_home'),
      under25_percentage_home: meanBy(stats, 'under25_percentage_home'),
      under35_percentage_home: meanBy(stats, 'under35_percentage_home'),
      under45_percentage_home: meanBy(stats, 'under45_percentage_home'),
      under55_percentage_home: meanBy(stats, 'under55_percentage_home'),
      under05_percentage_away: meanBy(stats, 'under05_percentage_away'),
      under15_percentage_away: meanBy(stats, 'under15_percentage_away'),
      under25_percentage_away: meanBy(stats, 'under25_percentage_away'),
      under35_percentage_away: meanBy(stats, 'under35_percentage_away'),
      under45_percentage_away: meanBy(stats, 'under45_percentage_away'),
      under55_percentage_away: meanBy(stats, 'under55_percentage_away'),
      over05_count_half_time: meanBy(stats, 'over05_count_half_time'),
      over15_count_half_time: meanBy(stats, 'over15_count_half_time'),
      over25_count_half_time: meanBy(stats, 'over25_count_half_time'),
      over05_count_half_time_home: meanBy(stats, 'over05_count_half_time_home'),
      over15_count_half_time_home: meanBy(stats, 'over15_count_half_time_home'),
      over25_count_half_time_home: meanBy(stats, 'over25_count_half_time_home'),
      over05_count_half_time_away: meanBy(stats, 'over05_count_half_time_away'),
      over15_count_half_time_away: meanBy(stats, 'over15_count_half_time_away'),
      over25_count_half_time_away: meanBy(stats, 'over25_count_half_time_away'),
      over05_half_time_percentage: meanBy(stats, 'over05_half_time_percentage'),
      over15_half_time_percentage: meanBy(stats, 'over15_half_time_percentage'),
      over25_half_time_percentage: meanBy(stats, 'over25_half_time_percentage'),
      over05_half_time_percentage_home: meanBy(
        stats,
        'over05_half_time_percentage_home'
      ),
      over15_half_time_percentage_home: meanBy(
        stats,
        'over15_half_time_percentage_home'
      ),
      over25_half_time_percentage_home: meanBy(
        stats,
        'over25_half_time_percentage_home'
      ),
      over05_half_time_percentage_away: meanBy(
        stats,
        'over05_half_time_percentage_away'
      ),
      over15_half_time_percentage_away: meanBy(
        stats,
        'over15_half_time_percentage_away'
      ),
      over25_half_time_percentage_away: meanBy(
        stats,
        'over25_half_time_percentage_away'
      ),
      win_percentage: meanBy(stats, 'win_percentage'),
      win_percentage_home: meanBy(stats, 'win_percentage_home'),
      win_percentage_away: meanBy(stats, 'win_percentage_away'),
      home_advantage_percentage: meanBy(stats, 'home_advantage_percentage'),
      clean_sheet_percentage: meanBy(stats, 'clean_sheet_percentage'),
      clean_sheet_percentage_home: meanBy(stats, 'clean_sheet_percentage_home'),
      clean_sheet_percentage_away: meanBy(stats, 'clean_sheet_percentage_away'),
      btts_percentage: meanBy(stats, 'btts_percentage'),
      btts_percentage_home: meanBy(stats, 'btts_percentage_home'),
      btts_percentage_away: meanBy(stats, 'btts_percentage_away'),
      fts_percentage: meanBy(stats, 'fts_percentage'),
      fts_percentage_home: meanBy(stats, 'fts_percentage_home'),
      fts_percentage_away: meanBy(stats, 'fts_percentage_away'),
      first_team_to_score_percentage: meanBy(
        stats,
        'first_team_to_score_percentage'
      ),
      first_team_to_score_percentage_home: meanBy(
        stats,
        'first_team_to_score_percentage_home'
      ),
      first_team_to_score_percentage_away: meanBy(
        stats,
        'first_team_to_score_percentage_away'
      ),
      clean_sheet_half_time: meanBy(stats, 'clean_sheet_half_time'),
      clean_sheet_half_time_home: meanBy(stats, 'clean_sheet_half_time_home'),
      clean_sheet_half_time_away: meanBy(stats, 'clean_sheet_half_time_away'),
      clean_sheet_half_time_percentage: meanBy(
        stats,
        'clean_sheet_half_time_percentage'
      ),
      clean_sheet_half_time_percentage_home: meanBy(
        stats,
        'clean_sheet_half_time_percentage_home'
      ),
      clean_sheet_half_time_percentage_away: meanBy(
        stats,
        'clean_sheet_half_time_percentage_away'
      ),
      fts_half_time: meanBy(stats, 'fts_half_time'),
      fts_half_time_home: meanBy(stats, 'fts_half_time_home'),
      fts_half_time_away: meanBy(stats, 'fts_half_time_away'),
      fts_half_time_percentage: meanBy(stats, 'fts_half_time_percentage'),
      fts_half_time_percentage_home: meanBy(
        stats,
        'fts_half_time_percentage_home'
      ),
      fts_half_time_percentage_away: meanBy(
        stats,
        'fts_half_time_percentage_away'
      ),
      btts_half_time: meanBy(stats, 'btts_half_time'),
      btts_half_time_home: meanBy(stats, 'btts_half_time_home'),
      btts_half_time_away: meanBy(stats, 'btts_half_time_away'),
      btts_half_time_percentage: meanBy(stats, 'btts_half_time_percentage'),
      btts_half_time_percentage_home: meanBy(
        stats,
        'btts_half_time_percentage_home'
      ),
      btts_half_time_percentage_away: meanBy(
        stats,
        'btts_half_time_percentage_away'
      ),
      leading_at_half_time_percentage: meanBy(
        stats,
        'leading_at_half_time_percentage'
      ),
      leading_at_half_time_percentage_home: meanBy(
        stats,
        'leading_at_half_time_percentage_home'
      ),
      leading_at_half_time_percentage_away: meanBy(
        stats,
        'leading_at_half_time_percentage_away'
      ),
      draw_at_half_time_percentage: meanBy(
        stats,
        'draw_at_half_time_percentage'
      ),
      draw_at_half_time_percentage_home: meanBy(
        stats,
        'draw_at_half_time_percentage_home'
      ),
      draw_at_half_time_percentage_away: meanBy(
        stats,
        'draw_at_half_time_percentage_away'
      ),
      losing_at_half_time_percentage: meanBy(
        stats,
        'losing_at_half_time_percentage'
      ),
      losing_at_half_time_percentage_home: meanBy(
        stats,
        'losing_at_half_time_percentage_home'
      ),
      losing_at_half_time_percentage_away: meanBy(
        stats,
        'losing_at_half_time_percentage_away'
      ),
      corners_per_match: meanBy(stats, 'corners_per_match'),
      corners_per_match_home: meanBy(stats, 'corners_per_match_home'),
      corners_per_match_away: meanBy(stats, 'corners_per_match_away'),
      cards_per_match: meanBy(stats, 'cards_per_match'),
      cards_per_match_home: meanBy(stats, 'cards_per_match_home'),
      cards_per_match_away: meanBy(stats, 'cards_per_match_away'),
      over65_corners_percentage: meanBy(stats, 'over65_corners_percentage'),
      over75_corners_percentage: meanBy(stats, 'over75_corners_percentage'),
      over85_corners_percentage: meanBy(stats, 'over85_corners_percentage'),
      over95_corners_percentage: meanBy(stats, 'over95_corners_percentage'),
      over105_corners_percentage: meanBy(stats, 'over105_corners_percentage'),
      over115_corners_percentage: meanBy(stats, 'over115_corners_percentage'),
      over125_corners_percentage: meanBy(stats, 'over125_corners_percentage'),
      over135_corners_percentage: meanBy(stats, 'over135_corners_percentage'),
      xg_for_avg_overall: meanBy(stats, 'xg_for_avg_overall'),
      xg_for_avg_home: meanBy(stats, 'xg_for_avg_home'),
      xg_for_avg_away: meanBy(stats, 'xg_for_avg_away'),
      xg_against_avg_overall: meanBy(stats, 'xg_against_avg_overall'),
      xg_against_avg_home: meanBy(stats, 'xg_against_avg_home'),
      xg_against_avg_away: meanBy(stats, 'xg_against_avg_away'),
      prediction_risk: meanBy(stats, 'prediction_risk'),
      goals_scored_min_0_to_10: meanBy(stats, 'goals_scored_min_0_to_10'),
      goals_scored_min_11_to_20: meanBy(stats, 'goals_scored_min_11_to_20'),
      goals_scored_min_21_to_30: meanBy(stats, 'goals_scored_min_21_to_30'),
      goals_scored_min_31_to_40: meanBy(stats, 'goals_scored_min_31_to_40'),
      goals_scored_min_41_to_50: meanBy(stats, 'goals_scored_min_41_to_50'),
      goals_scored_min_51_to_60: meanBy(stats, 'goals_scored_min_51_to_60'),
      goals_scored_min_61_to_70: meanBy(stats, 'goals_scored_min_61_to_70'),
      goals_scored_min_71_to_80: meanBy(stats, 'goals_scored_min_71_to_80'),
      goals_scored_min_81_to_90: meanBy(stats, 'goals_scored_min_81_to_90'),
      goals_conceded_min_0_to_10: meanBy(stats, 'goals_conceded_min_0_to_10'),
      goals_conceded_min_11_to_20: meanBy(stats, 'goals_conceded_min_11_to_20'),
      goals_conceded_min_21_to_30: meanBy(stats, 'goals_conceded_min_21_to_30'),
      goals_conceded_min_31_to_40: meanBy(stats, 'goals_conceded_min_31_to_40'),
      goals_conceded_min_41_to_50: meanBy(stats, 'goals_conceded_min_41_to_50'),
      goals_conceded_min_51_to_60: meanBy(stats, 'goals_conceded_min_51_to_60'),
      goals_conceded_min_61_to_70: meanBy(stats, 'goals_conceded_min_61_to_70'),
      goals_conceded_min_71_to_80: meanBy(stats, 'goals_conceded_min_71_to_80'),
      goals_conceded_min_81_to_90: meanBy(stats, 'goals_conceded_min_81_to_90'),
      draw_percentage_overall: meanBy(stats, 'draw_percentage_overall'),
      draw_percentage_home: meanBy(stats, 'draw_percentage_home'),
      draw_percentage_away: meanBy(stats, 'draw_percentage_away'),
      loss_percentage_ovearll: meanBy(stats, 'loss_percentage_ovearll'),
      loss_percentage_home: meanBy(stats, 'loss_percentage_home'),
      loss_percentage_away: meanBy(stats, 'loss_percentage_away'),
      over145_corners_percentage: meanBy(stats, 'over145_corners_percentage'),
    };
  }
}
