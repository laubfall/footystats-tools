package de.ludwig.footystats.tools.backend.services.stats;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import de.ludwig.footystats.tools.backend.services.csv.DoubleConverter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Regex to match underscores in properties: _(\w{1})(.*);$
 * Replace the matches with this: \U$1\E$2;
 *
 * ...only if you wonder how this file was generated...
 */
@Document
@CompoundIndexes({
	@CompoundIndex(name = "unique", def = "{'teamName' : 1, 'commonName': 1, 'season': 1, 'country': 1}")
})
public class Team2Stats {

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
	@CsvBindByName(column = "average_attendance_overall")
	@Getter
	@Setter
	private Integer averageAttendanceOverall;
	@CsvBindByName(column = "average_attendance_home")
	@Getter
	@Setter
	private Integer averageAttendanceHome;
	@CsvBindByName(column = "average_attendance_away")
	@Getter
	@Setter
	private Integer averageAttendanceAway;
	@CsvBindByName(column = "minutes_per_goal_scored_overall")
	@Getter
	@Setter
	private Integer minutesPerGoalScoredOverall;
	@CsvBindByName(column = "minutes_per_goal_scored_home")
	@Getter
	@Setter
	private Integer minutesPerGoalScoredHome;
	@CsvBindByName(column = "minutes_per_goal_scored_away")
	@Getter
	@Setter
	private Integer minutesPerGoalScoredAway;
	@CsvBindByName(column = "minutes_per_goal_conceded_overall")
	@Getter
	@Setter
	private Integer minutesPerGoalConcededOverall;
	@CsvBindByName(column = "minutes_per_goal_conceded_home")
	@Getter
	@Setter
	private Integer minutesPerGoalConcededHome;
	@CsvBindByName(column = "minutes_per_goal_conceded_away")
	@Getter
	@Setter
	private Integer minutesPerGoalConcededAway;
	@CsvBindByName(column = "highest_goals_scored_home")
	@Getter
	@Setter
	private Integer highestGoalsScoredHome;
	@CsvBindByName(column = "highest_goals_scored_away")
	@Getter
	@Setter
	private Integer highestGoalsScoredAway;
	@CsvBindByName(column = "highest_goals_conceded_home")
	@Getter
	@Setter
	private Integer highestGoalsConcededHome;
	@CsvBindByName(column = "highest_goals_conceded_away")
	@Getter
	@Setter
	private Integer highestGoalsConcededAway;
	@CsvBindByName(column = "half_time_points_overall")
	@Getter
	@Setter
	private Integer halfTimePointsOverall;
	@CsvBindByName(column = "half_time_points_home")
	@Getter
	@Setter
	private Integer halfTimePointsHome;
	@CsvBindByName(column = "half_time_points_away")
	@Getter
	@Setter
	private Integer halfTimePointsAway;
	@CsvBindByName(column = "corners_recorded_matches_num_overall")
	@Getter
	@Setter
	private Integer cornersRecordedMatchesNumOverall;
	@CsvBindByName(column = "corners_recorded_matches_num_home")
	@Getter
	@Setter
	private Integer cornersRecordedMatchesNumHome;
	@CsvBindByName(column = "cornersRecorded_matches_away")
	@Getter
	@Setter
	private Integer cornersRecordedMatchesAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "corners_total_per_match_overall")
	@Getter
	@Setter
	private Double cornersTotalPerMatchOverall;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "corners_total_per_match_home")
	@Getter
	@Setter
	private Double cornersTotalPerMatchHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "corners_total_per_match_away")
	@Getter
	@Setter
	private Double cornersTotalPerMatchAway;
	@CsvBindByName(column = "corners_against_overall")
	@Getter
	@Setter
	private Integer cornersAgainstOverall;
	@CsvBindByName(column = "corners_against_home")
	@Getter
	@Setter
	private Integer cornersAgainstHome;
	@CsvBindByName(column = "corners_against_away")
	@Getter
	@Setter
	private Integer cornersAgainstAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "corners_against_per_match_overall")
	@Getter
	@Setter
	private Double cornersAgainstPerMatchOverall;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "corners_against_per_match_home")
	@Getter
	@Setter
	private Double cornersAgainstPerMatchHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "corners_against_per_match_away")
	@Getter
	@Setter
	private Double cornersAgainstPerMatchAway;
	@CsvBindByName(column = "over65_corners_num_overall")
	@Getter
	@Setter
	private Integer over65CornersNumOverall;
	@CsvBindByName(column = "over75_corners_num_overall")
	@Getter
	@Setter
	private Integer over75CornersNumOverall;
	@CsvBindByName(column = "over85_corners_num_overall")
	@Getter
	@Setter
	private Integer over85CornersNumOverall;
	@CsvBindByName(column = "over95_corners_num_overall")
	@Getter
	@Setter
	private Integer over95CornersNumOverall;
	@CsvBindByName(column = "over105_corners_num_overall")
	@Getter
	@Setter
	private Integer over105CornersNumOverall;
	@CsvBindByName(column = "over115_corners_num_overall")
	@Getter
	@Setter
	private Integer over115CornersNumOverall;
	@CsvBindByName(column = "over125_corners_num_overall")
	@Getter
	@Setter
	private Integer over125CornersNumOverall;
	@CsvBindByName(column = "over135_corners_num_overall")
	@Getter
	@Setter
	private Integer over135CornersNumOverall;
	@CsvBindByName(column = "over145_corners_num_overall")
	@Getter
	@Setter
	private Integer over145CornersNumOverall;
	@CsvBindByName(column = "over65_corners_num_home")
	@Getter
	@Setter
	private Integer over65CornersNumHome;
	@CsvBindByName(column = "over75_corners_num_home")
	@Getter
	@Setter
	private Integer over75CornersNumHome;
	@CsvBindByName(column = "over85_corners_num_home")
	@Getter
	@Setter
	private Integer over85CornersNumHome;
	@CsvBindByName(column = "over95_corners_num_home")
	@Getter
	@Setter
	private Integer over95CornersNumHome;
	@CsvBindByName(column = "over105_corners_num_home")
	@Getter
	@Setter
	private Integer over105CornersNumHome;
	@CsvBindByName(column = "over115_corners_num_home")
	@Getter
	@Setter
	private Integer over115CornersNumHome;
	@CsvBindByName(column = "over125_corners_num_home")
	@Getter
	@Setter
	private Integer over125CornersNumHome;
	@CsvBindByName(column = "over135_corners_num_home")
	@Getter
	@Setter
	private Integer over135CornersNumHome;
	@CsvBindByName(column = "over145_corners_num_home")
	@Getter
	@Setter
	private Integer over145CornersNumHome;
	@CsvBindByName(column = "over65_corners_percentage_home")
	@Getter
	@Setter
	private Integer over65CornersPercentageHome;
	@CsvBindByName(column = "over75_corners_percentage_home")
	@Getter
	@Setter
	private Integer over75CornersPercentageHome;
	@CsvBindByName(column = "over85_corners_percentage_home")
	@Getter
	@Setter
	private Integer over85CornersPercentageHome;
	@CsvBindByName(column = "over95_corners_percentage_home")
	@Getter
	@Setter
	private Integer over95CornersPercentageHome;
	@CsvBindByName(column = "over105_corners_percentage_home")
	@Getter
	@Setter
	private Integer over105CornersPercentageHome;
	@CsvBindByName(column = "over115_corners_percentage_home")
	@Getter
	@Setter
	private Integer over115CornersPercentageHome;
	@CsvBindByName(column = "over125_corners_percentage_home")
	@Getter
	@Setter
	private Integer over125CornersPercentageHome;
	@CsvBindByName(column = "over135_corners_percentage_home")
	@Getter
	@Setter
	private Integer over135CornersPercentageHome;
	@CsvBindByName(column = "over145_corners_percentage_home")
	@Getter
	@Setter
	private Integer over145CornersPercentageHome;
	@CsvBindByName(column = "over65_corners_num_away")
	@Getter
	@Setter
	private Integer over65CornersNumAway;
	@CsvBindByName(column = "over75_corners_num_away")
	@Getter
	@Setter
	private Integer over75CornersNumAway;
	@CsvBindByName(column = "over85_corners_num_away")
	@Getter
	@Setter
	private Integer over85CornersNumAway;
	@CsvBindByName(column = "over95_corners_num_away")
	@Getter
	@Setter
	private Integer over95CornersNumAway;
	@CsvBindByName(column = "over105_corners_num_away")
	@Getter
	@Setter
	private Integer over105CornersNumAway;
	@CsvBindByName(column = "over115_corners_num_away")
	@Getter
	@Setter
	private Integer over115CornersNumAway;
	@CsvBindByName(column = "over125_corners_num_away")
	@Getter
	@Setter
	private Integer over125CornersNumAway;
	@CsvBindByName(column = "over135_corners_num_away")
	@Getter
	@Setter
	private Integer over135CornersNumAway;
	@CsvBindByName(column = "over145_corners_num_away")
	@Getter
	@Setter
	private Integer over145CornersNumAway;
	@CsvBindByName(column = "over65_corners_percentage_away")
	@Getter
	@Setter
	private Integer over65CornersPercentageAway;
	@CsvBindByName(column = "over75_corners_percentage_away")
	@Getter
	@Setter
	private Integer over75CornersPercentageAway;
	@CsvBindByName(column = "over85_corners_percentage_away")
	@Getter
	@Setter
	private Integer over85CornersPercentageAway;
	@CsvBindByName(column = "over95_corners_percentage_away")
	@Getter
	@Setter
	private Integer over95CornersPercentageAway;
	@CsvBindByName(column = "over105_corners_percentage_away")
	@Getter
	@Setter
	private Integer over105CornersPercentageAway;
	@CsvBindByName(column = "over115_corners_percentage_away")
	@Getter
	@Setter
	private Integer over115CornersPercentageAway;
	@CsvBindByName(column = "over125_corners_percentage_away")
	@Getter
	@Setter
	private Integer over125CornersPercentageAway;
	@CsvBindByName(column = "over135_corners_percentage_away")
	@Getter
	@Setter
	private Integer over135CornersPercentageAway;
	@CsvBindByName(column = "over145_corners_percentage_away")
	@Getter
	@Setter
	private Integer over145CornersPercentageAway;
	@CsvBindByName(column = "over25_corners_for_overall")
	@Getter
	@Setter
	private Integer over25CornersForOverall;
	@CsvBindByName(column = "over35_corners_for_overall")
	@Getter
	@Setter
	private Integer over35CornersForOverall;
	@CsvBindByName(column = "over45_corners_for_overall")
	@Getter
	@Setter
	private Integer over45CornersForOverall;
	@CsvBindByName(column = "over55_corners_for_overall")
	@Getter
	@Setter
	private Integer over55CornersForOverall;
	@CsvBindByName(column = "over65_corners_for_overall")
	@Getter
	@Setter
	private Integer over65CornersForOverall;
	@CsvBindByName(column = "over75_corners_for_overall")
	@Getter
	@Setter
	private Integer over75CornersForOverall;
	@CsvBindByName(column = "over85_corners_for_overall")
	@Getter
	@Setter
	private Integer over85CornersForOverall;
	@CsvBindByName(column = "over25_corners_for_percentage_overall")
	@Getter
	@Setter
	private Integer over25CornersForPercentageOverall;
	@CsvBindByName(column = "over35_corners_for_percentage_overall")
	@Getter
	@Setter
	private Integer over35CornersForPercentageOverall;
	@CsvBindByName(column = "over45_corners_for_percentage_overall")
	@Getter
	@Setter
	private Integer over45CornersForPercentageOverall;
	@CsvBindByName(column = "over55_corners_for_percentage_overall")
	@Getter
	@Setter
	private Integer over55CornersForPercentageOverall;
	@CsvBindByName(column = "over65_corners_for_percentage_overall")
	@Getter
	@Setter
	private Integer over65CornersForPercentageOverall;
	@CsvBindByName(column = "over75_corners_for_percentage_overall")
	@Getter
	@Setter
	private Integer over75CornersForPercentageOverall;
	@CsvBindByName(column = "over85_corners_for_percentage_overall")
	@Getter
	@Setter
	private Integer over85CornersForPercentageOverall;
	@CsvBindByName(column = "over25_corners_for_home")
	@Getter
	@Setter
	private Integer over25CornersForHome;
	@CsvBindByName(column = "over35_corners_for_home")
	@Getter
	@Setter
	private Integer over35CornersForHome;
	@CsvBindByName(column = "over45_corners_for_home")
	@Getter
	@Setter
	private Integer over45CornersForHome;
	@CsvBindByName(column = "over55_corners_for_home")
	@Getter
	@Setter
	private Integer over55CornersForHome;
	@CsvBindByName(column = "over65_corners_for_home")
	@Getter
	@Setter
	private Integer over65CornersForHome;
	@CsvBindByName(column = "over75_corners_for_home")
	@Getter
	@Setter
	private Integer over75CornersForHome;
	@CsvBindByName(column = "over85_corners_for_home")
	@Getter
	@Setter
	private Integer over85CornersForHome;
	@CsvBindByName(column = "over25_corners_for_percentage_home")
	@Getter
	@Setter
	private Integer over25CornersForPercentageHome;
	@CsvBindByName(column = "over35_corners_for_percentage_home")
	@Getter
	@Setter
	private Integer over35CornersForPercentageHome;
	@CsvBindByName(column = "over45_corners_for_percentage_home")
	@Getter
	@Setter
	private Integer over45CornersForPercentageHome;
	@CsvBindByName(column = "over55_corners_for_percentage_home")
	@Getter
	@Setter
	private Integer over55CornersForPercentageHome;
	@CsvBindByName(column = "over65_corners_for_percentage_home")
	@Getter
	@Setter
	private Integer over65CornersForPercentageHome;
	@CsvBindByName(column = "over75_corners_for_percentage_home")
	@Getter
	@Setter
	private Integer over75CornersForPercentageHome;
	@CsvBindByName(column = "over85_corners_for_percentage_home")
	@Getter
	@Setter
	private Integer over85CornersForPercentageHome;
	@CsvBindByName(column = "over25_corners_for_away")
	@Getter
	@Setter
	private Integer over25CornersForAway;
	@CsvBindByName(column = "over35_corners_for_away")
	@Getter
	@Setter
	private Integer over35CornersForAway;
	@CsvBindByName(column = "over45_corners_for_away")
	@Getter
	@Setter
	private Integer over45CornersForAway;
	@CsvBindByName(column = "over55_corners_for_away")
	@Getter
	@Setter
	private Integer over55CornersForAway;
	@CsvBindByName(column = "over65_corners_for_away")
	@Getter
	@Setter
	private Integer over65CornersForAway;
	@CsvBindByName(column = "over75_corners_for_away")
	@Getter
	@Setter
	private Integer over75CornersForAway;
	@CsvBindByName(column = "over85_corners_for_away")
	@Getter
	@Setter
	private Integer over85CornersForAway;
	@CsvBindByName(column = "over25_corners_for_percentage_away")
	@Getter
	@Setter
	private Integer over25CornersForPercentageAway;
	@CsvBindByName(column = "over35_corners_for_percentage_away")
	@Getter
	@Setter
	private Integer over35CornersForPercentageAway;
	@CsvBindByName(column = "over45_corners_for_percentage_away")
	@Getter
	@Setter
	private Integer over45CornersForPercentageAway;
	@CsvBindByName(column = "over55_corners_for_percentage_away")
	@Getter
	@Setter
	private Integer over55CornersForPercentageAway;
	@CsvBindByName(column = "over65_corners_for_percentage_away")
	@Getter
	@Setter
	private Integer over65CornersForPercentageAway;
	@CsvBindByName(column = "over75_corners_for_percentage_away")
	@Getter
	@Setter
	private Integer over75CornersForPercentageAway;
	@CsvBindByName(column = "over85_corners_for_percentage_away")
	@Getter
	@Setter
	private Integer over85CornersForPercentageAway;
	@CsvBindByName(column = "over25_corners_against_overall")
	@Getter
	@Setter
	private Integer over25CornersAgainstOverall;
	@CsvBindByName(column = "over35_corners_against_overall")
	@Getter
	@Setter
	private Integer over35CornersAgainstOverall;
	@CsvBindByName(column = "over45_corners_against_overall")
	@Getter
	@Setter
	private Integer over45CornersAgainstOverall;
	@CsvBindByName(column = "over55_corners_against_overall")
	@Getter
	@Setter
	private Integer over55CornersAgainstOverall;
	@CsvBindByName(column = "over65_corners_against_overall")
	@Getter
	@Setter
	private Integer over65CornersAgainstOverall;
	@CsvBindByName(column = "over75_corners_against_overall")
	@Getter
	@Setter
	private Integer over75CornersAgainstOverall;
	@CsvBindByName(column = "over85_corners_against_overall")
	@Getter
	@Setter
	private Integer over85CornersAgainstOverall;
	@CsvBindByName(column = "over25_corners_against_percentage_overall")
	@Getter
	@Setter
	private Integer over25CornersAgainstPercentageOverall;
	@CsvBindByName(column = "over35_corners_against_percentage_overall")
	@Getter
	@Setter
	private Integer over35CornersAgainstPercentageOverall;
	@CsvBindByName(column = "over45_corners_against_percentage_overall")
	@Getter
	@Setter
	private Integer over45CornersAgainstPercentageOverall;
	@CsvBindByName(column = "over55_corners_against_percentage_overall")
	@Getter
	@Setter
	private Integer over55CornersAgainstPercentageOverall;
	@CsvBindByName(column = "over65_corners_against_percentage_overall")
	@Getter
	@Setter
	private Integer over65CornersAgainstPercentageOverall;
	@CsvBindByName(column = "over75_corners_against_percentage_overall")
	@Getter
	@Setter
	private Integer over75CornersAgainstPercentageOverall;
	@CsvBindByName(column = "over85_corners_against_percentage_overall")
	@Getter
	@Setter
	private Integer over85CornersAgainstPercentageOverall;
	@CsvBindByName(column = "over25_corners_against_home")
	@Getter
	@Setter
	private Integer over25CornersAgainstHome;
	@CsvBindByName(column = "over35_corners_against_home")
	@Getter
	@Setter
	private Integer over35CornersAgainstHome;
	@CsvBindByName(column = "over45_corners_against_home")
	@Getter
	@Setter
	private Integer over45CornersAgainstHome;
	@CsvBindByName(column = "over55_corners_against_home")
	@Getter
	@Setter
	private Integer over55CornersAgainstHome;
	@CsvBindByName(column = "over65_corners_against_home")
	@Getter
	@Setter
	private Integer over65CornersAgainstHome;
	@CsvBindByName(column = "over75_corners_against_home")
	@Getter
	@Setter
	private Integer over75CornersAgainstHome;
	@CsvBindByName(column = "over85_corners_against_home")
	@Getter
	@Setter
	private Integer over85CornersAgainstHome;
	@CsvBindByName(column = "over25_corners_against_percentage_home")
	@Getter
	@Setter
	private Integer over25CornersAgainstPercentageHome;
	@CsvBindByName(column = "over35_corners_against_percentage_home")
	@Getter
	@Setter
	private Integer over35CornersAgainstPercentageHome;
	@CsvBindByName(column = "over45_corners_against_percentage_home")
	@Getter
	@Setter
	private Integer over45CornersAgainstPercentageHome;
	@CsvBindByName(column = "over55_corners_against_percentage_home")
	@Getter
	@Setter
	private Integer over55CornersAgainstPercentageHome;
	@CsvBindByName(column = "over65_corners_against_percentage_home")
	@Getter
	@Setter
	private Integer over65CornersAgainstPercentageHome;
	@CsvBindByName(column = "over75_corners_against_percentage_home")
	@Getter
	@Setter
	private Integer over75CornersAgainstPercentageHome;
	@CsvBindByName(column = "over85_corners_against_percentage_home")
	@Getter
	@Setter
	private Integer over85CornersAgainstPercentageHome;
	@CsvBindByName(column = "over25_corners_against_away")
	@Getter
	@Setter
	private Integer over25CornersAgainstAway;
	@CsvBindByName(column = "over35_corners_against_away")
	@Getter
	@Setter
	private Integer over35CornersAgainstAway;
	@CsvBindByName(column = "over45_corners_against_away")
	@Getter
	@Setter
	private Integer over45CornersAgainstAway;
	@CsvBindByName(column = "over55_corners_against_away")
	@Getter
	@Setter
	private Integer over55CornersAgainstAway;
	@CsvBindByName(column = "over65_corners_against_away")
	@Getter
	@Setter
	private Integer over65CornersAgainstAway;
	@CsvBindByName(column = "over75_corners_against_away")
	@Getter
	@Setter
	private Integer over75CornersAgainstAway;
	@CsvBindByName(column = "over85_corners_against_away")
	@Getter
	@Setter
	private Integer over85CornersAgainstAway;
	@CsvBindByName(column = "over25_corners_against_percentage_away")
	@Getter
	@Setter
	private Integer over25CornersAgainstPercentageAway;
	@CsvBindByName(column = "over35_corners_against_percentage_away")
	@Getter
	@Setter
	private Integer over35CornersAgainstPercentageAway;
	@CsvBindByName(column = "over45_corners_against_percentage_away")
	@Getter
	@Setter
	private Integer over45CornersAgainstPercentageAway;
	@CsvBindByName(column = "over55_corners_against_percentage_away")
	@Getter
	@Setter
	private Integer over55CornersAgainstPercentageAway;
	@CsvBindByName(column = "over65_corners_against_percentage_away")
	@Getter
	@Setter
	private Integer over65CornersAgainstPercentageAway;
	@CsvBindByName(column = "over75_corners_against_percentage_away")
	@Getter
	@Setter
	private Integer over75CornersAgainstPercentageAway;
	@CsvBindByName(column = "over85_corners_against_percentage_away")
	@Getter
	@Setter
	private Integer over85CornersAgainstPercentageAway;
	@CsvBindByName(column = "cornerTimingRecorded_matches_overall")
	@Getter
	@Setter
	private Integer cornerTimingRecordedMatchesOverall;
	@CsvBindByName(column = "cornerTimingRecorded_matches_home")
	@Getter
	@Setter
	private Integer cornerTimingRecordedMatchesHome;
	@CsvBindByName(column = "cornerTimingRecorded_matches_away")
	@Getter
	@Setter
	private Integer cornerTimingRecordedMatchesAway;
	@CsvBindByName(column = "corners_total_fh_overall")
	@Getter
	@Setter
	private Integer cornersTotalFhOverall;
	@CsvBindByName(column = "corners_total_fh_home")
	@Getter
	@Setter
	private Integer cornersTotalFhHome;
	@CsvBindByName(column = "corners_total_fh_away")
	@Getter
	@Setter
	private Integer cornersTotalFhAway;
	@CsvBindByName(column = "corners_total_2h_overall")
	@Getter
	@Setter
	private Integer cornersTotal2hOverall;
	@CsvBindByName(column = "corners_total_2h_home")
	@Getter
	@Setter
	private Integer cornersTotal2hHome;
	@CsvBindByName(column = "corners_total_2h_away")
	@Getter
	@Setter
	private Integer cornersTotal2hAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "corners_total_per_match_fh_overall")
	@Getter
	@Setter
	private Double cornersTotalPerMatchFhOverall;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "corners_total_per_match_fh_home")
	@Getter
	@Setter
	private Double cornersTotalPerMatchFhHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "corners_total_per_match_fh_away")
	@Getter
	@Setter
	private Double cornersTotalPerMatchFhAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "corners_total_per_match_2h_overall")
	@Getter
	@Setter
	private Double cornersTotalPerMatch2hOverall;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "corners_total_per_match_2h_home")
	@Getter
	@Setter
	private Double cornersTotalPerMatch2hHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "corners_total_per_match_2h_away")
	@Getter
	@Setter
	private Double cornersTotalPerMatch2hAway;
	@CsvBindByName(column = "corners_total_fh_over4_overall")
	@Getter
	@Setter
	private Integer cornersTotalFhOver4Overall;
	@CsvBindByName(column = "corners_total_fh_over5_overall")
	@Getter
	@Setter
	private Integer cornersTotalFhOver5Overall;
	@CsvBindByName(column = "corners_total_fh_over6_overall")
	@Getter
	@Setter
	private Integer cornersTotalFhOver6Overall;
	@CsvBindByName(column = "corners_total_2h_over4_overall")
	@Getter
	@Setter
	private Integer cornersTotal2hOver4Overall;
	@CsvBindByName(column = "corners_total_2h_over5_overall")
	@Getter
	@Setter
	private Integer cornersTotal2hOver5Overall;
	@CsvBindByName(column = "corners_total_2h_over6_overall")
	@Getter
	@Setter
	private Integer cornersTotal2hOver6Overall;
	@CsvBindByName(column = "over05_cards_total_overall")
	@Getter
	@Setter
	private Integer over05CardsTotalOverall;
	@CsvBindByName(column = "over15_cards_total_overall")
	@Getter
	@Setter
	private Integer over15CardsTotalOverall;
	@CsvBindByName(column = "over25_cards_total_overall")
	@Getter
	@Setter
	private Integer over25CardsTotalOverall;
	@CsvBindByName(column = "over35_cards_total_overall")
	@Getter
	@Setter
	private Integer over35CardsTotalOverall;
	@CsvBindByName(column = "over45_cards_total_overall")
	@Getter
	@Setter
	private Integer over45CardsTotalOverall;
	@CsvBindByName(column = "over55_cards_total_overall")
	@Getter
	@Setter
	private Integer over55CardsTotalOverall;
	@CsvBindByName(column = "over65_cards_total_overall")
	@Getter
	@Setter
	private Integer over65CardsTotalOverall;
	@CsvBindByName(column = "over75_cards_total_overall")
	@Getter
	@Setter
	private Integer over75CardsTotalOverall;
	@CsvBindByName(column = "over85_cards_total_overall")
	@Getter
	@Setter
	private Integer over85CardsTotalOverall;
	@CsvBindByName(column = "over05_cards_total_percentage_overall")
	@Getter
	@Setter
	private Integer over05CardsTotalPercentageOverall;
	@CsvBindByName(column = "over15_cards_total_percentage_overall")
	@Getter
	@Setter
	private Integer over15CardsTotalPercentageOverall;
	@CsvBindByName(column = "over25_cards_total_percentage_overall")
	@Getter
	@Setter
	private Integer over25CardsTotalPercentageOverall;
	@CsvBindByName(column = "over35_cards_total_percentage_overall")
	@Getter
	@Setter
	private Integer over35CardsTotalPercentageOverall;
	@CsvBindByName(column = "over45_cards_total_percentage_overall")
	@Getter
	@Setter
	private Integer over45CardsTotalPercentageOverall;
	@CsvBindByName(column = "over55_cards_total_percentage_overall")
	@Getter
	@Setter
	private Integer over55CardsTotalPercentageOverall;
	@CsvBindByName(column = "over65_cards_total_percentage_overall")
	@Getter
	@Setter
	private Integer over65CardsTotalPercentageOverall;
	@CsvBindByName(column = "over75_cards_total_percentage_overall")
	@Getter
	@Setter
	private Integer over75CardsTotalPercentageOverall;
	@CsvBindByName(column = "over85_cards_total_percentage_overall")
	@Getter
	@Setter
	private Integer over85CardsTotalPercentageOverall;
	@CsvBindByName(column = "over05_cards_total_home")
	@Getter
	@Setter
	private Integer over05CardsTotalHome;
	@CsvBindByName(column = "over15_cards_total_home")
	@Getter
	@Setter
	private Integer over15CardsTotalHome;
	@CsvBindByName(column = "over25_cards_total_home")
	@Getter
	@Setter
	private Integer over25CardsTotalHome;
	@CsvBindByName(column = "over35_cards_total_home")
	@Getter
	@Setter
	private Integer over35CardsTotalHome;
	@CsvBindByName(column = "over45_cards_total_home")
	@Getter
	@Setter
	private Integer over45CardsTotalHome;
	@CsvBindByName(column = "over55_cards_total_home")
	@Getter
	@Setter
	private Integer over55CardsTotalHome;
	@CsvBindByName(column = "over65_cards_total_home")
	@Getter
	@Setter
	private Integer over65CardsTotalHome;
	@CsvBindByName(column = "over75_cards_total_home")
	@Getter
	@Setter
	private Integer over75CardsTotalHome;
	@CsvBindByName(column = "over85_cards_total_home")
	@Getter
	@Setter
	private Integer over85CardsTotalHome;
	@CsvBindByName(column = "over05_cards_total_percentage_home")
	@Getter
	@Setter
	private Integer over05CardsTotalPercentageHome;
	@CsvBindByName(column = "over15_cards_total_percentage_home")
	@Getter
	@Setter
	private Integer over15CardsTotalPercentageHome;
	@CsvBindByName(column = "over25_cards_total_percentage_home")
	@Getter
	@Setter
	private Integer over25CardsTotalPercentageHome;
	@CsvBindByName(column = "over35_cards_total_percentage_home")
	@Getter
	@Setter
	private Integer over35CardsTotalPercentageHome;
	@CsvBindByName(column = "over45_cards_total_percentage_home")
	@Getter
	@Setter
	private Integer over45CardsTotalPercentageHome;
	@CsvBindByName(column = "over55_cards_total_percentage_home")
	@Getter
	@Setter
	private Integer over55CardsTotalPercentageHome;
	@CsvBindByName(column = "over65_cards_total_percentage_home")
	@Getter
	@Setter
	private Integer over65CardsTotalPercentageHome;
	@CsvBindByName(column = "over75_cards_total_percentage_home")
	@Getter
	@Setter
	private Integer over75CardsTotalPercentageHome;
	@CsvBindByName(column = "over85_cards_total_percentage_home")
	@Getter
	@Setter
	private Integer over85CardsTotalPercentageHome;
	@CsvBindByName(column = "over05_cards_total_away")
	@Getter
	@Setter
	private Integer over05CardsTotalAway;
	@CsvBindByName(column = "over15_cards_total_away")
	@Getter
	@Setter
	private Integer over15CardsTotalAway;
	@CsvBindByName(column = "over25_cards_total_away")
	@Getter
	@Setter
	private Integer over25CardsTotalAway;
	@CsvBindByName(column = "over35_cards_total_away")
	@Getter
	@Setter
	private Integer over35CardsTotalAway;
	@CsvBindByName(column = "over45_cards_total_away")
	@Getter
	@Setter
	private Integer over45CardsTotalAway;
	@CsvBindByName(column = "over55_cards_total_away")
	@Getter
	@Setter
	private Integer over55CardsTotalAway;
	@CsvBindByName(column = "over65_cards_total_away")
	@Getter
	@Setter
	private Integer over65CardsTotalAway;
	@CsvBindByName(column = "over75_cards_total_away")
	@Getter
	@Setter
	private Integer over75CardsTotalAway;
	@CsvBindByName(column = "over85_cards_total_away")
	@Getter
	@Setter
	private Integer over85CardsTotalAway;
	@CsvBindByName(column = "over05_cards_total_percentage_away")
	@Getter
	@Setter
	private Integer over05CardsTotalPercentageAway;
	@CsvBindByName(column = "over15_cards_total_percentage_away")
	@Getter
	@Setter
	private Integer over15CardsTotalPercentageAway;
	@CsvBindByName(column = "over25_cards_total_percentage_away")
	@Getter
	@Setter
	private Integer over25CardsTotalPercentageAway;
	@CsvBindByName(column = "over35_cards_total_percentage_away")
	@Getter
	@Setter
	private Integer over35CardsTotalPercentageAway;
	@CsvBindByName(column = "over45_cards_total_percentage_away")
	@Getter
	@Setter
	private Integer over45CardsTotalPercentageAway;
	@CsvBindByName(column = "over55_cards_total_percentage_away")
	@Getter
	@Setter
	private Integer over55CardsTotalPercentageAway;
	@CsvBindByName(column = "over65_cards_total_percentage_away")
	@Getter
	@Setter
	private Integer over65CardsTotalPercentageAway;
	@CsvBindByName(column = "over75_cards_total_percentage_away")
	@Getter
	@Setter
	private Integer over75CardsTotalPercentageAway;
	@CsvBindByName(column = "over85_cards_total_percentage_away")
	@Getter
	@Setter
	private Integer over85CardsTotalPercentageAway;
	@CsvBindByName(column = "over05_cards_for_overall")
	@Getter
	@Setter
	private Integer over05CardsForOverall;
	@CsvBindByName(column = "over15_cards_for_overall")
	@Getter
	@Setter
	private Integer over15CardsForOverall;
	@CsvBindByName(column = "over25_cards_for_overall")
	@Getter
	@Setter
	private Integer over25CardsForOverall;
	@CsvBindByName(column = "over35_cards_for_overall")
	@Getter
	@Setter
	private Integer over35CardsForOverall;
	@CsvBindByName(column = "over45_cards_for_overall")
	@Getter
	@Setter
	private Integer over45CardsForOverall;
	@CsvBindByName(column = "over55_cards_for_overall")
	@Getter
	@Setter
	private Integer over55CardsForOverall;
	@CsvBindByName(column = "over65_cards_for_overall")
	@Getter
	@Setter
	private Integer over65CardsForOverall;
	@CsvBindByName(column = "over05_cards_for_percentage_overall")
	@Getter
	@Setter
	private Integer over05CardsForPercentageOverall;
	@CsvBindByName(column = "over15_cards_for_percentage_overall")
	@Getter
	@Setter
	private Integer over15CardsForPercentageOverall;
	@CsvBindByName(column = "over25_cards_for_percentage_overall")
	@Getter
	@Setter
	private Integer over25CardsForPercentageOverall;
	@CsvBindByName(column = "over35_cards_for_percentage_overall")
	@Getter
	@Setter
	private Integer over35CardsForPercentageOverall;
	@CsvBindByName(column = "over45_cards_for_percentage_overall")
	@Getter
	@Setter
	private Integer over45CardsForPercentageOverall;
	@CsvBindByName(column = "over55_cards_for_percentage_overall")
	@Getter
	@Setter
	private Integer over55CardsForPercentageOverall;
	@CsvBindByName(column = "over65_cards_for_percentage_overall")
	@Getter
	@Setter
	private Integer over65CardsForPercentageOverall;
	@CsvBindByName(column = "over05_cards_for_home")
	@Getter
	@Setter
	private Integer over05CardsForHome;
	@CsvBindByName(column = "over15_cards_for_home")
	@Getter
	@Setter
	private Integer over15CardsForHome;
	@CsvBindByName(column = "over25_cards_for_home")
	@Getter
	@Setter
	private Integer over25CardsForHome;
	@CsvBindByName(column = "over35_cards_for_home")
	@Getter
	@Setter
	private Integer over35CardsForHome;
	@CsvBindByName(column = "over45_cards_for_home")
	@Getter
	@Setter
	private Integer over45CardsForHome;
	@CsvBindByName(column = "over55_cards_for_home")
	@Getter
	@Setter
	private Integer over55CardsForHome;
	@CsvBindByName(column = "over65_cards_for_home")
	@Getter
	@Setter
	private Integer over65CardsForHome;
	@CsvBindByName(column = "over05_cards_for_percentage_home")
	@Getter
	@Setter
	private Integer over05CardsForPercentageHome;
	@CsvBindByName(column = "over15_cards_for_percentage_home")
	@Getter
	@Setter
	private Integer over15CardsForPercentageHome;
	@CsvBindByName(column = "over25_cards_for_percentage_home")
	@Getter
	@Setter
	private Integer over25CardsForPercentageHome;
	@CsvBindByName(column = "over35_cards_for_percentage_home")
	@Getter
	@Setter
	private Integer over35CardsForPercentageHome;
	@CsvBindByName(column = "over45_cards_for_percentage_home")
	@Getter
	@Setter
	private Integer over45CardsForPercentageHome;
	@CsvBindByName(column = "over55_cards_for_percentage_home")
	@Getter
	@Setter
	private Integer over55CardsForPercentageHome;
	@CsvBindByName(column = "over65_cards_for_percentage_home")
	@Getter
	@Setter
	private Integer over65CardsForPercentageHome;
	@CsvBindByName(column = "over05_cards_for_away")
	@Getter
	@Setter
	private Integer over05CardsForAway;
	@CsvBindByName(column = "over15_cards_for_away")
	@Getter
	@Setter
	private Integer over15CardsForAway;
	@CsvBindByName(column = "over25_cards_for_away")
	@Getter
	@Setter
	private Integer over25CardsForAway;
	@CsvBindByName(column = "over35_cards_for_away")
	@Getter
	@Setter
	private Integer over35CardsForAway;
	@CsvBindByName(column = "over45_cards_for_away")
	@Getter
	@Setter
	private Integer over45CardsForAway;
	@CsvBindByName(column = "over55_cards_for_away")
	@Getter
	@Setter
	private Integer over55CardsForAway;
	@CsvBindByName(column = "over65_cards_for_away")
	@Getter
	@Setter
	private Integer over65CardsForAway;
	@CsvBindByName(column = "over05_cards_for_percentage_away")
	@Getter
	@Setter
	private Integer over05CardsForPercentageAway;
	@CsvBindByName(column = "over15_cards_for_percentage_away")
	@Getter
	@Setter
	private Integer over15CardsForPercentageAway;
	@CsvBindByName(column = "over25_cards_for_percentage_away")
	@Getter
	@Setter
	private Integer over25CardsForPercentageAway;
	@CsvBindByName(column = "over35_cards_for_percentage_away")
	@Getter
	@Setter
	private Integer over35CardsForPercentageAway;
	@CsvBindByName(column = "over45_cards_for_percentage_away")
	@Getter
	@Setter
	private Integer over45CardsForPercentageAway;
	@CsvBindByName(column = "over55_cards_for_percentage_away")
	@Getter
	@Setter
	private Integer over55CardsForPercentageAway;
	@CsvBindByName(column = "over65_cards_for_percentage_away")
	@Getter
	@Setter
	private Integer over65CardsForPercentageAway;
	@CsvBindByName(column = "over05_cards_against_overall")
	@Getter
	@Setter
	private Integer over05CardsAgainstOverall;
	@CsvBindByName(column = "over15_cards_against_overall")
	@Getter
	@Setter
	private Integer over15CardsAgainstOverall;
	@CsvBindByName(column = "over25_cards_against_overall")
	@Getter
	@Setter
	private Integer over25CardsAgainstOverall;
	@CsvBindByName(column = "over35_cards_against_overall")
	@Getter
	@Setter
	private Integer over35CardsAgainstOverall;
	@CsvBindByName(column = "over45_cards_against_overall")
	@Getter
	@Setter
	private Integer over45CardsAgainstOverall;
	@CsvBindByName(column = "over55_cards_against_overall")
	@Getter
	@Setter
	private Integer over55CardsAgainstOverall;
	@CsvBindByName(column = "over65_cards_against_overall")
	@Getter
	@Setter
	private Integer over65CardsAgainstOverall;
	@CsvBindByName(column = "over05_cards_against_percentage_overall")
	@Getter
	@Setter
	private Integer over05CardsAgainstPercentageOverall;
	@CsvBindByName(column = "over15_cards_against_percentage_overall")
	@Getter
	@Setter
	private Integer over15CardsAgainstPercentageOverall;
	@CsvBindByName(column = "over25_cards_against_percentage_overall")
	@Getter
	@Setter
	private Integer over25CardsAgainstPercentageOverall;
	@CsvBindByName(column = "over35_cards_against_percentage_overall")
	@Getter
	@Setter
	private Integer over35CardsAgainstPercentageOverall;
	@CsvBindByName(column = "over45_cards_against_percentage_overall")
	@Getter
	@Setter
	private Integer over45CardsAgainstPercentageOverall;
	@CsvBindByName(column = "over55_cards_against_percentage_overall")
	@Getter
	@Setter
	private Integer over55CardsAgainstPercentageOverall;
	@CsvBindByName(column = "over65_cards_against_percentage_overall")
	@Getter
	@Setter
	private Integer over65CardsAgainstPercentageOverall;
	@CsvBindByName(column = "over05_cards_against_home")
	@Getter
	@Setter
	private Integer over05CardsAgainstHome;
	@CsvBindByName(column = "over15_cards_against_home")
	@Getter
	@Setter
	private Integer over15CardsAgainstHome;
	@CsvBindByName(column = "over25_cards_against_home")
	@Getter
	@Setter
	private Integer over25CardsAgainstHome;
	@CsvBindByName(column = "over35_cards_against_home")
	@Getter
	@Setter
	private Integer over35CardsAgainstHome;
	@CsvBindByName(column = "over45_cards_against_home")
	@Getter
	@Setter
	private Integer over45CardsAgainstHome;
	@CsvBindByName(column = "over55_cards_against_home")
	@Getter
	@Setter
	private Integer over55CardsAgainstHome;
	@CsvBindByName(column = "over65_cards_against_home")
	@Getter
	@Setter
	private Integer over65CardsAgainstHome;
	@CsvBindByName(column = "over05_cards_against_percentage_home")
	@Getter
	@Setter
	private Integer over05CardsAgainstPercentageHome;
	@CsvBindByName(column = "over15_cards_against_percentage_home")
	@Getter
	@Setter
	private Integer over15CardsAgainstPercentageHome;
	@CsvBindByName(column = "over25_cards_against_percentage_home")
	@Getter
	@Setter
	private Integer over25CardsAgainstPercentageHome;
	@CsvBindByName(column = "over35_cards_against_percentage_home")
	@Getter
	@Setter
	private Integer over35CardsAgainstPercentageHome;
	@CsvBindByName(column = "over45_cards_against_percentage_home")
	@Getter
	@Setter
	private Integer over45CardsAgainstPercentageHome;
	@CsvBindByName(column = "over55_cards_against_percentage_home")
	@Getter
	@Setter
	private Integer over55CardsAgainstPercentageHome;
	@CsvBindByName(column = "over65_cards_against_percentage_home")
	@Getter
	@Setter
	private Integer over65CardsAgainstPercentageHome;
	@CsvBindByName(column = "over05_cards_against_away")
	@Getter
	@Setter
	private Integer over05CardsAgainstAway;
	@CsvBindByName(column = "over15_cards_against_away")
	@Getter
	@Setter
	private Integer over15CardsAgainstAway;
	@CsvBindByName(column = "over25_cards_against_away")
	@Getter
	@Setter
	private Integer over25CardsAgainstAway;
	@CsvBindByName(column = "over35_cards_against_away")
	@Getter
	@Setter
	private Integer over35CardsAgainstAway;
	@CsvBindByName(column = "over45_cards_against_away")
	@Getter
	@Setter
	private Integer over45CardsAgainstAway;
	@CsvBindByName(column = "over55_cards_against_away")
	@Getter
	@Setter
	private Integer over55CardsAgainstAway;
	@CsvBindByName(column = "over65_cards_against_away")
	@Getter
	@Setter
	private Integer over65CardsAgainstAway;
	@CsvBindByName(column = "over05_cards_against_percentage_away")
	@Getter
	@Setter
	private Integer over05CardsAgainstPercentageAway;
	@CsvBindByName(column = "over15_cards_against_percentage_away")
	@Getter
	@Setter
	private Integer over15CardsAgainstPercentageAway;
	@CsvBindByName(column = "over25_cards_against_percentage_away")
	@Getter
	@Setter
	private Integer over25CardsAgainstPercentageAway;
	@CsvBindByName(column = "over35_cards_against_percentage_away")
	@Getter
	@Setter
	private Integer over35CardsAgainstPercentageAway;
	@CsvBindByName(column = "over45_cards_against_percentage_away")
	@Getter
	@Setter
	private Integer over45CardsAgainstPercentageAway;
	@CsvBindByName(column = "over55_cards_against_percentage_away")
	@Getter
	@Setter
	private Integer over55CardsAgainstPercentageAway;
	@CsvBindByName(column = "over65_cards_against_percentage_away")
	@Getter
	@Setter
	private Integer over65CardsAgainstPercentageAway;
	@CsvBindByName(column = "firstGoalScoredPercentage_overall")
	@Getter
	@Setter
	private Integer firstGoalScoredPercentageOverall;
	@CsvBindByName(column = "firstGoalScoredPercentage_home")
	@Getter
	@Setter
	private Integer firstGoalScoredPercentageHome;
	@CsvBindByName(column = "firstGoalScoredPercentage_away")
	@Getter
	@Setter
	private Integer firstGoalScoredPercentageAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "shots_per_match_overall")
	@Getter
	@Setter
	private Double shotsPerMatchOverall;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "shots_per_match_home")
	@Getter
	@Setter
	private Double shotsPerMatchHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "shots_per_match_away")
	@Getter
	@Setter
	private Double shotsPerMatchAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "shots_on_target_per_match_overall")
	@Getter
	@Setter
	private Double shotsOnTargetPerMatchOverall;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "shots_on_target_per_match_home")
	@Getter
	@Setter
	private Double shotsOnTargetPerMatchHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "shots_on_target_per_match_away")
	@Getter
	@Setter
	private Double shotsOnTargetPerMatchAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "shots_off_target_per_match_overall")
	@Getter
	@Setter
	private Double shotsOffTargetPerMatchOverall;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "shots_off_target_per_match_home")
	@Getter
	@Setter
	private Double shotsOffTargetPerMatchHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "shots_off_target_per_match_away")
	@Getter
	@Setter
	private Double shotsOffTargetPerMatchAway;
	@CsvBindByName(column = "fouls_by_this_team_overall")
	@Getter
	@Setter
	private Integer foulsByThisTeamOverall;
	@CsvBindByName(column = "fouls_by_this_team_home")
	@Getter
	@Setter
	private Integer foulsByThisTeamHome;
	@CsvBindByName(column = "fouls_by_this_team_away")
	@Getter
	@Setter
	private Integer foulsByThisTeamAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "fouls_per_match_overall")
	@Getter
	@Setter
	private Double foulsPerMatchOverall;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "fouls_per_match_home")
	@Getter
	@Setter
	private Double foulsPerMatchHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "fouls_per_match_away")
	@Getter
	@Setter
	private Double foulsPerMatchAway;
	@CsvBindByName(column = "offsides_total_overall")
	@Getter
	@Setter
	private Integer offsidesTotalOverall;
	@CsvBindByName(column = "offsides_total_home")
	@Getter
	@Setter
	private Integer offsidesTotalHome;
	@CsvBindByName(column = "offsides_total_away")
	@Getter
	@Setter
	private Integer offsidesTotalAway;
	@CsvBindByName(column = "offsides_this_team_overall")
	@Getter
	@Setter
	private Integer offsidesThisTeamOverall;
	@CsvBindByName(column = "offsides_this_team_home")
	@Getter
	@Setter
	private Integer offsidesThisTeamHome;
	@CsvBindByName(column = "offsides_this_team_away")
	@Getter
	@Setter
	private Integer offsidesThisTeamAway;
	@CsvBindByName(column = "offsidesRecorded_matches_overall")
	@Getter
	@Setter
	private Integer offsidesRecordedMatchesOverall;
	@CsvBindByName(column = "offsidesRecorded_matches_home")
	@Getter
	@Setter
	private Integer offsidesRecordedMatchesHome;
	@CsvBindByName(column = "offsidesRecorded_matches_away")
	@Getter
	@Setter
	private Integer offsidesRecordedMatchesAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "offsides_total_per_match_overall")
	@Getter
	@Setter
	private Double offsidesTotalPerMatchOverall;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "offsides_total_per_match_home")
	@Getter
	@Setter
	private Double offsidesTotalPerMatchHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "offsides_total_per_match_away")
	@Getter
	@Setter
	private Double offsidesTotalPerMatchAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "offsides_this_team_per_match_overall")
	@Getter
	@Setter
	private Double offsidesThisTeamPerMatchOverall;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "offsides_this_team_per_match_home")
	@Getter
	@Setter
	private Double offsidesThisTeamPerMatchHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "offsides_this_team_per_match_away")
	@Getter
	@Setter
	private Double offsidesThisTeamPerMatchAway;
	@CsvBindByName(column = "offsides_total_over05_overall")
	@Getter
	@Setter
	private Integer offsidesTotalOver05Overall;
	@CsvBindByName(column = "offsides_total_over15_overall")
	@Getter
	@Setter
	private Integer offsidesTotalOver15Overall;
	@CsvBindByName(column = "offsides_total_over25_overall")
	@Getter
	@Setter
	private Integer offsidesTotalOver25Overall;
	@CsvBindByName(column = "offsides_total_over35_overall")
	@Getter
	@Setter
	private Integer offsidesTotalOver35Overall;
	@CsvBindByName(column = "offsides_total_over45_overall")
	@Getter
	@Setter
	private Integer offsidesTotalOver45Overall;
	@CsvBindByName(column = "scoredBothHalves_overall")
	@Getter
	@Setter
	private Integer scoredBothHalvesOverall;
	@CsvBindByName(column = "scoredBothHalves_home")
	@Getter
	@Setter
	private Integer scoredBothHalvesHome;
	@CsvBindByName(column = "scoredBothHalves_away")
	@Getter
	@Setter
	private Integer scoredBothHalvesAway;
	@CsvBindByName(column = "scoredBothHalvesPercentage_overall")
	@Getter
	@Setter
	private Integer scoredBothHalvesPercentageOverall;
	@CsvBindByName(column = "scoredBothHalvesPercentage_home")
	@Getter
	@Setter
	private Integer scoredBothHalvesPercentageHome;
	@CsvBindByName(column = "scoredBothHalvesPercentage_away")
	@Getter
	@Setter
	private Integer scoredBothHalvesPercentageAway;
	@CsvBindByName(column = "BTTS_both_halves_overall")
	@Getter
	@Setter
	private Integer BTTSBothHalvesOverall;
	@CsvBindByName(column = "BTTS_both_halves_home")
	@Getter
	@Setter
	private Integer BTTSBothHalvesHome;
	@CsvBindByName(column = "BTTS_both_halves_away")
	@Getter
	@Setter
	private Integer BTTSBothHalvesAway;
	@CsvBindByName(column = "BTTS_and_win_overall")
	@Getter
	@Setter
	private Integer BTTSAndWinOverall;
	@CsvBindByName(column = "BTTS_and_win_home")
	@Getter
	@Setter
	private Integer BTTSAndWinHome;
	@CsvBindByName(column = "BTTS_and_win_away")
	@Getter
	@Setter
	private Integer BTTSAndWinAway;
	@CsvBindByName(column = "BTTS_and_win_percentage_overall")
	@Getter
	@Setter
	private Integer BTTSAndWinPercentageOverall;
	@CsvBindByName(column = "BTTS_and_win_percentage_home")
	@Getter
	@Setter
	private Integer BTTSAndWinPercentageHome;
	@CsvBindByName(column = "BTTS_and_win_percentage_away")
	@Getter
	@Setter
	private Integer BTTSAndWinPercentageAway;
	@CsvBindByName(column = "BTTS_and_draw_overall")
	@Getter
	@Setter
	private Integer BTTSAndDrawOverall;
	@CsvBindByName(column = "BTTS_and_draw_home")
	@Getter
	@Setter
	private Integer BTTSAndDrawHome;
	@CsvBindByName(column = "BTTS_and_draw_away")
	@Getter
	@Setter
	private Integer BTTSAndDrawAway;
	@CsvBindByName(column = "BTTS_and_draw_percentage_overall")
	@Getter
	@Setter
	private Integer BTTSAndDrawPercentageOverall;
	@CsvBindByName(column = "BTTS_and_draw_percentage_home")
	@Getter
	@Setter
	private Integer BTTSAndDrawPercentageHome;
	@CsvBindByName(column = "BTTS_and_draw_percentage_away")
	@Getter
	@Setter
	private Integer BTTSAndDrawPercentageAway;
	@CsvBindByName(column = "BTTS_and_lose_overall")
	@Getter
	@Setter
	private Integer BTTSAndLoseOverall;
	@CsvBindByName(column = "BTTS_and_lose_home")
	@Getter
	@Setter
	private Integer BTTSAndLoseHome;
	@CsvBindByName(column = "BTTS_and_lose_away")
	@Getter
	@Setter
	private Integer BTTSAndLoseAway;
	@CsvBindByName(column = "BTTS_and_lose_percentage_overall")
	@Getter
	@Setter
	private Integer BTTSAndLosePercentageOverall;
	@CsvBindByName(column = "BTTS_and_lose_percentage_home")
	@Getter
	@Setter
	private Integer BTTSAndLosePercentageHome;
	@CsvBindByName(column = "BTTS_and_lose_percentage_away")
	@Getter
	@Setter
	private Integer BTTSAndLosePercentageAway;
	@CsvBindByName(column = "matches_goal_timings_recorded_overall")
	@Getter
	@Setter
	private Integer matchesGoalTimingsRecordedOverall;
	@CsvBindByName(column = "matches_goal_timings_recorded_home")
	@Getter
	@Setter
	private Integer matchesGoalTimingsRecordedHome;
	@CsvBindByName(column = "matches_goal_timings_recorded_away")
	@Getter
	@Setter
	private Integer matchesGoalTimingsRecordedAway;
	@CsvBindByName(column = "total_goals_2h_overall")
	@Getter
	@Setter
	private Integer totalGoals2hOverall;
	@CsvBindByName(column = "total_goals_2h_home")
	@Getter
	@Setter
	private Integer totalGoals2hHome;
	@CsvBindByName(column = "total_goals_2h_away")
	@Getter
	@Setter
	private Integer totalGoals2hAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "average_total_goals_2h_per_match_overall")
	@Getter
	@Setter
	private Double averageTotalGoals2hPerMatchOverall;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "average_total_goals_2h_per_match_home")
	@Getter
	@Setter
	private Double averageTotalGoals2hPerMatchHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "@CsvCustomBindByName(converter = DoubleConverter.class, column = \"points_per_game\")")
	@Getter
	@Setter
	private Double averageTotalGoals2hPerMatchAway;
	@CsvBindByName(column = "goals_scored_2h_overall")
	@Getter
	@Setter
	private Integer goalsScored2hOverall;
	@CsvBindByName(column = "goals_scored_2h_home")
	@Getter
	@Setter
	private Integer goalsScored2hHome;
	@CsvBindByName(column = "goals_scored_2h_away")
	@Getter
	@Setter
	private Integer goalsScored2hAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_scored_2h_per_match_overall")
	@Getter
	@Setter
	private Double goalsScored2hPerMatchOverall;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_scored_2h_per_match_home")
	@Getter
	@Setter
	private Double goalsScored2hPerMatchHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_scored_2h_per_match_away")
	@Getter
	@Setter
	private Double goalsScored2hPerMatchAway;
	@CsvBindByName(column = "goals_conceded_2h_overall")
	@Getter
	@Setter
	private Integer goalsConceded2hOverall;
	@CsvBindByName(column = "goals_conceded_2h_home")
	@Getter
	@Setter
	private Integer goalsConceded2hHome;
	@CsvBindByName(column = "goals_conceded_2h_away")
	@Getter
	@Setter
	private Integer goalsConceded2hAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_conceded_2h_per_match_overall")
	@Getter
	@Setter
	private Double goalsConceded2hPerMatchOverall;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_conceded_2h_per_match_home")
	@Getter
	@Setter
	private Double goalsConceded2hPerMatchHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "goals_conceded_2h_per_match_away")
	@Getter
	@Setter
	private Double goalsConceded2hPerMatchAway;
	@CsvBindByName(column = "over05_2hg_matches_overall")
	@Getter
	@Setter
	private Integer over052hgMatchesOverall;
	@CsvBindByName(column = "over15_2hg_matches_overall")
	@Getter
	@Setter
	private Integer over152hgMatchesOverall;
	@CsvBindByName(column = "over25_2hg_matches_overall")
	@Getter
	@Setter
	private Integer over252hgMatchesOverall;
	@CsvBindByName(column = "over05_2hg_percentage_overall")
	@Getter
	@Setter
	private Integer over052hgPercentageOverall;
	@CsvBindByName(column = "over15_2hg_percentage_overall")
	@Getter
	@Setter
	private Integer over152hgPercentageOverall;
	@CsvBindByName(column = "over25_2hg_percentage_overall")
	@Getter
	@Setter
	private Integer over252hgPercentageOverall;
	@CsvBindByName(column = "points_2h_overall")
	@Getter
	@Setter
	private Integer points2hOverall;
	@CsvBindByName(column = "points_2h_home")
	@Getter
	@Setter
	private Integer points2hHome;
	@CsvBindByName(column = "points_2h_away")
	@Getter
	@Setter
	private Integer points2hAway;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "ppg_2h_overall")
	@Getter
	@Setter
	private Double ppg2hOverall;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "ppg_2h_home")
	@Getter
	@Setter
	private Double ppg2hHome;
	@CsvCustomBindByName(converter = DoubleConverter.class, column = "ppg_2h_away")
	@Getter
	@Setter
	private Double ppg2hAway;
	@CsvBindByName(column = "wins_2h_overall")
	@Getter
	@Setter
	private Integer wins2hOverall;
	@CsvBindByName(column = "wins_2h_home")
	@Getter
	@Setter
	private Integer wins2hHome;
	@CsvBindByName(column = "wins_2h_away")
	@Getter
	@Setter
	private Integer wins2hAway;
	@CsvBindByName(column = "wins_2h_percentage_overall")
	@Getter
	@Setter
	private Integer wins2hPercentageOverall;
	@CsvBindByName(column = "wins_2h_percentage_home")
	@Getter
	@Setter
	private Integer wins2hPercentageHome;
	@CsvBindByName(column = "wins_2h_percentage_away")
	@Getter
	@Setter
	private Integer wins2hPercentageAway;
	@CsvBindByName(column = "draws_2h_overall")
	@Getter
	@Setter
	private Integer draws2hOverall;
	@CsvBindByName(column = "draws_2h_home")
	@Getter
	@Setter
	private Integer draws2hHome;
	@CsvBindByName(column = "draws_2h_away")
	@Getter
	@Setter
	private Integer draws2hAway;
	@CsvBindByName(column = "losses_2h_overall")
	@Getter
	@Setter
	private Integer losses2hOverall;
	@CsvBindByName(column = "losses_2h_home")
	@Getter
	@Setter
	private Integer losses2hHome;
	@CsvBindByName(column = "losses_2h_away")
	@Getter
	@Setter
	private Integer losses2hAway;
	@CsvBindByName(column = "btts_2h_overall")
	@Getter
	@Setter
	private Integer btts2hOverall;
	@CsvBindByName(column = "btts_2h_home")
	@Getter
	@Setter
	private Integer btts2hHome;
	@CsvBindByName(column = "btts_2h_away")
	@Getter
	@Setter
	private Integer btts2hAway;
	@CsvBindByName(column = "btts_2h_percentage_overall")
	@Getter
	@Setter
	private Integer btts2hPercentageOverall;
	@CsvBindByName(column = "btts_2h_percentage_home")
	@Getter
	@Setter
	private Integer btts2hPercentageHome;
	@CsvBindByName(column = "btts_2h_percentage_away")
	@Getter
	@Setter
	private Integer btts2hPercentageAway;
	@CsvBindByName(column = "clean_sheets_2h_overall")
	@Getter
	@Setter
	private Integer cleanSheets2hOverall;
	@CsvBindByName(column = "clean_sheets_2h_home")
	@Getter
	@Setter
	private Integer cleanSheets2hHome;
	@CsvBindByName(column = "clean_sheets_2h_away")
	@Getter
	@Setter
	private Integer cleanSheets2hAway;
	@CsvBindByName(column = "failed_to_score_2h_overall")
	@Getter
	@Setter
	private Integer failedToScore2hOverall;
	@CsvBindByName(column = "failed_to_score_2h_home")
	@Getter
	@Setter
	private Integer failedToScore2hHome;
	@CsvBindByName(column = "failed_to_score_2h_away")
	@Getter
	@Setter
	private Integer failedToScore2hAway;
}
