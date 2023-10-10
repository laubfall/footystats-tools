package de.footystats.tools.services.stats;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvDate;
import de.footystats.tools.services.csv.FloatConverter;
import de.footystats.tools.services.domain.Country;
import de.footystats.tools.services.domain.CountryCsvConverter;
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

@AllArgsConstructor
@NoArgsConstructor // used for csv import
@Builder
@CompoundIndex(name = "unique", def = "{'dateUnix' : 1, 'country': 1, 'league': 1, 'homeTeam': 1, 'awayTeam': 1}")
@Document
public class MatchStats {

	@Id
	@Getter
	@Setter
	private ObjectId id;

	@Indexed
	@CsvBindByName(column = "date_unix")
	@Getter
	@Setter
	private Long dateUnix;

	@CsvDate("MMM d u - h:mma")
	@CsvBindByName(column = "date_GMT", locale = "en-in")
	@Getter
	@Setter
	private LocalDateTime dateGmt;

	@CsvCustomBindByName(column = "country", required = true, converter = CountryCsvConverter.class)
	@Getter
	@Setter
	private Country country;

	@CsvBindByName(column = "League")
	@Getter
	@Setter
	private String league;

	@CsvBindByName(column = "Home Team")
	@Getter
	@Setter
	private String homeTeam;
	@CsvBindByName(column = "Away Team")
	@Getter
	@Setter
	private String awayTeam;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Points Per Game (Pre-Match)")
	@Getter
	@Setter
	private Float homeTeamPointsPerGamePreMatch;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Points Per Game (Pre-Match)")
	@Getter
	@Setter
	private Float awayTeamPointsPerGamePreMatch;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Points Per Game (Current)")
	@Getter
	@Setter
	private Float homeTeamPointsPerGameCurrent;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Points Per Game (Current)")
	@Getter
	@Setter
	private Float awayTeamPointsPerGameCurrent;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Average Goals")
	@Getter
	@Setter
	private Float averageGoals;
	@CsvBindByName(column = "BTTS Average")
	@Getter
	@Setter
	private Integer bTTSAverage;
	@CsvBindByName(column = "Over05 Average")
	@Getter
	@Setter
	private Integer over05Average;
	@CsvBindByName(column = "Over15 Average")
	@Getter
	@Setter
	private Integer over15Average;
	@CsvBindByName(column = "Over25 Average")
	@Getter
	@Setter
	private Integer over25Average;
	@CsvBindByName(column = "Over35 Average")
	@Getter
	@Setter
	private Integer over35Average;
	@CsvBindByName(column = "Over45 Average")
	@Getter
	@Setter
	private Integer over45Average;
	@CsvBindByName(column = "Over05 FHG HT Average")
	@Getter
	@Setter
	private Integer over05FHGHTAverage;
	@CsvBindByName(column = "Over15 FHG HT Average")
	@Getter
	@Setter
	private Integer over15FHGHTAverage;
	@CsvBindByName(column = "Over05 2HG Average")
	@Getter
	@Setter
	private Integer over052HGAverage;
	@CsvBindByName(column = "Over15 2HG Average")
	@Getter
	@Setter
	private Integer over152HGAverage;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Average Corners")
	@Getter
	@Setter
	private Float averageCorners;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Average Cards")
	@Getter
	@Setter
	private Float averageCards;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Average Over 8dot5 Corners")
	@Getter
	@Setter
	private Float averageOver8dot5Corners;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Average Over 9dot5 Corners")
	@Getter
	@Setter
	private Float averageOver9dot5Corners;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Average Over 10dot5 Corners")
	@Getter
	@Setter
	private Float averageOver10dot5Corners;
	@CsvBindByName(column = "Match Status")
	@Getter
	@Setter
	private MatchStatus matchStatus;
	@CsvBindByName(column = "Result - Home Team Goals")
	@Getter
	@Setter
	private Integer resultHomeTeamGoals;
	@CsvBindByName(column = "Result - Away Team Goals")
	@Getter
	@Setter
	private Integer resultAwayTeamGoals;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Corners")
	@Getter
	@Setter
	private Float homeTeamCorners;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Corners")
	@Getter
	@Setter
	private Float awayTeamCorners;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Offsides")
	@Getter
	@Setter
	private Float homeTeamOffsides;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Offsides")
	@Getter
	@Setter
	private Float awayTeamOffsides;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Yellow Cards")
	@Getter
	@Setter
	private Float homeTeamYellowCards;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Yellow Cards")
	@Getter
	@Setter
	private Float awayTeamYellowCards;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Red Cards")
	@Getter
	@Setter
	private Float homeTeamRedCards;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Red Cards")
	@Getter
	@Setter
	private Float awayTeamRedCards;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Shots")
	@Getter
	@Setter
	private Float homeTeamShots;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Shots")
	@Getter
	@Setter
	private Float awayTeamShots;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Shots On Target")
	@Getter
	@Setter
	private Float homeTeamShotsOnTarget;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Shots On Target")
	@Getter
	@Setter
	private Float awayTeamShotsOnTarget;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Shots Off Target")
	@Getter
	@Setter
	private Float homeTeamShotsOffTarget;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Shots Off Target")
	@Getter
	@Setter
	private Float awayTeamShotsOffTarget;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Possession")
	@Getter
	@Setter
	private Float homeTeamPossession;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Possession")
	@Getter
	@Setter
	private Float awayTeamPossession;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Home_Win")
	@Getter
	@Setter
	private Float oddsHomeWin;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Draw")
	@Getter
	@Setter
	private Float oddsDraw;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Away_Win")
	@Getter
	@Setter
	private Float oddsAwayWin;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Over15")
	@Getter
	@Setter
	private Float oddsOver15;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Over25")
	@Getter
	@Setter
	private Float oddsOver25;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Over35")
	@Getter
	@Setter
	private Float oddsOver35;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Over45")
	@Getter
	@Setter
	private Float oddsOver45;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_BTTS_Yes")
	@Getter
	@Setter
	private Float oddsBTTS_Yes;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_BTTS_No")
	@Getter
	@Setter
	private Float oddsBTTS_No;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Pre-Match xG")
	@Getter
	@Setter
	private Float homeTeamPreMatchxG;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Pre-Match xG")
	@Getter
	@Setter
	private Float awayTeamPreMatchxG;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team xG")
	@Getter
	@Setter
	private Float homeTeamxG;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team xG")
	@Getter
	@Setter
	private Float awayTeamxG;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Under05")
	@Getter
	@Setter
	private Float oddsUnder05;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Under15")
	@Getter
	@Setter
	private Float oddsUnder15;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Under25")
	@Getter
	@Setter
	private Float oddsUnder25;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Under35")
	@Getter
	@Setter
	private Float oddsUnder35;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Under45")
	@Getter
	@Setter
	private Float oddsUnder45;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_DoubleChance_1x")
	@Getter
	@Setter
	private Float oddsDoubleChance1x;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_DoubleChance_12")
	@Getter
	@Setter
	private Float oddsDoubleChance12;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_DoubleChance_x2")
	@Getter
	@Setter
	private Float oddsDoubleChancex2;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_DrawNoBet_1")
	@Getter
	@Setter
	private Float oddsDrawNoBet1;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_DrawNoBet_2")
	@Getter
	@Setter
	private Float oddsDrawNoBet2;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Corners_Over75")
	@Getter
	@Setter
	private Float oddsCornersOver75;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Corners_Over85")
	@Getter
	@Setter
	private Float oddsCornersOver85;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Corners_Over95")
	@Getter
	@Setter
	private Float oddsCornersOver95;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Corners_Over105")
	@Getter
	@Setter
	private Float oddsCornersOver105;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Corners_Over115")
	@Getter
	@Setter
	private Float oddsCornersOver115;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Corners_Under75")
	@Getter
	@Setter
	private Float oddsCornersUnder75;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Corners_Under85")
	@Getter
	@Setter
	private Float oddsCornersUnder85;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Corners_Under95")
	@Getter
	@Setter
	private Float oddsCornersUnder95;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Corners_Under105")
	@Getter
	@Setter
	private Float oddsCornersUnder105;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Corners_Under115")
	@Getter
	@Setter
	private Float oddsCornersUnder115;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Under05 Average")
	@Getter
	@Setter
	private Float under05Average;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Under15 Average")
	@Getter
	@Setter
	private Float under15Average;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Under25 Average")
	@Getter
	@Setter
	private Float under25Average;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Under35 Average")
	@Getter
	@Setter
	private Float under35Average;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Under45 Average")
	@Getter
	@Setter
	private Float under45Average;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_1st_Half_Over05")
	@Getter
	@Setter
	private Float odds1stHalfOver05;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_1st_Half_Over15")
	@Getter
	@Setter
	private Float odds1stHalfOver15;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_1st_Half_Over25")
	@Getter
	@Setter
	private Float odds1stHalfOver25;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_1st_Half_Under05")
	@Getter
	@Setter
	private Float odds1stHalfUnder05;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_1st_Half_Under15")
	@Getter
	@Setter
	private Float odds1stHalfUnder15;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_1st_Half_Under25")
	@Getter
	@Setter
	private Float odds1stHalfUnder25;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_2nd_Half_Over05")
	@Getter
	@Setter
	private Float odds2ndHalfOver05;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_2nd_Half_Over15")
	@Getter
	@Setter
	private Float odds2ndHalfOver15;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_2nd_Half_Over25")
	@Getter
	@Setter
	private Float odds2ndHalfOver25;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_2nd_Half_Under05")
	@Getter
	@Setter
	private Float odds2ndHalfUnder05;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_2nd_Half_Under15")
	@Getter
	@Setter
	private Float odds2ndHalfUnder15;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_2nd_Half_Under25")
	@Getter
	@Setter
	private Float odds2ndHalfUnder25;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_1st_Half_BTTS_Yes")
	@Getter
	@Setter
	private Float odds1stHalfBTTS_Yes;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_1st_Half_BTTS_No")
	@Getter
	@Setter
	private Float odds1stHalfBTTS_No;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_2nd_Half_BTTS_Yes")
	@Getter
	@Setter
	private Float odds2ndHalfBTTS_Yes;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_2nd_Half_BTTS_No")
	@Getter
	@Setter
	private Float odds2ndHalfBTTS_No;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "1H BTTS Average")
	@Getter
	@Setter
	private Float firstHBTTSAverage;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Home_Team_Had_More_Corners")
	@Getter
	@Setter
	private Float oddsHomeTeamHadMoreCorners;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Both_Teams_Same_Corners")
	@Getter
	@Setter
	private Float oddsBothTeamsSameCorners;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Away_Team_Had_More_Corners")
	@Getter
	@Setter
	private Float oddsAwayTeamHadMoreCorners;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Overall Points Per Game (Pre-Match)")
	@Getter
	@Setter
	private Float homeTeamOverallPointsPerGamePreMatch;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Overall Points Per Game (Pre-Match)")
	@Getter
	@Setter
	private Float awayTeamOverallPointsPerGamePreMatch;
	@CsvBindByName(column = "Game Week")
	@Getter
	@Setter
	private Integer gameWeek;
	@CsvBindByName(column = "Match FootyStats URL")
	@Getter
	@Setter
	private String matchFootyStatsURL;

}
