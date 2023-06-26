package de.footystats.tools.services.stats;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvDate;
import de.footystats.tools.services.csv.FloatConverter;
import de.footystats.tools.services.csv.IntegerConverter;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor // used for csv import
@Builder
@CompoundIndexes({
	@CompoundIndex(name = "unique", def = "{'timestamp' : 1, 'homeTeam': 1, 'awayTeam': 1}")
})
@Document
public class LeagueMatchStats {

	@Getter
	@Setter
	private String _id;

	@Indexed
	@CsvBindByName(column = "timestamp")
	@Getter
	@Setter
	private Long timestamp;
	@CsvDate("MMM d u - h:mma")
	@CsvBindByName(column = "date_GMT", locale = "en-in")
	@Getter
	@Setter
	private LocalDateTime dateGMT;
	@CsvBindByName(column = "status")
	@Getter
	@Setter
	private MatchStatus status;
	@CsvCustomBindByName(column = "attendance", converter = IntegerConverter.class)
	@Getter
	@Setter
	private Integer attendance;
	@CsvBindByName(column = "home_team_name")
	@Getter
	@Setter
	private String homeTeamName;
	@CsvBindByName(column = "away_team_name")
	@Getter
	@Setter
	private String awayTeamName;
	@CsvBindByName(column = "referee")
	@Getter
	@Setter
	private String referee;
	@CsvBindByName(column = "Game Week")
	@Getter
	@Setter
	private Integer gameWeek;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Pre-Match PPG (Home)")
	@Getter
	@Setter
	private Float preMatchPPGHome;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Pre-Match PPG (Away)")
	@Getter
	@Setter
	private Float preMatchPPGAway;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "home_ppg")
	@Getter
	@Setter
	private Float homePpg;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "away_ppg")
	@Getter
	@Setter
	private Float awayPpg;
	@CsvBindByName(column = "home_team_goal_count")
	@Getter
	@Setter
	private Integer homeTeamGoalCount;
	@CsvBindByName(column = "away_team_goal_count")
	@Getter
	@Setter
	private Integer awayTeamGoalCount;
	@CsvBindByName(column = "total_goal_count")
	@Getter
	@Setter
	private Integer totalGoalCount;
	@CsvBindByName(column = "total_goals_at_half_time")
	@Getter
	@Setter
	private Integer totalGoalsAtHalfTime;
	@CsvBindByName(column = "home_team_goal_count_half_time")
	@Getter
	@Setter
	private Integer homeTeamGoalCountHalfTime;
	@CsvBindByName(column = "away_team_goal_count_half_time")
	@Getter
	@Setter
	private Integer awayTeamGoalCountHalfTime;
	@CsvBindByName(column = "home_team_goal_timings")
	@Getter
	@Setter
	private String homeTeamGoalTimings;
	@CsvBindByName(column = "away_team_goal_timings")
	@Getter
	@Setter
	private String awayTeamGoalTimings;
	@CsvBindByName(column = "home_team_corner_count")
	@Getter
	@Setter
	private Integer homeTeamCornerCount;
	@CsvBindByName(column = "away_team_corner_count")
	@Getter
	@Setter
	private Integer awayTeamCornerCount;
	@CsvBindByName(column = "home_team_yellow_cards")
	@Getter
	@Setter
	private Integer homeTeamYellowCards;
	@CsvBindByName(column = "home_team_red_cards")
	@Getter
	@Setter
	private Integer homeTeamRedCards;
	@CsvBindByName(column = "away_team_yellow_cards")
	@Getter
	@Setter
	private Integer awayTeamYellowCards;
	@CsvBindByName(column = "away_team_red_cards")
	@Getter
	@Setter
	private Integer awayTeamRedCards;
	@CsvBindByName(column = "home_team_first_half_cards")
	@Getter
	@Setter
	private Integer homeTeamFirstHalfCards;
	@CsvBindByName(column = "home_team_second_half_cards")
	@Getter
	@Setter
	private Integer homeTeamSecondHalfCards;
	@CsvBindByName(column = "away_team_first_half_cards")
	@Getter
	@Setter
	private Integer awayTeamFirstHalfCards;
	@CsvBindByName(column = "away_team_second_half_cards")
	@Getter
	@Setter
	private Integer awayTeamSecondHalfCards;
	@CsvBindByName(column = "home_team_shots")
	@Getter
	@Setter
	private Integer homeTeamShots;
	@CsvBindByName(column = "away_team_shots")
	@Getter
	@Setter
	private Integer awayTeamShots;
	@CsvBindByName(column = "home_team_shots_on_target")
	@Getter
	@Setter
	private Integer homeTeamShotsOnTarget;
	@CsvBindByName(column = "away_team_shots_on_target")
	@Getter
	@Setter
	private Integer awayTeamShotsOnTarget;
	@CsvBindByName(column = "home_team_shots_off_target")
	@Getter
	@Setter
	private Integer homeTeamShotsOffTarget;
	@CsvBindByName(column = "away_team_shots_off_target")
	@Getter
	@Setter
	private Integer awayTeamShotsOffTarget;
	@CsvBindByName(column = "home_team_fouls")
	@Getter
	@Setter
	private Integer homeTeamFouls;
	@CsvBindByName(column = "away_team_fouls")
	@Getter
	@Setter
	private Integer awayTeamFouls;
	@CsvBindByName(column = "home_team_possession")
	@Getter
	@Setter
	private Integer homeTeamPossession;
	@CsvBindByName(column = "away_team_possession")
	@Getter
	@Setter
	private Integer awayTeamPossession;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Pre-Match xG")
	@Getter
	@Setter
	private Float homeTeamPreMatchxG;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Pre-Match xG")
	@Getter
	@Setter
	private Float awayTeamPreMatchxG;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "team_a_xg")
	@Getter
	@Setter
	private Float teamAXg;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "team_b_xg")
	@Getter
	@Setter
	private Float teamBXg;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "average_goals_per_match_pre_match")
	@Getter
	@Setter
	private Float averageGoalsPerMatchPreMatch;
	@CsvBindByName(column = "btts_percentage_pre_match")
	@Getter
	@Setter
	private Integer bttsPercentagePreMatch;
	@CsvBindByName(column = "over_15_percentage_pre_match")
	@Getter
	@Setter
	private Integer over_15PercentagePreMatch;
	@CsvBindByName(column = "over_25_percentage_pre_match")
	@Getter
	@Setter
	private Integer over_25PercentagePreMatch;
	@CsvBindByName(column = "over_35_percentage_pre_match")
	@Getter
	@Setter
	private Integer over_35PercentagePreMatch;
	@CsvBindByName(column = "over_45_percentage_pre_match")
	@Getter
	@Setter
	private Integer over_45PercentagePreMatch;
	@CsvBindByName(column = "over_15_HT_FHG_percentage_pre_match")
	@Getter
	@Setter
	private Integer over_15HTFHGPercentagePreMatch;
	@CsvBindByName(column = "over_05_HT_FHG_percentage_pre_match")
	@Getter
	@Setter
	private Integer over_05HTFHGPercentagePreMatch;
	@CsvBindByName(column = "over_15_2HG_percentage_pre_match")
	@Getter
	@Setter
	private Integer over_15_2HGPercentagePreMatch;
	@CsvBindByName(column = "over_05_2HG_percentage_pre_match")
	@Getter
	@Setter
	private Integer over_05_2HGPercentagePreMatch;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "average_corners_per_match_pre_match")
	@Getter
	@Setter
	private Float averageCornersPerMatchPreMatch;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "average_cards_per_match_pre_match")
	@Getter
	@Setter
	private Float averageCardsPerMatchPreMatch;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "odds_ft_home_team_win")
	@Getter
	@Setter
	private Float oddsFtHomeTeamWin;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "odds_ft_draw")
	@Getter
	@Setter
	private Float oddsFtDraw;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "odds_ft_away_team_win")
	@Getter
	@Setter
	private Float oddsFtAwayTeamWin;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "odds_ft_over15")
	@Getter
	@Setter
	private Float oddsFtOver15;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "odds_ft_over25")
	@Getter
	@Setter
	private Float oddsFtOver25;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "odds_ft_over35")
	@Getter
	@Setter
	private Float oddsFtOver35;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "odds_ft_over45")
	@Getter
	@Setter
	private Float oddsFtOver45;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "odds_btts_yes")
	@Getter
	@Setter
	private Float oddsBttsYes;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "odds_btts_no")
	@Getter
	@Setter
	private Float oddsBttsNo;
	@CsvBindByName(column = "stadium_name")
	@Getter
	@Setter
	private String stadiumName;
}
