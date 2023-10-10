package de.footystats.tools.services.stats;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import de.footystats.tools.services.csv.DoubleConverter;
import de.footystats.tools.services.csv.IntegerConverter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class PlayerStats {

	@CsvBindByName(column = "full_name")
	private String fullName;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "age")
	private Integer age;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "birthday")
	private Integer birthday;
	@CsvBindByName(column = "birthday_GMT")
	private String birthdayGMT;
	@CsvBindByName(column = "league")
	private String league;
	@CsvBindByName(column = "season")
	private String season;
	@CsvBindByName(column = "position")
	private String position;
	@CsvBindByName(column = "Current Club")
	private String currentClub;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "minutes_played_overall")
	private Integer minutesPlayedOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "minutes_played_home")
	private Integer minutesPlayedHome;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "minutes_played_away")
	private Integer minutesPlayedAway;
	@CsvBindByName(column = "nationality")
	private String nationality;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "appearances_overall")
	private Integer appearancesOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "appearances_home")
	private Integer appearancesHome;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "appearances_away")
	private Integer appearancesAway;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_overall")
	private Integer goalsOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_home")
	private Integer goalsHome;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_away")
	private Integer goalsAway;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "assists_overall")
	private Integer assistsOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "assists_home")
	private Integer assistsHome;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "assists_away")
	private Integer assistsAway;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "penalty_goals")
	private Integer penaltyGoals;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "penalty_misses")
	private Integer penaltyMisses;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "clean_sheets_overall")
	private Integer cleanSheetsOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "clean_sheets_home")
	private Integer cleanSheetsHome;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "clean_sheets_away")
	private Integer cleanSheetsAway;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "conceded_overall")
	private Integer concededOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "conceded_home")
	private Integer concededHome;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "conceded_away")
	private Integer concededAway;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "yellow_cards_overall")
	private Integer yellowCardsOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "red_cards_overall")
	private Integer redCardsOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_involved_per_90_overall")
	private Integer goalsInvolvedPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "assists_per_90_overall")
	private Integer assistsPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_per_90_overall")
	private Integer goalsPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_per_90_home")
	private Integer goalsPer90Home;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_per_90_away")
	private Integer goalsPer90Away;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "min_per_goal_overall")
	private Integer minPerGoalOverall;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "conceded_per_90_overall")
	private Double concededPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "min_per_conceded_overall")
	private Integer minPerConcededOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "min_per_match")
	private Integer minPerMatch;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "min_per_card_overall")
	private Integer minPerCardOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "min_per_assist_overall")
	private Integer minPerAssistOverall;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "cards_per_90_overall")
	private Double cardsPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "rank_in_league_top_attackers")
	private Integer rankInLeagueTopAttackers;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "rank_in_league_top_midfielders")
	private Integer rankInLeagueTopMidfielders;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "rank_in_league_top_defenders")
	private Integer rankInLeagueTopDefenders;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "rank_in_club_top_scorer")
	private Integer rankInClubTopScorer;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "average_rating_overall")
	private Integer averageRatingOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "assists_per_game_overall")
	private Integer assistsPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "sm_assists_total_overall")
	private Integer smAssistsTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "assists_per90_percentile_overall")
	private Integer assistsPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "passes_per_90_overall")
	private Integer passesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "passes_per_game_overall")
	private Integer passesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "passes_per90_percentile_overall")
	private Integer passesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "passes_total_overall")
	private Integer passesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "passes_completed_per_game_overall")
	private Integer passesCompletedPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "passes_completed_total_overall")
	private Integer passesCompletedTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pass_completion_rate_percentile_overall")
	private Integer passCompletionRatePercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "passes_completed_per_90_overall")
	private Integer passesCompletedPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "passes_completed_per90_percentile_overall")
	private Integer passesCompletedPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "short_passes_per_game_overall")
	private Integer shortPassesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "long_passes_per_game_overall")
	private Integer longPassesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "key_passes_per_game_overall")
	private Integer keyPassesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "key_passes_total_overall")
	private Integer keyPassesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "through_passes_per_game_overall")
	private Integer throughPassesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "crosses_per_game_overall")
	private Integer crossesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "tackles_per_90_overall")
	private Integer tacklesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "tackles_per_game_overall")
	private Integer tacklesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "tackles_total_overall")
	private Integer tacklesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "tackles_successful_per_game_overall")
	private Integer tacklesSuccessfulPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dispossesed_per_game_overall")
	private Integer dispossesedPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "possession_regained_per_game_overall")
	private Integer possessionRegainedPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pressures_per_game_overall")
	private Integer pressuresPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "saves_per_game_overall")
	private Integer savesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "interceptions_per_game_overall")
	private Integer interceptionsPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_successful_per_game_overall")
	private Integer dribblesSuccessfulPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_faced_per_game_overall")
	private Integer shotsFacedPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_per_goal_scored_overall")
	private Integer shotsPerGoalScoredOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_per_90_overall")
	private Integer shotsPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_off_target_per_game_overall")
	private Integer shotsOffTargetPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_per_game_overall")
	private Integer dribblesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "distance_travelled_per_game_overall")
	private Integer distanceTravelledPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_on_target_per_game_overall")
	private Integer shotsOnTargetPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xg_per_game_overall")
	private Integer xgPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "chances_created_per_game_overall")
	private Integer chancesCreatedPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_won_per_game_overall")
	private Integer aerialDuelsWonPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_per_game_overall")
	private Integer aerialDuelsPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "possession_regained_per_90_overall")
	private Integer possessionRegainedPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "possession_regained_total_overall")
	private Integer possessionRegainedTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "possession_regained_per90_percentile_overall")
	private Integer possessionRegainedPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "additional_info")
	private Integer additionalInfo;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_total_overall")
	private Integer shotsTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_per_game_overall")
	private Integer shotsPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_per90_percentile_overall")
	private Integer shotsPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_on_target_total_overall")
	private Integer shotsOnTargetTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_on_target_per_90_overall")
	private Integer shotsOnTargetPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_on_target_per90_percentile_overall")
	private Integer shotsOnTargetPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_off_target_total_overall")
	private Integer shotsOffTargetTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_off_target_per_90_overall")
	private Integer shotsOffTargetPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_off_target_per90_percentile_overall")
	private Integer shotsOffTargetPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "games_subbed_out")
	private Integer gamesSubbedOut;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "games_subbed_in")
	private Integer gamesSubbedIn;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "games_started")
	private Integer gamesStarted;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "tackles_per90_percentile_overall")
	private Integer tacklesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "tackles_successful_per_90_overall")
	private Integer tacklesSuccessfulPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "tackles_successful_per90_percentile_overall")
	private Integer tacklesSuccessfulPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "tackles_successful_total_overall")
	private Integer tacklesSuccessfulTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "interceptions_total_overall")
	private Integer interceptionsTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "interceptions_per_90_overall")
	private Integer interceptionsPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "interceptions_per90_percentile_overall")
	private Integer interceptionsPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "crosses_total_overall")
	private Integer crossesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "cross_completion_rate_percentile_overall")
	private Integer crossCompletionRatePercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "crosses_per_90_overall")
	private Integer crossesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "crosses_per90_percentile_overall")
	private Integer crossesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "through_passes_total_overall")
	private Integer throughPassesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "through_passes_per_90_overall")
	private Integer throughPassesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "through_passes_per90_percentile_overall")
	private Integer throughPassesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "long_passes_total_overall")
	private Integer longPassesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "long_passes_per_90_overall")
	private Integer longPassesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "long_passes_per90_percentile_overall")
	private Integer longPassesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "short_passes_total_overall")
	private Integer shortPassesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "short_passes_per_90_overall")
	private Integer shortPassesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "short_passes_per90_percentile_overall")
	private Integer shortPassesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "key_passes_per_90_overall")
	private Integer keyPassesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "key_passes_per90_percentile_overall")
	private Integer keyPassesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_total_overall")
	private Integer dribblesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_per_90_overall")
	private Integer dribblesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_per90_percentile_overall")
	private Integer dribblesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_successful_total_overall")
	private Integer dribblesSuccessfulTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_successful_per_90_overall")
	private Integer dribblesSuccessfulPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_successful_percentage_overall")
	private Integer dribblesSuccessfulPercentageOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "chances_created_total_overall")
	private Integer chancesCreatedTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "chances_created_per_90_overall")
	private Integer chancesCreatedPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "chances_created_per90_percentile_overall")
	private Integer chancesCreatedPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "saves_total_overall")
	private Integer savesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "save_percentage_percentile_overall")
	private Integer savePercentagePercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "saves_per_90_overall")
	private Integer savesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "saves_per90_percentile_overall")
	private Integer savesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_faced_total_overall")
	private Integer shotsFacedTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_per_goal_conceded_overall")
	private Integer shotsPerGoalConcededOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "sm_goals_conceded_total_overall")
	private Integer smGoalsConcededTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "conceded_per90_percentile_overall")
	private Integer concededPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_faced_per_90_overall")
	private Integer shotsFacedPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shots_faced_per90_percentile_overall")
	private Integer shotsFacedPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xg_faced_per_90_overall")
	private Integer xgFacedPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xg_faced_per90_percentile_overall")
	private Integer xgFacedPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xg_faced_per_game_overall")
	private Integer xgFacedPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xg_faced_total_overall")
	private Integer xgFacedTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "save_percentage_overall")
	private Integer savePercentageOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pressures_total_overall")
	private Integer pressuresTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pressures_per_90_overall")
	private Integer pressuresPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pressures_per90_percentile_overall")
	private Integer pressuresPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xg_total_overall")
	private Integer xgTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "market_value")
	private Integer marketValue;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "market_value_percentile")
	private Integer marketValuePercentile;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pass_completion_rate_overall")
	private Integer passCompletionRateOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shot_accuraccy_percentage_overall")
	private Integer shotAccuraccyPercentageOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shot_accuraccy_percentage_percentile_overall")
	private Integer shotAccuraccyPercentagePercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "sm_goals_scored_total_overall")
	private Integer smGoalsScoredTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbled_past_per90_percentile_overall")
	private Integer dribbledPastPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbled_past_per_game_overall")
	private Integer dribbledPastPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbled_past_per_90_overall")
	private Integer dribbledPastPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbled_past_total_overall")
	private Integer dribbledPastTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_successful_per90_percentile_overall")
	private Integer dribblesSuccessfulPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dribbles_successful_percentage_percentile_overall")
	private Integer dribblesSuccessfulPercentagePercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pen_scored_total_overall")
	private Integer penScoredTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pen_missed_total_overall")
	private Integer penMissedTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "inside_box_saves_total_overall")
	private Integer insideBoxSavesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "blocks_per_game_overall")
	private Integer blocksPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "blocks_per_90_overall")
	private Integer blocksPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "blocks_total_overall")
	private Integer blocksTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "blocks_per90_percentile_overall")
	private Integer blocksPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "ratings_total_overall")
	private Integer ratingsTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "clearances_per_game_overall")
	private Integer clearancesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "clearances_per_90_overall")
	private Integer clearancesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "clearances_total_overall")
	private Integer clearancesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pen_committed_total_overall")
	private Integer penCommittedTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pen_save_percentage_overall")
	private Integer penSavePercentageOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pen_committed_per_90_overall")
	private Integer penCommittedPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pen_committed_per90_percentile_overall")
	private Integer penCommittedPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pen_committed_per_game_overall")
	private Integer penCommittedPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pens_saved_total_overall")
	private Integer pensSavedTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "pens_taken_total_overall")
	private Integer pensTakenTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "hit_woodwork_total_overall")
	private Integer hitWoodworkTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "hit_woodwork_per_game_overall")
	private Integer hitWoodworkPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "hit_woodwork_per_90_overall")
	private Integer hitWoodworkPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "punches_total_overall")
	private Integer punchesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "punches_per_game_overall")
	private Integer punchesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "punches_per_90_overall")
	private Integer punchesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "offsides_per_90_overall")
	private Integer offsidesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "offsides_per_game_overall")
	private Integer offsidesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "offsides_total_overall")
	private Integer offsidesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "penalties_won_total_overall")
	private Integer penaltiesWonTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shot_conversion_rate_overall")
	private Integer shotConversionRateOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shot_conversion_rate_percentile_overall")
	private Integer shotConversionRatePercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "sm_minutes_played_per90_percentile_overall")
	private Integer smMinutesPlayedPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "sm_minutes_played_recorded_overall")
	private Integer smMinutesPlayedRecordedOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "minutes_played_percentile_overall")
	private Integer minutesPlayedPercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "matches_played_percentile_overall")
	private Integer matchesPlayedPercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "min_per_goal_percentile_overall")
	private Integer minPerGoalPercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "min_per_conceded_percentile_overall")
	private Integer minPerConcededPercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xa_total_overall")
	private Integer xaTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xa_per90_percentile_overall")
	private Integer xaPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xa_per_game_overall")
	private Integer xaPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xa_per_90_overall")
	private Integer xaPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "npxg_total_overall")
	private Integer npxgTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "npxg_per90_percentile_overall")
	private Integer npxgPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "npxg_per_game_overall")
	private Integer npxgPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "npxg_per_90_overall")
	private Integer npxgPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "fouls_drawn_per90_percentile_overall")
	private Integer foulsDrawnPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "fouls_drawn_total_overall")
	private Integer foulsDrawnTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "fouls_drawn_per_game_overall")
	private Integer foulsDrawnPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "fouls_drawn_per_90_overall")
	private Integer foulsDrawnPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "fouls_committed_per_90_overall")
	private Integer foulsCommittedPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "fouls_committed_per_game_overall")
	private Integer foulsCommittedPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "fouls_committed_per90_percentile_overall")
	private Integer foulsCommittedPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "fouls_committed_total_overall")
	private Integer foulsCommittedTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xg_per_90_overall")
	private Integer xgPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "xg_per90_percentile_overall")
	private Integer xgPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "average_rating_percentile_overall")
	private Integer averageRatingPercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "clearances_per90_percentile_overall")
	private Integer clearancesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "hit_woodwork_per90_percentile_overall")
	private Integer hitWoodworkPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "punches_per90_percentile_overall")
	private Integer punchesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "offsides_per90_percentile_overall")
	private Integer offsidesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_won_per90_percentile_overall")
	private Integer aerialDuelsWonPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_total_overall")
	private Integer aerialDuelsTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_per_90_overall")
	private Integer aerialDuelsPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_per90_percentile_overall")
	private Integer aerialDuelsPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_won_total_overall")
	private Integer aerialDuelsWonTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_won_percentage_overall")
	private Integer aerialDuelsWonPercentageOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "aerial_duels_won_per_90_overall")
	private Integer aerialDuelsWonPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_per_90_overall")
	private Integer duelsPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_per_game_overall")
	private Integer duelsPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_total_overall")
	private Integer duelsTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_won_total_overall")
	private Integer duelsWonTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_won_per90_percentile_overall")
	private Integer duelsWonPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_per90_percentile_overall")
	private Integer duelsPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_won_per_90_overall")
	private Integer duelsWonPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_won_per_game_overall")
	private Integer duelsWonPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "duels_won_percentage_overall")
	private Integer duelsWonPercentageOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dispossesed_total_overall")
	private Integer dispossesedTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dispossesed_per_90_overall")
	private Integer dispossesedPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "dispossesed_per90_percentile_overall")
	private Integer dispossesedPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "progressive_passes_total_overall")
	private Integer progressivePassesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "cross_completion_rate_overall")
	private Integer crossCompletionRateOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "distance_travelled_total_overall")
	private Integer distanceTravelledTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "distance_travelled_per_90_overall")
	private Integer distanceTravelledPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "distance_travelled_per90_percentile_overall")
	private Integer distanceTravelledPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "accurate_crosses_total_overall")
	private Integer accurateCrossesTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "accurate_crosses_per_game_overall")
	private Integer accurateCrossesPerGameOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "accurate_crosses_per_90_overall")
	private Integer accurateCrossesPer90Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "accurate_crosses_per90_percentile_overall")
	private Integer accurateCrossesPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "sm_matches_recorded_total_overall")
	private Integer smMatchesRecordedTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "games_started_percentile_overall")
	private Integer gamesStartedPercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "games_subbed_in_percentile_overall")
	private Integer gamesSubbedInPercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "games_subbed_out_percentile_overall")
	private Integer gamesSubbedOutPercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "hattricks_total_overall")
	private Integer hattricksTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "two_goals_in_a_game_total_overall")
	private Integer twoGoalsInAGameTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "three_goals_in_a_game_total_overall")
	private Integer threeGoalsInAGameTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "two_goals_in_a_game_percentage_overall")
	private Integer twoGoalsInAGamePercentageOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "three_goals_in_a_game_percentage_overall")
	private Integer threeGoalsInAGamePercentageOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_involved_per90_percentile_overall")
	private Integer goalsInvolvedPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_per90_percentile_overall")
	private Integer goalsPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_per90_percentile_away")
	private Integer goalsPer90PercentileAway;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "goals_per90_percentile_home")
	private Integer goalsPer90PercentileHome;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "man_of_the_match_total_overall")
	private Integer manOfTheMatchTotalOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "annual_salary_eur")
	private Integer annualSalaryEur;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "annual_salary_eur_percentile")
	private Integer annualSalaryEurPercentile;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "clean_sheets_percentage_percentile_overall")
	private Integer cleanSheetsPercentagePercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "min_per_card_percentile_overall")
	private Integer minPerCardPercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "cards_per90_percentile_overall")
	private Integer cardsPer90PercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "booked_over05_overall")
	private Integer bookedOver05Overall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "booked_over05_percentage_overall")
	private Integer bookedOver05PercentageOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "booked_over05_percentage_percentile_overall")
	private Integer bookedOver05PercentagePercentileOverall;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "shirt_number")
	private Integer shirtNumber;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "annual_salary_gbp")
	private Integer annualSalaryGbp;
	@CsvCustomBindByName(converter = IntegerConverter.class, column = "annual_salary_usd")
	private Integer annualSalaryUsd;
}
