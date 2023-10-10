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
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor // used for csv import
@Builder
@CompoundIndex(name = "unique", def = "{'timestamp' : 1, 'homeTeam': 1, 'awayTeam': 1}")
@Document
public class LeagueMatchStats {

	@Id
	private ObjectId _id;

	@Indexed
	@CsvBindByName(column = "timestamp")
	private Long timestamp;
	@CsvDate("MMM d u - h:mma")
	@CsvBindByName(column = "date_GMT", locale = "en-in")
	private LocalDateTime dateGMT;
	@CsvBindByName(column = "status")
	private MatchStatus status;
	@CsvCustomBindByName(column = "attendance", converter = IntegerConverter.class)
	private Integer attendance;
	@CsvBindByName(column = "home_team_name")
	private String homeTeamName;
	@CsvBindByName(column = "away_team_name")
	private String awayTeamName;
	@CsvBindByName(column = "referee")
	private String referee;
	@CsvBindByName(column = "Game Week")
	private Integer gameWeek;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Pre-Match PPG (Home)")
	private Float preMatchPPGHome;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Pre-Match PPG (Away)")
	private Float preMatchPPGAway;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "home_ppg")
	private Float homePpg;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "away_ppg")
	private Float awayPpg;
	@CsvBindByName(column = "home_team_goal_count")
	private Integer homeTeamGoalCount;
	@CsvBindByName(column = "away_team_goal_count")
	private Integer awayTeamGoalCount;
	@CsvBindByName(column = "total_goal_count")
	private Integer totalGoalCount;
	@CsvBindByName(column = "total_goals_at_half_time")
	private Integer totalGoalsAtHalfTime;
	@CsvBindByName(column = "home_team_goal_count_half_time")
	private Integer homeTeamGoalCountHalfTime;
	@CsvBindByName(column = "away_team_goal_count_half_time")
	private Integer awayTeamGoalCountHalfTime;
	@CsvBindByName(column = "home_team_goal_timings")
	private String homeTeamGoalTimings;
	@CsvBindByName(column = "away_team_goal_timings")
	private String awayTeamGoalTimings;
	@CsvBindByName(column = "home_team_corner_count")
	private Integer homeTeamCornerCount;
	@CsvBindByName(column = "away_team_corner_count")
	private Integer awayTeamCornerCount;
	@CsvBindByName(column = "home_team_yellow_cards")
	private Integer homeTeamYellowCards;
	@CsvBindByName(column = "home_team_red_cards")
	private Integer homeTeamRedCards;
	@CsvBindByName(column = "away_team_yellow_cards")
	private Integer awayTeamYellowCards;
	@CsvBindByName(column = "away_team_red_cards")
	private Integer awayTeamRedCards;
	@CsvBindByName(column = "home_team_first_half_cards")
	private Integer homeTeamFirstHalfCards;
	@CsvBindByName(column = "home_team_second_half_cards")
	private Integer homeTeamSecondHalfCards;
	@CsvBindByName(column = "away_team_first_half_cards")
	private Integer awayTeamFirstHalfCards;
	@CsvBindByName(column = "away_team_second_half_cards")
	private Integer awayTeamSecondHalfCards;
	@CsvBindByName(column = "home_team_shots")
	private Integer homeTeamShots;
	@CsvBindByName(column = "away_team_shots")
	private Integer awayTeamShots;
	@CsvBindByName(column = "home_team_shots_on_target")
	private Integer homeTeamShotsOnTarget;
	@CsvBindByName(column = "away_team_shots_on_target")
	private Integer awayTeamShotsOnTarget;
	@CsvBindByName(column = "home_team_shots_off_target")
	private Integer homeTeamShotsOffTarget;
	@CsvBindByName(column = "away_team_shots_off_target")
	private Integer awayTeamShotsOffTarget;
	@CsvBindByName(column = "home_team_fouls")
	private Integer homeTeamFouls;
	@CsvBindByName(column = "away_team_fouls")
	private Integer awayTeamFouls;
	@CsvBindByName(column = "home_team_possession")
	private Integer homeTeamPossession;
	@CsvBindByName(column = "away_team_possession")
	private Integer awayTeamPossession;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Pre-Match xG")
	private Float homeTeamPreMatchxG;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Pre-Match xG")
	private Float awayTeamPreMatchxG;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "team_a_xg")
	private Float teamAXg;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "team_b_xg")
	private Float teamBXg;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "average_goals_per_match_pre_match")
	private Float averageGoalsPerMatchPreMatch;
	@CsvBindByName(column = "btts_percentage_pre_match")
	private Integer bttsPercentagePreMatch;
	@CsvBindByName(column = "over_15_percentage_pre_match")
	private Integer over_15PercentagePreMatch;
	@CsvBindByName(column = "over_25_percentage_pre_match")
	private Integer over_25PercentagePreMatch;
	@CsvBindByName(column = "over_35_percentage_pre_match")
	private Integer over_35PercentagePreMatch;
	@CsvBindByName(column = "over_45_percentage_pre_match")
	private Integer over_45PercentagePreMatch;
	@CsvBindByName(column = "over_15_HT_FHG_percentage_pre_match")
	private Integer over_15HTFHGPercentagePreMatch;
	@CsvBindByName(column = "over_05_HT_FHG_percentage_pre_match")
	private Integer over_05HTFHGPercentagePreMatch;
	@CsvBindByName(column = "over_15_2HG_percentage_pre_match")
	private Integer over_15_2HGPercentagePreMatch;
	@CsvBindByName(column = "over_05_2HG_percentage_pre_match")
	private Integer over_05_2HGPercentagePreMatch;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "average_corners_per_match_pre_match")
	private Float averageCornersPerMatchPreMatch;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "average_cards_per_match_pre_match")
	private Float averageCardsPerMatchPreMatch;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "odds_ft_home_team_win")
	private Float oddsFtHomeTeamWin;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "odds_ft_draw")
	private Float oddsFtDraw;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "odds_ft_away_team_win")
	private Float oddsFtAwayTeamWin;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "odds_ft_over15")
	private Float oddsFtOver15;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "odds_ft_over25")
	private Float oddsFtOver25;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "odds_ft_over35")
	private Float oddsFtOver35;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "odds_ft_over45")
	private Float oddsFtOver45;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "odds_btts_yes")
	private Float oddsBttsYes;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "odds_btts_no")
	private Float oddsBttsNo;
	@CsvBindByName(column = "stadium_name")
	private String stadiumName;
}
