package de.footystats.tools.services.stats;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import de.footystats.tools.services.csv.DoubleConverter;
import de.footystats.tools.services.domain.Country;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Document
@CompoundIndex(name = "unique", def = "{'country': 1, 'name' : 1, 'season': 1}")
@Getter
@Setter
public class LeagueStats {

	// Does not exist inside the csv file, but is added by the application.
	private Country country;
	@CsvBindByName(column = "name")
	private String name;
	@CsvBindByName(column = "season")
	private String season;
	@CsvBindByName(column = "status")
	private String status;
	@CsvBindByName(column = "format")
	private String format;
	@CsvBindByName(column = "number_of_clubs")
	private Integer numberOfClubs;
	@CsvBindByName(column = "total_matches")
	private Integer totalMatches;
	@CsvBindByName(column = "matches_completed")
	private Integer matchesCompleted;
	@CsvBindByName(column = "game_week")
	private Integer gameWeek;
	@CsvBindByName(column = "total_game_week")
	private Integer totalGameWeek;
	@CsvBindByName(column = "progress")
	private Integer progress;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "average_goals_per_match")
	private Double averageGoalsPerMatch;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "average_scored_home_team")
	private Double averageScoredHomeTeam;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "average_scored_away_team")
	private Double averageScoredAwayTeam;
	@CsvBindByName(column = "btts_percentage")
	private Integer bttsPercentage;
	@CsvBindByName(column = "clean_sheets_percentage")
	private Integer cleanSheetsPercentage;
	@CsvBindByName(column = "prediction_risk")
	private Integer predictionRisk;
	@CsvBindByName(column = "home_scored_advantage_percentage")
	private Integer homeScoredAdvantagePercentage;
	@CsvBindByName(column = "home_defence_advantage_percentage")
	private Integer homeDefenceAdvantagePercentage;
	@CsvBindByName(column = "home_advantage_percentage")
	private Integer homeAdvantagePercentage;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "average_corners_per_match_home_team")
	private Double averageCornersPerMatch;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "average_corners_per_match_home_team")
	private Double averageCornersPerMatchHomeTeam;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "average_corners_per_match_away_team")
	private Double averageCornersPerMatchAwayTeam;
	@CsvBindByName(column = "total_corners_for_season")
	private Integer totalCornersForSeason;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "average_cards_per_match")
	private Double averageCardsPerMatch;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "average_cards_per_match_home_team")
	private Double averageCardsPerMatchHomeTeam;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "average_cards_per_match_away_team")
	private Double averageCardsPerMatchAwayTeam;
	@CsvBindByName(column = "total_cards_for_season")
	private Integer totalCardsForSeason;
	@CsvBindByName(column = "over_05_percentage")
	private Integer over05Percentage;
	@CsvBindByName(column = "over_15_percentage")
	private Integer over15Percentage;
	@CsvBindByName(column = "over_25_percentage")
	private Integer over25Percentage;
	@CsvBindByName(column = "over_35_percentage")
	private Integer over35Percentage;
	@CsvBindByName(column = "over_45_percentage")
	private Integer over45Percentage;
	@CsvBindByName(column = "over_55_percentage")
	private Integer over55Percentage;
	@CsvBindByName(column = "under_05_percentage")
	private Integer under05Percentage;
	@CsvBindByName(column = "under_15_percentage")
	private Integer under15Percentage;
	@CsvBindByName(column = "under_25_percentage")
	private Integer under25Percentage;
	@CsvBindByName(column = "under_35_percentage")
	private Integer under35Percentage;
	@CsvBindByName(column = "under_45_percentage")
	private Integer under45Percentage;
	@CsvBindByName(column = "under_55_percentage")
	private Integer under55Percentage;
	@CsvBindByName(column = "over_65_corners_percentage")
	private Integer over65CornersPercentage;
	@CsvBindByName(column = "over_75_corners_percentage")
	private Integer over75CornersPercentage;
	@CsvBindByName(column = "over_85_corners_percentage")
	private Integer over85CornersPercentage;
	@CsvBindByName(column = "over_95_corners_percentage")
	private Integer over95CornersPercentage;
	@CsvBindByName(column = "over_105_corners_percentage")
	private Integer over105CornersPercentage;
	@CsvBindByName(column = "over_115_corners_percentage")
	private Integer over115CornersPercentage;
	@CsvBindByName(column = "over_125_corners_percentage")
	private Integer over125CornersPercentage;
	@CsvBindByName(column = "over_135_corners_percentage")
	private Integer over135CornersPercentage;
	@CsvBindByName(column = "over_05_cards_percentage")
	private Integer over05CardsPercentage;
	@CsvBindByName(column = "over_15_cards_percentage")
	private Integer over15CardsPercentage;
	@CsvBindByName(column = "over_25_cards_percentage")
	private Integer over25CardsPercentage;
	@CsvBindByName(column = "over_35_cards_percentage")
	private Integer over35CardsPercentage;
	@CsvBindByName(column = "over_45_cards_percentage")
	private Integer over45CardsPercentage;
	@CsvBindByName(column = "over_55_cards_percentage")
	private Integer over55CardsPercentage;
	@CsvBindByName(column = "over_65_cards_percentage")
	private Integer over65CardsPercentage;
	@CsvBindByName(column = "over_75_cards_percentage")
	private Integer over75CardsPercentage;
	@CsvBindByName(column = "goals_min_0_to_10")
	private Integer goalsMin0To10;
	@CsvBindByName(column = "goals_min_11_to_20")
	private Integer goalsMin11To20;
	@CsvBindByName(column = "goals_min_21_to_30")
	private Integer goalsMin21To30;
	@CsvBindByName(column = "goals_min_31_to_40")
	private Integer goalsMin31To40;
	@CsvBindByName(column = "goals_min_41_to_50")
	private Integer goalsMin41To50;
	@CsvBindByName(column = "goals_min_51_to_60")
	private Integer goalsMin51To60;
	@CsvBindByName(column = "goals_min_61_to_70")
	private Integer goalsMin61To70;
	@CsvBindByName(column = "goals_min_71_to_80")
	private Integer goalsMin71To80;
	@CsvBindByName(column = "goals_min_81_to_90")
	private Integer goalsMin81To90;
	@CsvBindByName(column = "goals_min_0_to_15")
	private Integer goalsMin0To15;
	@CsvBindByName(column = "goals_min_16_to_30")
	private Integer goalsMin16To30;
	@CsvBindByName(column = "goals_min_31_to_45")
	private Integer goalsMin31To45;
	@CsvBindByName(column = "goals_min_46_to_60")
	private Integer goalsMin46To60;
	@CsvBindByName(column = "goals_min_61_to_75")
	private Integer goalsMin61To75;
	@CsvBindByName(column = "goals_min_76_to_90")
	private Integer goalsMin76To90;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "xg_avg_per_match")
	private Double xgAvgPerMatch;
}
