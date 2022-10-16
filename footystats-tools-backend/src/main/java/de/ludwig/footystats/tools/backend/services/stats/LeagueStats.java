package de.ludwig.footystats.tools.backend.services.stats;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import de.ludwig.footystats.tools.backend.services.csv.FloatConverter;
import lombok.Getter;
import lombok.Setter;

public class LeagueStats {
    @CsvBindByName(column = "name)")
    @Getter
    @Setter
    private String name;
    @CsvBindByName(column = "season)")
    @Getter
    @Setter
    private String season;
    @CsvBindByName(column = "status)")
    @Getter
    @Setter
    private String status;
    @CsvBindByName(column = "format)")
    @Getter
    @Setter
    private String format;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "number_of_clubs)")
    @Getter
    @Setter
    private Float number_of_clubs;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "total_matches)")
    @Getter
    @Setter
    private Float total_matches;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "matches_completed)")
    @Getter
    @Setter
    private Float matches_completed;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "game_week)")
    @Getter
    @Setter
    private Float game_week;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "total_game_week)")
    @Getter
    @Setter
    private Float total_game_week;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "progress)")
    @Getter
    @Setter
    private Float progress;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "average_goals_per_match)")
    @Getter
    @Setter
    private Float average_goals_per_match;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "average_scored_home_team)")
    @Getter
    @Setter
    private Float average_scored_home_team;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "average_scored_away_team)")
    @Getter
    @Setter
    private Float average_scored_away_team;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "btts_percentage)")
    @Getter
    @Setter
    private Float btts_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "clean_sheets_percentage)")
    @Getter
    @Setter
    private Float clean_sheets_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "prediction_risk)")
    @Getter
    @Setter
    private Float prediction_risk;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "home_scored_advantage_percentage)")
    @Getter
    @Setter
    private Float home_scored_advantage_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "home_defence_advantage_percentage)")
    @Getter
    @Setter
    private Float home_defence_advantage_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "home_advantage_percentage)")
    @Getter
    @Setter
    private Float home_advantage_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "average_corners_per_match)")
    @Getter
    @Setter
    private Float average_corners_per_match;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "average_corners_per_match_home_team)")
    @Getter
    @Setter
    private Float average_corners_per_match_home_team;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "average_corners_per_match_away_team)")
    @Getter
    @Setter
    private Float average_corners_per_match_away_team;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "total_corners_for_season)")
    @Getter
    @Setter
    private Float total_corners_for_season;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "average_cards_per_match)")
    @Getter
    @Setter
    private Float average_cards_per_match;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "average_cards_per_match_home_team)")
    @Getter
    @Setter
    private Float average_cards_per_match_home_team;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "average_cards_per_match_away_team)")
    @Getter
    @Setter
    private Float average_cards_per_match_away_team;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "total_cards_for_season)")
    @Getter
    @Setter
    private Float total_cards_for_season;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over_05_percentage)")
    @Getter
    @Setter
    private Float over_05_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over_15_percentage)")
    @Getter
    @Setter
    private Float over_15_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over_25_percentage)")
    @Getter
    @Setter
    private Float over_25_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over_35_percentage)")
    @Getter
    @Setter
    private Float over_35_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over_45_percentage)")
    @Getter
    @Setter
    private Float over_45_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over_55_percentage)")
    @Getter
    @Setter
    private Float over_55_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under_05_percentage)")
    @Getter
    @Setter
    private Float under_05_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under_15_percentage)")
    @Getter
    @Setter
    private Float under_15_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under_25_percentage)")
    @Getter
    @Setter
    private Float under_25_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under_35_percentage)")
    @Getter
    @Setter
    private Float under_35_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under_45_percentage)")
    @Getter
    @Setter
    private Float under_45_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "under_55_percentage)")
    @Getter
    @Setter
    private Float under_55_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over_65_corners_percentage)")
    @Getter
    @Setter
    private Float over_65_corners_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over_75_corners_percentage)")
    @Getter
    @Setter
    private Float over_75_corners_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over_85_corners_percentage)")
    @Getter
    @Setter
    private Float over_85_corners_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over_95_corners_percentage)")
    @Getter
    @Setter
    private Float over_95_corners_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over_105_corners_percentage)")
    @Getter
    @Setter
    private Float over_105_corners_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over_115_corners_percentage)")
    @Getter
    @Setter
    private Float over_115_corners_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over_125_corners_percentage)")
    @Getter
    @Setter
    private Float over_125_corners_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over_135_corners_percentage)")
    @Getter
    @Setter
    private Float over_135_corners_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over_05_cards_percentage)")
    @Getter
    @Setter
    private Float over_05_cards_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over_15_cards_percentage)")
    @Getter
    @Setter
    private Float over_15_cards_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over_25_cards_percentage)")
    @Getter
    @Setter
    private Float over_25_cards_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over_35_cards_percentage)")
    @Getter
    @Setter
    private Float over_35_cards_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over_45_cards_percentage)")
    @Getter
    @Setter
    private Float over_45_cards_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over_55_cards_percentage)")
    @Getter
    @Setter
    private Float over_55_cards_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over_65_cards_percentage)")
    @Getter
    @Setter
    private Float over_65_cards_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "over_75_cards_percentage)")
    @Getter
    @Setter
    private Float over_75_cards_percentage;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_min_0_to_10)")
    @Getter
    @Setter
    private Float goals_min_0_to_10;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_min_11_to_20)")
    @Getter
    @Setter
    private Float goals_min_11_to_20;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_min_21_to_30)")
    @Getter
    @Setter
    private Float goals_min_21_to_30;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_min_31_to_40)")
    @Getter
    @Setter
    private Float goals_min_31_to_40;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_min_41_to_50)")
    @Getter
    @Setter
    private Float goals_min_41_to_50;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_min_51_to_60)")
    @Getter
    @Setter
    private Float goals_min_51_to_60;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_min_61_to_70)")
    @Getter
    @Setter
    private Float goals_min_61_to_70;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_min_71_to_80)")
    @Getter
    @Setter
    private Float goals_min_71_to_80;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_min_81_to_90)")
    @Getter
    @Setter
    private Float goals_min_81_to_90;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_min_0_to_15)")
    @Getter
    @Setter
    private Float goals_min_0_to_15;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_min_16_to_30)")
    @Getter
    @Setter
    private Float goals_min_16_to_30;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_min_31_to_45)")
    @Getter
    @Setter
    private Float goals_min_31_to_45;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_min_46_to_60)")
    @Getter
    @Setter
    private Float goals_min_46_to_60;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_min_61_to_75)")
    @Getter
    @Setter
    private Float goals_min_61_to_75;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "goals_min_76_to_90)")
    @Getter
    @Setter
    private Float goals_min_76_to_90;
    @CsvCustomBindByName(converter = FloatConverter.class, column = "xg_avg_per_match)")
    @Getter
    @Setter
    private Float xg_avg_per_match;
}
