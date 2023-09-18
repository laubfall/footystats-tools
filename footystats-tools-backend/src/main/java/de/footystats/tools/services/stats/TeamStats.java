package de.footystats.tools.services.stats;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import de.footystats.tools.services.csv.DoubleConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@JsonComponent
@NoArgsConstructor
@CompoundIndex(name = "unique", def = "{'teamName' : 1, 'commonName': 1, 'season': 1, 'country': 1}")
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
	private Integer matchesPlayed;
	@CsvBindByName(column = "matches_played_home")
	@Getter
	@Setter
	private Integer matchesPlayedHome;
	@CsvBindByName(column = "matches_played_away")
	@Getter
	@Setter
	private Integer matchesPlayedAway;
	@CsvBindByName(column = "suspended_matches")
	@Getter
	@Setter
	private Integer suspendedMatches;
	@CsvBindByName(column = "wins")
	@Getter
	@Setter
	private Integer wins;
	@CsvBindByName(column = "wins_home")
	@Getter
	@Setter
	private Integer winsHome;
	@CsvBindByName(column = "wins_away")
	@Getter
	@Setter
	private Integer winsAway;
	@CsvBindByName(column = "draws")
	@Getter
	@Setter
	private Integer draws;
	@CsvBindByName(column = "draws_home")
	@Getter
	@Setter
	private Integer drawsHome;
	@CsvBindByName(column = "draws_away")
	@Getter
	@Setter
	private Integer drawsAway;
	@CsvBindByName(column = "losses")
	@Getter
	@Setter
	private Integer losses;
	@CsvBindByName(column = "losses_home")
	@Getter
	@Setter
	private Integer lossesHome;
	@CsvBindByName(column = "losses_away")
	@Getter
	@Setter
	private Integer lossesAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "points_per_game")
	@Getter
	@Setter
	private Double pointsPerGame;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "points_per_game_home")
	@Getter
	@Setter
	private Double pointsPerGameHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "points_per_game_away")
	@Getter
	@Setter
	private Double pointsPerGameAway;
	@CsvBindByName(column = "league_position")
	@Getter
	@Setter
	private Integer leaguePosition;
	@CsvBindByName(column = "league_position_home")
	@Getter
	@Setter
	private Integer leaguePositionHome;
	@CsvBindByName(column = "league_position_away")
	@Getter
	@Setter
	private Integer leaguePositionAway;
	@CsvBindByName(column = "performance_rank")
	@Getter
	@Setter
	private Integer performanceRank;
	@CsvBindByName(column = "goals_scored")
	@Getter
	@Setter
	private Integer goalsScored;
	@CsvBindByName(column = "goals_conceded")
	@Getter
	@Setter
	private Integer goalsConceded;
	@CsvBindByName(column = "goal_difference")
	@Getter
	@Setter
	private Integer goalDifference;
	@CsvBindByName(column = "total_goal_count")
	@Getter
	@Setter
	private Integer totalGoalCount;
	@CsvBindByName(column = "total_goal_count_home")
	@Getter
	@Setter
	private Integer totalGoalCountHome;
	@CsvBindByName(column = "total_goal_count_away")
	@Getter
	@Setter
	private Integer totalGoalCountAway;
	@CsvBindByName(column = "goals_scored_home")
	@Getter
	@Setter
	private Integer goalsScoredHome;
	@CsvBindByName(column = "goals_scored_away")
	@Getter
	@Setter
	private Integer goalsScoredAway;
	@CsvBindByName(column = "goals_conceded_home")
	@Getter
	@Setter
	private Integer goalsConcededHome;
	@CsvBindByName(column = "goals_conceded_away")
	@Getter
	@Setter
	private Integer goalsConcededAway;
	@CsvBindByName(column = "goal_difference_home")
	@Getter
	@Setter
	private Integer goalDifferenceHome;
	@CsvBindByName(column = "goal_difference_away")
	@Getter
	@Setter
	private Integer goalDifferenceAway;
	@CsvBindByName(column = "minutes_per_goal_scored")
	@Getter
	@Setter
	private Integer minutesPerGoalScored;
	@CsvBindByName(column = "minutes_per_goal_scored_home")
	@Getter
	@Setter
	private Integer minutesPerGoalScoredHome;
	@CsvBindByName(column = "minutes_per_goal_scored_away")
	@Getter
	@Setter
	private Integer minutesPerGoalScoredAway;
	@CsvBindByName(column = "minutes_per_goal_conceded")
	@Getter
	@Setter
	private Integer minutesPerGoalConceded;
	@CsvBindByName(column = "minutes_per_goal_conceded_home")
	@Getter
	@Setter
	private Integer minutesPerGoalConcededHome;
	@CsvBindByName(column = "minutes_per_goal_conceded_away")
	@Getter
	@Setter
	private Integer minutesPerGoalConcededAway;
	@CsvBindByName(column = "clean_sheets")
	@Getter
	@Setter
	private Integer cleanSheets;
	@CsvBindByName(column = "clean_sheets_home")
	@Getter
	@Setter
	private Integer cleanSheetsHome;
	@CsvBindByName(column = "clean_sheets_away")
	@Getter
	@Setter
	private Integer cleanSheetsAway;
	@CsvBindByName(column = "btts_count")
	@Getter
	@Setter
	private Integer bttsCount;
	@CsvBindByName(column = "btts_count_home")
	@Getter
	@Setter
	private Integer bttsCountHome;
	@CsvBindByName(column = "btts_count_away")
	@Getter
	@Setter
	private Integer bttsCountAway;
	@CsvBindByName(column = "fts_count")
	@Getter
	@Setter
	private Integer ftsCount;
	@CsvBindByName(column = "fts_count_home")
	@Getter
	@Setter
	private Integer ftsCountHome;
	@CsvBindByName(column = "fts_count_away")
	@Getter
	@Setter
	private Integer ftsCountAway;
	@CsvBindByName(column = "first_team_to_score_count")
	@Getter
	@Setter
	private Integer firstTeamToScoreCount;
	@CsvBindByName(column = "first_team_to_score_count_home")
	@Getter
	@Setter
	private Integer firstTeamToScoreCountHome;
	@CsvBindByName(column = "first_team_to_score_count_away")
	@Getter
	@Setter
	private Integer firstTeamToScoreCountAway;
	@CsvBindByName(column = "corners_total")
	@Getter
	@Setter
	private Integer cornersTotal;
	@CsvBindByName(column = "corners_total_home")
	@Getter
	@Setter
	private Integer cornersTotalHome;
	@CsvBindByName(column = "corners_total_away")
	@Getter
	@Setter
	private Integer cornersTotalAway;
	@CsvBindByName(column = "cards_total")
	@Getter
	@Setter
	private Integer cardsTotal;
	@CsvBindByName(column = "cards_total_home")
	@Getter
	@Setter
	private Integer cardsTotalHome;
	@CsvBindByName(column = "cards_total_away")
	@Getter
	@Setter
	private Integer cardsTotalAway;
	@CsvBindByName(column = "average_possession")
	@Getter
	@Setter
	private Integer averagePossession;
	@CsvBindByName(column = "average_possession_home")
	@Getter
	@Setter
	private Integer averagePossessionHome;
	@CsvBindByName(column = "average_possession_away")
	@Getter
	@Setter
	private Integer averagePossessionAway;
	@CsvBindByName(column = "shots")
	@Getter
	@Setter
	private Integer shots;
	@CsvBindByName(column = "shots_home")
	@Getter
	@Setter
	private Integer shotsHome;
	@CsvBindByName(column = "shots_away")
	@Getter
	@Setter
	private Integer shotsAway;
	@CsvBindByName(column = "shots_on_target")
	@Getter
	@Setter
	private Integer shotsOnTarget;
	@CsvBindByName(column = "shots_on_target_home")
	@Getter
	@Setter
	private Integer shotsOnTargetHome;
	@CsvBindByName(column = "shots_on_target_away")
	@Getter
	@Setter
	private Integer shotsOnTargetAway;
	@CsvBindByName(column = "shots_off_target")
	@Getter
	@Setter
	private Integer shotsOffTarget;
	@CsvBindByName(column = "shots_off_target_home")
	@Getter
	@Setter
	private Integer shotsOffTargetHome;
	@CsvBindByName(column = "shots_off_target_away")
	@Getter
	@Setter
	private Integer shotsOffTargetAway;
	@CsvBindByName(column = "fouls")
	@Getter
	@Setter
	private Integer fouls;
	@CsvBindByName(column = "fouls_home")
	@Getter
	@Setter
	private Integer foulsHome;
	@CsvBindByName(column = "fouls_away")
	@Getter
	@Setter
	private Integer foulsAway;
	@CsvBindByName(column = "goals_scored_half_time")
	@Getter
	@Setter
	private Integer goalsScoredHalfTime;
	@CsvBindByName(column = "goals_scored_half_time_home")
	@Getter
	@Setter
	private Integer goalsScoredHalfTimeHome;
	@CsvBindByName(column = "goals_scored_half_time_away")
	@Getter
	@Setter
	private Integer goalsScoredHalfTimeAway;
	@CsvBindByName(column = "goals_conceded_half_time")
	@Getter
	@Setter
	private Integer goalsConcededHalfTime;
	@CsvBindByName(column = "goals_conceded_half_time_home")
	@Getter
	@Setter
	private Integer goalsConcededHalfTimeHome;
	@CsvBindByName(column = "goals_conceded_half_time_away")
	@Getter
	@Setter
	private Integer goalsConcededHalfTimeAway;
	@CsvBindByName(column = "goal_difference_half_time")
	@Getter
	@Setter
	private Integer goalDifferenceHalfTime;
	@CsvBindByName(column = "goal_difference_half_time_home")
	@Getter
	@Setter
	private Integer goalDifferenceHalfTimeHome;
	@CsvBindByName(column = "goal_difference_half_time_away")
	@Getter
	@Setter
	private Integer goalDifferenceHalfTimeAway;
	@CsvBindByName(column = "leading_at_half_time")
	@Getter
	@Setter
	private Integer leadingAtHalfTime;
	@CsvBindByName(column = "leading_at_half_time_home")
	@Getter
	@Setter
	private Integer leadingAtHalfTimeHome;
	@CsvBindByName(column = "leading_at_half_time_away")
	@Getter
	@Setter
	private Integer leadingAtHalfTimeAway;
	@CsvBindByName(column = "draw_at_half_time")
	@Getter
	@Setter
	private Integer drawAtHalfTime;
	@CsvBindByName(column = "draw_at_half_time_home")
	@Getter
	@Setter
	private Integer drawAtHalfTimeHome;
	@CsvBindByName(column = "draw_at_half_time_away")
	@Getter
	@Setter
	private Integer drawAtHalfTimeAway;
	@CsvBindByName(column = "losing_at_half_time")
	@Getter
	@Setter
	private Integer losingAtHalfTime;
	@CsvBindByName(column = "losing_at_half_time_home")
	@Getter
	@Setter
	private Integer losingAtHalfTimeHome;
	@CsvBindByName(column = "losing_at_half_time_away")
	@Getter
	@Setter
	private Integer losingAtHalfTimeAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "points_per_game_half_time")
	@Getter
	@Setter
	private Double pointsPerGameHalfTime;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "points_per_game_half_time_home")
	@Getter
	@Setter
	private Double pointsPerGameHalfTimeHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "points_per_game_half_time_away")
	@Getter
	@Setter
	private Double pointsPerGameHalfTimeAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "average_total_goals_per_match")
	@Getter
	@Setter
	private Double averageTotalGoalsPerMatch;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "average_total_goals_per_match_home")
	@Getter
	@Setter
	private Double averageTotalGoalsPerMatchHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "average_total_goals_per_match_away")
	@Getter
	@Setter
	private Double averageTotalGoalsPerMatchAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_scored_per_match")
	@Getter
	@Setter
	private Double goalsScoredPerMatch;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_scored_per_match_home")
	@Getter
	@Setter
	private Double goalsScoredPerMatchHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_scored_per_match_away")
	@Getter
	@Setter
	private Double goalsScoredPerMatchAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_conceded_per_match")
	@Getter
	@Setter
	private Double goalsConcededPerMatch;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_conceded_per_match_home")
	@Getter
	@Setter
	private Double goalsConcededPerMatchHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_conceded_per_match_away")
	@Getter
	@Setter
	private Double goalsConcededPerMatchAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "total_goals_per_match_half_time")
	@Getter
	@Setter
	private Double totalGoalsPerMatchHalfTime;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "total_goals_per_match_half_time_home")
	@Getter
	@Setter
	private Double totalGoalsPerMatchHalfTimeHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "total_goals_per_match_half_time_away")
	@Getter
	@Setter
	private Double totalGoalsPerMatchHalfTimeAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_scored_per_match_half_time")
	@Getter
	@Setter
	private Double goalsScoredPerMatchHalfTime;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_scored_per_match_half_time_home")
	@Getter
	@Setter
	private Double goalsScoredPerMatchHalfTimeHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_scored_per_match_half_time_away")
	@Getter
	@Setter
	private Double goalsScoredPerMatchHalfTimeAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_conceded_per_match_half_time")
	@Getter
	@Setter
	private Double goalsConcededPerMatchHalfTime;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_conceded_per_match_half_time_home")
	@Getter
	@Setter
	private Double goalsConcededPerMatchHalfTimeHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_conceded_per_match_half_time_away")
	@Getter
	@Setter
	private Double goalsConcededPerMatchHalfTimeAway;
	@CsvBindByName(column = "over05_count")
	@Getter
	@Setter
	private Integer over05Count;
	@CsvBindByName(column = "over15_count")
	@Getter
	@Setter
	private Integer over15Count;
	@CsvBindByName(column = "over25_count")
	@Getter
	@Setter
	private Integer over25Count;
	@CsvBindByName(column = "over35_count")
	@Getter
	@Setter
	private Integer over35Count;
	@CsvBindByName(column = "over45_count")
	@Getter
	@Setter
	private Integer over45Count;
	@CsvBindByName(column = "over55_count")
	@Getter
	@Setter
	private Integer over55Count;
	@CsvBindByName(column = "over05_count_home")
	@Getter
	@Setter
	private Integer over05CountHome;
	@CsvBindByName(column = "over15_count_home")
	@Getter
	@Setter
	private Integer over15CountHome;
	@CsvBindByName(column = "over25_count_home")
	@Getter
	@Setter
	private Integer over25CountHome;
	@CsvBindByName(column = "over35_count_home")
	@Getter
	@Setter
	private Integer over35CountHome;
	@CsvBindByName(column = "over45_count_home")
	@Getter
	@Setter
	private Integer over45CountHome;
	@CsvBindByName(column = "over55_count_home")
	@Getter
	@Setter
	private Integer over55CountHome;
	@CsvBindByName(column = "over05_count_away")
	@Getter
	@Setter
	private Integer over05CountAway;
	@CsvBindByName(column = "over15_count_away")
	@Getter
	@Setter
	private Integer over15CountAway;
	@CsvBindByName(column = "over25_count_away")
	@Getter
	@Setter
	private Integer over25CountAway;
	@CsvBindByName(column = "over35_count_away")
	@Getter
	@Setter
	private Integer over35CountAway;
	@CsvBindByName(column = "over45_count_away")
	@Getter
	@Setter
	private Integer over45CountAway;
	@CsvBindByName(column = "over55_count_away")
	@Getter
	@Setter
	private Integer over55CountAway;
	@CsvBindByName(column = "under05_count")
	@Getter
	@Setter
	private Integer under05Count;
	@CsvBindByName(column = "under15_count")
	@Getter
	@Setter
	private Integer under15Count;
	@CsvBindByName(column = "under25_count")
	@Getter
	@Setter
	private Integer under25Count;
	@CsvBindByName(column = "under35_count")
	@Getter
	@Setter
	private Integer under35Count;
	@CsvBindByName(column = "under45_count")
	@Getter
	@Setter
	private Integer under45Count;
	@CsvBindByName(column = "under55_count")
	@Getter
	@Setter
	private Integer under55Count;
	@CsvBindByName(column = "under05_count_home")
	@Getter
	@Setter
	private Integer under05CountHome;
	@CsvBindByName(column = "under15_count_home")
	@Getter
	@Setter
	private Integer under15CountHome;
	@CsvBindByName(column = "under25_count_home")
	@Getter
	@Setter
	private Integer under25CountHome;
	@CsvBindByName(column = "under35_count_home")
	@Getter
	@Setter
	private Integer under35CountHome;
	@CsvBindByName(column = "under45_count_home")
	@Getter
	@Setter
	private Integer under45CountHome;
	@CsvBindByName(column = "under55_count_home")
	@Getter
	@Setter
	private Integer under55CountHome;
	@CsvBindByName(column = "under05_count_away")
	@Getter
	@Setter
	private Integer under05CountAway;
	@CsvBindByName(column = "under15_count_away")
	@Getter
	@Setter
	private Integer under15CountAway;
	@CsvBindByName(column = "under25_count_away")
	@Getter
	@Setter
	private Integer under25CountAway;
	@CsvBindByName(column = "under35_count_away")
	@Getter
	@Setter
	private Integer under35CountAway;
	@CsvBindByName(column = "under45_count_away")
	@Getter
	@Setter
	private Integer under45CountAway;
	@CsvBindByName(column = "under55_count_away")
	@Getter
	@Setter
	private Integer under55CountAway;
	@CsvBindByName(column = "over05_percentage")
	@Getter
	@Setter
	private Integer over05Percentage;
	@CsvBindByName(column = "over15_percentage")
	@Getter
	@Setter
	private Integer over15Percentage;
	@CsvBindByName(column = "over25_percentage")
	@Getter
	@Setter
	private Integer over25Percentage;
	@CsvBindByName(column = "over35_percentage")
	@Getter
	@Setter
	private Integer over35Percentage;
	@CsvBindByName(column = "over45_percentage")
	@Getter
	@Setter
	private Integer over45Percentage;
	@CsvBindByName(column = "over55_percentage")
	@Getter
	@Setter
	private Integer over55Percentage;
	@CsvBindByName(column = "over05_percentage_home")
	@Getter
	@Setter
	private Integer over05PercentageHome;
	@CsvBindByName(column = "over15_percentage_home")
	@Getter
	@Setter
	private Integer over15PercentageHome;
	@CsvBindByName(column = "over25_percentage_home")
	@Getter
	@Setter
	private Integer over25PercentageHome;
	@CsvBindByName(column = "over35_percentage_home")
	@Getter
	@Setter
	private Integer over35PercentageHome;
	@CsvBindByName(column = "over45_percentage_home")
	@Getter
	@Setter
	private Integer over45PercentageHome;
	@CsvBindByName(column = "over55_percentage_home")
	@Getter
	@Setter
	private Integer over55PercentageHome;
	@CsvBindByName(column = "over05_percentage_away")
	@Getter
	@Setter
	private Integer over05PercentageAway;
	@CsvBindByName(column = "over15_percentage_away")
	@Getter
	@Setter
	private Integer over15PercentageAway;
	@CsvBindByName(column = "over25_percentage_away")
	@Getter
	@Setter
	private Integer over25PercentageAway;
	@CsvBindByName(column = "over35_percentage_away")
	@Getter
	@Setter
	private Integer over35PercentageAway;
	@CsvBindByName(column = "over45_percentage_away")
	@Getter
	@Setter
	private Integer over45PercentageAway;
	@CsvBindByName(column = "over55_percentage_away")
	@Getter
	@Setter
	private Integer over55PercentageAway;
	@CsvBindByName(column = "under05_percentage")
	@Getter
	@Setter
	private Integer under05Percentage;
	@CsvBindByName(column = "under15_percentage")
	@Getter
	@Setter
	private Integer under15Percentage;
	@CsvBindByName(column = "under25_percentage")
	@Getter
	@Setter
	private Integer under25Percentage;
	@CsvBindByName(column = "under35_percentage")
	@Getter
	@Setter
	private Integer under35Percentage;
	@CsvBindByName(column = "under45_percentage")
	@Getter
	@Setter
	private Integer under45Percentage;
	@CsvBindByName(column = "under55_percentage")
	@Getter
	@Setter
	private Integer under55Percentage;
	@CsvBindByName(column = "under05_percentage_home")
	@Getter
	@Setter
	private Integer under05PercentageHome;
	@CsvBindByName(column = "under15_percentage_home")
	@Getter
	@Setter
	private Integer under15PercentageHome;
	@CsvBindByName(column = "under25_percentage_home")
	@Getter
	@Setter
	private Integer under25PercentageHome;
	@CsvBindByName(column = "under35_percentage_home")
	@Getter
	@Setter
	private Integer under35PercentageHome;
	@CsvBindByName(column = "under45_percentage_home")
	@Getter
	@Setter
	private Integer under45PercentageHome;
	@CsvBindByName(column = "under55_percentage_home")
	@Getter
	@Setter
	private Integer under55PercentageHome;
	@CsvBindByName(column = "under05_percentage_away")
	@Getter
	@Setter
	private Integer under05PercentageAway;
	@CsvBindByName(column = "under15_percentage_away")
	@Getter
	@Setter
	private Integer under15PercentageAway;
	@CsvBindByName(column = "under25_percentage_away")
	@Getter
	@Setter
	private Integer under25PercentageAway;
	@CsvBindByName(column = "under35_percentage_away")
	@Getter
	@Setter
	private Integer under35PercentageAway;
	@CsvBindByName(column = "under45_percentage_away")
	@Getter
	@Setter
	private Integer under45PercentageAway;
	@CsvBindByName(column = "under55_percentage_away")
	@Getter
	@Setter
	private Integer under55PercentageAway;
	@CsvBindByName(column = "over05_count_half_time")
	@Getter
	@Setter
	private Integer over05CountHalfTime;
	@CsvBindByName(column = "over15_count_half_time")
	@Getter
	@Setter
	private Integer over15CountHalfTime;
	@CsvBindByName(column = "over25_count_half_time")
	@Getter
	@Setter
	private Integer over25CountHalfTime;
	@CsvBindByName(column = "over05_count_half_time_home")
	@Getter
	@Setter
	private Integer over05CountHalfTimeHome;
	@CsvBindByName(column = "over15_count_half_time_home")
	@Getter
	@Setter
	private Integer over15CountHalfTimeHome;
	@CsvBindByName(column = "over25_count_half_time_home")
	@Getter
	@Setter
	private Integer over25CountHalfTimeHome;
	@CsvBindByName(column = "over05_count_half_time_away")
	@Getter
	@Setter
	private Integer over05CountHalfTimeAway;
	@CsvBindByName(column = "over15_count_half_time_away")
	@Getter
	@Setter
	private Integer over15CountHalfTimeAway;
	@CsvBindByName(column = "over25_count_half_time_away")
	@Getter
	@Setter
	private Integer over25CountHalfTimeAway;
	@CsvBindByName(column = "over05_half_time_percentage")
	@Getter
	@Setter
	private Integer over05HalfTimePercentage;
	@CsvBindByName(column = "over15_half_time_percentage")
	@Getter
	@Setter
	private Integer over15HalfTimePercentage;
	@CsvBindByName(column = "over25_half_time_percentage")
	@Getter
	@Setter
	private Integer over25HalfTimePercentage;
	@CsvBindByName(column = "over05_half_time_percentage_home")
	@Getter
	@Setter
	private Integer over05HalfTimePercentageHome;
	@CsvBindByName(column = "over15_half_time_percentage_home")
	@Getter
	@Setter
	private Integer over15HalfTimePercentageHome;
	@CsvBindByName(column = "over25_half_time_percentage_home")
	@Getter
	@Setter
	private Integer over25HalfTimePercentageHome;
	@CsvBindByName(column = "over05_half_time_percentage_away")
	@Getter
	@Setter
	private Integer over05HalfTimePercentageAway;
	@CsvBindByName(column = "over15_half_time_percentage_away")
	@Getter
	@Setter
	private Integer over15HalfTimePercentageAway;
	@CsvBindByName(column = "over25_half_time_percentage_away")
	@Getter
	@Setter
	private Integer over25HalfTimePercentageAway;
	@CsvBindByName(column = "win_percentage")
	@Getter
	@Setter
	private Integer winPercentage;
	@CsvBindByName(column = "win_percentage_home")
	@Getter
	@Setter
	private Integer winPercentageHome;
	@CsvBindByName(column = "win_percentage_away")
	@Getter
	@Setter
	private Integer winPercentageAway;
	@CsvBindByName(column = "home_advantage_percentage")
	@Getter
	@Setter
	private Integer homeAdvantagePercentage;
	@CsvBindByName(column = "clean_sheet_percentage")
	@Getter
	@Setter
	private Integer cleanSheetPercentage;
	@CsvBindByName(column = "clean_sheet_percentage_home")
	@Getter
	@Setter
	private Integer cleanSheetPercentageHome;
	@CsvBindByName(column = "clean_sheet_percentage_away")
	@Getter
	@Setter
	private Integer cleanSheetPercentageAway;
	@CsvBindByName(column = "btts_percentage")
	@Getter
	@Setter
	private Integer bttsPercentage;
	@CsvBindByName(column = "btts_percentage_home")
	@Getter
	@Setter
	private Integer bttsPercentageHome;
	@CsvBindByName(column = "btts_percentage_away")
	@Getter
	@Setter
	private Integer bttsPercentageAway;
	@CsvBindByName(column = "fts_percentage")
	@Getter
	@Setter
	private Integer ftsPercentage;
	@CsvBindByName(column = "fts_percentage_home")
	@Getter
	@Setter
	private Integer ftsPercentageHome;
	@CsvBindByName(column = "fts_percentage_away")
	@Getter
	@Setter
	private Integer ftsPercentageAway;
	@CsvBindByName(column = "first_team_to_score_percentage")
	@Getter
	@Setter
	private Integer firstTeamToScorePercentage;
	@CsvBindByName(column = "first_team_to_score_percentage_home")
	@Getter
	@Setter
	private Integer firstTeamToScorePercentageHome;
	@CsvBindByName(column = "first_team_to_score_percentage_away")
	@Getter
	@Setter
	private Integer firstTeamToScorePercentageAway;
	@CsvBindByName(column = "clean_sheet_half_time")
	@Getter
	@Setter
	private Integer cleanSheetHalfTime;
	@CsvBindByName(column = "clean_sheet_half_time_home")
	@Getter
	@Setter
	private Integer cleanSheetHalfTimeHome;
	@CsvBindByName(column = "clean_sheet_half_time_away")
	@Getter
	@Setter
	private Integer cleanSheetHalfTimeAway;
	@CsvBindByName(column = "clean_sheet_half_time_percentage")
	@Getter
	@Setter
	private Integer cleanSheetHalfTimePercentage;
	@CsvBindByName(column = "clean_sheet_half_time_percentage_home")
	@Getter
	@Setter
	private Integer cleanSheetHalfTimePercentageHome;
	@CsvBindByName(column = "clean_sheet_half_time_percentage_away")
	@Getter
	@Setter
	private Integer cleanSheetHalfTimePercentageAway;
	@CsvBindByName(column = "fts_half_time")
	@Getter
	@Setter
	private Integer ftsHalfTime;
	@CsvBindByName(column = "fts_half_time_home")
	@Getter
	@Setter
	private Integer ftsHalfTimeHome;
	@CsvBindByName(column = "fts_half_time_away")
	@Getter
	@Setter
	private Integer ftsHalfTimeAway;
	@CsvBindByName(column = "fts_half_time_percentage")
	@Getter
	@Setter
	private Integer ftsHalfTimePercentage;
	@CsvBindByName(column = "fts_half_time_percentage_home")
	@Getter
	@Setter
	private Integer ftsHalfTimePercentageHome;
	@CsvBindByName(column = "fts_half_time_percentage_away")
	@Getter
	@Setter
	private Integer ftsHalfTimePercentageAway;
	@CsvBindByName(column = "btts_half_time")
	@Getter
	@Setter
	private Integer bttsHalfTime;
	@CsvBindByName(column = "btts_half_time_home")
	@Getter
	@Setter
	private Integer bttsHalfTimeHome;
	@CsvBindByName(column = "btts_half_time_away")
	@Getter
	@Setter
	private Integer bttsHalfTimeAway;
	@CsvBindByName(column = "btts_half_time_percentage")
	@Getter
	@Setter
	private Integer bttsHalfTimePercentage;
	@CsvBindByName(column = "btts_half_time_percentage_home")
	@Getter
	@Setter
	private Integer bttsHalfTimePercentageHome;
	@CsvBindByName(column = "btts_half_time_percentage_away")
	@Getter
	@Setter
	private Integer bttsHalfTimePercentageAway;
	@CsvBindByName(column = "leading_at_half_time_percentage")
	@Getter
	@Setter
	private Integer leadingAtHalfTimePercentage;
	@CsvBindByName(column = "leading_at_half_time_percentage_home")
	@Getter
	@Setter
	private Integer leadingAtHalfTimePercentageHome;
	@CsvBindByName(column = "leading_at_half_time_percentage_away")
	@Getter
	@Setter
	private Integer leadingAtHalfTimePercentageAway;
	@CsvBindByName(column = "draw_at_half_time_percentage")
	@Getter
	@Setter
	private Integer drawAtHalfTimePercentage;
	@CsvBindByName(column = "draw_at_half_time_percentage_home")
	@Getter
	@Setter
	private Integer drawAtHalfTimePercentageHome;
	@CsvBindByName(column = "draw_at_half_time_percentage_away")
	@Getter
	@Setter
	private Integer drawAtHalfTimePercentageAway;
	@CsvBindByName(column = "losing_at_half_time_percentage")
	@Getter
	@Setter
	private Integer losingAtHalfTimePercentage;
	@CsvBindByName(column = "losing_at_half_time_percentage_home")
	@Getter
	@Setter
	private Integer losingAtHalfTimePercentageHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "losing_at_half_time_percentage_away")
	@Getter
	@Setter
	private Double losingAtHalfTimePercentageAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "corners_per_match")
	@Getter
	@Setter
	private Double cornersPerMatch;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "corners_per_match_home")
	@Getter
	@Setter
	private Double cornersPerMatchHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "corners_per_match_away")
	@Getter
	@Setter
	private Double cornersPerMatchAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "cards_per_match")
	@Getter
	@Setter
	private Double cardsPerMatch;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "cards_per_match_home")
	@Getter
	@Setter
	private Double cardsPerMatchHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "cards_per_match_away")
	@Getter
	@Setter
	private Double cardsPerMatchAway;
	@CsvBindByName(column = "over65_corners_percentage")
	@Getter
	@Setter
	private Integer over65CornersPercentage;
	@CsvBindByName(column = "over75_corners_percentage")
	@Getter
	@Setter
	private Integer over75CornersPercentage;
	@CsvBindByName(column = "over85_corners_percentage")
	@Getter
	@Setter
	private Integer over85CornersPercentage;
	@CsvBindByName(column = "over95_corners_percentage")
	@Getter
	@Setter
	private Integer over95CornersPercentage;
	@CsvBindByName(column = "over105_corners_percentage")
	@Getter
	@Setter
	private Integer over105CornersPercentage;
	@CsvBindByName(column = "over115_corners_percentage")
	@Getter
	@Setter
	private Integer over115CornersPercentage;
	@CsvBindByName(column = "over125_corners_percentage")
	@Getter
	@Setter
	private Integer over125CornersPercentage;
	@CsvBindByName(column = "over135_corners_percentage")
	@Getter
	@Setter
	private Integer over135CornersPercentage;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "xg_for_avg_overall")
	@Getter
	@Setter
	private Double xgForAvgOverall;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "xg_for_avg_home")
	@Getter
	@Setter
	private Double xgForAvgHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "xg_for_avg_away")
	@Getter
	@Setter
	private Double xgForAvgAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "xg_against_avg_overall")
	@Getter
	@Setter
	private Double xgAgainstAvgOverall;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "xg_against_avg_home")
	@Getter
	@Setter
	private Double xgAgainstAvgHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "xg_against_avg_away")
	@Getter
	@Setter
	private Double xgAgainstAvgAway;
	@CsvBindByName(column = "prediction_risk")
	@Getter
	@Setter
	private Integer predictionRisk;
	@CsvBindByName(column = "goals_scored_min_0_to_10")
	@Getter
	@Setter
	private Integer goalsScoredMin0To10;
	@CsvBindByName(column = "goals_scored_min_11_to_20")
	@Getter
	@Setter
	private Integer goalsScoredMin11To20;
	@CsvBindByName(column = "goals_scored_min_21_to_30")
	@Getter
	@Setter
	private Integer goalsScoredMin21To30;
	@CsvBindByName(column = "goals_scored_min_31_to_40")
	@Getter
	@Setter
	private Integer goalsScoredMin31To40;
	@CsvBindByName(column = "goals_scored_min_41_to_50")
	@Getter
	@Setter
	private Integer goalsScoredMin41To50;
	@CsvBindByName(column = "goals_scored_min_51_to_60")
	@Getter
	@Setter
	private Integer goalsScoredMin51To60;
	@CsvBindByName(column = "goals_scored_min_61_to_70")
	@Getter
	@Setter
	private Integer goalsScoredMin61To70;
	@CsvBindByName(column = "goals_scored_min_71_to_80")
	@Getter
	@Setter
	private Integer goalsScoredMin71To80;
	@CsvBindByName(column = "goals_scored_min_81_to_90")
	@Getter
	@Setter
	private Integer goalsScoredMin81To90;
	@CsvBindByName(column = "goals_conceded_min_0_to_10")
	@Getter
	@Setter
	private Integer goalsConcededMin0To10;
	@CsvBindByName(column = "goals_conceded_min_11_to_20")
	@Getter
	@Setter
	private Integer goalsConcededMin11To20;
	@CsvBindByName(column = "goals_conceded_min_21_to_30")
	@Getter
	@Setter
	private Integer goalsConcededMin21To30;
	@CsvBindByName(column = "goals_conceded_min_31_to_40")
	@Getter
	@Setter
	private Integer goalsConcededMin31To40;
	@CsvBindByName(column = "goals_conceded_min_41_to_50")
	@Getter
	@Setter
	private Integer goalsConcededMin41To50;
	@CsvBindByName(column = "goals_conceded_min_51_to_60")
	@Getter
	@Setter
	private Integer goalsConcededMin51To60;
	@CsvBindByName(column = "goals_conceded_min_61_to_70")
	@Getter
	@Setter
	private Integer goalsConcededMin61To70;
	@CsvBindByName(column = "goals_conceded_min_71_to_80")
	@Getter
	@Setter
	private Integer goalsConcededMin71To80;
	@CsvBindByName(column = "goals_conceded_min_81_to_90")
	@Getter
	@Setter
	private Integer goalsConcededMin81To90;
	@CsvBindByName(column = "draw_percentage_overall")
	@Getter
	@Setter
	private Integer drawPercentageOverall;
	@CsvBindByName(column = "draw_percentage_home")
	@Getter
	@Setter
	private Integer drawPercentageHome;
	@CsvBindByName(column = "draw_percentage_away")
	@Getter
	@Setter
	private Integer drawPercentageAway;
	@CsvBindByName(column = "loss_percentage_ovearll")
	@Getter
	@Setter
	private Integer lossPercentageOvearll;
	@CsvBindByName(column = "loss_percentage_home")
	@Getter
	@Setter
	private Integer lossPercentageHome;
	@CsvBindByName(column = "loss_percentage_away")
	@Getter
	@Setter
	private Integer lossPercentageAway;
	@CsvBindByName(column = "over145_corners_percentage")
	@Getter
	@Setter
	private Integer over145CornersPercentage;
}
