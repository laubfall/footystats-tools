package de.ludwig.footystats.tools.backend.services.stats;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import de.ludwig.footystats.tools.backend.services.csv.DoubleConverter;
import de.ludwig.footystats.tools.backend.services.csv.IntegerConverter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PlayerStats {

	@CsvBindByName(column = "full_name")
	@Getter
	@Setter
	private String full_name;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "age")
	@Getter
	@Setter
	private Integer age;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "birthday")
	@Getter
	@Setter
	private Integer birthday;
	@CsvBindByName(column = "birthday_GMT")
	@Getter
	@Setter
	private String birthday_GMT;
	@CsvBindByName(column = "league")
	@Getter
	@Setter
	private String league;
	@CsvBindByName(column = "season")
	@Getter
	@Setter
	private String season;
	@CsvBindByName(column = "position")
	@Getter
	@Setter
	private String position;
	@CsvBindByName(column = "Current Club")
	@Getter
	@Setter
	private String current_Club;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "minutes_played_overall")
	@Getter
	@Setter
	private Integer minutes_played_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "minutes_played_home")
	@Getter
	@Setter
	private Integer minutes_played_home;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "minutes_played_away")
	@Getter
	@Setter
	private Integer minutes_played_away;
	@CsvBindByName(column = "nationality")
	@Getter
	@Setter
	private String nationality;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "appearances_overall")
	@Getter
	@Setter
	private Integer appearances_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "appearances_home")
	@Getter
	@Setter
	private Integer appearances_home;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "appearances_away")
	@Getter
	@Setter
	private Integer appearances_away;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_overall")
	@Getter
	@Setter
	private Integer goals_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_home")
	@Getter
	@Setter
	private Integer goals_home;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_away")
	@Getter
	@Setter
	private Integer goals_away;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "assists_overall")
	@Getter
	@Setter
	private Integer assists_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "assists_home")
	@Getter
	@Setter
	private Integer assists_home;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "assists_away")
	@Getter
	@Setter
	private Integer assists_away;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "penalty_goals")
	@Getter
	@Setter
	private Integer penalty_goals;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "penalty_misses")
	@Getter
	@Setter
	private Integer penalty_misses;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "clean_sheets_overall")
	@Getter
	@Setter
	private Integer clean_sheets_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "clean_sheets_home")
	@Getter
	@Setter
	private Integer clean_sheets_home;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "clean_sheets_away")
	@Getter
	@Setter
	private Integer clean_sheets_away;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "conceded_overall")
	@Getter
	@Setter
	private Integer conceded_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "conceded_home")
	@Getter
	@Setter
	private Integer conceded_home;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "conceded_away")
	@Getter
	@Setter
	private Integer conceded_away;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "yellow_cards_overall")
	@Getter
	@Setter
	private Integer yellow_cards_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "red_cards_overall")
	@Getter
	@Setter
	private Integer red_cards_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_involved_per_90_overall")
	@Getter
	@Setter
	private Integer goals_involved_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "assists_per_90_overall")
	@Getter
	@Setter
	private Integer assists_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_per_90_overall")
	@Getter
	@Setter
	private Integer goals_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_per_90_home")
	@Getter
	@Setter
	private Integer goals_per_90_home;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_per_90_away")
	@Getter
	@Setter
	private Integer goals_per_90_away;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "min_per_goal_overall")
	@Getter
	@Setter
	private Integer min_per_goal_overall;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "conceded_per_90_overall")
	@Getter
	@Setter
	private Double conceded_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "min_per_conceded_overall")
	@Getter
	@Setter
	private Integer min_per_conceded_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "min_per_match")
	@Getter
	@Setter
	private Integer min_per_match;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "min_per_card_overall")
	@Getter
	@Setter
	private Integer min_per_card_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "min_per_assist_overall")
	@Getter
	@Setter
	private Integer min_per_assist_overall;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "cards_per_90_overall")
	@Getter
	@Setter
	private Double cards_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "rank_in_league_top_attackers")
	@Getter
	@Setter
	private Integer rank_in_league_top_attackers;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "rank_in_league_top_midfielders")
	@Getter
	@Setter
	private Integer rank_in_league_top_midfielders;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "rank_in_league_top_defenders")
	@Getter
	@Setter
	private Integer rank_in_league_top_defenders;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "rank_in_club_top_scorer")
	@Getter
	@Setter
	private Integer rank_in_club_top_scorer;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "average_rating_overall")
	@Getter
	@Setter
	private Integer average_rating_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "assists_per_game_overall")
	@Getter
	@Setter
	private Integer assists_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "sm_assists_total_overall")
	@Getter
	@Setter
	private Integer sm_assists_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "assists_per90_percentile_overall")
	@Getter
	@Setter
	private Integer assists_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "passes_per_90_overall")
	@Getter
	@Setter
	private Integer passes_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "passes_per_game_overall")
	@Getter
	@Setter
	private Integer passes_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "passes_per90_percentile_overall")
	@Getter
	@Setter
	private Integer passes_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "passes_total_overall")
	@Getter
	@Setter
	private Integer passes_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "passes_completed_per_game_overall")
	@Getter
	@Setter
	private Integer passes_completed_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "passes_completed_total_overall")
	@Getter
	@Setter
	private Integer passes_completed_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pass_completion_rate_percentile_overall")
	@Getter
	@Setter
	private Integer pass_completion_rate_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "passes_completed_per_90_overall")
	@Getter
	@Setter
	private Integer passes_completed_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "passes_completed_per90_percentile_overall")
	@Getter
	@Setter
	private Integer passes_completed_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "short_passes_per_game_overall")
	@Getter
	@Setter
	private Integer short_passes_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "long_passes_per_game_overall")
	@Getter
	@Setter
	private Integer long_passes_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "key_passes_per_game_overall")
	@Getter
	@Setter
	private Integer key_passes_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "key_passes_total_overall")
	@Getter
	@Setter
	private Integer key_passes_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "through_passes_per_game_overall")
	@Getter
	@Setter
	private Integer through_passes_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "crosses_per_game_overall")
	@Getter
	@Setter
	private Integer crosses_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "tackles_per_90_overall")
	@Getter
	@Setter
	private Integer tackles_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "tackles_per_game_overall")
	@Getter
	@Setter
	private Integer tackles_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "tackles_total_overall")
	@Getter
	@Setter
	private Integer tackles_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "tackles_successful_per_game_overall")
	@Getter
	@Setter
	private Integer tackles_successful_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dispossesed_per_game_overall")
	@Getter
	@Setter
	private Integer dispossesed_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "possession_regained_per_game_overall")
	@Getter
	@Setter
	private Integer possession_regained_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pressures_per_game_overall")
	@Getter
	@Setter
	private Integer pressures_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "saves_per_game_overall")
	@Getter
	@Setter
	private Integer saves_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "interceptions_per_game_overall")
	@Getter
	@Setter
	private Integer interceptions_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_successful_per_game_overall")
	@Getter
	@Setter
	private Integer dribbles_successful_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_faced_per_game_overall")
	@Getter
	@Setter
	private Integer shots_faced_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_per_goal_scored_overall")
	@Getter
	@Setter
	private Integer shots_per_goal_scored_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_per_90_overall")
	@Getter
	@Setter
	private Integer shots_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_off_target_per_game_overall")
	@Getter
	@Setter
	private Integer shots_off_target_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_per_game_overall")
	@Getter
	@Setter
	private Integer dribbles_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "distance_travelled_per_game_overall")
	@Getter
	@Setter
	private Integer distance_travelled_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_on_target_per_game_overall")
	@Getter
	@Setter
	private Integer shots_on_target_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xg_per_game_overall")
	@Getter
	@Setter
	private Integer xg_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "chances_created_per_game_overall")
	@Getter
	@Setter
	private Integer chances_created_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_won_per_game_overall")
	@Getter
	@Setter
	private Integer aerial_duels_won_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_per_game_overall")
	@Getter
	@Setter
	private Integer aerial_duels_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "possession_regained_per_90_overall")
	@Getter
	@Setter
	private Integer possession_regained_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "possession_regained_total_overall")
	@Getter
	@Setter
	private Integer possession_regained_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "possession_regained_per90_percentile_overall")
	@Getter
	@Setter
	private Integer possession_regained_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "additional_info")
	@Getter
	@Setter
	private Integer additional_info;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_total_overall")
	@Getter
	@Setter
	private Integer shots_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_per_game_overall")
	@Getter
	@Setter
	private Integer shots_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_per90_percentile_overall")
	@Getter
	@Setter
	private Integer shots_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_on_target_total_overall")
	@Getter
	@Setter
	private Integer shots_on_target_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_on_target_per_90_overall")
	@Getter
	@Setter
	private Integer shots_on_target_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_on_target_per90_percentile_overall")
	@Getter
	@Setter
	private Integer shots_on_target_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_off_target_total_overall")
	@Getter
	@Setter
	private Integer shots_off_target_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_off_target_per_90_overall")
	@Getter
	@Setter
	private Integer shots_off_target_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_off_target_per90_percentile_overall")
	@Getter
	@Setter
	private Integer shots_off_target_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "games_subbed_out")
	@Getter
	@Setter
	private Integer games_subbed_out;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "games_subbed_in")
	@Getter
	@Setter
	private Integer games_subbed_in;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "games_started")
	@Getter
	@Setter
	private Integer games_started;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "tackles_per90_percentile_overall")
	@Getter
	@Setter
	private Integer tackles_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "tackles_successful_per_90_overall")
	@Getter
	@Setter
	private Integer tackles_successful_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "tackles_successful_per90_percentile_overall")
	@Getter
	@Setter
	private Integer tackles_successful_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "tackles_successful_total_overall")
	@Getter
	@Setter
	private Integer tackles_successful_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "interceptions_total_overall")
	@Getter
	@Setter
	private Integer interceptions_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "interceptions_per_90_overall")
	@Getter
	@Setter
	private Integer interceptions_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "interceptions_per90_percentile_overall")
	@Getter
	@Setter
	private Integer interceptions_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "crosses_total_overall")
	@Getter
	@Setter
	private Integer crosses_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "cross_completion_rate_percentile_overall")
	@Getter
	@Setter
	private Integer cross_completion_rate_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "crosses_per_90_overall")
	@Getter
	@Setter
	private Integer crosses_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "crosses_per90_percentile_overall")
	@Getter
	@Setter
	private Integer crosses_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "through_passes_total_overall")
	@Getter
	@Setter
	private Integer through_passes_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "through_passes_per_90_overall")
	@Getter
	@Setter
	private Integer through_passes_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "through_passes_per90_percentile_overall")
	@Getter
	@Setter
	private Integer through_passes_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "long_passes_total_overall")
	@Getter
	@Setter
	private Integer long_passes_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "long_passes_per_90_overall")
	@Getter
	@Setter
	private Integer long_passes_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "long_passes_per90_percentile_overall")
	@Getter
	@Setter
	private Integer long_passes_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "short_passes_total_overall")
	@Getter
	@Setter
	private Integer short_passes_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "short_passes_per_90_overall")
	@Getter
	@Setter
	private Integer short_passes_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "short_passes_per90_percentile_overall")
	@Getter
	@Setter
	private Integer short_passes_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "key_passes_per_90_overall")
	@Getter
	@Setter
	private Integer key_passes_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "key_passes_per90_percentile_overall")
	@Getter
	@Setter
	private Integer key_passes_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_total_overall")
	@Getter
	@Setter
	private Integer dribbles_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_per_90_overall")
	@Getter
	@Setter
	private Integer dribbles_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_per90_percentile_overall")
	@Getter
	@Setter
	private Integer dribbles_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_successful_total_overall")
	@Getter
	@Setter
	private Integer dribbles_successful_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_successful_per_90_overall")
	@Getter
	@Setter
	private Integer dribbles_successful_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_successful_percentage_overall")
	@Getter
	@Setter
	private Integer dribbles_successful_percentage_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "chances_created_total_overall")
	@Getter
	@Setter
	private Integer chances_created_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "chances_created_per_90_overall")
	@Getter
	@Setter
	private Integer chances_created_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "chances_created_per90_percentile_overall")
	@Getter
	@Setter
	private Integer chances_created_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "saves_total_overall")
	@Getter
	@Setter
	private Integer saves_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "save_percentage_percentile_overall")
	@Getter
	@Setter
	private Integer save_percentage_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "saves_per_90_overall")
	@Getter
	@Setter
	private Integer saves_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "saves_per90_percentile_overall")
	@Getter
	@Setter
	private Integer saves_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_faced_total_overall")
	@Getter
	@Setter
	private Integer shots_faced_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_per_goal_conceded_overall")
	@Getter
	@Setter
	private Integer shots_per_goal_conceded_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "sm_goals_conceded_total_overall")
	@Getter
	@Setter
	private Integer sm_goals_conceded_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "conceded_per90_percentile_overall")
	@Getter
	@Setter
	private Integer conceded_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_faced_per_90_overall")
	@Getter
	@Setter
	private Integer shots_faced_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_faced_per90_percentile_overall")
	@Getter
	@Setter
	private Integer shots_faced_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xg_faced_per_90_overall")
	@Getter
	@Setter
	private Integer xg_faced_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xg_faced_per90_percentile_overall")
	@Getter
	@Setter
	private Integer xg_faced_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xg_faced_per_game_overall")
	@Getter
	@Setter
	private Integer xg_faced_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xg_faced_total_overall")
	@Getter
	@Setter
	private Integer xg_faced_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "save_percentage_overall")
	@Getter
	@Setter
	private Integer save_percentage_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pressures_total_overall")
	@Getter
	@Setter
	private Integer pressures_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pressures_per_90_overall")
	@Getter
	@Setter
	private Integer pressures_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pressures_per90_percentile_overall")
	@Getter
	@Setter
	private Integer pressures_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xg_total_overall")
	@Getter
	@Setter
	private Integer xg_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "market_value")
	@Getter
	@Setter
	private Integer market_value;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "market_value_percentile")
	@Getter
	@Setter
	private Integer market_value_percentile;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pass_completion_rate_overall")
	@Getter
	@Setter
	private Integer pass_completion_rate_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shot_accuraccy_percentage_overall")
	@Getter
	@Setter
	private Integer shot_accuraccy_percentage_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shot_accuraccy_percentage_percentile_overall")
	@Getter
	@Setter
	private Integer shot_accuraccy_percentage_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "sm_goals_scored_total_overall")
	@Getter
	@Setter
	private Integer sm_goals_scored_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbled_past_per90_percentile_overall")
	@Getter
	@Setter
	private Integer dribbled_past_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbled_past_per_game_overall")
	@Getter
	@Setter
	private Integer dribbled_past_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbled_past_per_90_overall")
	@Getter
	@Setter
	private Integer dribbled_past_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbled_past_total_overall")
	@Getter
	@Setter
	private Integer dribbled_past_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_successful_per90_percentile_overall")
	@Getter
	@Setter
	private Integer dribbles_successful_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_successful_percentage_percentile_overall")
	@Getter
	@Setter
	private Integer dribbles_successful_percentage_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pen_scored_total_overall")
	@Getter
	@Setter
	private Integer pen_scored_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pen_missed_total_overall")
	@Getter
	@Setter
	private Integer pen_missed_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "inside_box_saves_total_overall")
	@Getter
	@Setter
	private Integer inside_box_saves_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "blocks_per_game_overall")
	@Getter
	@Setter
	private Integer blocks_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "blocks_per_90_overall")
	@Getter
	@Setter
	private Integer blocks_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "blocks_total_overall")
	@Getter
	@Setter
	private Integer blocks_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "blocks_per90_percentile_overall")
	@Getter
	@Setter
	private Integer blocks_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "ratings_total_overall")
	@Getter
	@Setter
	private Integer ratings_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "clearances_per_game_overall")
	@Getter
	@Setter
	private Integer clearances_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "clearances_per_90_overall")
	@Getter
	@Setter
	private Integer clearances_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "clearances_total_overall")
	@Getter
	@Setter
	private Integer clearances_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pen_committed_total_overall")
	@Getter
	@Setter
	private Integer pen_committed_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pen_save_percentage_overall")
	@Getter
	@Setter
	private Integer pen_save_percentage_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pen_committed_per_90_overall")
	@Getter
	@Setter
	private Integer pen_committed_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pen_committed_per90_percentile_overall")
	@Getter
	@Setter
	private Integer pen_committed_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pen_committed_per_game_overall")
	@Getter
	@Setter
	private Integer pen_committed_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pens_saved_total_overall")
	@Getter
	@Setter
	private Integer pens_saved_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pens_taken_total_overall")
	@Getter
	@Setter
	private Integer pens_taken_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "hit_woodwork_total_overall")
	@Getter
	@Setter
	private Integer hit_woodwork_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "hit_woodwork_per_game_overall")
	@Getter
	@Setter
	private Integer hit_woodwork_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "hit_woodwork_per_90_overall")
	@Getter
	@Setter
	private Integer hit_woodwork_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "punches_total_overall")
	@Getter
	@Setter
	private Integer punches_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "punches_per_game_overall")
	@Getter
	@Setter
	private Integer punches_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "punches_per_90_overall")
	@Getter
	@Setter
	private Integer punches_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "offsides_per_90_overall")
	@Getter
	@Setter
	private Integer offsides_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "offsides_per_game_overall")
	@Getter
	@Setter
	private Integer offsides_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "offsides_total_overall")
	@Getter
	@Setter
	private Integer offsides_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "penalties_won_total_overall")
	@Getter
	@Setter
	private Integer penalties_won_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shot_conversion_rate_overall")
	@Getter
	@Setter
	private Integer shot_conversion_rate_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shot_conversion_rate_percentile_overall")
	@Getter
	@Setter
	private Integer shot_conversion_rate_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "sm_minutes_played_per90_percentile_overall")
	@Getter
	@Setter
	private Integer sm_minutes_played_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "sm_minutes_played_recorded_overall")
	@Getter
	@Setter
	private Integer sm_minutes_played_recorded_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "minutes_played_percentile_overall")
	@Getter
	@Setter
	private Integer minutes_played_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "matches_played_percentile_overall")
	@Getter
	@Setter
	private Integer matches_played_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "min_per_goal_percentile_overall")
	@Getter
	@Setter
	private Integer min_per_goal_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "min_per_conceded_percentile_overall")
	@Getter
	@Setter
	private Integer min_per_conceded_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xa_total_overall")
	@Getter
	@Setter
	private Integer xa_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xa_per90_percentile_overall")
	@Getter
	@Setter
	private Integer xa_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xa_per_game_overall")
	@Getter
	@Setter
	private Integer xa_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xa_per_90_overall")
	@Getter
	@Setter
	private Integer xa_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "npxg_total_overall")
	@Getter
	@Setter
	private Integer npxg_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "npxg_per90_percentile_overall")
	@Getter
	@Setter
	private Integer npxg_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "npxg_per_game_overall")
	@Getter
	@Setter
	private Integer npxg_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "npxg_per_90_overall")
	@Getter
	@Setter
	private Integer npxg_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "fouls_drawn_per90_percentile_overall")
	@Getter
	@Setter
	private Integer fouls_drawn_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "fouls_drawn_total_overall")
	@Getter
	@Setter
	private Integer fouls_drawn_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "fouls_drawn_per_game_overall")
	@Getter
	@Setter
	private Integer fouls_drawn_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "fouls_drawn_per_90_overall")
	@Getter
	@Setter
	private Integer fouls_drawn_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "fouls_committed_per_90_overall")
	@Getter
	@Setter
	private Integer fouls_committed_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "fouls_committed_per_game_overall")
	@Getter
	@Setter
	private Integer fouls_committed_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "fouls_committed_per90_percentile_overall")
	@Getter
	@Setter
	private Integer fouls_committed_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "fouls_committed_total_overall")
	@Getter
	@Setter
	private Integer fouls_committed_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xg_per_90_overall")
	@Getter
	@Setter
	private Integer xg_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xg_per90_percentile_overall")
	@Getter
	@Setter
	private Integer xg_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "average_rating_percentile_overall")
	@Getter
	@Setter
	private Integer average_rating_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "clearances_per90_percentile_overall")
	@Getter
	@Setter
	private Integer clearances_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "hit_woodwork_per90_percentile_overall")
	@Getter
	@Setter
	private Integer hit_woodwork_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "punches_per90_percentile_overall")
	@Getter
	@Setter
	private Integer punches_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "offsides_per90_percentile_overall")
	@Getter
	@Setter
	private Integer offsides_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_won_per90_percentile_overall")
	@Getter
	@Setter
	private Integer aerial_duels_won_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_total_overall")
	@Getter
	@Setter
	private Integer aerial_duels_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_per_90_overall")
	@Getter
	@Setter
	private Integer aerial_duels_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_per90_percentile_overall")
	@Getter
	@Setter
	private Integer aerial_duels_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_won_total_overall")
	@Getter
	@Setter
	private Integer aerial_duels_won_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_won_percentage_overall")
	@Getter
	@Setter
	private Integer aerial_duels_won_percentage_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_won_per_90_overall")
	@Getter
	@Setter
	private Integer aerial_duels_won_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_per_90_overall")
	@Getter
	@Setter
	private Integer duels_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_per_game_overall")
	@Getter
	@Setter
	private Integer duels_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_total_overall")
	@Getter
	@Setter
	private Integer duels_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_won_total_overall")
	@Getter
	@Setter
	private Integer duels_won_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_won_per90_percentile_overall")
	@Getter
	@Setter
	private Integer duels_won_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_per90_percentile_overall")
	@Getter
	@Setter
	private Integer duels_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_won_per_90_overall")
	@Getter
	@Setter
	private Integer duels_won_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_won_per_game_overall")
	@Getter
	@Setter
	private Integer duels_won_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_won_percentage_overall")
	@Getter
	@Setter
	private Integer duels_won_percentage_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dispossesed_total_overall")
	@Getter
	@Setter
	private Integer dispossesed_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dispossesed_per_90_overall")
	@Getter
	@Setter
	private Integer dispossesed_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dispossesed_per90_percentile_overall")
	@Getter
	@Setter
	private Integer dispossesed_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "progressive_passes_total_overall")
	@Getter
	@Setter
	private Integer progressive_passes_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "cross_completion_rate_overall")
	@Getter
	@Setter
	private Integer cross_completion_rate_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "distance_travelled_total_overall")
	@Getter
	@Setter
	private Integer distance_travelled_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "distance_travelled_per_90_overall")
	@Getter
	@Setter
	private Integer distance_travelled_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "distance_travelled_per90_percentile_overall")
	@Getter
	@Setter
	private Integer distance_travelled_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "accurate_crosses_total_overall")
	@Getter
	@Setter
	private Integer accurate_crosses_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "accurate_crosses_per_game_overall")
	@Getter
	@Setter
	private Integer accurate_crosses_per_game_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "accurate_crosses_per_90_overall")
	@Getter
	@Setter
	private Integer accurate_crosses_per_90_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "accurate_crosses_per90_percentile_overall")
	@Getter
	@Setter
	private Integer accurate_crosses_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "sm_matches_recorded_total_overall")
	@Getter
	@Setter
	private Integer sm_matches_recorded_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "games_started_percentile_overall")
	@Getter
	@Setter
	private Integer games_started_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "games_subbed_in_percentile_overall")
	@Getter
	@Setter
	private Integer games_subbed_in_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "games_subbed_out_percentile_overall")
	@Getter
	@Setter
	private Integer games_subbed_out_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "hattricks_total_overall")
	@Getter
	@Setter
	private Integer hattricks_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "two_goals_in_a_game_total_overall")
	@Getter
	@Setter
	private Integer two_goals_in_a_game_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "three_goals_in_a_game_total_overall")
	@Getter
	@Setter
	private Integer three_goals_in_a_game_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "two_goals_in_a_game_percentage_overall")
	@Getter
	@Setter
	private Integer two_goals_in_a_game_percentage_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "three_goals_in_a_game_percentage_overall")
	@Getter
	@Setter
	private Integer three_goals_in_a_game_percentage_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_involved_per90_percentile_overall")
	@Getter
	@Setter
	private Integer goals_involved_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_per90_percentile_overall")
	@Getter
	@Setter
	private Integer goals_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_per90_percentile_away")
	@Getter
	@Setter
	private Integer goals_per90_percentile_away;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_per90_percentile_home")
	@Getter
	@Setter
	private Integer goals_per90_percentile_home;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "man_of_the_match_total_overall")
	@Getter
	@Setter
	private Integer man_of_the_match_total_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "annual_salary_eur")
	@Getter
	@Setter
	private Integer annual_salary_eur;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "annual_salary_eur_percentile")
	@Getter
	@Setter
	private Integer annual_salary_eur_percentile;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "clean_sheets_percentage_percentile_overall")
	@Getter
	@Setter
	private Integer clean_sheets_percentage_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "min_per_card_percentile_overall")
	@Getter
	@Setter
	private Integer min_per_card_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "cards_per90_percentile_overall")
	@Getter
	@Setter
	private Integer cards_per90_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "booked_over05_overall")
	@Getter
	@Setter
	private Integer booked_over05_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "booked_over05_percentage_overall")
	@Getter
	@Setter
	private Integer booked_over05_percentage_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "booked_over05_percentage_percentile_overall")
	@Getter
	@Setter
	private Integer booked_over05_percentage_percentile_overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shirt_number")
	@Getter
	@Setter
	private Integer shirt_number;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "annual_salary_gbp")
	@Getter
	@Setter
	private Integer annual_salary_gbp;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "annual_salary_usd")
	@Getter
	@Setter
	private Integer annual_salary_usd;
}
