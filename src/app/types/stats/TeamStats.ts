export default interface TeamStats {
  team_name: string;
  common_name: string;
  season: string;
  country: string;
  matches_played: number;
  matches_played_home: number;
  matches_played_away: number;
  suspended_matches: number;
  wins: number;
  wins_home: number;
  wins_away: number;
  draws: number;
  draws_home: number;
  draws_away: number;
  losses: number;
  losses_home: number;
  losses_away: number;
  points_per_game: number;
  points_per_game_home: number;
  points_per_game_away: number;
  league_position: number;
  league_position_home: number;
  league_position_away: number;
  performance_rank: number;
  goals_scored: number;
  goals_conceded: number;
  goal_difference: number;
  total_goal_count: number;
  total_goal_count_home: number;
  total_goal_count_away: number;
  goals_scored_home: number;
  goals_scored_away: number;
  goals_conceded_home: number;
  goals_conceded_away: number;
  goal_difference_home: number;
  goal_difference_away: number;
  minutes_per_goal_scored: number;
  minutes_per_goal_scored_home: number;
  minutes_per_goal_scored_away: number;
  minutes_per_goal_conceded: number;
  minutes_per_goal_conceded_home: number;
  minutes_per_goal_conceded_away: number;
  clean_sheets: number;
  clean_sheets_home: number;
  clean_sheets_away: number;
  btts_count: number;
  btts_count_home: number;
  btts_count_away: number;
  fts_count: number;
  fts_count_home: number;
  fts_count_away: number;
  first_team_to_score_count: number;
  first_team_to_score_count_home: number;
  first_team_to_score_count_away: number;
  corners_total: number;
  corners_total_home: number;
  corners_total_away: number;
  cards_total: number;
  cards_total_home: number;
  cards_total_away: number;
  average_possession: number;
  average_possession_home: number;
  average_possession_away: number;
  shots: number;
  shots_home: number;
  shots_away: number;
  shots_on_target: number;
  shots_on_target_home: number;
  shots_on_target_away: number;
  shots_off_target: number;
  shots_off_target_home: number;
  shots_off_target_away: number;
  fouls: number;
  fouls_home: number;
  fouls_away: number;
  goals_scored_half_time: number;
  goals_scored_half_time_home: number;
  goals_scored_half_time_away: number;
  goals_conceded_half_time: number;
  goals_conceded_half_time_home: number;
  goals_conceded_half_time_away: number;
  goal_difference_half_time: number;
  goal_difference_half_time_home: number;
  goal_difference_half_time_away: number;
  leading_at_half_time: number;
  leading_at_half_time_home: number;
  leading_at_half_time_away: number;
  draw_at_half_time: number;
  draw_at_half_time_home: number;
  draw_at_half_time_away: number;
  losing_at_half_time: number;
  losing_at_half_time_home: number;
  losing_at_half_time_away: number;
  points_per_game_half_time: number;
  points_per_game_half_time_home: number;
  points_per_game_half_time_away: number;
  average_total_goals_per_match: number;
  average_total_goals_per_match_home: number;
  average_total_goals_per_match_away: number;
  goals_scored_per_match: number;
  goals_scored_per_match_home: number;
  goals_scored_per_match_away: number;
  goals_conceded_per_match: number;
  goals_conceded_per_match_home: number;
  goals_conceded_per_match_away: number;
  total_goals_per_match_half_time: number;
  total_goals_per_match_half_time_home: number;
  total_goals_per_match_half_time_away: number;
  goals_scored_per_match_half_time: number;
  goals_scored_per_match_half_time_home: number;
  goals_scored_per_match_half_time_away: number;
  goals_conceded_per_match_half_time: number;
  goals_conceded_per_match_half_time_home: number;
  goals_conceded_per_match_half_time_away: number;
  over05_count: number;
  over15_count: number;
  over25_count: number;
  over35_count: number;
  over45_count: number;
  over55_count: number;
  over05_count_home: number;
  over15_count_home: number;
  over25_count_home: number;
  over35_count_home: number;
  over45_count_home: number;
  over55_count_home: number;
  over05_count_away: number;
  over15_count_away: number;
  over25_count_away: number;
  over35_count_away: number;
  over45_count_away: number;
  over55_count_away: number;
  under05_count: number;
  under15_count: number;
  under25_count: number;
  under35_count: number;
  under45_count: number;
  under55_count: number;
  under05_count_home: number;
  under15_count_home: number;
  under25_count_home: number;
  under35_count_home: number;
  under45_count_home: number;
  under55_count_home: number;
  under05_count_away: number;
  under15_count_away: number;
  under25_count_away: number;
  under35_count_away: number;
  under45_count_away: number;
  under55_count_away: number;
  over05_percentage: number;
  over15_percentage: number;
  over25_percentage: number;
  over35_percentage: number;
  over45_percentage: number;
  over55_percentage: number;
  over05_percentage_home: number;
  over15_percentage_home: number;
  over25_percentage_home: number;
  over35_percentage_home: number;
  over45_percentage_home: number;
  over55_percentage_home: number;
  over05_percentage_away: number;
  over15_percentage_away: number;
  over25_percentage_away: number;
  over35_percentage_away: number;
  over45_percentage_away: number;
  over55_percentage_away: number;
  under05_percentage: number;
  under15_percentage: number;
  under25_percentage: number;
  under35_percentage: number;
  under45_percentage: number;
  under55_percentage: number;
  under05_percentage_home: number;
  under15_percentage_home: number;
  under25_percentage_home: number;
  under35_percentage_home: number;
  under45_percentage_home: number;
  under55_percentage_home: number;
  under05_percentage_away: number;
  under15_percentage_away: number;
  under25_percentage_away: number;
  under35_percentage_away: number;
  under45_percentage_away: number;
  under55_percentage_away: number;
  over05_count_half_time: number;
  over15_count_half_time: number;
  over25_count_half_time: number;
  over05_count_half_time_home: number;
  over15_count_half_time_home: number;
  over25_count_half_time_home: number;
  over05_count_half_time_away: number;
  over15_count_half_time_away: number;
  over25_count_half_time_away: number;
  over05_half_time_percentage: number;
  over15_half_time_percentage: number;
  over25_half_time_percentage: number;
  over05_half_time_percentage_home: number;
  over15_half_time_percentage_home: number;
  over25_half_time_percentage_home: number;
  over05_half_time_percentage_away: number;
  over15_half_time_percentage_away: number;
  over25_half_time_percentage_away: number;
  win_percentage: number;
  win_percentage_home: number;
  win_percentage_away: number;
  home_advantage_percentage: number;
  clean_sheet_percentage: number;
  clean_sheet_percentage_home: number;
  clean_sheet_percentage_away: number;
  btts_percentage: number;
  btts_percentage_home: number;
  btts_percentage_away: number;
  fts_percentage: number;
  fts_percentage_home: number;
  fts_percentage_away: number;
  first_team_to_score_percentage: number;
  first_team_to_score_percentage_home: number;
  first_team_to_score_percentage_away: number;
  clean_sheet_half_time: number;
  clean_sheet_half_time_home: number;
  clean_sheet_half_time_away: number;
  clean_sheet_half_time_percentage: number;
  clean_sheet_half_time_percentage_home: number;
  clean_sheet_half_time_percentage_away: number;
  fts_half_time: number;
  fts_half_time_home: number;
  fts_half_time_away: number;
  fts_half_time_percentage: number;
  fts_half_time_percentage_home: number;
  fts_half_time_percentage_away: number;
  btts_half_time: number;
  btts_half_time_home: number;
  btts_half_time_away: number;
  btts_half_time_percentage: number;
  btts_half_time_percentage_home: number;
  btts_half_time_percentage_away: number;
  leading_at_half_time_percentage: number;
  leading_at_half_time_percentage_home: number;
  leading_at_half_time_percentage_away: number;
  draw_at_half_time_percentage: number;
  draw_at_half_time_percentage_home: number;
  draw_at_half_time_percentage_away: number;
  losing_at_half_time_percentage: number;
  losing_at_half_time_percentage_home: number;
  losing_at_half_time_percentage_away: number;
  corners_per_match: number;
  corners_per_match_home: number;
  corners_per_match_away: number;
  cards_per_match: number;
  cards_per_match_home: number;
  cards_per_match_away: number;
  over65_corners_percentage: number;
  over75_corners_percentage: number;
  over85_corners_percentage: number;
  over95_corners_percentage: number;
  over105_corners_percentage: number;
  over115_corners_percentage: number;
  over125_corners_percentage: number;
  over135_corners_percentage: number;
  xg_for_avg_overall: number;
  xg_for_avg_home: number;
  xg_for_avg_away: number;
  xg_against_avg_overall: number;
  xg_against_avg_home: number;
  xg_against_avg_away: number;
  prediction_risk: number;
  goals_scored_min_0_to_10: number;
  goals_scored_min_11_to_20: number;
  goals_scored_min_21_to_30: number;
  goals_scored_min_31_to_40: number;
  goals_scored_min_41_to_50: number;
  goals_scored_min_51_to_60: number;
  goals_scored_min_61_to_70: number;
  goals_scored_min_71_to_80: number;
  goals_scored_min_81_to_90: number;
  goals_conceded_min_0_to_10: number;
  goals_conceded_min_11_to_20: number;
  goals_conceded_min_21_to_30: number;
  goals_conceded_min_31_to_40: number;
  goals_conceded_min_41_to_50: number;
  goals_conceded_min_51_to_60: number;
  goals_conceded_min_61_to_70: number;
  goals_conceded_min_71_to_80: number;
  goals_conceded_min_81_to_90: number;
  draw_percentage_overall: number;
  draw_percentage_home: number;
  draw_percentage_away: number;
  loss_percentage_ovearll: number;
  loss_percentage_home: number;
  loss_percentage_away: number;
  over145_corners_percentage: number;
}
