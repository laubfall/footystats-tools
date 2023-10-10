package de.footystats.tools.services.stats;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import de.footystats.tools.services.csv.DoubleConverter;
import de.footystats.tools.services.domain.Country;
import de.footystats.tools.services.domain.CountryCsvConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Document
@JsonComponent
@NoArgsConstructor
@CompoundIndex(name = "unique", def = "{'teamName' : 1, 'commonName': 1, 'season': 1, 'country': 1}")
public class TeamStats {

	@CsvBindByName(column = "team_name")
	private String teamName;
	@CsvBindByName(column = "common_name")
	private String commonName;
	@CsvBindByName(column = "season")
	private String season;
	@CsvCustomBindByName(column = "country", required = true, converter = CountryCsvConverter.class)
	private Country country;
	@CsvBindByName(column = "matches_played")
	private Integer matchesPlayed;
	@CsvBindByName(column = "matches_played_home")
	private Integer matchesPlayedHome;
	@CsvBindByName(column = "matches_played_away")
	private Integer matchesPlayedAway;
	@CsvBindByName(column = "suspended_matches")
	private Integer suspendedMatches;
	@CsvBindByName(column = "wins")
	private Integer wins;
	@CsvBindByName(column = "wins_home")
	private Integer winsHome;
	@CsvBindByName(column = "wins_away")
	private Integer winsAway;
	@CsvBindByName(column = "draws")
	private Integer draws;
	@CsvBindByName(column = "draws_home")
	private Integer drawsHome;
	@CsvBindByName(column = "draws_away")
	private Integer drawsAway;
	@CsvBindByName(column = "losses")
	private Integer losses;
	@CsvBindByName(column = "losses_home")
	private Integer lossesHome;
	@CsvBindByName(column = "losses_away")
	private Integer lossesAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "points_per_game")
	private Double pointsPerGame;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "points_per_game_home")
	private Double pointsPerGameHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "points_per_game_away")
	private Double pointsPerGameAway;
	@CsvBindByName(column = "league_position")
	private Integer leaguePosition;
	@CsvBindByName(column = "league_position_home")
	private Integer leaguePositionHome;
	@CsvBindByName(column = "league_position_away")
	private Integer leaguePositionAway;
	@CsvBindByName(column = "performance_rank")
	private Integer performanceRank;
	@CsvBindByName(column = "goals_scored")
	private Integer goalsScored;
	@CsvBindByName(column = "goals_conceded")
	private Integer goalsConceded;
	@CsvBindByName(column = "goal_difference")
	private Integer goalDifference;
	@CsvBindByName(column = "total_goal_count")
	private Integer totalGoalCount;
	@CsvBindByName(column = "total_goal_count_home")
	private Integer totalGoalCountHome;
	@CsvBindByName(column = "total_goal_count_away")
	private Integer totalGoalCountAway;
	@CsvBindByName(column = "goals_scored_home")
	private Integer goalsScoredHome;
	@CsvBindByName(column = "goals_scored_away")
	private Integer goalsScoredAway;
	@CsvBindByName(column = "goals_conceded_home")
	private Integer goalsConcededHome;
	@CsvBindByName(column = "goals_conceded_away")
	private Integer goalsConcededAway;
	@CsvBindByName(column = "goal_difference_home")
	private Integer goalDifferenceHome;
	@CsvBindByName(column = "goal_difference_away")
	private Integer goalDifferenceAway;
	@CsvBindByName(column = "minutes_per_goal_scored")
	private Integer minutesPerGoalScored;
	@CsvBindByName(column = "minutes_per_goal_scored_home")
	private Integer minutesPerGoalScoredHome;
	@CsvBindByName(column = "minutes_per_goal_scored_away")
	private Integer minutesPerGoalScoredAway;
	@CsvBindByName(column = "minutes_per_goal_conceded")
	private Integer minutesPerGoalConceded;
	@CsvBindByName(column = "minutes_per_goal_conceded_home")
	private Integer minutesPerGoalConcededHome;
	@CsvBindByName(column = "minutes_per_goal_conceded_away")
	private Integer minutesPerGoalConcededAway;
	@CsvBindByName(column = "clean_sheets")
	private Integer cleanSheets;
	@CsvBindByName(column = "clean_sheets_home")
	private Integer cleanSheetsHome;
	@CsvBindByName(column = "clean_sheets_away")
	private Integer cleanSheetsAway;
	@CsvBindByName(column = "btts_count")
	private Integer bttsCount;
	@CsvBindByName(column = "btts_count_home")
	private Integer bttsCountHome;
	@CsvBindByName(column = "btts_count_away")
	private Integer bttsCountAway;
	@CsvBindByName(column = "fts_count")
	private Integer ftsCount;
	@CsvBindByName(column = "fts_count_home")
	private Integer ftsCountHome;
	@CsvBindByName(column = "fts_count_away")
	private Integer ftsCountAway;
	@CsvBindByName(column = "first_team_to_score_count")
	private Integer firstTeamToScoreCount;
	@CsvBindByName(column = "first_team_to_score_count_home")
	private Integer firstTeamToScoreCountHome;
	@CsvBindByName(column = "first_team_to_score_count_away")
	private Integer firstTeamToScoreCountAway;
	@CsvBindByName(column = "corners_total")
	private Integer cornersTotal;
	@CsvBindByName(column = "corners_total_home")
	private Integer cornersTotalHome;
	@CsvBindByName(column = "corners_total_away")
	private Integer cornersTotalAway;
	@CsvBindByName(column = "cards_total")
	private Integer cardsTotal;
	@CsvBindByName(column = "cards_total_home")
	private Integer cardsTotalHome;
	@CsvBindByName(column = "cards_total_away")
	private Integer cardsTotalAway;
	@CsvBindByName(column = "average_possession")
	private Integer averagePossession;
	@CsvBindByName(column = "average_possession_home")
	private Integer averagePossessionHome;
	@CsvBindByName(column = "average_possession_away")
	private Integer averagePossessionAway;
	@CsvBindByName(column = "shots")
	private Integer shots;
	@CsvBindByName(column = "shots_home")
	private Integer shotsHome;
	@CsvBindByName(column = "shots_away")
	private Integer shotsAway;
	@CsvBindByName(column = "shots_on_target")
	private Integer shotsOnTarget;
	@CsvBindByName(column = "shots_on_target_home")
	private Integer shotsOnTargetHome;
	@CsvBindByName(column = "shots_on_target_away")
	private Integer shotsOnTargetAway;
	@CsvBindByName(column = "shots_off_target")
	private Integer shotsOffTarget;
	@CsvBindByName(column = "shots_off_target_home")
	private Integer shotsOffTargetHome;
	@CsvBindByName(column = "shots_off_target_away")
	private Integer shotsOffTargetAway;
	@CsvBindByName(column = "fouls")
	private Integer fouls;
	@CsvBindByName(column = "fouls_home")
	private Integer foulsHome;
	@CsvBindByName(column = "fouls_away")
	private Integer foulsAway;
	@CsvBindByName(column = "goals_scored_half_time")
	private Integer goalsScoredHalfTime;
	@CsvBindByName(column = "goals_scored_half_time_home")
	private Integer goalsScoredHalfTimeHome;
	@CsvBindByName(column = "goals_scored_half_time_away")
	private Integer goalsScoredHalfTimeAway;
	@CsvBindByName(column = "goals_conceded_half_time")
	private Integer goalsConcededHalfTime;
	@CsvBindByName(column = "goals_conceded_half_time_home")
	private Integer goalsConcededHalfTimeHome;
	@CsvBindByName(column = "goals_conceded_half_time_away")
	private Integer goalsConcededHalfTimeAway;
	@CsvBindByName(column = "goal_difference_half_time")
	private Integer goalDifferenceHalfTime;
	@CsvBindByName(column = "goal_difference_half_time_home")
	private Integer goalDifferenceHalfTimeHome;
	@CsvBindByName(column = "goal_difference_half_time_away")
	private Integer goalDifferenceHalfTimeAway;
	@CsvBindByName(column = "leading_at_half_time")
	private Integer leadingAtHalfTime;
	@CsvBindByName(column = "leading_at_half_time_home")
	private Integer leadingAtHalfTimeHome;
	@CsvBindByName(column = "leading_at_half_time_away")
	private Integer leadingAtHalfTimeAway;
	@CsvBindByName(column = "draw_at_half_time")
	private Integer drawAtHalfTime;
	@CsvBindByName(column = "draw_at_half_time_home")
	private Integer drawAtHalfTimeHome;
	@CsvBindByName(column = "draw_at_half_time_away")
	private Integer drawAtHalfTimeAway;
	@CsvBindByName(column = "losing_at_half_time")
	private Integer losingAtHalfTime;
	@CsvBindByName(column = "losing_at_half_time_home")
	private Integer losingAtHalfTimeHome;
	@CsvBindByName(column = "losing_at_half_time_away")
	private Integer losingAtHalfTimeAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "points_per_game_half_time")
	private Double pointsPerGameHalfTime;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "points_per_game_half_time_home")
	private Double pointsPerGameHalfTimeHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "points_per_game_half_time_away")
	private Double pointsPerGameHalfTimeAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "average_total_goals_per_match")
	private Double averageTotalGoalsPerMatch;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "average_total_goals_per_match_home")
	private Double averageTotalGoalsPerMatchHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "average_total_goals_per_match_away")
	private Double averageTotalGoalsPerMatchAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_scored_per_match")
	private Double goalsScoredPerMatch;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_scored_per_match_home")
	private Double goalsScoredPerMatchHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_scored_per_match_away")
	private Double goalsScoredPerMatchAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_conceded_per_match")
	private Double goalsConcededPerMatch;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_conceded_per_match_home")
	private Double goalsConcededPerMatchHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_conceded_per_match_away")
	private Double goalsConcededPerMatchAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "total_goals_per_match_half_time")
	private Double totalGoalsPerMatchHalfTime;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "total_goals_per_match_half_time_home")
	private Double totalGoalsPerMatchHalfTimeHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "total_goals_per_match_half_time_away")
	private Double totalGoalsPerMatchHalfTimeAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_scored_per_match_half_time")
	private Double goalsScoredPerMatchHalfTime;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_scored_per_match_half_time_home")
	private Double goalsScoredPerMatchHalfTimeHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_scored_per_match_half_time_away")
	private Double goalsScoredPerMatchHalfTimeAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_conceded_per_match_half_time")
	private Double goalsConcededPerMatchHalfTime;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_conceded_per_match_half_time_home")
	private Double goalsConcededPerMatchHalfTimeHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_conceded_per_match_half_time_away")
	private Double goalsConcededPerMatchHalfTimeAway;
	@CsvBindByName(column = "over05_count")
	private Integer over05Count;
	@CsvBindByName(column = "over15_count")
	private Integer over15Count;
	@CsvBindByName(column = "over25_count")
	private Integer over25Count;
	@CsvBindByName(column = "over35_count")
	private Integer over35Count;
	@CsvBindByName(column = "over45_count")
	private Integer over45Count;
	@CsvBindByName(column = "over55_count")
	private Integer over55Count;
	@CsvBindByName(column = "over05_count_home")
	private Integer over05CountHome;
	@CsvBindByName(column = "over15_count_home")
	private Integer over15CountHome;
	@CsvBindByName(column = "over25_count_home")
	private Integer over25CountHome;
	@CsvBindByName(column = "over35_count_home")
	private Integer over35CountHome;
	@CsvBindByName(column = "over45_count_home")
	private Integer over45CountHome;
	@CsvBindByName(column = "over55_count_home")
	private Integer over55CountHome;
	@CsvBindByName(column = "over05_count_away")
	private Integer over05CountAway;
	@CsvBindByName(column = "over15_count_away")
	private Integer over15CountAway;
	@CsvBindByName(column = "over25_count_away")
	private Integer over25CountAway;
	@CsvBindByName(column = "over35_count_away")
	private Integer over35CountAway;
	@CsvBindByName(column = "over45_count_away")
	private Integer over45CountAway;
	@CsvBindByName(column = "over55_count_away")
	private Integer over55CountAway;
	@CsvBindByName(column = "under05_count")
	private Integer under05Count;
	@CsvBindByName(column = "under15_count")
	private Integer under15Count;
	@CsvBindByName(column = "under25_count")
	private Integer under25Count;
	@CsvBindByName(column = "under35_count")
	private Integer under35Count;
	@CsvBindByName(column = "under45_count")
	private Integer under45Count;
	@CsvBindByName(column = "under55_count")
	private Integer under55Count;
	@CsvBindByName(column = "under05_count_home")
	private Integer under05CountHome;
	@CsvBindByName(column = "under15_count_home")
	private Integer under15CountHome;
	@CsvBindByName(column = "under25_count_home")
	private Integer under25CountHome;
	@CsvBindByName(column = "under35_count_home")
	private Integer under35CountHome;
	@CsvBindByName(column = "under45_count_home")
	private Integer under45CountHome;
	@CsvBindByName(column = "under55_count_home")
	private Integer under55CountHome;
	@CsvBindByName(column = "under05_count_away")
	private Integer under05CountAway;
	@CsvBindByName(column = "under15_count_away")
	private Integer under15CountAway;
	@CsvBindByName(column = "under25_count_away")
	private Integer under25CountAway;
	@CsvBindByName(column = "under35_count_away")
	private Integer under35CountAway;
	@CsvBindByName(column = "under45_count_away")
	private Integer under45CountAway;
	@CsvBindByName(column = "under55_count_away")
	private Integer under55CountAway;
	@CsvBindByName(column = "over05_percentage")
	private Integer over05Percentage;
	@CsvBindByName(column = "over15_percentage")
	private Integer over15Percentage;
	@CsvBindByName(column = "over25_percentage")
	private Integer over25Percentage;
	@CsvBindByName(column = "over35_percentage")
	private Integer over35Percentage;
	@CsvBindByName(column = "over45_percentage")
	private Integer over45Percentage;
	@CsvBindByName(column = "over55_percentage")
	private Integer over55Percentage;
	@CsvBindByName(column = "over05_percentage_home")
	private Integer over05PercentageHome;
	@CsvBindByName(column = "over15_percentage_home")
	private Integer over15PercentageHome;
	@CsvBindByName(column = "over25_percentage_home")
	private Integer over25PercentageHome;
	@CsvBindByName(column = "over35_percentage_home")
	private Integer over35PercentageHome;
	@CsvBindByName(column = "over45_percentage_home")
	private Integer over45PercentageHome;
	@CsvBindByName(column = "over55_percentage_home")
	private Integer over55PercentageHome;
	@CsvBindByName(column = "over05_percentage_away")
	private Integer over05PercentageAway;
	@CsvBindByName(column = "over15_percentage_away")
	private Integer over15PercentageAway;
	@CsvBindByName(column = "over25_percentage_away")
	private Integer over25PercentageAway;
	@CsvBindByName(column = "over35_percentage_away")
	private Integer over35PercentageAway;
	@CsvBindByName(column = "over45_percentage_away")
	private Integer over45PercentageAway;
	@CsvBindByName(column = "over55_percentage_away")
	private Integer over55PercentageAway;
	@CsvBindByName(column = "under05_percentage")
	private Integer under05Percentage;
	@CsvBindByName(column = "under15_percentage")
	private Integer under15Percentage;
	@CsvBindByName(column = "under25_percentage")
	private Integer under25Percentage;
	@CsvBindByName(column = "under35_percentage")
	private Integer under35Percentage;
	@CsvBindByName(column = "under45_percentage")
	private Integer under45Percentage;
	@CsvBindByName(column = "under55_percentage")
	private Integer under55Percentage;
	@CsvBindByName(column = "under05_percentage_home")
	private Integer under05PercentageHome;
	@CsvBindByName(column = "under15_percentage_home")
	private Integer under15PercentageHome;
	@CsvBindByName(column = "under25_percentage_home")
	private Integer under25PercentageHome;
	@CsvBindByName(column = "under35_percentage_home")
	private Integer under35PercentageHome;
	@CsvBindByName(column = "under45_percentage_home")
	private Integer under45PercentageHome;
	@CsvBindByName(column = "under55_percentage_home")
	private Integer under55PercentageHome;
	@CsvBindByName(column = "under05_percentage_away")
	private Integer under05PercentageAway;
	@CsvBindByName(column = "under15_percentage_away")
	private Integer under15PercentageAway;
	@CsvBindByName(column = "under25_percentage_away")
	private Integer under25PercentageAway;
	@CsvBindByName(column = "under35_percentage_away")
	private Integer under35PercentageAway;
	@CsvBindByName(column = "under45_percentage_away")
	private Integer under45PercentageAway;
	@CsvBindByName(column = "under55_percentage_away")
	private Integer under55PercentageAway;
	@CsvBindByName(column = "over05_count_half_time")
	private Integer over05CountHalfTime;
	@CsvBindByName(column = "over15_count_half_time")
	private Integer over15CountHalfTime;
	@CsvBindByName(column = "over25_count_half_time")
	private Integer over25CountHalfTime;
	@CsvBindByName(column = "over05_count_half_time_home")
	private Integer over05CountHalfTimeHome;
	@CsvBindByName(column = "over15_count_half_time_home")
	private Integer over15CountHalfTimeHome;
	@CsvBindByName(column = "over25_count_half_time_home")
	private Integer over25CountHalfTimeHome;
	@CsvBindByName(column = "over05_count_half_time_away")
	private Integer over05CountHalfTimeAway;
	@CsvBindByName(column = "over15_count_half_time_away")
	private Integer over15CountHalfTimeAway;
	@CsvBindByName(column = "over25_count_half_time_away")
	private Integer over25CountHalfTimeAway;
	@CsvBindByName(column = "over05_half_time_percentage")
	private Integer over05HalfTimePercentage;
	@CsvBindByName(column = "over15_half_time_percentage")
	private Integer over15HalfTimePercentage;
	@CsvBindByName(column = "over25_half_time_percentage")
	private Integer over25HalfTimePercentage;
	@CsvBindByName(column = "over05_half_time_percentage_home")
	private Integer over05HalfTimePercentageHome;
	@CsvBindByName(column = "over15_half_time_percentage_home")
	private Integer over15HalfTimePercentageHome;
	@CsvBindByName(column = "over25_half_time_percentage_home")
	private Integer over25HalfTimePercentageHome;
	@CsvBindByName(column = "over05_half_time_percentage_away")
	private Integer over05HalfTimePercentageAway;
	@CsvBindByName(column = "over15_half_time_percentage_away")
	private Integer over15HalfTimePercentageAway;
	@CsvBindByName(column = "over25_half_time_percentage_away")
	private Integer over25HalfTimePercentageAway;
	@CsvBindByName(column = "win_percentage")
	private Integer winPercentage;
	@CsvBindByName(column = "win_percentage_home")
	private Integer winPercentageHome;
	@CsvBindByName(column = "win_percentage_away")
	private Integer winPercentageAway;
	@CsvBindByName(column = "home_advantage_percentage")
	private Integer homeAdvantagePercentage;
	@CsvBindByName(column = "clean_sheet_percentage")
	private Integer cleanSheetPercentage;
	@CsvBindByName(column = "clean_sheet_percentage_home")
	private Integer cleanSheetPercentageHome;
	@CsvBindByName(column = "clean_sheet_percentage_away")
	private Integer cleanSheetPercentageAway;
	@CsvBindByName(column = "btts_percentage")
	private Integer bttsPercentage;
	@CsvBindByName(column = "btts_percentage_home")
	private Integer bttsPercentageHome;
	@CsvBindByName(column = "btts_percentage_away")
	private Integer bttsPercentageAway;
	@CsvBindByName(column = "fts_percentage")
	private Integer ftsPercentage;
	@CsvBindByName(column = "fts_percentage_home")
	private Integer ftsPercentageHome;
	@CsvBindByName(column = "fts_percentage_away")
	private Integer ftsPercentageAway;
	@CsvBindByName(column = "first_team_to_score_percentage")
	private Integer firstTeamToScorePercentage;
	@CsvBindByName(column = "first_team_to_score_percentage_home")
	private Integer firstTeamToScorePercentageHome;
	@CsvBindByName(column = "first_team_to_score_percentage_away")
	private Integer firstTeamToScorePercentageAway;
	@CsvBindByName(column = "clean_sheet_half_time")
	private Integer cleanSheetHalfTime;
	@CsvBindByName(column = "clean_sheet_half_time_home")
	private Integer cleanSheetHalfTimeHome;
	@CsvBindByName(column = "clean_sheet_half_time_away")
	private Integer cleanSheetHalfTimeAway;
	@CsvBindByName(column = "clean_sheet_half_time_percentage")
	private Integer cleanSheetHalfTimePercentage;
	@CsvBindByName(column = "clean_sheet_half_time_percentage_home")
	private Integer cleanSheetHalfTimePercentageHome;
	@CsvBindByName(column = "clean_sheet_half_time_percentage_away")
	private Integer cleanSheetHalfTimePercentageAway;
	@CsvBindByName(column = "fts_half_time")
	private Integer ftsHalfTime;
	@CsvBindByName(column = "fts_half_time_home")
	private Integer ftsHalfTimeHome;
	@CsvBindByName(column = "fts_half_time_away")
	private Integer ftsHalfTimeAway;
	@CsvBindByName(column = "fts_half_time_percentage")
	private Integer ftsHalfTimePercentage;
	@CsvBindByName(column = "fts_half_time_percentage_home")
	private Integer ftsHalfTimePercentageHome;
	@CsvBindByName(column = "fts_half_time_percentage_away")
	private Integer ftsHalfTimePercentageAway;
	@CsvBindByName(column = "btts_half_time")
	private Integer bttsHalfTime;
	@CsvBindByName(column = "btts_half_time_home")
	private Integer bttsHalfTimeHome;
	@CsvBindByName(column = "btts_half_time_away")
	private Integer bttsHalfTimeAway;
	@CsvBindByName(column = "btts_half_time_percentage")
	private Integer bttsHalfTimePercentage;
	@CsvBindByName(column = "btts_half_time_percentage_home")
	private Integer bttsHalfTimePercentageHome;
	@CsvBindByName(column = "btts_half_time_percentage_away")
	private Integer bttsHalfTimePercentageAway;
	@CsvBindByName(column = "leading_at_half_time_percentage")
	private Integer leadingAtHalfTimePercentage;
	@CsvBindByName(column = "leading_at_half_time_percentage_home")
	private Integer leadingAtHalfTimePercentageHome;
	@CsvBindByName(column = "leading_at_half_time_percentage_away")
	private Integer leadingAtHalfTimePercentageAway;
	@CsvBindByName(column = "draw_at_half_time_percentage")
	private Integer drawAtHalfTimePercentage;
	@CsvBindByName(column = "draw_at_half_time_percentage_home")
	private Integer drawAtHalfTimePercentageHome;
	@CsvBindByName(column = "draw_at_half_time_percentage_away")
	private Integer drawAtHalfTimePercentageAway;
	@CsvBindByName(column = "losing_at_half_time_percentage")
	private Integer losingAtHalfTimePercentage;
	@CsvBindByName(column = "losing_at_half_time_percentage_home")
	private Integer losingAtHalfTimePercentageHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "losing_at_half_time_percentage_away")
	private Double losingAtHalfTimePercentageAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "corners_per_match")
	private Double cornersPerMatch;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "corners_per_match_home")
	private Double cornersPerMatchHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "corners_per_match_away")
	private Double cornersPerMatchAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "cards_per_match")
	private Double cardsPerMatch;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "cards_per_match_home")
	private Double cardsPerMatchHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "cards_per_match_away")
	private Double cardsPerMatchAway;
	@CsvBindByName(column = "over65_corners_percentage")
	private Integer over65CornersPercentage;
	@CsvBindByName(column = "over75_corners_percentage")
	private Integer over75CornersPercentage;
	@CsvBindByName(column = "over85_corners_percentage")
	private Integer over85CornersPercentage;
	@CsvBindByName(column = "over95_corners_percentage")
	private Integer over95CornersPercentage;
	@CsvBindByName(column = "over105_corners_percentage")
	private Integer over105CornersPercentage;
	@CsvBindByName(column = "over115_corners_percentage")
	private Integer over115CornersPercentage;
	@CsvBindByName(column = "over125_corners_percentage")
	private Integer over125CornersPercentage;
	@CsvBindByName(column = "over135_corners_percentage")
	private Integer over135CornersPercentage;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "xg_for_avg_overall")
	private Double xgForAvgOverall;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "xg_for_avg_home")
	private Double xgForAvgHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "xg_for_avg_away")
	private Double xgForAvgAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "xg_against_avg_overall")
	private Double xgAgainstAvgOverall;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "xg_against_avg_home")
	private Double xgAgainstAvgHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "xg_against_avg_away")
	private Double xgAgainstAvgAway;
	@CsvBindByName(column = "prediction_risk")
	private Integer predictionRisk;
	@CsvBindByName(column = "goals_scored_min_0_to_10")
	private Integer goalsScoredMin0To10;
	@CsvBindByName(column = "goals_scored_min_11_to_20")
	private Integer goalsScoredMin11To20;
	@CsvBindByName(column = "goals_scored_min_21_to_30")
	private Integer goalsScoredMin21To30;
	@CsvBindByName(column = "goals_scored_min_31_to_40")
	private Integer goalsScoredMin31To40;
	@CsvBindByName(column = "goals_scored_min_41_to_50")
	private Integer goalsScoredMin41To50;
	@CsvBindByName(column = "goals_scored_min_51_to_60")
	private Integer goalsScoredMin51To60;
	@CsvBindByName(column = "goals_scored_min_61_to_70")
	private Integer goalsScoredMin61To70;
	@CsvBindByName(column = "goals_scored_min_71_to_80")
	private Integer goalsScoredMin71To80;
	@CsvBindByName(column = "goals_scored_min_81_to_90")
	private Integer goalsScoredMin81To90;
	@CsvBindByName(column = "goals_conceded_min_0_to_10")
	private Integer goalsConcededMin0To10;
	@CsvBindByName(column = "goals_conceded_min_11_to_20")
	private Integer goalsConcededMin11To20;
	@CsvBindByName(column = "goals_conceded_min_21_to_30")
	private Integer goalsConcededMin21To30;
	@CsvBindByName(column = "goals_conceded_min_31_to_40")
	private Integer goalsConcededMin31To40;
	@CsvBindByName(column = "goals_conceded_min_41_to_50")
	private Integer goalsConcededMin41To50;
	@CsvBindByName(column = "goals_conceded_min_51_to_60")
	private Integer goalsConcededMin51To60;
	@CsvBindByName(column = "goals_conceded_min_61_to_70")
	private Integer goalsConcededMin61To70;
	@CsvBindByName(column = "goals_conceded_min_71_to_80")
	private Integer goalsConcededMin71To80;
	@CsvBindByName(column = "goals_conceded_min_81_to_90")
	private Integer goalsConcededMin81To90;
	@CsvBindByName(column = "draw_percentage_overall")
	private Integer drawPercentageOverall;
	@CsvBindByName(column = "draw_percentage_home")
	private Integer drawPercentageHome;
	@CsvBindByName(column = "draw_percentage_away")
	private Integer drawPercentageAway;
	@CsvBindByName(column = "loss_percentage_ovearll")
	private Integer lossPercentageOvearll;
	@CsvBindByName(column = "loss_percentage_home")
	private Integer lossPercentageHome;
	@CsvBindByName(column = "loss_percentage_away")
	private Integer lossPercentageAway;
	@CsvBindByName(column = "over145_corners_percentage")
	private Integer over145CornersPercentage;
}
