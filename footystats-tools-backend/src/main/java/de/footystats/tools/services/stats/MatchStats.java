package de.footystats.tools.services.stats;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvDate;
import de.footystats.tools.services.csv.FloatConverter;
import de.footystats.tools.services.domain.Country;
import de.footystats.tools.services.domain.CountryCsvConverter;
import de.footystats.tools.services.prediction.heatmap.HeatMapped;
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
@Getter
@Setter
@Builder
@CompoundIndex(name = "unique", def = "{'dateUnix' : 1, 'country': 1, 'league': 1, 'homeTeam': 1, 'awayTeam': 1}")
@Document
public class MatchStats {

	@Id
	private ObjectId id;
	@Indexed
	@CsvBindByName(column = "date_unix")
	private Long dateUnix;
	@CsvDate("MMM d u - h:mma")
	@CsvBindByName(column = "date_GMT", locale = "en-in")
	private LocalDateTime dateGmt;
	@CsvCustomBindByName(column = "country", required = true, converter = CountryCsvConverter.class)
	private Country country;
	@CsvBindByName(column = "League")
	private String league;
	@CsvBindByName(column = "Home Team")
	private String homeTeam;
	@CsvBindByName(column = "Away Team")
	private String awayTeam;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Points Per Game (Pre-Match)")
	private Float homeTeamPointsPerGamePreMatch;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Points Per Game (Pre-Match)")
	private Float awayTeamPointsPerGamePreMatch;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Points Per Game (Current)")
	private Float homeTeamPointsPerGameCurrent;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Points Per Game (Current)")
	private Float awayTeamPointsPerGameCurrent;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Average Goals")
	private Float averageGoals;
	@HeatMapped(heatMappedProperty = "bttsAverage")
	@CsvBindByName(column = "BTTS Average")
	private Integer bTTSAverage;
	@CsvBindByName(column = "Over05 Average")
	private Integer over05Average;
	@CsvBindByName(column = "Over15 Average")
	private Integer over15Average;
	@CsvBindByName(column = "Over25 Average")
	private Integer over25Average;
	@CsvBindByName(column = "Over35 Average")
	private Integer over35Average;
	@CsvBindByName(column = "Over45 Average")
	private Integer over45Average;
	@CsvBindByName(column = "Over05 FHG HT Average")
	private Integer over05FHGHTAverage;
	@CsvBindByName(column = "Over15 FHG HT Average")
	private Integer over15FHGHTAverage;
	@CsvBindByName(column = "Over05 2HG Average")
	private Integer over052HGAverage;
	@CsvBindByName(column = "Over15 2HG Average")
	private Integer over152HGAverage;
	@HeatMapped(heatMappedProperty = "averageCorners")
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Average Corners")
	private Float averageCorners;
	@HeatMapped(heatMappedProperty = "averageCards")
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Average Cards")
	private Float averageCards;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Average Over 8dot5 Corners")
	private Float averageOver8dot5Corners;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Average Over 9dot5 Corners")
	private Float averageOver9dot5Corners;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Average Over 10dot5 Corners")
	private Float averageOver10dot5Corners;
	@CsvBindByName(column = "Match Status")
	private MatchStatus matchStatus;
	@CsvBindByName(column = "Result - Home Team Goals")
	private Integer resultHomeTeamGoals;
	@CsvBindByName(column = "Result - Away Team Goals")
	private Integer resultAwayTeamGoals;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Corners")
	private Float homeTeamCorners;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Corners")
	private Float awayTeamCorners;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Offsides")
	private Float homeTeamOffsides;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Offsides")
	private Float awayTeamOffsides;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Yellow Cards")
	private Float homeTeamYellowCards;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Yellow Cards")
	private Float awayTeamYellowCards;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Red Cards")
	private Float homeTeamRedCards;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Red Cards")
	private Float awayTeamRedCards;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Shots")
	private Float homeTeamShots;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Shots")
	private Float awayTeamShots;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Shots On Target")
	private Float homeTeamShotsOnTarget;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Shots On Target")
	private Float awayTeamShotsOnTarget;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Shots Off Target")
	private Float homeTeamShotsOffTarget;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Shots Off Target")
	private Float awayTeamShotsOffTarget;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Possession")
	private Float homeTeamPossession;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Possession")
	private Float awayTeamPossession;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Home_Win")
	private Float oddsHomeWin;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Draw")
	private Float oddsDraw;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Away_Win")
	private Float oddsAwayWin;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Over15")
	private Float oddsOver15;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Over25")
	private Float oddsOver25;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Over35")
	private Float oddsOver35;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Over45")
	private Float oddsOver45;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_BTTS_Yes")
	private Float oddsBTTS_Yes;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_BTTS_No")
	private Float oddsBTTS_No;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Pre-Match xG")
	private Float homeTeamPreMatchxG;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Pre-Match xG")
	private Float awayTeamPreMatchxG;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team xG")
	private Float homeTeamxG;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team xG")
	private Float awayTeamxG;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Under05")
	private Float oddsUnder05;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Under15")
	private Float oddsUnder15;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Under25")
	private Float oddsUnder25;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Under35")
	private Float oddsUnder35;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Under45")
	private Float oddsUnder45;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_DoubleChance_1x")
	private Float oddsDoubleChance1x;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_DoubleChance_12")
	private Float oddsDoubleChance12;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_DoubleChance_x2")
	private Float oddsDoubleChancex2;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_DrawNoBet_1")
	private Float oddsDrawNoBet1;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_DrawNoBet_2")
	private Float oddsDrawNoBet2;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Corners_Over75")
	private Float oddsCornersOver75;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Corners_Over85")
	private Float oddsCornersOver85;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Corners_Over95")
	private Float oddsCornersOver95;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Corners_Over105")
	private Float oddsCornersOver105;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Corners_Over115")
	private Float oddsCornersOver115;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Corners_Under75")
	private Float oddsCornersUnder75;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Corners_Under85")
	private Float oddsCornersUnder85;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Corners_Under95")
	private Float oddsCornersUnder95;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Corners_Under105")
	private Float oddsCornersUnder105;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Corners_Under115")
	private Float oddsCornersUnder115;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Under05 Average")
	private Float under05Average;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Under15 Average")
	private Float under15Average;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Under25 Average")
	private Float under25Average;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Under35 Average")
	private Float under35Average;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Under45 Average")
	private Float under45Average;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_1st_Half_Over05")
	private Float odds1stHalfOver05;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_1st_Half_Over15")
	private Float odds1stHalfOver15;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_1st_Half_Over25")
	private Float odds1stHalfOver25;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_1st_Half_Under05")
	private Float odds1stHalfUnder05;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_1st_Half_Under15")
	private Float odds1stHalfUnder15;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_1st_Half_Under25")
	private Float odds1stHalfUnder25;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_2nd_Half_Over05")
	private Float odds2ndHalfOver05;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_2nd_Half_Over15")
	private Float odds2ndHalfOver15;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_2nd_Half_Over25")
	private Float odds2ndHalfOver25;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_2nd_Half_Under05")
	private Float odds2ndHalfUnder05;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_2nd_Half_Under15")
	private Float odds2ndHalfUnder15;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_2nd_Half_Under25")
	private Float odds2ndHalfUnder25;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_1st_Half_BTTS_Yes")
	private Float odds1stHalfBTTS_Yes;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_1st_Half_BTTS_No")
	private Float odds1stHalfBTTS_No;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_2nd_Half_BTTS_Yes")
	private Float odds2ndHalfBTTS_Yes;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_2nd_Half_BTTS_No")
	private Float odds2ndHalfBTTS_No;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "1H BTTS Average")
	private Float firstHBTTSAverage;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Home_Team_Had_More_Corners")
	private Float oddsHomeTeamHadMoreCorners;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Both_Teams_Same_Corners")
	private Float oddsBothTeamsSameCorners;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Odds_Away_Team_Had_More_Corners")
	private Float oddsAwayTeamHadMoreCorners;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Home Team Overall Points Per Game (Pre-Match)")
	private Float homeTeamOverallPointsPerGamePreMatch;
	@CsvCustomBindByName(converter = FloatConverter.class, column = "Away Team Overall Points Per Game (Pre-Match)")
	private Float awayTeamOverallPointsPerGamePreMatch;
	@CsvBindByName(column = "Game Week")
	private Integer gameWeek;
	@CsvBindByName(column = "Match FootyStats URL")
	private String matchFootyStatsURL;

	public String matchStatsShort() {
		return getCountry().getCountryNameByFootystats() + "-" + getLeague() + "-" + getHomeTeam() + "-" + getAwayTeam();
	}
}
