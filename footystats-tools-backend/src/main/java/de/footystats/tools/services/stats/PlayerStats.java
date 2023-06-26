package de.footystats.tools.services.stats;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import de.footystats.tools.services.csv.DoubleConverter;
import de.footystats.tools.services.csv.IntegerConverter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PlayerStats {

	@CsvBindByName(column = "full_name")
	@Getter
	@Setter
	private String fullName;
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
	private String birthdayGMT;
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
	private String currentClub;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "minutes_played_overall")
	@Getter
	@Setter
	private Integer minutesPlayedOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "minutes_played_home")
	@Getter
	@Setter
	private Integer minutesPlayedHome;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "minutes_played_away")
	@Getter
	@Setter
	private Integer minutesPlayedAway;
	@CsvBindByName(column = "nationality")
	@Getter
	@Setter
	private String nationality;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "appearances_overall")
	@Getter
	@Setter
	private Integer appearancesOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "appearances_home")
	@Getter
	@Setter
	private Integer appearancesHome;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "appearances_away")
	@Getter
	@Setter
	private Integer appearancesAway;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_overall")
	@Getter
	@Setter
	private Integer goalsOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_home")
	@Getter
	@Setter
	private Integer goalsHome;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_away")
	@Getter
	@Setter
	private Integer goalsAway;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "assists_overall")
	@Getter
	@Setter
	private Integer assistsOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "assists_home")
	@Getter
	@Setter
	private Integer assistsHome;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "assists_away")
	@Getter
	@Setter
	private Integer assistsAway;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "penalty_goals")
	@Getter
	@Setter
	private Integer penaltyGoals;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "penalty_misses")
	@Getter
	@Setter
	private Integer penaltyMisses;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "clean_sheets_overall")
	@Getter
	@Setter
	private Integer cleanSheetsOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "clean_sheets_home")
	@Getter
	@Setter
	private Integer cleanSheetsHome;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "clean_sheets_away")
	@Getter
	@Setter
	private Integer cleanSheetsAway;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "conceded_overall")
	@Getter
	@Setter
	private Integer concededOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "conceded_home")
	@Getter
	@Setter
	private Integer concededHome;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "conceded_away")
	@Getter
	@Setter
	private Integer concededAway;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "yellow_cards_overall")
	@Getter
	@Setter
	private Integer yellowCardsOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "red_cards_overall")
	@Getter
	@Setter
	private Integer redCardsOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_involved_per_90_overall")
	@Getter
	@Setter
	private Integer goalsInvolvedPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "assists_per_90_overall")
	@Getter
	@Setter
	private Integer assistsPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_per_90_overall")
	@Getter
	@Setter
	private Integer goalsPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_per_90_home")
	@Getter
	@Setter
	private Integer goalsPer90Home;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_per_90_away")
	@Getter
	@Setter
	private Integer goalsPer90Away;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "min_per_goal_overall")
	@Getter
	@Setter
	private Integer minPerGoalOverall;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "conceded_per_90_overall")
	@Getter
	@Setter
	private Double concededPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "min_per_conceded_overall")
	@Getter
	@Setter
	private Integer minPerConcededOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "min_per_match")
	@Getter
	@Setter
	private Integer minPerMatch;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "min_per_card_overall")
	@Getter
	@Setter
	private Integer minPerCardOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "min_per_assist_overall")
	@Getter
	@Setter
	private Integer minPerAssistOverall;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "cards_per_90_overall")
	@Getter
	@Setter
	private Double cardsPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "rank_in_league_top_attackers")
	@Getter
	@Setter
	private Integer rankInLeagueTopAttackers;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "rank_in_league_top_midfielders")
	@Getter
	@Setter
	private Integer rankInLeagueTopMidfielders;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "rank_in_league_top_defenders")
	@Getter
	@Setter
	private Integer rankInLeagueTopDefenders;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "rank_in_club_top_scorer")
	@Getter
	@Setter
	private Integer rankInClubTopScorer;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "average_rating_overall")
	@Getter
	@Setter
	private Integer averageRatingOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "assists_per_game_overall")
	@Getter
	@Setter
	private Integer assistsPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "sm_assists_total_overall")
	@Getter
	@Setter
	private Integer smAssistsTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "assists_per90_percentile_overall")
	@Getter
	@Setter
	private Integer assistsPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "passes_per_90_overall")
	@Getter
	@Setter
	private Integer passesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "passes_per_game_overall")
	@Getter
	@Setter
	private Integer passesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "passes_per90_percentile_overall")
	@Getter
	@Setter
	private Integer passesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "passes_total_overall")
	@Getter
	@Setter
	private Integer passesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "passes_completed_per_game_overall")
	@Getter
	@Setter
	private Integer passesCompletedPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "passes_completed_total_overall")
	@Getter
	@Setter
	private Integer passesCompletedTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pass_completion_rate_percentile_overall")
	@Getter
	@Setter
	private Integer passCompletionRatePercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "passes_completed_per_90_overall")
	@Getter
	@Setter
	private Integer passesCompletedPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "passes_completed_per90_percentile_overall")
	@Getter
	@Setter
	private Integer passesCompletedPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "short_passes_per_game_overall")
	@Getter
	@Setter
	private Integer shortPassesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "long_passes_per_game_overall")
	@Getter
	@Setter
	private Integer longPassesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "key_passes_per_game_overall")
	@Getter
	@Setter
	private Integer keyPassesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "key_passes_total_overall")
	@Getter
	@Setter
	private Integer keyPassesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "through_passes_per_game_overall")
	@Getter
	@Setter
	private Integer throughPassesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "crosses_per_game_overall")
	@Getter
	@Setter
	private Integer crossesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "tackles_per_90_overall")
	@Getter
	@Setter
	private Integer tacklesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "tackles_per_game_overall")
	@Getter
	@Setter
	private Integer tacklesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "tackles_total_overall")
	@Getter
	@Setter
	private Integer tacklesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "tackles_successful_per_game_overall")
	@Getter
	@Setter
	private Integer tacklesSuccessfulPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dispossesed_per_game_overall")
	@Getter
	@Setter
	private Integer dispossesedPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "possession_regained_per_game_overall")
	@Getter
	@Setter
	private Integer possessionRegainedPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pressures_per_game_overall")
	@Getter
	@Setter
	private Integer pressuresPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "saves_per_game_overall")
	@Getter
	@Setter
	private Integer savesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "interceptions_per_game_overall")
	@Getter
	@Setter
	private Integer interceptionsPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_successful_per_game_overall")
	@Getter
	@Setter
	private Integer dribblesSuccessfulPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_faced_per_game_overall")
	@Getter
	@Setter
	private Integer shotsFacedPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_per_goal_scored_overall")
	@Getter
	@Setter
	private Integer shotsPerGoalScoredOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_per_90_overall")
	@Getter
	@Setter
	private Integer shotsPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_off_target_per_game_overall")
	@Getter
	@Setter
	private Integer shotsOffTargetPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_per_game_overall")
	@Getter
	@Setter
	private Integer dribblesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "distance_travelled_per_game_overall")
	@Getter
	@Setter
	private Integer distanceTravelledPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_on_target_per_game_overall")
	@Getter
	@Setter
	private Integer shotsOnTargetPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xg_per_game_overall")
	@Getter
	@Setter
	private Integer xgPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "chances_created_per_game_overall")
	@Getter
	@Setter
	private Integer chancesCreatedPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_won_per_game_overall")
	@Getter
	@Setter
	private Integer aerialDuelsWonPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_per_game_overall")
	@Getter
	@Setter
	private Integer aerialDuelsPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "possession_regained_per_90_overall")
	@Getter
	@Setter
	private Integer possessionRegainedPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "possession_regained_total_overall")
	@Getter
	@Setter
	private Integer possessionRegainedTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "possession_regained_per90_percentile_overall")
	@Getter
	@Setter
	private Integer possessionRegainedPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "additional_info")
	@Getter
	@Setter
	private Integer additionalInfo;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_total_overall")
	@Getter
	@Setter
	private Integer shotsTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_per_game_overall")
	@Getter
	@Setter
	private Integer shotsPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_per90_percentile_overall")
	@Getter
	@Setter
	private Integer shotsPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_on_target_total_overall")
	@Getter
	@Setter
	private Integer shotsOnTargetTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_on_target_per_90_overall")
	@Getter
	@Setter
	private Integer shotsOnTargetPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_on_target_per90_percentile_overall")
	@Getter
	@Setter
	private Integer shotsOnTargetPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_off_target_total_overall")
	@Getter
	@Setter
	private Integer shotsOffTargetTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_off_target_per_90_overall")
	@Getter
	@Setter
	private Integer shotsOffTargetPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_off_target_per90_percentile_overall")
	@Getter
	@Setter
	private Integer shotsOffTargetPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "games_subbed_out")
	@Getter
	@Setter
	private Integer gamesSubbedOut;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "games_subbed_in")
	@Getter
	@Setter
	private Integer gamesSubbedIn;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "games_started")
	@Getter
	@Setter
	private Integer gamesStarted;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "tackles_per90_percentile_overall")
	@Getter
	@Setter
	private Integer tacklesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "tackles_successful_per_90_overall")
	@Getter
	@Setter
	private Integer tacklesSuccessfulPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "tackles_successful_per90_percentile_overall")
	@Getter
	@Setter
	private Integer tacklesSuccessfulPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "tackles_successful_total_overall")
	@Getter
	@Setter
	private Integer tacklesSuccessfulTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "interceptions_total_overall")
	@Getter
	@Setter
	private Integer interceptionsTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "interceptions_per_90_overall")
	@Getter
	@Setter
	private Integer interceptionsPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "interceptions_per90_percentile_overall")
	@Getter
	@Setter
	private Integer interceptionsPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "crosses_total_overall")
	@Getter
	@Setter
	private Integer crossesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "cross_completion_rate_percentile_overall")
	@Getter
	@Setter
	private Integer crossCompletionRatePercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "crosses_per_90_overall")
	@Getter
	@Setter
	private Integer crossesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "crosses_per90_percentile_overall")
	@Getter
	@Setter
	private Integer crossesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "through_passes_total_overall")
	@Getter
	@Setter
	private Integer throughPassesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "through_passes_per_90_overall")
	@Getter
	@Setter
	private Integer throughPassesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "through_passes_per90_percentile_overall")
	@Getter
	@Setter
	private Integer throughPassesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "long_passes_total_overall")
	@Getter
	@Setter
	private Integer longPassesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "long_passes_per_90_overall")
	@Getter
	@Setter
	private Integer longPassesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "long_passes_per90_percentile_overall")
	@Getter
	@Setter
	private Integer longPassesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "short_passes_total_overall")
	@Getter
	@Setter
	private Integer shortPassesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "short_passes_per_90_overall")
	@Getter
	@Setter
	private Integer shortPassesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "short_passes_per90_percentile_overall")
	@Getter
	@Setter
	private Integer shortPassesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "key_passes_per_90_overall")
	@Getter
	@Setter
	private Integer keyPassesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "key_passes_per90_percentile_overall")
	@Getter
	@Setter
	private Integer keyPassesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_total_overall")
	@Getter
	@Setter
	private Integer dribblesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_per_90_overall")
	@Getter
	@Setter
	private Integer dribblesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_per90_percentile_overall")
	@Getter
	@Setter
	private Integer dribblesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_successful_total_overall")
	@Getter
	@Setter
	private Integer dribblesSuccessfulTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_successful_per_90_overall")
	@Getter
	@Setter
	private Integer dribblesSuccessfulPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_successful_percentage_overall")
	@Getter
	@Setter
	private Integer dribblesSuccessfulPercentageOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "chances_created_total_overall")
	@Getter
	@Setter
	private Integer chancesCreatedTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "chances_created_per_90_overall")
	@Getter
	@Setter
	private Integer chancesCreatedPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "chances_created_per90_percentile_overall")
	@Getter
	@Setter
	private Integer chancesCreatedPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "saves_total_overall")
	@Getter
	@Setter
	private Integer savesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "save_percentage_percentile_overall")
	@Getter
	@Setter
	private Integer savePercentagePercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "saves_per_90_overall")
	@Getter
	@Setter
	private Integer savesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "saves_per90_percentile_overall")
	@Getter
	@Setter
	private Integer savesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_faced_total_overall")
	@Getter
	@Setter
	private Integer shotsFacedTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_per_goal_conceded_overall")
	@Getter
	@Setter
	private Integer shotsPerGoalConcededOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "sm_goals_conceded_total_overall")
	@Getter
	@Setter
	private Integer smGoalsConcededTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "conceded_per90_percentile_overall")
	@Getter
	@Setter
	private Integer concededPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_faced_per_90_overall")
	@Getter
	@Setter
	private Integer shotsFacedPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_faced_per90_percentile_overall")
	@Getter
	@Setter
	private Integer shotsFacedPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xg_faced_per_90_overall")
	@Getter
	@Setter
	private Integer xgFacedPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xg_faced_per90_percentile_overall")
	@Getter
	@Setter
	private Integer xgFacedPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xg_faced_per_game_overall")
	@Getter
	@Setter
	private Integer xgFacedPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xg_faced_total_overall")
	@Getter
	@Setter
	private Integer xgFacedTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "save_percentage_overall")
	@Getter
	@Setter
	private Integer savePercentageOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pressures_total_overall")
	@Getter
	@Setter
	private Integer pressuresTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pressures_per_90_overall")
	@Getter
	@Setter
	private Integer pressuresPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pressures_per90_percentile_overall")
	@Getter
	@Setter
	private Integer pressuresPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xg_total_overall")
	@Getter
	@Setter
	private Integer xgTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "market_value")
	@Getter
	@Setter
	private Integer marketValue;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "market_value_percentile")
	@Getter
	@Setter
	private Integer marketValuePercentile;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pass_completion_rate_overall")
	@Getter
	@Setter
	private Integer passCompletionRateOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shot_accuraccy_percentage_overall")
	@Getter
	@Setter
	private Integer shotAccuraccyPercentageOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shot_accuraccy_percentage_percentile_overall")
	@Getter
	@Setter
	private Integer shotAccuraccyPercentagePercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "sm_goals_scored_total_overall")
	@Getter
	@Setter
	private Integer smGoalsScoredTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbled_past_per90_percentile_overall")
	@Getter
	@Setter
	private Integer dribbledPastPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbled_past_per_game_overall")
	@Getter
	@Setter
	private Integer dribbledPastPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbled_past_per_90_overall")
	@Getter
	@Setter
	private Integer dribbledPastPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbled_past_total_overall")
	@Getter
	@Setter
	private Integer dribbledPastTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_successful_per90_percentile_overall")
	@Getter
	@Setter
	private Integer dribblesSuccessfulPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_successful_percentage_percentile_overall")
	@Getter
	@Setter
	private Integer dribblesSuccessfulPercentagePercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pen_scored_total_overall")
	@Getter
	@Setter
	private Integer penScoredTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pen_missed_total_overall")
	@Getter
	@Setter
	private Integer penMissedTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "inside_box_saves_total_overall")
	@Getter
	@Setter
	private Integer insideBoxSavesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "blocks_per_game_overall")
	@Getter
	@Setter
	private Integer blocksPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "blocks_per_90_overall")
	@Getter
	@Setter
	private Integer blocksPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "blocks_total_overall")
	@Getter
	@Setter
	private Integer blocksTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "blocks_per90_percentile_overall")
	@Getter
	@Setter
	private Integer blocksPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "ratings_total_overall")
	@Getter
	@Setter
	private Integer ratingsTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "clearances_per_game_overall")
	@Getter
	@Setter
	private Integer clearancesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "clearances_per_90_overall")
	@Getter
	@Setter
	private Integer clearancesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "clearances_total_overall")
	@Getter
	@Setter
	private Integer clearancesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pen_committed_total_overall")
	@Getter
	@Setter
	private Integer penCommittedTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pen_save_percentage_overall")
	@Getter
	@Setter
	private Integer penSavePercentageOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pen_committed_per_90_overall")
	@Getter
	@Setter
	private Integer penCommittedPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pen_committed_per90_percentile_overall")
	@Getter
	@Setter
	private Integer penCommittedPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pen_committed_per_game_overall")
	@Getter
	@Setter
	private Integer penCommittedPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pens_saved_total_overall")
	@Getter
	@Setter
	private Integer pensSavedTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pens_taken_total_overall")
	@Getter
	@Setter
	private Integer pensTakenTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "hit_woodwork_total_overall")
	@Getter
	@Setter
	private Integer hitWoodworkTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "hit_woodwork_per_game_overall")
	@Getter
	@Setter
	private Integer hitWoodworkPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "hit_woodwork_per_90_overall")
	@Getter
	@Setter
	private Integer hitWoodworkPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "punches_total_overall")
	@Getter
	@Setter
	private Integer punchesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "punches_per_game_overall")
	@Getter
	@Setter
	private Integer punchesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "punches_per_90_overall")
	@Getter
	@Setter
	private Integer punchesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "offsides_per_90_overall")
	@Getter
	@Setter
	private Integer offsidesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "offsides_per_game_overall")
	@Getter
	@Setter
	private Integer offsidesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "offsides_total_overall")
	@Getter
	@Setter
	private Integer offsidesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "penalties_won_total_overall")
	@Getter
	@Setter
	private Integer penaltiesWonTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shot_conversion_rate_overall")
	@Getter
	@Setter
	private Integer shotConversionRateOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shot_conversion_rate_percentile_overall")
	@Getter
	@Setter
	private Integer shotConversionRatePercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "sm_minutes_played_per90_percentile_overall")
	@Getter
	@Setter
	private Integer smMinutesPlayedPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "sm_minutes_played_recorded_overall")
	@Getter
	@Setter
	private Integer smMinutesPlayedRecordedOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "minutes_played_percentile_overall")
	@Getter
	@Setter
	private Integer minutesPlayedPercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "matches_played_percentile_overall")
	@Getter
	@Setter
	private Integer matchesPlayedPercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "min_per_goal_percentile_overall")
	@Getter
	@Setter
	private Integer minPerGoalPercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "min_per_conceded_percentile_overall")
	@Getter
	@Setter
	private Integer minPerConcededPercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xa_total_overall")
	@Getter
	@Setter
	private Integer xaTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xa_per90_percentile_overall")
	@Getter
	@Setter
	private Integer xaPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xa_per_game_overall")
	@Getter
	@Setter
	private Integer xaPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xa_per_90_overall")
	@Getter
	@Setter
	private Integer xaPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "npxg_total_overall")
	@Getter
	@Setter
	private Integer npxgTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "npxg_per90_percentile_overall")
	@Getter
	@Setter
	private Integer npxgPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "npxg_per_game_overall")
	@Getter
	@Setter
	private Integer npxgPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "npxg_per_90_overall")
	@Getter
	@Setter
	private Integer npxgPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "fouls_drawn_per90_percentile_overall")
	@Getter
	@Setter
	private Integer foulsDrawnPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "fouls_drawn_total_overall")
	@Getter
	@Setter
	private Integer foulsDrawnTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "fouls_drawn_per_game_overall")
	@Getter
	@Setter
	private Integer foulsDrawnPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "fouls_drawn_per_90_overall")
	@Getter
	@Setter
	private Integer foulsDrawnPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "fouls_committed_per_90_overall")
	@Getter
	@Setter
	private Integer foulsCommittedPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "fouls_committed_per_game_overall")
	@Getter
	@Setter
	private Integer foulsCommittedPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "fouls_committed_per90_percentile_overall")
	@Getter
	@Setter
	private Integer foulsCommittedPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "fouls_committed_total_overall")
	@Getter
	@Setter
	private Integer foulsCommittedTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xg_per_90_overall")
	@Getter
	@Setter
	private Integer xgPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xg_per90_percentile_overall")
	@Getter
	@Setter
	private Integer xgPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "average_rating_percentile_overall")
	@Getter
	@Setter
	private Integer averageRatingPercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "clearances_per90_percentile_overall")
	@Getter
	@Setter
	private Integer clearancesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "hit_woodwork_per90_percentile_overall")
	@Getter
	@Setter
	private Integer hitWoodworkPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "punches_per90_percentile_overall")
	@Getter
	@Setter
	private Integer punchesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "offsides_per90_percentile_overall")
	@Getter
	@Setter
	private Integer offsidesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_won_per90_percentile_overall")
	@Getter
	@Setter
	private Integer aerialDuelsWonPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_total_overall")
	@Getter
	@Setter
	private Integer aerialDuelsTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_per_90_overall")
	@Getter
	@Setter
	private Integer aerialDuelsPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_per90_percentile_overall")
	@Getter
	@Setter
	private Integer aerialDuelsPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_won_total_overall")
	@Getter
	@Setter
	private Integer aerialDuelsWonTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_won_percentage_overall")
	@Getter
	@Setter
	private Integer aerialDuelsWonPercentageOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_won_per_90_overall")
	@Getter
	@Setter
	private Integer aerialDuelsWonPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_per_90_overall")
	@Getter
	@Setter
	private Integer duelsPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_per_game_overall")
	@Getter
	@Setter
	private Integer duelsPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_total_overall")
	@Getter
	@Setter
	private Integer duelsTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_won_total_overall")
	@Getter
	@Setter
	private Integer duelsWonTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_won_per90_percentile_overall")
	@Getter
	@Setter
	private Integer duelsWonPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_per90_percentile_overall")
	@Getter
	@Setter
	private Integer duelsPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_won_per_90_overall")
	@Getter
	@Setter
	private Integer duelsWonPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_won_per_game_overall")
	@Getter
	@Setter
	private Integer duelsWonPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_won_percentage_overall")
	@Getter
	@Setter
	private Integer duelsWonPercentageOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dispossesed_total_overall")
	@Getter
	@Setter
	private Integer dispossesedTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dispossesed_per_90_overall")
	@Getter
	@Setter
	private Integer dispossesedPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dispossesed_per90_percentile_overall")
	@Getter
	@Setter
	private Integer dispossesedPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "progressive_passes_total_overall")
	@Getter
	@Setter
	private Integer progressivePassesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "cross_completion_rate_overall")
	@Getter
	@Setter
	private Integer crossCompletionRateOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "distance_travelled_total_overall")
	@Getter
	@Setter
	private Integer distanceTravelledTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "distance_travelled_per_90_overall")
	@Getter
	@Setter
	private Integer distanceTravelledPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "distance_travelled_per90_percentile_overall")
	@Getter
	@Setter
	private Integer distanceTravelledPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "accurate_crosses_total_overall")
	@Getter
	@Setter
	private Integer accurateCrossesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "accurate_crosses_per_game_overall")
	@Getter
	@Setter
	private Integer accurateCrossesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "accurate_crosses_per_90_overall")
	@Getter
	@Setter
	private Integer accurateCrossesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "accurate_crosses_per90_percentile_overall")
	@Getter
	@Setter
	private Integer accurateCrossesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "sm_matches_recorded_total_overall")
	@Getter
	@Setter
	private Integer smMatchesRecordedTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "games_started_percentile_overall")
	@Getter
	@Setter
	private Integer gamesStartedPercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "games_subbed_in_percentile_overall")
	@Getter
	@Setter
	private Integer gamesSubbedInPercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "games_subbed_out_percentile_overall")
	@Getter
	@Setter
	private Integer gamesSubbedOutPercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "hattricks_total_overall")
	@Getter
	@Setter
	private Integer hattricksTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "two_goals_in_a_game_total_overall")
	@Getter
	@Setter
	private Integer twoGoalsInAGameTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "three_goals_in_a_game_total_overall")
	@Getter
	@Setter
	private Integer threeGoalsInAGameTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "two_goals_in_a_game_percentage_overall")
	@Getter
	@Setter
	private Integer twoGoalsInAGamePercentageOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "three_goals_in_a_game_percentage_overall")
	@Getter
	@Setter
	private Integer threeGoalsInAGamePercentageOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_involved_per90_percentile_overall")
	@Getter
	@Setter
	private Integer goalsInvolvedPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_per90_percentile_overall")
	@Getter
	@Setter
	private Integer goalsPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_per90_percentile_away")
	@Getter
	@Setter
	private Integer goalsPer90PercentileAway;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_per90_percentile_home")
	@Getter
	@Setter
	private Integer goalsPer90PercentileHome;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "man_of_the_match_total_overall")
	@Getter
	@Setter
	private Integer manOfTheMatchTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "annual_salary_eur")
	@Getter
	@Setter
	private Integer annualSalaryEur;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "annual_salary_eur_percentile")
	@Getter
	@Setter
	private Integer annualSalaryEurPercentile;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "clean_sheets_percentage_percentile_overall")
	@Getter
	@Setter
	private Integer cleanSheetsPercentagePercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "min_per_card_percentile_overall")
	@Getter
	@Setter
	private Integer minPerCardPercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "cards_per90_percentile_overall")
	@Getter
	@Setter
	private Integer cardsPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "booked_over05_overall")
	@Getter
	@Setter
	private Integer bookedOver05Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "booked_over05_percentage_overall")
	@Getter
	@Setter
	private Integer bookedOver05PercentageOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "booked_over05_percentage_percentile_overall")
	@Getter
	@Setter
	private Integer bookedOver05PercentagePercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shirt_number")
	@Getter
	@Setter
	private Integer shirtNumber;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "annual_salary_gbp")
	@Getter
	@Setter
	private Integer annualSalaryGbp;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "annual_salary_usd")
	@Getter
	@Setter
	private Integer annualSalaryUsd;
}
