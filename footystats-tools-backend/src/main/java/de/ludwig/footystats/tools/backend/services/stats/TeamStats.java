package de.ludwig.footystats.tools.backend.services.stats;

import com.opencsv.bean.CsvCustomBindByName;
import de.ludwig.footystats.tools.backend.services.csv.FloatConverter;
import lombok.Getter;
import lombok.Setter;

public class TeamStats {
    @CsvCustomBindByName(converter = FloatConverter.class, column = "team_name")
    @Getter
    @Setter
    private String team_name;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "common_name")
    @Getter
    @Setter
    private String common_name;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "season")
    @Getter
    @Setter
    private String season;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "country")
    @Getter
    @Setter
    private String country;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "matches_played")
    @Getter
    @Setter
    private Float matches_played;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "matches_played_home")
    @Getter
    @Setter
    private Float matches_played_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "matches_played_away")
    @Getter
    @Setter
    private Float matches_played_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "suspended_matches")
    @Getter
    @Setter
    private Float suspended_matches;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "wins")
    @Getter
    @Setter
    private Float wins;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "wins_home")
    @Getter
    @Setter
    private Float wins_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "wins_away")
    @Getter
    @Setter
    private Float wins_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "draws")
    @Getter
    @Setter
    private Float draws;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "draws_home")
    @Getter
    @Setter
    private Float draws_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "draws_away")
    @Getter
    @Setter
    private Float draws_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "losses")
    @Getter
    @Setter
    private Float losses;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "losses_home")
    @Getter
    @Setter
    private Float losses_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "losses_away")
    @Getter
    @Setter
    private Float losses_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "points_per_game")
    @Getter
    @Setter
    private Float points_per_game;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "points_per_game_home")
    @Getter
    @Setter
    private Float points_per_game_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "points_per_game_away")
    @Getter
    @Setter
    private Float points_per_game_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "league_position")
    @Getter
    @Setter
    private Float league_position;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "league_position_home")
    @Getter
    @Setter
    private Float league_position_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "league_position_away")
    @Getter
    @Setter
    private Float league_position_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "performance_rank")
    @Getter
    @Setter
    private Float performance_rank;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_scored")
    @Getter
    @Setter
    private Float goals_scored;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_conceded")
    @Getter
    @Setter
    private Float goals_conceded;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goal_difference")
    @Getter
    @Setter
    private Float goal_difference;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "total_goal_count")
    @Getter
    @Setter
    private Float total_goal_count;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "total_goal_count_home")
    @Getter
    @Setter
    private Float total_goal_count_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "total_goal_count_away")
    @Getter
    @Setter
    private Float total_goal_count_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_scored_home")
    @Getter
    @Setter
    private Float goals_scored_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_scored_away")
    @Getter
    @Setter
    private Float goals_scored_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_conceded_home")
    @Getter
    @Setter
    private Float goals_conceded_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_conceded_away")
    @Getter
    @Setter
    private Float goals_conceded_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goal_difference_home")
    @Getter
    @Setter
    private Float goal_difference_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goal_difference_away")
    @Getter
    @Setter
    private Float goal_difference_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "minutes_per_goal_scored")
    @Getter
    @Setter
    private Float minutes_per_goal_scored;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "minutes_per_goal_scored_home")
    @Getter
    @Setter
    private Float minutes_per_goal_scored_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "minutes_per_goal_scored_away")
    @Getter
    @Setter
    private Float minutes_per_goal_scored_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "minutes_per_goal_conceded")
    @Getter
    @Setter
    private Float minutes_per_goal_conceded;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "minutes_per_goal_conceded_home")
    @Getter
    @Setter
    private Float minutes_per_goal_conceded_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "minutes_per_goal_conceded_away")
    @Getter
    @Setter
    private Float minutes_per_goal_conceded_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "clean_sheets")
    @Getter
    @Setter
    private Float clean_sheets;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "clean_sheets_home")
    @Getter
    @Setter
    private Float clean_sheets_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "clean_sheets_away")
    @Getter
    @Setter
    private Float clean_sheets_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "btts_count")
    @Getter
    @Setter
    private Float btts_count;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "btts_count_home")
    @Getter
    @Setter
    private Float btts_count_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "btts_count_away")
    @Getter
    @Setter
    private Float btts_count_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "fts_count")
    @Getter
    @Setter
    private Float fts_count;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "fts_count_home")
    @Getter
    @Setter
    private Float fts_count_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "fts_count_away")
    @Getter
    @Setter
    private Float fts_count_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "first_team_to_score_count")
    @Getter
    @Setter
    private Float first_team_to_score_count;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "first_team_to_score_count_home")
    @Getter
    @Setter
    private Float first_team_to_score_count_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "first_team_to_score_count_away")
    @Getter
    @Setter
    private Float first_team_to_score_count_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "corners_total")
    @Getter
    @Setter
    private Float corners_total;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "corners_total_home")
    @Getter
    @Setter
    private Float corners_total_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "corners_total_away")
    @Getter
    @Setter
    private Float corners_total_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "cards_total")
    @Getter
    @Setter
    private Float cards_total;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "cards_total_home")
    @Getter
    @Setter
    private Float cards_total_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "cards_total_away")
    @Getter
    @Setter
    private Float cards_total_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "average_possession")
    @Getter
    @Setter
    private Float average_possession;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "average_possession_home")
    @Getter
    @Setter
    private Float average_possession_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "average_possession_away")
    @Getter
    @Setter
    private Float average_possession_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "shots")
    @Getter
    @Setter
    private Float shots;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "shots_home")
    @Getter
    @Setter
    private Float shots_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "shots_away")
    @Getter
    @Setter
    private Float shots_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "shots_on_target")
    @Getter
    @Setter
    private Float shots_on_target;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "shots_on_target_home")
    @Getter
    @Setter
    private Float shots_on_target_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "shots_on_target_away")
    @Getter
    @Setter
    private Float shots_on_target_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "shots_off_target")
    @Getter
    @Setter
    private Float shots_off_target;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "shots_off_target_home")
    @Getter
    @Setter
    private Float shots_off_target_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "shots_off_target_away")
    @Getter
    @Setter
    private Float shots_off_target_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "fouls")
    @Getter
    @Setter
    private Float fouls;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "fouls_home")
    @Getter
    @Setter
    private Float fouls_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "fouls_away")
    @Getter
    @Setter
    private Float fouls_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_scored_half_time")
    @Getter
    @Setter
    private Float goals_scored_half_time;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_scored_half_time_home")
    @Getter
    @Setter
    private Float goals_scored_half_time_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_scored_half_time_away")
    @Getter
    @Setter
    private Float goals_scored_half_time_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_conceded_half_time")
    @Getter
    @Setter
    private Float goals_conceded_half_time;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_conceded_half_time_home")
    @Getter
    @Setter
    private Float goals_conceded_half_time_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_conceded_half_time_away")
    @Getter
    @Setter
    private Float goals_conceded_half_time_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goal_difference_half_time")
    @Getter
    @Setter
    private Float goal_difference_half_time;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goal_difference_half_time_home")
    @Getter
    @Setter
    private Float goal_difference_half_time_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goal_difference_half_time_away")
    @Getter
    @Setter
    private Float goal_difference_half_time_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "leading_at_half_time")
    @Getter
    @Setter
    private Float leading_at_half_time;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "leading_at_half_time_home")
    @Getter
    @Setter
    private Float leading_at_half_time_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "leading_at_half_time_away")
    @Getter
    @Setter
    private Float leading_at_half_time_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "draw_at_half_time")
    @Getter
    @Setter
    private Float draw_at_half_time;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "draw_at_half_time_home")
    @Getter
    @Setter
    private Float draw_at_half_time_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "draw_at_half_time_away")
    @Getter
    @Setter
    private Float draw_at_half_time_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "losing_at_half_time")
    @Getter
    @Setter
    private Float losing_at_half_time;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "losing_at_half_time_home")
    @Getter
    @Setter
    private Float losing_at_half_time_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "losing_at_half_time_away")
    @Getter
    @Setter
    private Float losing_at_half_time_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "points_per_game_half_time")
    @Getter
    @Setter
    private Float points_per_game_half_time;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "points_per_game_half_time_home")
    @Getter
    @Setter
    private Float points_per_game_half_time_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "points_per_game_half_time_away")
    @Getter
    @Setter
    private Float points_per_game_half_time_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "average_total_goals_per_match")
    @Getter
    @Setter
    private Float average_total_goals_per_match;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "average_total_goals_per_match_home")
    @Getter
    @Setter
    private Float average_total_goals_per_match_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "average_total_goals_per_match_away")
    @Getter
    @Setter
    private Float average_total_goals_per_match_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_scored_per_match")
    @Getter
    @Setter
    private Float goals_scored_per_match;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_scored_per_match_home")
    @Getter
    @Setter
    private Float goals_scored_per_match_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_scored_per_match_away")
    @Getter
    @Setter
    private Float goals_scored_per_match_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_conceded_per_match")
    @Getter
    @Setter
    private Float goals_conceded_per_match;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_conceded_per_match_home")
    @Getter
    @Setter
    private Float goals_conceded_per_match_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_conceded_per_match_away")
    @Getter
    @Setter
    private Float goals_conceded_per_match_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "total_goals_per_match_half_time")
    @Getter
    @Setter
    private Float total_goals_per_match_half_time;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "total_goals_per_match_half_time_home")
    @Getter
    @Setter
    private Float total_goals_per_match_half_time_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "total_goals_per_match_half_time_away")
    @Getter
    @Setter
    private Float total_goals_per_match_half_time_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_scored_per_match_half_time")
    @Getter
    @Setter
    private Float goals_scored_per_match_half_time;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_scored_per_match_half_time_home")
    @Getter
    @Setter
    private Float goals_scored_per_match_half_time_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_scored_per_match_half_time_away")
    @Getter
    @Setter
    private Float goals_scored_per_match_half_time_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_conceded_per_match_half_time")
    @Getter
    @Setter
    private Float goals_conceded_per_match_half_time;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_conceded_per_match_half_time_home")
    @Getter
    @Setter
    private Float goals_conceded_per_match_half_time_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_conceded_per_match_half_time_away")
    @Getter
    @Setter
    private Float goals_conceded_per_match_half_time_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over05_count")
    @Getter
    @Setter
    private Float over05_count;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over15_count")
    @Getter
    @Setter
    private Float over15_count;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over25_count")
    @Getter
    @Setter
    private Float over25_count;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over35_count")
    @Getter
    @Setter
    private Float over35_count;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over45_count")
    @Getter
    @Setter
    private Float over45_count;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over55_count")
    @Getter
    @Setter
    private Float over55_count;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over05_count_home")
    @Getter
    @Setter
    private Float over05_count_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over15_count_home")
    @Getter
    @Setter
    private Float over15_count_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over25_count_home")
    @Getter
    @Setter
    private Float over25_count_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over35_count_home")
    @Getter
    @Setter
    private Float over35_count_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over45_count_home")
    @Getter
    @Setter
    private Float over45_count_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over55_count_home")
    @Getter
    @Setter
    private Float over55_count_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over05_count_away")
    @Getter
    @Setter
    private Float over05_count_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over15_count_away")
    @Getter
    @Setter
    private Float over15_count_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over25_count_away")
    @Getter
    @Setter
    private Float over25_count_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over35_count_away")
    @Getter
    @Setter
    private Float over35_count_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over45_count_away")
    @Getter
    @Setter
    private Float over45_count_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over55_count_away")
    @Getter
    @Setter
    private Float over55_count_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under05_count")
    @Getter
    @Setter
    private Float under05_count;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under15_count")
    @Getter
    @Setter
    private Float under15_count;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under25_count")
    @Getter
    @Setter
    private Float under25_count;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under35_count")
    @Getter
    @Setter
    private Float under35_count;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under45_count")
    @Getter
    @Setter
    private Float under45_count;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under55_count")
    @Getter
    @Setter
    private Float under55_count;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under05_count_home")
    @Getter
    @Setter
    private Float under05_count_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under15_count_home")
    @Getter
    @Setter
    private Float under15_count_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under25_count_home")
    @Getter
    @Setter
    private Float under25_count_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under35_count_home")
    @Getter
    @Setter
    private Float under35_count_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under45_count_home")
    @Getter
    @Setter
    private Float under45_count_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under55_count_home")
    @Getter
    @Setter
    private Float under55_count_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under05_count_away")
    @Getter
    @Setter
    private Float under05_count_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under15_count_away")
    @Getter
    @Setter
    private Float under15_count_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under25_count_away")
    @Getter
    @Setter
    private Float under25_count_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under35_count_away")
    @Getter
    @Setter
    private Float under35_count_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under45_count_away")
    @Getter
    @Setter
    private Float under45_count_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under55_count_away")
    @Getter
    @Setter
    private Float under55_count_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over05_percentage")
    @Getter
    @Setter
    private Float over05_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over15_percentage")
    @Getter
    @Setter
    private Float over15_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over25_percentage")
    @Getter
    @Setter
    private Float over25_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over35_percentage")
    @Getter
    @Setter
    private Float over35_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over45_percentage")
    @Getter
    @Setter
    private Float over45_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over55_percentage")
    @Getter
    @Setter
    private Float over55_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over05_percentage_home")
    @Getter
    @Setter
    private Float over05_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over15_percentage_home")
    @Getter
    @Setter
    private Float over15_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over25_percentage_home")
    @Getter
    @Setter
    private Float over25_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over35_percentage_home")
    @Getter
    @Setter
    private Float over35_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over45_percentage_home")
    @Getter
    @Setter
    private Float over45_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over55_percentage_home")
    @Getter
    @Setter
    private Float over55_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over05_percentage_away")
    @Getter
    @Setter
    private Float over05_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over15_percentage_away")
    @Getter
    @Setter
    private Float over15_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over25_percentage_away")
    @Getter
    @Setter
    private Float over25_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over35_percentage_away")
    @Getter
    @Setter
    private Float over35_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over45_percentage_away")
    @Getter
    @Setter
    private Float over45_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over55_percentage_away")
    @Getter
    @Setter
    private Float over55_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under05_percentage")
    @Getter
    @Setter
    private Float under05_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under15_percentage")
    @Getter
    @Setter
    private Float under15_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under25_percentage")
    @Getter
    @Setter
    private Float under25_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under35_percentage")
    @Getter
    @Setter
    private Float under35_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under45_percentage")
    @Getter
    @Setter
    private Float under45_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under55_percentage")
    @Getter
    @Setter
    private Float under55_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under05_percentage_home")
    @Getter
    @Setter
    private Float under05_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under15_percentage_home")
    @Getter
    @Setter
    private Float under15_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under25_percentage_home")
    @Getter
    @Setter
    private Float under25_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under35_percentage_home")
    @Getter
    @Setter
    private Float under35_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under45_percentage_home")
    @Getter
    @Setter
    private Float under45_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under55_percentage_home")
    @Getter
    @Setter
    private Float under55_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under05_percentage_away")
    @Getter
    @Setter
    private Float under05_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under15_percentage_away")
    @Getter
    @Setter
    private Float under15_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under25_percentage_away")
    @Getter
    @Setter
    private Float under25_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under35_percentage_away")
    @Getter
    @Setter
    private Float under35_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under45_percentage_away")
    @Getter
    @Setter
    private Float under45_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under55_percentage_away")
    @Getter
    @Setter
    private Float under55_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over05_count_half_time")
    @Getter
    @Setter
    private Float over05_count_half_time;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over15_count_half_time")
    @Getter
    @Setter
    private Float over15_count_half_time;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over25_count_half_time")
    @Getter
    @Setter
    private Float over25_count_half_time;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over05_count_half_time_home")
    @Getter
    @Setter
    private Float over05_count_half_time_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over15_count_half_time_home")
    @Getter
    @Setter
    private Float over15_count_half_time_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over25_count_half_time_home")
    @Getter
    @Setter
    private Float over25_count_half_time_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over05_count_half_time_away")
    @Getter
    @Setter
    private Float over05_count_half_time_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over15_count_half_time_away")
    @Getter
    @Setter
    private Float over15_count_half_time_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over25_count_half_time_away")
    @Getter
    @Setter
    private Float over25_count_half_time_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over05_half_time_percentage")
    @Getter
    @Setter
    private Float over05_half_time_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over15_half_time_percentage")
    @Getter
    @Setter
    private Float over15_half_time_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over25_half_time_percentage")
    @Getter
    @Setter
    private Float over25_half_time_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over05_half_time_percentage_home")
    @Getter
    @Setter
    private Float over05_half_time_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over15_half_time_percentage_home")
    @Getter
    @Setter
    private Float over15_half_time_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over25_half_time_percentage_home")
    @Getter
    @Setter
    private Float over25_half_time_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over05_half_time_percentage_away")
    @Getter
    @Setter
    private Float over05_half_time_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over15_half_time_percentage_away")
    @Getter
    @Setter
    private Float over15_half_time_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over25_half_time_percentage_away")
    @Getter
    @Setter
    private Float over25_half_time_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "win_percentage")
    @Getter
    @Setter
    private Float win_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "win_percentage_home")
    @Getter
    @Setter
    private Float win_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "win_percentage_away")
    @Getter
    @Setter
    private Float win_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "home_advantage_percentage")
    @Getter
    @Setter
    private Float home_advantage_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "clean_sheet_percentage")
    @Getter
    @Setter
    private Float clean_sheet_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "clean_sheet_percentage_home")
    @Getter
    @Setter
    private Float clean_sheet_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "clean_sheet_percentage_away")
    @Getter
    @Setter
    private Float clean_sheet_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "btts_percentage")
    @Getter
    @Setter
    private Float btts_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "btts_percentage_home")
    @Getter
    @Setter
    private Float btts_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "btts_percentage_away")
    @Getter
    @Setter
    private Float btts_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "fts_percentage")
    @Getter
    @Setter
    private Float fts_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "fts_percentage_home")
    @Getter
    @Setter
    private Float fts_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "fts_percentage_away")
    @Getter
    @Setter
    private Float fts_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "first_team_to_score_percentage")
    @Getter
    @Setter
    private Float first_team_to_score_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "first_team_to_score_percentage_home")
    @Getter
    @Setter
    private Float first_team_to_score_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "first_team_to_score_percentage_away")
    @Getter
    @Setter
    private Float first_team_to_score_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "clean_sheet_half_time")
    @Getter
    @Setter
    private Float clean_sheet_half_time;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "clean_sheet_half_time_home")
    @Getter
    @Setter
    private Float clean_sheet_half_time_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "clean_sheet_half_time_away")
    @Getter
    @Setter
    private Float clean_sheet_half_time_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "clean_sheet_half_time_percentage")
    @Getter
    @Setter
    private Float clean_sheet_half_time_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "clean_sheet_half_time_percentage_home")
    @Getter
    @Setter
    private Float clean_sheet_half_time_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "clean_sheet_half_time_percentage_away")
    @Getter
    @Setter
    private Float clean_sheet_half_time_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "fts_half_time")
    @Getter
    @Setter
    private Float fts_half_time;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "fts_half_time_home")
    @Getter
    @Setter
    private Float fts_half_time_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "fts_half_time_away")
    @Getter
    @Setter
    private Float fts_half_time_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "fts_half_time_percentage")
    @Getter
    @Setter
    private Float fts_half_time_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "fts_half_time_percentage_home")
    @Getter
    @Setter
    private Float fts_half_time_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "fts_half_time_percentage_away")
    @Getter
    @Setter
    private Float fts_half_time_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "btts_half_time")
    @Getter
    @Setter
    private Float btts_half_time;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "btts_half_time_home")
    @Getter
    @Setter
    private Float btts_half_time_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "btts_half_time_away")
    @Getter
    @Setter
    private Float btts_half_time_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "btts_half_time_percentage")
    @Getter
    @Setter
    private Float btts_half_time_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "btts_half_time_percentage_home")
    @Getter
    @Setter
    private Float btts_half_time_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "btts_half_time_percentage_away")
    @Getter
    @Setter
    private Float btts_half_time_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "leading_at_half_time_percentage")
    @Getter
    @Setter
    private Float leading_at_half_time_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "leading_at_half_time_percentage_home")
    @Getter
    @Setter
    private Float leading_at_half_time_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "leading_at_half_time_percentage_away")
    @Getter
    @Setter
    private Float leading_at_half_time_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "draw_at_half_time_percentage")
    @Getter
    @Setter
    private Float draw_at_half_time_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "draw_at_half_time_percentage_home")
    @Getter
    @Setter
    private Float draw_at_half_time_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "draw_at_half_time_percentage_away")
    @Getter
    @Setter
    private Float draw_at_half_time_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "losing_at_half_time_percentage")
    @Getter
    @Setter
    private Float losing_at_half_time_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "losing_at_half_time_percentage_home")
    @Getter
    @Setter
    private Float losing_at_half_time_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "losing_at_half_time_percentage_away")
    @Getter
    @Setter
    private Float losing_at_half_time_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "corners_per_match")
    @Getter
    @Setter
    private Float corners_per_match;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "corners_per_match_home")
    @Getter
    @Setter
    private Float corners_per_match_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "corners_per_match_away")
    @Getter
    @Setter
    private Float corners_per_match_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "cards_per_match")
    @Getter
    @Setter
    private Float cards_per_match;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "cards_per_match_home")
    @Getter
    @Setter
    private Float cards_per_match_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "cards_per_match_away")
    @Getter
    @Setter
    private Float cards_per_match_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over65_corners_percentage")
    @Getter
    @Setter
    private Float over65_corners_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over75_corners_percentage")
    @Getter
    @Setter
    private Float over75_corners_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over85_corners_percentage")
    @Getter
    @Setter
    private Float over85_corners_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over95_corners_percentage")
    @Getter
    @Setter
    private Float over95_corners_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over105_corners_percentage")
    @Getter
    @Setter
    private Float over105_corners_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over115_corners_percentage")
    @Getter
    @Setter
    private Float over115_corners_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over125_corners_percentage")
    @Getter
    @Setter
    private Float over125_corners_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over135_corners_percentage")
    @Getter
    @Setter
    private Float over135_corners_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "xg_for_avg_overall")
    @Getter
    @Setter
    private Float xg_for_avg_overall;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "xg_for_avg_home")
    @Getter
    @Setter
    private Float xg_for_avg_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "xg_for_avg_away")
    @Getter
    @Setter
    private Float xg_for_avg_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "xg_against_avg_overall")
    @Getter
    @Setter
    private Float xg_against_avg_overall;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "xg_against_avg_home")
    @Getter
    @Setter
    private Float xg_against_avg_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "xg_against_avg_away")
    @Getter
    @Setter
    private Float xg_against_avg_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "prediction_risk")
    @Getter
    @Setter
    private Float prediction_risk;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_scored_min_0_to_10")
    @Getter
    @Setter
    private Float goals_scored_min_0_to_10;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_scored_min_11_to_20")
    @Getter
    @Setter
    private Float goals_scored_min_11_to_20;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_scored_min_21_to_30")
    @Getter
    @Setter
    private Float goals_scored_min_21_to_30;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_scored_min_31_to_40")
    @Getter
    @Setter
    private Float goals_scored_min_31_to_40;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_scored_min_41_to_50")
    @Getter
    @Setter
    private Float goals_scored_min_41_to_50;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_scored_min_51_to_60")
    @Getter
    @Setter
    private Float goals_scored_min_51_to_60;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_scored_min_61_to_70")
    @Getter
    @Setter
    private Float goals_scored_min_61_to_70;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_scored_min_71_to_80")
    @Getter
    @Setter
    private Float goals_scored_min_71_to_80;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_scored_min_81_to_90")
    @Getter
    @Setter
    private Float goals_scored_min_81_to_90;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_conceded_min_0_to_10")
    @Getter
    @Setter
    private Float goals_conceded_min_0_to_10;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_conceded_min_11_to_20")
    @Getter
    @Setter
    private Float goals_conceded_min_11_to_20;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_conceded_min_21_to_30")
    @Getter
    @Setter
    private Float goals_conceded_min_21_to_30;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_conceded_min_31_to_40")
    @Getter
    @Setter
    private Float goals_conceded_min_31_to_40;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_conceded_min_41_to_50")
    @Getter
    @Setter
    private Float goals_conceded_min_41_to_50;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_conceded_min_51_to_60")
    @Getter
    @Setter
    private Float goals_conceded_min_51_to_60;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_conceded_min_61_to_70")
    @Getter
    @Setter
    private Float goals_conceded_min_61_to_70;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_conceded_min_71_to_80")
    @Getter
    @Setter
    private Float goals_conceded_min_71_to_80;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_conceded_min_81_to_90")
    @Getter
    @Setter
    private Float goals_conceded_min_81_to_90;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "draw_percentage_overall")
    @Getter
    @Setter
    private Float draw_percentage_overall;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "draw_percentage_home")
    @Getter
    @Setter
    private Float draw_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "draw_percentage_away")
    @Getter
    @Setter
    private Float draw_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "loss_percentage_ovearll")
    @Getter
    @Setter
    private Float loss_percentage_ovearll;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "loss_percentage_home")
    @Getter
    @Setter
    private Float loss_percentage_home;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "loss_percentage_away")
    @Getter
    @Setter
    private Float loss_percentage_away;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over145_corners_percentage")
    @Getter
    @Setter
    private Float over145_corners_percentage;
}
