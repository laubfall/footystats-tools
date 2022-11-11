package de.ludwig.footystats.tools.backend.services.stats;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import de.ludwig.footystats.tools.backend.services.csv.FloatConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@JsonComponent
@Document
@CompoundIndexes({
	@CompoundIndex(name = "unique", def = "{'name' : 1, 'season': 1}")
})
public class LeagueStats {
	@CsvBindByName(column = "name")
	@Getter
	@Setter
	private String name;
	@CsvBindByName(column = "season")
	@Getter
	@Setter
	private String season;
	@CsvBindByName(column = "status")
	@Getter
	@Setter
	private String status;
	@CsvBindByName(column = "format")
	@Getter
	@Setter
	private String format;
	@CsvBindByName(column = "number_of_clubs")
	@Getter
	@Setter
	private Integer number_of_clubs;
	@CsvBindByName(column = "total_matches")
	@Getter
	@Setter
	private Integer total_matches;
	@CsvBindByName(column = "matches_completed")
	@Getter
	@Setter
	private Integer matches_completed;
	@CsvBindByName(column = "game_week")
	@Getter
	@Setter
	private Integer game_week;
	@CsvBindByName(column = "total_game_week")
	@Getter
	@Setter
	private Integer total_game_week;
	@CsvBindByName(column = "progress")
	@Getter
	@Setter
	private Integer progress;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "average_goals_per_match")
	@Getter
	@Setter
	private Float average_goals_per_match;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "average_scored_home_team")
	@Getter
	@Setter
	private Float average_scored_home_team;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "average_scored_away_team")
	@Getter
	@Setter
	private Float average_scored_away_team;
	@CsvBindByName(column = "btts_percentage")
	@Getter
	@Setter
	private Integer btts_percentage;
	@CsvBindByName(column = "clean_sheets_percentage")
	@Getter
	@Setter
	private Integer clean_sheets_percentage;
	@CsvBindByName(column = "prediction_risk")
	@Getter
	@Setter
	private Integer prediction_risk;
	@CsvBindByName(column = "home_scored_advantage_percentage")
	@Getter
	@Setter
	private Integer home_scored_advantage_percentage;
	@CsvBindByName(column = "home_defence_advantage_percentage")
	@Getter
	@Setter
	private Integer home_defence_advantage_percentage;
	@CsvBindByName(column = "home_advantage_percentage")
	@Getter
	@Setter
	private Integer home_advantage_percentage;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "average_corners_per_match_home_team")
	@Getter
	@Setter
	private Float average_corners_per_match;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "average_corners_per_match_home_team")
	@Getter
	@Setter
	private Float average_corners_per_match_home_team;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "average_corners_per_match_away_team")
	@Getter
	@Setter
	private Float average_corners_per_match_away_team;
	@CsvBindByName(column = "total_corners_for_season")
	@Getter
	@Setter
	private Integer total_corners_for_season;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "average_cards_per_match")
	@Getter
	@Setter
	private Float average_cards_per_match;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "average_cards_per_match_home_team")
	@Getter
	@Setter
	private Float average_cards_per_match_home_team;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "average_cards_per_match_away_team")
	@Getter
	@Setter
	private Float average_cards_per_match_away_team;
	@CsvBindByName(column = "total_cards_for_season")
	@Getter
	@Setter
	private Integer total_cards_for_season;
	@CsvBindByName(column = "over_05_percentage")
	@Getter
	@Setter
	private Integer over_05_percentage;
	@CsvBindByName(column = "over_15_percentage")
	@Getter
	@Setter
	private Integer over_15_percentage;
	@CsvBindByName(column = "over_25_percentage")
	@Getter
	@Setter
	private Integer over_25_percentage;
	@CsvBindByName(column = "over_35_percentage")
	@Getter
	@Setter
	private Integer over_35_percentage;
	@CsvBindByName(column = "over_45_percentage")
	@Getter
	@Setter
	private Integer over_45_percentage;
	@CsvBindByName(column = "over_55_percentage")
	@Getter
	@Setter
	private Integer over_55_percentage;
	@CsvBindByName(column = "under_05_percentage")
	@Getter
	@Setter
	private Integer under_05_percentage;
	@CsvBindByName(column = "under_15_percentage")
	@Getter
	@Setter
	private Integer under_15_percentage;
	@CsvBindByName(column = "under_25_percentage")
	@Getter
	@Setter
	private Integer under_25_percentage;
	@CsvBindByName(column = "under_35_percentage")
	@Getter
	@Setter
	private Integer under_35_percentage;
	@CsvBindByName(column = "under_45_percentage")
	@Getter
	@Setter
	private Integer under_45_percentage;
	@CsvBindByName(column = "under_55_percentage")
	@Getter
	@Setter
	private Integer under_55_percentage;
	@CsvBindByName(column = "over_65_corners_percentage")
	@Getter
	@Setter
	private Integer over_65_corners_percentage;
	@CsvBindByName(column = "over_75_corners_percentage")
	@Getter
	@Setter
	private Integer over_75_corners_percentage;
	@CsvBindByName(column = "over_85_corners_percentage")
	@Getter
	@Setter
	private Integer over_85_corners_percentage;
	@CsvBindByName(column = "over_95_corners_percentage")
	@Getter
	@Setter
	private Integer over_95_corners_percentage;
	@CsvBindByName(column = "over_105_corners_percentage")
	@Getter
	@Setter
	private Integer over_105_corners_percentage;
	@CsvBindByName(column = "over_115_corners_percentage")
	@Getter
	@Setter
	private Integer over_115_corners_percentage;
	@CsvBindByName(column = "over_125_corners_percentage")
	@Getter
	@Setter
	private Integer over_125_corners_percentage;
	@CsvBindByName(column = "over_135_corners_percentage")
	@Getter
	@Setter
	private Integer over_135_corners_percentage;
	@CsvBindByName(column = "over_05_cards_percentage")
	@Getter
	@Setter
	private Integer over_05_cards_percentage;
	@CsvBindByName(column = "over_15_cards_percentage")
	@Getter
	@Setter
	private Integer over_15_cards_percentage;
	@CsvBindByName(column = "over_25_cards_percentage")
	@Getter
	@Setter
	private Integer over_25_cards_percentage;
	@CsvBindByName(column = "over_35_cards_percentage")
	@Getter
	@Setter
	private Integer over_35_cards_percentage;
	@CsvBindByName(column = "over_45_cards_percentage")
	@Getter
	@Setter
	private Integer over_45_cards_percentage;
	@CsvBindByName(column = "over_55_cards_percentage")
	@Getter
	@Setter
	private Integer over_55_cards_percentage;
	@CsvBindByName(column = "over_65_cards_percentage")
	@Getter
	@Setter
	private Integer over_65_cards_percentage;
	@CsvBindByName(column = "over_75_cards_percentage")
	@Getter
	@Setter
	private Integer over_75_cards_percentage;
	@CsvBindByName(column = "goals_min_0_to_10")
	@Getter
	@Setter
	private Integer goals_min_0_to_10;
	@CsvBindByName(column = "goals_min_11_to_20")
	@Getter
	@Setter
	private Integer goals_min_11_to_20;
	@CsvBindByName(column = "goals_min_21_to_30")
	@Getter
	@Setter
	private Integer goals_min_21_to_30;
	@CsvBindByName(column = "goals_min_31_to_40")
	@Getter
	@Setter
	private Integer goals_min_31_to_40;
	@CsvBindByName(column = "goals_min_41_to_50")
	@Getter
	@Setter
	private Integer goals_min_41_to_50;
	@CsvBindByName(column = "goals_min_51_to_60")
	@Getter
	@Setter
	private Integer goals_min_51_to_60;
	@CsvBindByName(column = "goals_min_61_to_70")
	@Getter
	@Setter
	private Integer goals_min_61_to_70;
	@CsvBindByName(column = "goals_min_71_to_80")
	@Getter
	@Setter
	private Integer goals_min_71_to_80;
	@CsvBindByName(column = "goals_min_81_to_90")
	@Getter
	@Setter
	private Integer goals_min_81_to_90;
	@CsvBindByName(column = "goals_min_0_to_15")
	@Getter
	@Setter
	private Integer goals_min_0_to_15;
	@CsvBindByName(column = "goals_min_16_to_30")
	@Getter
	@Setter
	private Integer goals_min_16_to_30;
	@CsvBindByName(column = "goals_min_31_to_45")
	@Getter
	@Setter
	private Integer goals_min_31_to_45;
	@CsvBindByName(column = "goals_min_46_to_60")
	@Getter
	@Setter
	private Integer goals_min_46_to_60;
	@CsvBindByName(column = "goals_min_61_to_75")
	@Getter
	@Setter
	private Integer goals_min_61_to_75;
	@CsvBindByName(column = "goals_min_76_to_90")
	@Getter
	@Setter
	private Integer goals_min_76_to_90;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "xg_avg_per_match")
	@Getter
	@Setter
	private Float xg_avg_per_match;
}
