package de.ludwig.footystats.tools.backend.services.stats;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import de.ludwig.footystats.tools.backend.services.csv.FloatConverter;
import lombok.*;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@JsonComponent
@NoArgsConstructor
@CompoundIndexes({
	@CompoundIndex(name = "unique", def = "{'teamName' : 1, 'commonName': 1, 'season': 1, 'country': 1}")
})
public class TeamStats {
    @CsvBindByName(column = "team_name")
    @Getter
    @Setter
    private String teamName;
    @CsvBindByName(column = "common_name")
    @Getter
    @Setter
    private String commonName;
    @CsvBindByName(column = "season")
    @Getter
    @Setter
    private String season;
    @CsvBindByName(column = "country")
    @Getter
    @Setter
    private String country;
    @CsvBindByName(column = "matches_played")
    @Getter
    @Setter
    private Integer matches_played;
    @CsvBindByName(column = "matches_played_home")
    @Getter
    @Setter
    private Integer matches_played_home;
    @CsvBindByName(column = "matches_played_away")
    @Getter
    @Setter
    private Integer matches_played_away;
    @CsvBindByName(column = "suspended_matches")
    @Getter
    @Setter
    private Integer suspended_matches;
    @CsvBindByName(column = "wins")
    @Getter
    @Setter
    private Integer wins;
    @CsvBindByName(column = "wins_home")
    @Getter
    @Setter
    private Integer wins_home;
    @CsvBindByName(column = "wins_away")
    @Getter
    @Setter
    private Integer wins_away;
    @CsvBindByName(column = "draws")
    @Getter
    @Setter
    private Integer draws;
    @CsvBindByName(column = "draws_home")
    @Getter
    @Setter
    private Integer draws_home;
    @CsvBindByName(column = "draws_away")
    @Getter
    @Setter
    private Integer draws_away;
    @CsvBindByName(column = "losses")
    @Getter
    @Setter
    private Integer losses;
    @CsvBindByName(column = "losses_home")
    @Getter
    @Setter
    private Integer losses_home;
    @CsvBindByName(column = "losses_away")
    @Getter
    @Setter
    private Integer losses_away;
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
    @CsvBindByName(column = "league_position")
    @Getter
    @Setter
    private Integer league_position;
    @CsvBindByName(column = "league_position_home")
    @Getter
    @Setter
    private Integer league_position_home;
    @CsvBindByName(column = "league_position_away")
    @Getter
    @Setter
    private Integer league_position_away;
    @CsvBindByName(column = "performance_rank")
    @Getter
    @Setter
    private Integer performance_rank;
    @CsvBindByName(column = "goals_scored")
    @Getter
    @Setter
    private Integer goals_scored;
    @CsvBindByName(column = "goals_conceded")
    @Getter
    @Setter
    private Integer goals_conceded;
    @CsvBindByName(column = "goal_difference")
    @Getter
    @Setter
    private Integer goal_difference;
    @CsvBindByName(column = "total_goal_count")
    @Getter
    @Setter
    private Integer total_goal_count;
    @CsvBindByName(column = "total_goal_count_home")
    @Getter
    @Setter
    private Integer total_goal_count_home;
    @CsvBindByName(column = "total_goal_count_away")
    @Getter
    @Setter
    private Integer total_goal_count_away;
    @CsvBindByName(column = "goals_scored_home")
    @Getter
    @Setter
    private Integer goals_scored_home;
    @CsvBindByName(column = "goals_scored_away")
    @Getter
    @Setter
    private Integer goals_scored_away;
    @CsvBindByName(column = "goals_conceded_home")
    @Getter
    @Setter
    private Integer goals_conceded_home;
    @CsvBindByName(column = "goals_conceded_away")
    @Getter
    @Setter
    private Integer goals_conceded_away;
    @CsvBindByName(column = "goal_difference_home")
    @Getter
    @Setter
    private Integer goal_difference_home;
    @CsvBindByName(column = "goal_difference_away")
    @Getter
    @Setter
    private Integer goal_difference_away;
    @CsvBindByName(column = "minutes_per_goal_scored")
    @Getter
    @Setter
    private Integer minutes_per_goal_scored;
    @CsvBindByName(column = "minutes_per_goal_scored_home")
    @Getter
    @Setter
    private Integer minutes_per_goal_scored_home;
    @CsvBindByName(column = "minutes_per_goal_scored_away")
    @Getter
    @Setter
    private Integer minutes_per_goal_scored_away;
    @CsvBindByName(column = "minutes_per_goal_conceded")
    @Getter
    @Setter
    private Integer minutes_per_goal_conceded;
    @CsvBindByName(column = "minutes_per_goal_conceded_home")
    @Getter
    @Setter
    private Integer minutes_per_goal_conceded_home;
    @CsvBindByName(column = "minutes_per_goal_conceded_away")
    @Getter
    @Setter
    private Integer minutes_per_goal_conceded_away;
    @CsvBindByName(column = "clean_sheets")
    @Getter
    @Setter
    private Integer clean_sheets;
    @CsvBindByName(column = "clean_sheets_home")
    @Getter
    @Setter
    private Integer clean_sheets_home;
    @CsvBindByName(column = "clean_sheets_away")
    @Getter
    @Setter
    private Integer clean_sheets_away;
    @CsvBindByName(column = "btts_count")
    @Getter
    @Setter
    private Integer btts_count;
    @CsvBindByName(column = "btts_count_home")
    @Getter
    @Setter
    private Integer btts_count_home;
    @CsvBindByName(column = "btts_count_away")
    @Getter
    @Setter
    private Integer btts_count_away;
    @CsvBindByName(column = "fts_count")
    @Getter
    @Setter
    private Integer fts_count;
    @CsvBindByName(column = "fts_count_home")
    @Getter
    @Setter
    private Integer fts_count_home;
    @CsvBindByName(column = "fts_count_away")
    @Getter
    @Setter
    private Integer fts_count_away;
    @CsvBindByName(column = "first_team_to_score_count")
    @Getter
    @Setter
    private Integer first_team_to_score_count;
    @CsvBindByName(column = "first_team_to_score_count_home")
    @Getter
    @Setter
    private Integer first_team_to_score_count_home;
    @CsvBindByName(column = "first_team_to_score_count_away")
    @Getter
    @Setter
    private Integer first_team_to_score_count_away;
    @CsvBindByName(column = "corners_total")
    @Getter
    @Setter
    private Integer corners_total;
    @CsvBindByName(column = "corners_total_home")
    @Getter
    @Setter
    private Integer corners_total_home;
    @CsvBindByName(column = "corners_total_away")
    @Getter
    @Setter
    private Integer corners_total_away;
    @CsvBindByName(column = "cards_total")
    @Getter
    @Setter
    private Integer cards_total;
    @CsvBindByName(column = "cards_total_home")
    @Getter
    @Setter
    private Integer cards_total_home;
    @CsvBindByName(column = "cards_total_away")
    @Getter
    @Setter
    private Integer cards_total_away;
    @CsvBindByName(column = "average_possession")
    @Getter
    @Setter
    private Integer average_possession;
    @CsvBindByName(column = "average_possession_home")
    @Getter
    @Setter
    private Integer average_possession_home;
    @CsvBindByName(column = "average_possession_away")
    @Getter
    @Setter
    private Integer average_possession_away;
    @CsvBindByName(column = "shots")
    @Getter
    @Setter
    private Integer shots;
    @CsvBindByName(column = "shots_home")
    @Getter
    @Setter
    private Integer shots_home;
    @CsvBindByName(column = "shots_away")
    @Getter
    @Setter
    private Integer shots_away;
    @CsvBindByName(column = "shots_on_target")
    @Getter
    @Setter
    private Integer shots_on_target;
    @CsvBindByName(column = "shots_on_target_home")
    @Getter
    @Setter
    private Integer shots_on_target_home;
    @CsvBindByName(column = "shots_on_target_away")
    @Getter
    @Setter
    private Integer shots_on_target_away;
    @CsvBindByName(column = "shots_off_target")
    @Getter
    @Setter
    private Integer shots_off_target;
    @CsvBindByName(column = "shots_off_target_home")
    @Getter
    @Setter
    private Integer shots_off_target_home;
    @CsvBindByName(column = "shots_off_target_away")
    @Getter
    @Setter
    private Integer shots_off_target_away;
    @CsvBindByName(column = "fouls")
    @Getter
    @Setter
    private Integer fouls;
    @CsvBindByName(column = "fouls_home")
    @Getter
    @Setter
    private Integer fouls_home;
    @CsvBindByName(column = "fouls_away")
    @Getter
    @Setter
    private Integer fouls_away;
    @CsvBindByName(column = "goals_scored_half_time")
    @Getter
    @Setter
    private Integer goals_scored_half_time;
    @CsvBindByName(column = "goals_scored_half_time_home")
    @Getter
    @Setter
    private Integer goals_scored_half_time_home;
    @CsvBindByName(column = "goals_scored_half_time_away")
    @Getter
    @Setter
    private Integer goals_scored_half_time_away;
    @CsvBindByName(column = "goals_conceded_half_time")
    @Getter
    @Setter
    private Integer goals_conceded_half_time;
    @CsvBindByName(column = "goals_conceded_half_time_home")
    @Getter
    @Setter
    private Integer goals_conceded_half_time_home;
    @CsvBindByName(column = "goals_conceded_half_time_away")
    @Getter
    @Setter
    private Integer goals_conceded_half_time_away;
    @CsvBindByName(column = "goal_difference_half_time")
    @Getter
    @Setter
    private Integer goal_difference_half_time;
    @CsvBindByName(column = "goal_difference_half_time_home")
    @Getter
    @Setter
    private Integer goal_difference_half_time_home;
    @CsvBindByName(column = "goal_difference_half_time_away")
    @Getter
    @Setter
    private Integer goal_difference_half_time_away;
    @CsvBindByName(column = "leading_at_half_time")
    @Getter
    @Setter
    private Integer leading_at_half_time;
    @CsvBindByName(column = "leading_at_half_time_home")
    @Getter
    @Setter
    private Integer leading_at_half_time_home;
    @CsvBindByName(column = "leading_at_half_time_away")
    @Getter
    @Setter
    private Integer leading_at_half_time_away;
    @CsvBindByName(column = "draw_at_half_time")
    @Getter
    @Setter
    private Integer draw_at_half_time;
    @CsvBindByName(column = "draw_at_half_time_home")
    @Getter
    @Setter
    private Integer draw_at_half_time_home;
    @CsvBindByName(column = "draw_at_half_time_away")
    @Getter
    @Setter
    private Integer draw_at_half_time_away;
    @CsvBindByName(column = "losing_at_half_time")
    @Getter
    @Setter
    private Integer losing_at_half_time;
    @CsvBindByName(column = "losing_at_half_time_home")
    @Getter
    @Setter
    private Integer losing_at_half_time_home;
    @CsvBindByName(column = "losing_at_half_time_away")
    @Getter
    @Setter
    private Integer losing_at_half_time_away;
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
    @CsvBindByName(column = "over05_count")
    @Getter
    @Setter
    private Integer over05_count;
    @CsvBindByName(column = "over15_count")
    @Getter
    @Setter
    private Integer over15_count;
    @CsvBindByName(column = "over25_count")
    @Getter
    @Setter
    private Integer over25_count;
    @CsvBindByName(column = "over35_count")
    @Getter
    @Setter
    private Integer over35_count;
    @CsvBindByName(column = "over45_count")
    @Getter
    @Setter
    private Integer over45_count;
    @CsvBindByName(column = "over55_count")
    @Getter
    @Setter
    private Integer over55_count;
    @CsvBindByName(column = "over05_count_home")
    @Getter
    @Setter
    private Integer over05_count_home;
    @CsvBindByName(column = "over15_count_home")
    @Getter
    @Setter
    private Integer over15_count_home;
    @CsvBindByName(column = "over25_count_home")
    @Getter
    @Setter
    private Integer over25_count_home;
    @CsvBindByName(column = "over35_count_home")
    @Getter
    @Setter
    private Integer over35_count_home;
    @CsvBindByName(column = "over45_count_home")
    @Getter
    @Setter
    private Integer over45_count_home;
    @CsvBindByName(column = "over55_count_home")
    @Getter
    @Setter
    private Integer over55_count_home;
    @CsvBindByName(column = "over05_count_away")
    @Getter
    @Setter
    private Integer over05_count_away;
    @CsvBindByName(column = "over15_count_away")
    @Getter
    @Setter
    private Integer over15_count_away;
    @CsvBindByName(column = "over25_count_away")
    @Getter
    @Setter
    private Integer over25_count_away;
    @CsvBindByName(column = "over35_count_away")
    @Getter
    @Setter
    private Integer over35_count_away;
    @CsvBindByName(column = "over45_count_away")
    @Getter
    @Setter
    private Integer over45_count_away;
    @CsvBindByName(column = "over55_count_away")
    @Getter
    @Setter
    private Integer over55_count_away;
    @CsvBindByName(column = "under05_count")
    @Getter
    @Setter
    private Integer under05_count;
    @CsvBindByName(column = "under15_count")
    @Getter
    @Setter
    private Integer under15_count;
    @CsvBindByName(column = "under25_count")
    @Getter
    @Setter
    private Integer under25_count;
    @CsvBindByName(column = "under35_count")
    @Getter
    @Setter
    private Integer under35_count;
    @CsvBindByName(column = "under45_count")
    @Getter
    @Setter
    private Integer under45_count;
    @CsvBindByName(column = "under55_count")
    @Getter
    @Setter
    private Integer under55_count;
    @CsvBindByName(column = "under05_count_home")
    @Getter
    @Setter
    private Integer under05_count_home;
    @CsvBindByName(column = "under15_count_home")
    @Getter
    @Setter
    private Integer under15_count_home;
    @CsvBindByName(column = "under25_count_home")
    @Getter
    @Setter
    private Integer under25_count_home;
    @CsvBindByName(column = "under35_count_home")
    @Getter
    @Setter
    private Integer under35_count_home;
    @CsvBindByName(column = "under45_count_home")
    @Getter
    @Setter
    private Integer under45_count_home;
    @CsvBindByName(column = "under55_count_home")
    @Getter
    @Setter
    private Integer under55_count_home;
    @CsvBindByName(column = "under05_count_away")
    @Getter
    @Setter
    private Integer under05_count_away;
    @CsvBindByName(column = "under15_count_away")
    @Getter
    @Setter
    private Integer under15_count_away;
    @CsvBindByName(column = "under25_count_away")
    @Getter
    @Setter
    private Integer under25_count_away;
    @CsvBindByName(column = "under35_count_away")
    @Getter
    @Setter
    private Integer under35_count_away;
    @CsvBindByName(column = "under45_count_away")
    @Getter
    @Setter
    private Integer under45_count_away;
    @CsvBindByName(column = "under55_count_away")
    @Getter
    @Setter
    private Integer under55_count_away;
    @CsvBindByName(column = "over05_percentage")
    @Getter
    @Setter
    private Integer over05_percentage;
    @CsvBindByName(column = "over15_percentage")
    @Getter
    @Setter
    private Integer over15_percentage;
    @CsvBindByName(column = "over25_percentage")
    @Getter
    @Setter
    private Integer over25_percentage;
    @CsvBindByName(column = "over35_percentage")
    @Getter
    @Setter
    private Integer over35_percentage;
    @CsvBindByName(column = "over45_percentage")
    @Getter
    @Setter
    private Integer over45_percentage;
    @CsvBindByName(column = "over55_percentage")
    @Getter
    @Setter
    private Integer over55_percentage;
    @CsvBindByName(column = "over05_percentage_home")
    @Getter
    @Setter
    private Integer over05_percentage_home;
    @CsvBindByName(column = "over15_percentage_home")
    @Getter
    @Setter
    private Integer over15_percentage_home;
    @CsvBindByName(column = "over25_percentage_home")
    @Getter
    @Setter
    private Integer over25_percentage_home;
    @CsvBindByName(column = "over35_percentage_home")
    @Getter
    @Setter
    private Integer over35_percentage_home;
    @CsvBindByName(column = "over45_percentage_home")
    @Getter
    @Setter
    private Integer over45_percentage_home;
    @CsvBindByName(column = "over55_percentage_home")
    @Getter
    @Setter
    private Integer over55_percentage_home;
    @CsvBindByName(column = "over05_percentage_away")
    @Getter
    @Setter
    private Integer over05_percentage_away;
    @CsvBindByName(column = "over15_percentage_away")
    @Getter
    @Setter
    private Integer over15_percentage_away;
    @CsvBindByName(column = "over25_percentage_away")
    @Getter
    @Setter
    private Integer over25_percentage_away;
    @CsvBindByName(column = "over35_percentage_away")
    @Getter
    @Setter
    private Integer over35_percentage_away;
    @CsvBindByName(column = "over45_percentage_away")
    @Getter
    @Setter
    private Integer over45_percentage_away;
    @CsvBindByName(column = "over55_percentage_away")
    @Getter
    @Setter
    private Integer over55_percentage_away;
    @CsvBindByName(column = "under05_percentage")
    @Getter
    @Setter
    private Integer under05_percentage;
    @CsvBindByName(column = "under15_percentage")
    @Getter
    @Setter
    private Integer under15_percentage;
    @CsvBindByName(column = "under25_percentage")
    @Getter
    @Setter
    private Integer under25_percentage;
    @CsvBindByName(column = "under35_percentage")
    @Getter
    @Setter
    private Integer under35_percentage;
    @CsvBindByName(column = "under45_percentage")
    @Getter
    @Setter
    private Integer under45_percentage;
    @CsvBindByName(column = "under55_percentage")
    @Getter
    @Setter
    private Integer under55_percentage;
    @CsvBindByName(column = "under05_percentage_home")
    @Getter
    @Setter
    private Integer under05_percentage_home;
    @CsvBindByName(column = "under15_percentage_home")
    @Getter
    @Setter
    private Integer under15_percentage_home;
    @CsvBindByName(column = "under25_percentage_home")
    @Getter
    @Setter
    private Integer under25_percentage_home;
    @CsvBindByName(column = "under35_percentage_home")
    @Getter
    @Setter
    private Integer under35_percentage_home;
    @CsvBindByName(column = "under45_percentage_home")
    @Getter
    @Setter
    private Integer under45_percentage_home;
    @CsvBindByName(column = "under55_percentage_home")
    @Getter
    @Setter
    private Integer under55_percentage_home;
    @CsvBindByName(column = "under05_percentage_away")
    @Getter
    @Setter
    private Integer under05_percentage_away;
    @CsvBindByName(column = "under15_percentage_away")
    @Getter
    @Setter
    private Integer under15_percentage_away;
    @CsvBindByName(column = "under25_percentage_away")
    @Getter
    @Setter
    private Integer under25_percentage_away;
    @CsvBindByName(column = "under35_percentage_away")
    @Getter
    @Setter
    private Integer under35_percentage_away;
    @CsvBindByName(column = "under45_percentage_away")
    @Getter
    @Setter
    private Integer under45_percentage_away;
    @CsvBindByName(column = "under55_percentage_away")
    @Getter
    @Setter
    private Integer under55_percentage_away;
    @CsvBindByName(column = "over05_count_half_time")
    @Getter
    @Setter
    private Integer over05_count_half_time;
    @CsvBindByName(column = "over15_count_half_time")
    @Getter
    @Setter
    private Integer over15_count_half_time;
    @CsvBindByName(column = "over25_count_half_time")
    @Getter
    @Setter
    private Integer over25_count_half_time;
    @CsvBindByName(column = "over05_count_half_time_home")
    @Getter
    @Setter
    private Integer over05_count_half_time_home;
    @CsvBindByName(column = "over15_count_half_time_home")
    @Getter
    @Setter
    private Integer over15_count_half_time_home;
    @CsvBindByName(column = "over25_count_half_time_home")
    @Getter
    @Setter
    private Integer over25_count_half_time_home;
    @CsvBindByName(column = "over05_count_half_time_away")
    @Getter
    @Setter
    private Integer over05_count_half_time_away;
    @CsvBindByName(column = "over15_count_half_time_away")
    @Getter
    @Setter
    private Integer over15_count_half_time_away;
    @CsvBindByName(column = "over25_count_half_time_away")
    @Getter
    @Setter
    private Integer over25_count_half_time_away;
    @CsvBindByName(column = "over05_half_time_percentage")
    @Getter
    @Setter
    private Integer over05_half_time_percentage;
    @CsvBindByName(column = "over15_half_time_percentage")
    @Getter
    @Setter
    private Integer over15_half_time_percentage;
    @CsvBindByName(column = "over25_half_time_percentage")
    @Getter
    @Setter
    private Integer over25_half_time_percentage;
    @CsvBindByName(column = "over05_half_time_percentage_home")
    @Getter
    @Setter
    private Integer over05_half_time_percentage_home;
    @CsvBindByName(column = "over15_half_time_percentage_home")
    @Getter
    @Setter
    private Integer over15_half_time_percentage_home;
    @CsvBindByName(column = "over25_half_time_percentage_home")
    @Getter
    @Setter
    private Integer over25_half_time_percentage_home;
    @CsvBindByName(column = "over05_half_time_percentage_away")
    @Getter
    @Setter
    private Integer over05_half_time_percentage_away;
    @CsvBindByName(column = "over15_half_time_percentage_away")
    @Getter
    @Setter
    private Integer over15_half_time_percentage_away;
    @CsvBindByName(column = "over25_half_time_percentage_away")
    @Getter
    @Setter
    private Integer over25_half_time_percentage_away;
    @CsvBindByName(column = "win_percentage")
    @Getter
    @Setter
    private Integer win_percentage;
    @CsvBindByName(column = "win_percentage_home")
    @Getter
    @Setter
    private Integer win_percentage_home;
    @CsvBindByName(column = "win_percentage_away")
    @Getter
    @Setter
    private Integer win_percentage_away;
    @CsvBindByName(column = "home_advantage_percentage")
    @Getter
    @Setter
    private Integer home_advantage_percentage;
    @CsvBindByName(column = "clean_sheet_percentage")
    @Getter
    @Setter
    private Integer clean_sheet_percentage;
    @CsvBindByName(column = "clean_sheet_percentage_home")
    @Getter
    @Setter
    private Integer clean_sheet_percentage_home;
    @CsvBindByName(column = "clean_sheet_percentage_away")
    @Getter
    @Setter
    private Integer clean_sheet_percentage_away;
    @CsvBindByName(column = "btts_percentage")
    @Getter
    @Setter
    private Integer btts_percentage;
    @CsvBindByName(column = "btts_percentage_home")
    @Getter
    @Setter
    private Integer btts_percentage_home;
    @CsvBindByName(column = "btts_percentage_away")
    @Getter
    @Setter
    private Integer btts_percentage_away;
    @CsvBindByName(column = "fts_percentage")
    @Getter
    @Setter
    private Integer fts_percentage;
    @CsvBindByName(column = "fts_percentage_home")
    @Getter
    @Setter
    private Integer fts_percentage_home;
    @CsvBindByName(column = "fts_percentage_away")
    @Getter
    @Setter
    private Integer fts_percentage_away;
    @CsvBindByName(column = "first_team_to_score_percentage")
    @Getter
    @Setter
    private Integer first_team_to_score_percentage;
    @CsvBindByName(column = "first_team_to_score_percentage_home")
    @Getter
    @Setter
    private Integer first_team_to_score_percentage_home;
    @CsvBindByName(column = "first_team_to_score_percentage_away")
    @Getter
    @Setter
    private Integer first_team_to_score_percentage_away;
    @CsvBindByName(column = "clean_sheet_half_time")
    @Getter
    @Setter
    private Integer clean_sheet_half_time;
    @CsvBindByName(column = "clean_sheet_half_time_home")
    @Getter
    @Setter
    private Integer clean_sheet_half_time_home;
    @CsvBindByName(column = "clean_sheet_half_time_away")
    @Getter
    @Setter
    private Integer clean_sheet_half_time_away;
    @CsvBindByName(column = "clean_sheet_half_time_percentage")
    @Getter
    @Setter
    private Integer clean_sheet_half_time_percentage;
    @CsvBindByName(column = "clean_sheet_half_time_percentage_home")
    @Getter
    @Setter
    private Integer clean_sheet_half_time_percentage_home;
    @CsvBindByName(column = "clean_sheet_half_time_percentage_away")
    @Getter
    @Setter
    private Integer clean_sheet_half_time_percentage_away;
    @CsvBindByName(column = "fts_half_time")
    @Getter
    @Setter
    private Integer fts_half_time;
    @CsvBindByName(column = "fts_half_time_home")
    @Getter
    @Setter
    private Integer fts_half_time_home;
    @CsvBindByName(column = "fts_half_time_away")
    @Getter
    @Setter
    private Integer fts_half_time_away;
    @CsvBindByName(column = "fts_half_time_percentage")
    @Getter
    @Setter
    private Integer fts_half_time_percentage;
    @CsvBindByName(column = "fts_half_time_percentage_home")
    @Getter
    @Setter
    private Integer fts_half_time_percentage_home;
    @CsvBindByName(column = "fts_half_time_percentage_away")
    @Getter
    @Setter
    private Integer fts_half_time_percentage_away;
    @CsvBindByName(column = "btts_half_time")
    @Getter
    @Setter
    private Integer btts_half_time;
    @CsvBindByName(column = "btts_half_time_home")
    @Getter
    @Setter
    private Integer btts_half_time_home;
    @CsvBindByName(column = "btts_half_time_away")
    @Getter
    @Setter
    private Integer btts_half_time_away;
    @CsvBindByName(column = "btts_half_time_percentage")
    @Getter
    @Setter
    private Integer btts_half_time_percentage;
    @CsvBindByName(column = "btts_half_time_percentage_home")
    @Getter
    @Setter
    private Integer btts_half_time_percentage_home;
    @CsvBindByName(column = "btts_half_time_percentage_away")
    @Getter
    @Setter
    private Integer btts_half_time_percentage_away;
    @CsvBindByName(column = "leading_at_half_time_percentage")
    @Getter
    @Setter
    private Integer leading_at_half_time_percentage;
    @CsvBindByName(column = "leading_at_half_time_percentage_home")
    @Getter
    @Setter
    private Integer leading_at_half_time_percentage_home;
    @CsvBindByName(column = "leading_at_half_time_percentage_away")
    @Getter
    @Setter
    private Integer leading_at_half_time_percentage_away;
    @CsvBindByName(column = "draw_at_half_time_percentage")
    @Getter
    @Setter
    private Integer draw_at_half_time_percentage;
    @CsvBindByName(column = "draw_at_half_time_percentage_home")
    @Getter
    @Setter
    private Integer draw_at_half_time_percentage_home;
    @CsvBindByName(column = "draw_at_half_time_percentage_away")
    @Getter
    @Setter
    private Integer draw_at_half_time_percentage_away;
    @CsvBindByName(column = "losing_at_half_time_percentage")
    @Getter
    @Setter
    private Integer losing_at_half_time_percentage;
    @CsvBindByName(column = "losing_at_half_time_percentage_home")
    @Getter
    @Setter
    private Integer losing_at_half_time_percentage_home;
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
    @CsvBindByName(column = "over65_corners_percentage")
    @Getter
    @Setter
    private Integer over65_corners_percentage;
    @CsvBindByName(column = "over75_corners_percentage")
    @Getter
    @Setter
    private Integer over75_corners_percentage;
    @CsvBindByName(column = "over85_corners_percentage")
    @Getter
    @Setter
    private Integer over85_corners_percentage;
    @CsvBindByName(column = "over95_corners_percentage")
    @Getter
    @Setter
    private Integer over95_corners_percentage;
    @CsvBindByName(column = "over105_corners_percentage")
    @Getter
    @Setter
    private Integer over105_corners_percentage;
    @CsvBindByName(column = "over115_corners_percentage")
    @Getter
    @Setter
    private Integer over115_corners_percentage;
    @CsvBindByName(column = "over125_corners_percentage")
    @Getter
    @Setter
    private Integer over125_corners_percentage;
    @CsvBindByName(column = "over135_corners_percentage")
    @Getter
    @Setter
    private Integer over135_corners_percentage;
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
    @CsvBindByName(column = "prediction_risk")
    @Getter
    @Setter
    private Integer prediction_risk;
    @CsvBindByName(column = "goals_scored_min_0_to_10")
    @Getter
    @Setter
    private Integer goals_scored_min_0_to_10;
    @CsvBindByName(column = "goals_scored_min_11_to_20")
    @Getter
    @Setter
    private Integer goals_scored_min_11_to_20;
    @CsvBindByName(column = "goals_scored_min_21_to_30")
    @Getter
    @Setter
    private Integer goals_scored_min_21_to_30;
    @CsvBindByName(column = "goals_scored_min_31_to_40")
    @Getter
    @Setter
    private Integer goals_scored_min_31_to_40;
    @CsvBindByName(column = "goals_scored_min_41_to_50")
    @Getter
    @Setter
    private Integer goals_scored_min_41_to_50;
    @CsvBindByName(column = "goals_scored_min_51_to_60")
    @Getter
    @Setter
    private Integer goals_scored_min_51_to_60;
    @CsvBindByName(column = "goals_scored_min_61_to_70")
    @Getter
    @Setter
    private Integer goals_scored_min_61_to_70;
    @CsvBindByName(column = "goals_scored_min_71_to_80")
    @Getter
    @Setter
    private Integer goals_scored_min_71_to_80;
    @CsvBindByName(column = "goals_scored_min_81_to_90")
    @Getter
    @Setter
    private Integer goals_scored_min_81_to_90;
    @CsvBindByName(column = "goals_conceded_min_0_to_10")
    @Getter
    @Setter
    private Integer goals_conceded_min_0_to_10;
    @CsvBindByName(column = "goals_conceded_min_11_to_20")
    @Getter
    @Setter
    private Integer goals_conceded_min_11_to_20;
    @CsvBindByName(column = "goals_conceded_min_21_to_30")
    @Getter
    @Setter
    private Integer goals_conceded_min_21_to_30;
    @CsvBindByName(column = "goals_conceded_min_31_to_40")
    @Getter
    @Setter
    private Integer goals_conceded_min_31_to_40;
    @CsvBindByName(column = "goals_conceded_min_41_to_50")
    @Getter
    @Setter
    private Integer goals_conceded_min_41_to_50;
    @CsvBindByName(column = "goals_conceded_min_51_to_60")
    @Getter
    @Setter
    private Integer goals_conceded_min_51_to_60;
    @CsvBindByName(column = "goals_conceded_min_61_to_70")
    @Getter
    @Setter
    private Integer goals_conceded_min_61_to_70;
    @CsvBindByName(column = "goals_conceded_min_71_to_80")
    @Getter
    @Setter
    private Integer goals_conceded_min_71_to_80;
    @CsvBindByName(column = "goals_conceded_min_81_to_90")
    @Getter
    @Setter
    private Integer goals_conceded_min_81_to_90;
    @CsvBindByName(column = "draw_percentage_overall")
    @Getter
    @Setter
    private Integer draw_percentage_overall;
    @CsvBindByName(column = "draw_percentage_home")
    @Getter
    @Setter
    private Integer draw_percentage_home;
    @CsvBindByName(column = "draw_percentage_away")
    @Getter
    @Setter
    private Integer draw_percentage_away;
    @CsvBindByName(column = "loss_percentage_ovearll")
    @Getter
    @Setter
    private Integer loss_percentage_ovearll;
    @CsvBindByName(column = "loss_percentage_home")
    @Getter
    @Setter
    private Integer loss_percentage_home;
    @CsvBindByName(column = "loss_percentage_away")
    @Getter
    @Setter
    private Integer loss_percentage_away;
    @CsvBindByName(column = "over145_corners_percentage")
    @Getter
    @Setter
    private Integer over145_corners_percentage;
}
