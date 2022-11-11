package de.ludwig.footystats.tools.backend.services.league;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@JsonComponent
@Document
public class LeagueStats {
	@Getter
	@Setter
	private String name;
	@Getter
	@Setter
	private String season;
	@Getter
	@Setter
	private String status;
	@Getter
	@Setter
	private String format;
	@Getter
	@Setter
	private Integer number_of_clubs;
	@Getter
	@Setter
	private Integer total_matches;
	@Getter
	@Setter
	private Integer matches_completed;
	@Getter
	@Setter
	private Integer game_week;
	@Getter
	@Setter
	private Integer total_game_week;
	@Getter
	@Setter
	private Integer progress;
	@Getter
	@Setter
	private Float average_goals_per_match;
	@Getter
	@Setter
	private Float average_scored_home_team;
	@Getter
	@Setter
	private Float average_scored_away_team;
	@Getter
	@Setter
	private Integer btts_percentage;
	@Getter
	@Setter
	private Integer clean_sheets_percentage;
	@Getter
	@Setter
	private Integer prediction_risk;
	@Getter
	@Setter
	private Integer home_scored_advantage_percentage;
	@Getter
	@Setter
	private Integer home_defence_advantage_percentage;
	@Getter
	@Setter
	private Integer home_advantage_percentage;
	@Getter
	@Setter
	private Float average_corners_per_match;
	@Getter
	@Setter
	private Float average_corners_per_match_home_team;
	@Getter
	@Setter
	private Float average_corners_per_match_away_team;
	@Getter
	@Setter
	private Integer total_corners_for_season;
	@Getter
	@Setter
	private Float average_cards_per_match;
	@Getter
	@Setter
	private Float average_cards_per_match_home_team;
	@Getter
	@Setter
	private Float average_cards_per_match_away_team;
	@Getter
	@Setter
	private Integer total_cards_for_season;
	@Getter
	@Setter
	private Integer over_05_percentage;
	@Getter
	@Setter
	private Integer over_15_percentage;
	@Getter
	@Setter
	private Integer over_25_percentage;
	@Getter
	@Setter
	private Integer over_35_percentage;
	@Getter
	@Setter
	private Integer over_45_percentage;
	@Getter
	@Setter
	private Integer over_55_percentage;
	@Getter
	@Setter
	private Integer under_05_percentage;
	@Getter
	@Setter
	private Integer under_15_percentage;
	@Getter
	@Setter
	private Integer under_25_percentage;
	@Getter
	@Setter
	private Integer under_35_percentage;
	@Getter
	@Setter
	private Integer under_45_percentage;
	@Getter
	@Setter
	private Integer under_55_percentage;
	@Getter
	@Setter
	private Integer over_65_corners_percentage;
	@Getter
	@Setter
	private Integer over_75_corners_percentage;
	@Getter
	@Setter
	private Integer over_85_corners_percentage;
	@Getter
	@Setter
	private Integer over_95_corners_percentage;
	@Getter
	@Setter
	private Integer over_105_corners_percentage;
	@Getter
	@Setter
	private Integer over_115_corners_percentage;
	@Getter
	@Setter
	private Integer over_125_corners_percentage;
	@Getter
	@Setter
	private Integer over_135_corners_percentage;
	@Getter
	@Setter
	private Integer over_05_cards_percentage;
	@Getter
	@Setter
	private Integer over_15_cards_percentage;
	@Getter
	@Setter
	private Integer over_25_cards_percentage;
	@Getter
	@Setter
	private Integer over_35_cards_percentage;
	@Getter
	@Setter
	private Integer over_45_cards_percentage;
	@Getter
	@Setter
	private Integer over_55_cards_percentage;
	@Getter
	@Setter
	private Integer over_65_cards_percentage;
	@Getter
	@Setter
	private Integer over_75_cards_percentage;
	@Getter
	@Setter
	private Integer goals_min_0_to_10;
	@Getter
	@Setter
	private Integer goals_min_11_to_20;
	@Getter
	@Setter
	private Integer goals_min_21_to_30;
	@Getter
	@Setter
	private Integer goals_min_31_to_40;
	@Getter
	@Setter
	private Integer goals_min_41_to_50;
	@Getter
	@Setter
	private Integer goals_min_51_to_60;
	@Getter
	@Setter
	private Integer goals_min_61_to_70;
	@Getter
	@Setter
	private Integer goals_min_71_to_80;
	@Getter
	@Setter
	private Integer goals_min_81_to_90;
	@Getter
	@Setter
	private Integer goals_min_0_to_15;
	@Getter
	@Setter
	private Integer goals_min_16_to_30;
	@Getter
	@Setter
	private Integer goals_min_31_to_45;
	@Getter
	@Setter
	private Integer goals_min_46_to_60;
	@Getter
	@Setter
	private Integer goals_min_61_to_75;
	@Getter
	@Setter
	private Integer goals_min_76_to_90;
	@Getter
	@Setter
	private Float xg_avg_per_match;
}
