package de.ludwig.footystats.tools.backend.services.stats;

import de.ludwig.footystats.tools.backend.FootystatsProperties;
import de.ludwig.footystats.tools.backend.services.MongoService;
import de.ludwig.footystats.tools.backend.services.csv.CsvFileService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

@Service
public class TeamStatsService extends MongoService<TeamStats> {

	private final CsvFileService<TeamStats> teamStatsCsvFileService;

	private final TeamStatsRepository teamStatsRepository;

	private final FootystatsProperties footystatsProperties;

	public TeamStatsService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter, CsvFileService<TeamStats> teamStatsCsvFileService, TeamStatsRepository teamStatsRepository, FootystatsProperties footystatsProperties) {
		super(mongoTemplate, mappingMongoConverter);
		this.teamStatsCsvFileService = teamStatsCsvFileService;
		this.teamStatsRepository = teamStatsRepository;
		this.footystatsProperties = footystatsProperties;
	}

	public Collection<TeamStats> readTeamStats(InputStream data) {
		List<TeamStats> stats = teamStatsCsvFileService.importFile(data, TeamStats.class);
		stats.forEach(ls -> upsert(ls));
		return stats;
	}

	public Collection<TeamStats> latestThree(String team, String country, Integer year) {
		var seasons = new String[]{year -1 + "/" +year, year-2+"/"+(year-1),year+"/"+(year+1), year +"", year +1+"",year-1+""};
		Query query = Query.query(Criteria.where("country").is(country).and("season").in(seasons).orOperator(Criteria.where("teamName").is(team), Criteria.where("commonName").is(team)));
		return mongoTemplate.find(query, TeamStats.class);
	}

	public TeamStats aggregate(Collection<TeamStats> teamStats){
		if(teamStats == null || teamStats.isEmpty()){
			return null;
		}
		var matchingTeamStats = teamStats.stream().filter(ts -> ts.getMatches_played() >= footystatsProperties.getIgnoreTeamStatsWithGamesPlayedLowerThan()).collect(Collectors.toList());
		if(matchingTeamStats.isEmpty()){
			return null;
		}

		var probe = matchingTeamStats.get(0);

		var tsNew = new TeamStats();
		tsNew.setTeamName(probe.getTeamName());
		tsNew.setCommonName(probe.getCommonName());
		tsNew.setSeason("");
		tsNew.setCountry(probe.getCountry());
		tsNew.setMatches_played(integerAverageRoundUp(matchingTeamStats, TeamStats::getMatches_played));
		tsNew.setMatches_played_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getMatches_played_home));
		tsNew.setMatches_played_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getMatches_played_away));
		tsNew.setSuspended_matches(integerAverageRoundUp(matchingTeamStats, TeamStats::getSuspended_matches));
		tsNew.setWins(integerAverageRoundUp(matchingTeamStats, TeamStats::getWins));
		tsNew.setWins_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getWins_home));
		tsNew.setWins_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getWins_away));
		tsNew.setDraws(integerAverageRoundUp(matchingTeamStats, TeamStats::getDraws));
		tsNew.setDraws_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getDraws_home));
		tsNew.setDraws_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getDraws_away));
		tsNew.setLosses(integerAverageRoundUp(matchingTeamStats, TeamStats::getLosses));
		tsNew.setLosses_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getLosses_home));
		tsNew.setLosses_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getLosses_away));
		tsNew.setPoints_per_game(floatAverage(matchingTeamStats, TeamStats::getPoints_per_game));
		tsNew.setPoints_per_game_home(floatAverage(matchingTeamStats, TeamStats::getPoints_per_game_home));
		tsNew.setPoints_per_game_away(floatAverage(matchingTeamStats, TeamStats::getPoints_per_game_away));
		tsNew.setLeague_position(integerAverageRoundUp(matchingTeamStats, TeamStats::getLeague_position));
		tsNew.setLeague_position_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getLeague_position_home));
		tsNew.setLeague_position_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getLeague_position_away));
		tsNew.setPerformance_rank(integerAverageRoundUp(matchingTeamStats, TeamStats::getPerformance_rank));
		tsNew.setGoals_scored(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_scored));
		tsNew.setGoals_conceded(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_conceded));
		tsNew.setGoal_difference(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoal_difference));
		tsNew.setTotal_goal_count(integerAverageRoundUp(matchingTeamStats, TeamStats::getTotal_goal_count));
		tsNew.setTotal_goal_count_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getTotal_goal_count_home));
		tsNew.setTotal_goal_count_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getTotal_goal_count_away));
		tsNew.setGoals_scored_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_scored_home));
		tsNew.setGoals_scored_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_scored_away));
		tsNew.setGoals_conceded_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_conceded_home));
		tsNew.setGoals_conceded_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_conceded_away));
		tsNew.setGoal_difference_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoal_difference_home));
		tsNew.setGoal_difference_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoal_difference_away));
		tsNew.setMinutes_per_goal_scored(integerAverageRoundUp(matchingTeamStats, TeamStats::getMinutes_per_goal_scored));
		tsNew.setMinutes_per_goal_scored_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getMinutes_per_goal_scored_home));
		tsNew.setMinutes_per_goal_scored_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getMinutes_per_goal_scored_away));
		tsNew.setMinutes_per_goal_conceded(integerAverageRoundUp(matchingTeamStats, TeamStats::getMinutes_per_goal_conceded));
		tsNew.setMinutes_per_goal_conceded_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getMinutes_per_goal_conceded_home));
		tsNew.setMinutes_per_goal_conceded_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getMinutes_per_goal_conceded_away));
		tsNew.setClean_sheets(integerAverageRoundUp(matchingTeamStats, TeamStats::getClean_sheets));
		tsNew.setClean_sheets_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getClean_sheets_home));
		tsNew.setClean_sheets_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getClean_sheets_away));
		tsNew.setBtts_count(integerAverageRoundUp(matchingTeamStats, TeamStats::getBtts_count));
		tsNew.setBtts_count_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getBtts_count_home));
		tsNew.setBtts_count_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getBtts_count_away));
		tsNew.setFts_count(integerAverageRoundUp(matchingTeamStats, TeamStats::getFts_count));
		tsNew.setFts_count_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getFts_count_home));
		tsNew.setFts_count_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getFts_count_away));
		tsNew.setFirst_team_to_score_count(integerAverageRoundUp(matchingTeamStats, TeamStats::getFirst_team_to_score_count));
		tsNew.setFirst_team_to_score_count_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getFirst_team_to_score_count_home));
		tsNew.setFirst_team_to_score_count_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getFirst_team_to_score_count_away));
		tsNew.setCorners_total(integerAverageRoundUp(matchingTeamStats, TeamStats::getCorners_total));
		tsNew.setCorners_total_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getCorners_total_home));
		tsNew.setCorners_total_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getCorners_total_away));
		tsNew.setCards_total(integerAverageRoundUp(matchingTeamStats, TeamStats::getCards_total));
		tsNew.setCards_total_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getCards_total_home));
		tsNew.setCards_total_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getCards_total_away));
		tsNew.setAverage_possession(integerAverageRoundUp(matchingTeamStats, TeamStats::getAverage_possession));
		tsNew.setAverage_possession_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getAverage_possession_home));
		tsNew.setAverage_possession_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getAverage_possession_away));
		tsNew.setShots(integerAverageRoundUp(matchingTeamStats, TeamStats::getShots));
		tsNew.setShots_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getShots_home));
		tsNew.setShots_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getShots_away));
		tsNew.setShots_on_target(integerAverageRoundUp(matchingTeamStats, TeamStats::getShots_on_target));
		tsNew.setShots_on_target_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getShots_on_target_home));
		tsNew.setShots_on_target_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getShots_on_target_away));
		tsNew.setShots_off_target(integerAverageRoundUp(matchingTeamStats, TeamStats::getShots_off_target));
		tsNew.setShots_off_target_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getShots_off_target_home));
		tsNew.setShots_off_target_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getShots_off_target_away));
		tsNew.setFouls(integerAverageRoundUp(matchingTeamStats, TeamStats::getFouls));
		tsNew.setFouls_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getFouls_home));
		tsNew.setFouls_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getFouls_away));
		tsNew.setGoals_scored_half_time(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_scored_half_time));
		tsNew.setGoals_scored_half_time_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_scored_half_time_home));
		tsNew.setGoals_scored_half_time_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_scored_half_time_away));
		tsNew.setGoals_conceded_half_time(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_conceded_half_time));
		tsNew.setGoals_conceded_half_time_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_conceded_half_time_home));
		tsNew.setGoals_conceded_half_time_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_conceded_half_time_away));
		tsNew.setGoal_difference_half_time(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoal_difference_half_time));
		tsNew.setGoal_difference_half_time_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoal_difference_half_time_home));
		tsNew.setGoal_difference_half_time_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoal_difference_half_time_away));
		tsNew.setLeading_at_half_time(integerAverageRoundUp(matchingTeamStats, TeamStats::getLeading_at_half_time));
		tsNew.setLeading_at_half_time_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getLeading_at_half_time_home));
		tsNew.setLeading_at_half_time_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getLeading_at_half_time_away));
		tsNew.setDraw_at_half_time(integerAverageRoundUp(matchingTeamStats, TeamStats::getDraw_at_half_time));
		tsNew.setDraw_at_half_time_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getDraw_at_half_time_home));
		tsNew.setDraw_at_half_time_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getDraw_at_half_time_away));
		tsNew.setLosing_at_half_time(integerAverageRoundUp(matchingTeamStats, TeamStats::getLosing_at_half_time));
		tsNew.setLosing_at_half_time_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getLosing_at_half_time_home));
		tsNew.setLosing_at_half_time_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getLosing_at_half_time_away));
		tsNew.setPoints_per_game_half_time(floatAverage(matchingTeamStats, TeamStats::getPoints_per_game_half_time));
		tsNew.setPoints_per_game_half_time_home(floatAverage(matchingTeamStats, TeamStats::getPoints_per_game_half_time_home));
		tsNew.setPoints_per_game_half_time_away(floatAverage(matchingTeamStats, TeamStats::getPoints_per_game_half_time_away));
		tsNew.setAverage_total_goals_per_match(floatAverage(matchingTeamStats, TeamStats::getAverage_total_goals_per_match));
		tsNew.setAverage_total_goals_per_match_home(floatAverage(matchingTeamStats, TeamStats::getAverage_total_goals_per_match_home));
		tsNew.setAverage_total_goals_per_match_away(floatAverage(matchingTeamStats, TeamStats::getAverage_total_goals_per_match_away));
		tsNew.setGoals_scored_per_match(floatAverage(matchingTeamStats, TeamStats::getGoals_scored_per_match));
		tsNew.setGoals_scored_per_match_home(floatAverage(matchingTeamStats, TeamStats::getGoals_scored_per_match_home));
		tsNew.setGoals_scored_per_match_away(floatAverage(matchingTeamStats, TeamStats::getGoals_scored_per_match_away));
		tsNew.setGoals_conceded_per_match(floatAverage(matchingTeamStats, TeamStats::getGoals_conceded_per_match));
		tsNew.setGoals_conceded_per_match_home(floatAverage(matchingTeamStats, TeamStats::getGoals_conceded_per_match_home));
		tsNew.setGoals_conceded_per_match_away(floatAverage(matchingTeamStats, TeamStats::getGoals_conceded_per_match_away));
		tsNew.setTotal_goals_per_match_half_time(floatAverage(matchingTeamStats, TeamStats::getTotal_goals_per_match_half_time));
		tsNew.setTotal_goals_per_match_half_time_home(floatAverage(matchingTeamStats, TeamStats::getTotal_goals_per_match_half_time_home));
		tsNew.setTotal_goals_per_match_half_time_away(floatAverage(matchingTeamStats, TeamStats::getTotal_goals_per_match_half_time_away));
		tsNew.setGoals_scored_per_match_half_time(floatAverage(matchingTeamStats, TeamStats::getGoals_scored_per_match_half_time));
		tsNew.setGoals_scored_per_match_half_time_home(floatAverage(matchingTeamStats, TeamStats::getGoals_scored_per_match_half_time_home));
		tsNew.setGoals_scored_per_match_half_time_away(floatAverage(matchingTeamStats, TeamStats::getGoals_scored_per_match_half_time_away));
		tsNew.setGoals_conceded_per_match_half_time(floatAverage(matchingTeamStats, TeamStats::getGoals_conceded_per_match_half_time));
		tsNew.setGoals_conceded_per_match_half_time_home(floatAverage(matchingTeamStats, TeamStats::getGoals_conceded_per_match_half_time_home));
		tsNew.setGoals_conceded_per_match_half_time_away(floatAverage(matchingTeamStats, TeamStats::getGoals_conceded_per_match_half_time_away));
		tsNew.setOver05_count(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver05_count));
		tsNew.setOver15_count(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver15_count));
		tsNew.setOver25_count(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver25_count));
		tsNew.setOver35_count(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver35_count));
		tsNew.setOver45_count(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver45_count));
		tsNew.setOver55_count(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver55_count));
		tsNew.setOver05_count_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver05_count_home));
		tsNew.setOver15_count_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver15_count_home));
		tsNew.setOver25_count_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver25_count_home));
		tsNew.setOver35_count_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver35_count_home));
		tsNew.setOver45_count_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver45_count_home));
		tsNew.setOver55_count_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver55_count_home));
		tsNew.setOver05_count_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver05_count_away));
		tsNew.setOver15_count_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver15_count_away));
		tsNew.setOver25_count_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver25_count_away));
		tsNew.setOver35_count_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver35_count_away));
		tsNew.setOver45_count_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver45_count_away));
		tsNew.setOver55_count_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver55_count_away));
		tsNew.setUnder05_count(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder05_count));
		tsNew.setUnder15_count(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder15_count));
		tsNew.setUnder25_count(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder25_count));
		tsNew.setUnder35_count(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder35_count));
		tsNew.setUnder45_count(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder45_count));
		tsNew.setUnder55_count(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder55_count));
		tsNew.setUnder05_count_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder05_count_home));
		tsNew.setUnder15_count_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder15_count_home));
		tsNew.setUnder25_count_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder25_count_home));
		tsNew.setUnder35_count_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder35_count_home));
		tsNew.setUnder45_count_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder45_count_home));
		tsNew.setUnder55_count_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder55_count_home));
		tsNew.setUnder05_count_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder05_count_away));
		tsNew.setUnder15_count_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder15_count_away));
		tsNew.setUnder25_count_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder25_count_away));
		tsNew.setUnder35_count_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder35_count_away));
		tsNew.setUnder45_count_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder45_count_away));
		tsNew.setUnder55_count_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder55_count_away));
		tsNew.setOver05_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver05_percentage));
		tsNew.setOver15_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver15_percentage));
		tsNew.setOver25_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver25_percentage));
		tsNew.setOver35_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver35_percentage));
		tsNew.setOver45_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver45_percentage));
		tsNew.setOver55_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver55_percentage));
		tsNew.setOver05_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver05_percentage_home));
		tsNew.setOver15_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver15_percentage_home));
		tsNew.setOver25_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver25_percentage_home));
		tsNew.setOver35_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver35_percentage_home));
		tsNew.setOver45_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver45_percentage_home));
		tsNew.setOver55_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver55_percentage_home));
		tsNew.setOver05_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver05_percentage_away));
		tsNew.setOver15_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver15_percentage_away));
		tsNew.setOver25_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver25_percentage_away));
		tsNew.setOver35_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver35_percentage_away));
		tsNew.setOver45_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver45_percentage_away));
		tsNew.setOver55_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver55_percentage_away));
		tsNew.setUnder05_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder05_percentage));
		tsNew.setUnder15_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder15_percentage));
		tsNew.setUnder25_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder25_percentage));
		tsNew.setUnder35_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder35_percentage));
		tsNew.setUnder45_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder45_percentage));
		tsNew.setUnder55_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder55_percentage));
		tsNew.setUnder05_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder05_percentage_home));
		tsNew.setUnder15_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder15_percentage_home));
		tsNew.setUnder25_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder25_percentage_home));
		tsNew.setUnder35_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder35_percentage_home));
		tsNew.setUnder45_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder45_percentage_home));
		tsNew.setUnder55_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder55_percentage_home));
		tsNew.setUnder05_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder05_percentage_away));
		tsNew.setUnder15_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder15_percentage_away));
		tsNew.setUnder25_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder25_percentage_away));
		tsNew.setUnder35_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder35_percentage_away));
		tsNew.setUnder45_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder45_percentage_away));
		tsNew.setUnder55_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder55_percentage_away));
		tsNew.setOver05_count_half_time(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver05_count_half_time));
		tsNew.setOver15_count_half_time(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver15_count_half_time));
		tsNew.setOver25_count_half_time(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver25_count_half_time));
		tsNew.setOver05_count_half_time_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver05_count_half_time_home));
		tsNew.setOver15_count_half_time_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver15_count_half_time_home));
		tsNew.setOver25_count_half_time_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver25_count_half_time_home));
		tsNew.setOver05_count_half_time_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver05_count_half_time_away));
		tsNew.setOver15_count_half_time_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver15_count_half_time_away));
		tsNew.setOver25_count_half_time_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver25_count_half_time_away));
		tsNew.setOver05_half_time_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver05_half_time_percentage));
		tsNew.setOver15_half_time_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver15_half_time_percentage));
		tsNew.setOver25_half_time_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver25_half_time_percentage));
		tsNew.setOver05_half_time_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver05_half_time_percentage_home));
		tsNew.setOver15_half_time_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver15_half_time_percentage_home));
		tsNew.setOver25_half_time_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver25_half_time_percentage_home));
		tsNew.setOver05_half_time_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver05_half_time_percentage_away));
		tsNew.setOver15_half_time_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver15_half_time_percentage_away));
		tsNew.setOver25_half_time_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver25_half_time_percentage_away));
		tsNew.setWin_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getWin_percentage));
		tsNew.setWin_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getWin_percentage_home));
		tsNew.setWin_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getWin_percentage_away));
		tsNew.setHome_advantage_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getHome_advantage_percentage));
		tsNew.setClean_sheet_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getClean_sheet_percentage));
		tsNew.setClean_sheet_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getClean_sheet_percentage_home));
		tsNew.setClean_sheet_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getClean_sheet_percentage_away));
		tsNew.setBtts_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getBtts_percentage));
		tsNew.setBtts_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getBtts_percentage_home));
		tsNew.setBtts_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getBtts_percentage_away));
		tsNew.setFts_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getFts_percentage));
		tsNew.setFts_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getFts_percentage_home));
		tsNew.setFts_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getFts_percentage_away));
		tsNew.setFirst_team_to_score_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getFirst_team_to_score_percentage));
		tsNew.setFirst_team_to_score_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getFirst_team_to_score_percentage_home));
		tsNew.setFirst_team_to_score_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getFirst_team_to_score_percentage_away));
		tsNew.setClean_sheet_half_time(integerAverageRoundUp(matchingTeamStats, TeamStats::getClean_sheet_half_time));
		tsNew.setClean_sheet_half_time_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getClean_sheet_half_time_home));
		tsNew.setClean_sheet_half_time_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getClean_sheet_half_time_away));
		tsNew.setClean_sheet_half_time_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getClean_sheet_half_time_percentage));
		tsNew.setClean_sheet_half_time_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getClean_sheet_half_time_percentage_home));
		tsNew.setClean_sheet_half_time_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getClean_sheet_half_time_percentage_away));
		tsNew.setFts_half_time(integerAverageRoundUp(matchingTeamStats, TeamStats::getFts_half_time));
		tsNew.setFts_half_time_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getFts_half_time_home));
		tsNew.setFts_half_time_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getFts_half_time_away));
		tsNew.setFts_half_time_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getFts_half_time_percentage));
		tsNew.setFts_half_time_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getFts_half_time_percentage_home));
		tsNew.setFts_half_time_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getFts_half_time_percentage_away));
		tsNew.setBtts_half_time(integerAverageRoundUp(matchingTeamStats, TeamStats::getBtts_half_time));
		tsNew.setBtts_half_time_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getBtts_half_time_home));
		tsNew.setBtts_half_time_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getBtts_half_time_away));
		tsNew.setBtts_half_time_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getBtts_half_time_percentage));
		tsNew.setBtts_half_time_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getBtts_half_time_percentage_home));
		tsNew.setBtts_half_time_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getBtts_half_time_percentage_away));
		tsNew.setLeading_at_half_time_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getLeading_at_half_time_percentage));
		tsNew.setLeading_at_half_time_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getLeading_at_half_time_percentage_home));
		tsNew.setLeading_at_half_time_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getLeading_at_half_time_percentage_away));
		tsNew.setDraw_at_half_time_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getDraw_at_half_time_percentage));
		tsNew.setDraw_at_half_time_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getDraw_at_half_time_percentage_home));
		tsNew.setDraw_at_half_time_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getDraw_at_half_time_percentage_away));
		tsNew.setLosing_at_half_time_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getLosing_at_half_time_percentage));
		tsNew.setLosing_at_half_time_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getLosing_at_half_time_percentage_home));
		tsNew.setLosing_at_half_time_percentage_away(floatAverage(matchingTeamStats, TeamStats::getLosing_at_half_time_percentage_away));
		tsNew.setCorners_per_match(floatAverage(matchingTeamStats, TeamStats::getCorners_per_match));
		tsNew.setCorners_per_match_home(floatAverage(matchingTeamStats, TeamStats::getCorners_per_match_home));
		tsNew.setCorners_per_match_away(floatAverage(matchingTeamStats, TeamStats::getCorners_per_match_away));
		tsNew.setCards_per_match(floatAverage(matchingTeamStats, TeamStats::getCards_per_match));
		tsNew.setCards_per_match_home(floatAverage(matchingTeamStats, TeamStats::getCards_per_match_home));
		tsNew.setCards_per_match_away(floatAverage(matchingTeamStats, TeamStats::getCards_per_match_away));
		tsNew.setOver65_corners_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver65_corners_percentage));
		tsNew.setOver75_corners_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver75_corners_percentage));
		tsNew.setOver85_corners_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver85_corners_percentage));
		tsNew.setOver95_corners_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver95_corners_percentage));
		tsNew.setOver105_corners_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver105_corners_percentage));
		tsNew.setOver115_corners_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver115_corners_percentage));
		tsNew.setOver125_corners_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver125_corners_percentage));
		tsNew.setOver135_corners_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver135_corners_percentage));
		tsNew.setXg_for_avg_overall(floatAverage(matchingTeamStats, TeamStats::getXg_for_avg_overall));
		tsNew.setXg_for_avg_home(floatAverage(matchingTeamStats, TeamStats::getXg_for_avg_home));
		tsNew.setXg_for_avg_away(floatAverage(matchingTeamStats, TeamStats::getXg_for_avg_away));
		tsNew.setXg_against_avg_overall(floatAverage(matchingTeamStats, TeamStats::getXg_against_avg_overall));
		tsNew.setXg_against_avg_home(floatAverage(matchingTeamStats, TeamStats::getXg_against_avg_home));
		tsNew.setXg_against_avg_away(floatAverage(matchingTeamStats, TeamStats::getXg_against_avg_away));
		tsNew.setPrediction_risk(integerAverageRoundUp(matchingTeamStats, TeamStats::getPrediction_risk));
		tsNew.setGoals_scored_min_0_to_10(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_scored_min_0_to_10));
		tsNew.setGoals_scored_min_11_to_20(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_scored_min_11_to_20));
		tsNew.setGoals_scored_min_21_to_30(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_scored_min_21_to_30));
		tsNew.setGoals_scored_min_31_to_40(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_scored_min_31_to_40));
		tsNew.setGoals_scored_min_41_to_50(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_scored_min_41_to_50));
		tsNew.setGoals_scored_min_51_to_60(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_scored_min_51_to_60));
		tsNew.setGoals_scored_min_61_to_70(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_scored_min_61_to_70));
		tsNew.setGoals_scored_min_71_to_80(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_scored_min_71_to_80));
		tsNew.setGoals_scored_min_81_to_90(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_scored_min_81_to_90));
		tsNew.setGoals_conceded_min_0_to_10(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_conceded_min_0_to_10));
		tsNew.setGoals_conceded_min_11_to_20(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_conceded_min_11_to_20));
		tsNew.setGoals_conceded_min_21_to_30(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_conceded_min_21_to_30));
		tsNew.setGoals_conceded_min_31_to_40(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_conceded_min_31_to_40));
		tsNew.setGoals_conceded_min_41_to_50(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_conceded_min_41_to_50));
		tsNew.setGoals_conceded_min_51_to_60(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_conceded_min_51_to_60));
		tsNew.setGoals_conceded_min_61_to_70(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_conceded_min_61_to_70));
		tsNew.setGoals_conceded_min_71_to_80(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_conceded_min_71_to_80));
		tsNew.setGoals_conceded_min_81_to_90(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoals_conceded_min_81_to_90));
		tsNew.setDraw_percentage_overall(integerAverageRoundUp(matchingTeamStats, TeamStats::getDraw_percentage_overall));
		tsNew.setDraw_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getDraw_percentage_home));
		tsNew.setDraw_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getDraw_percentage_away));
		tsNew.setLoss_percentage_ovearll(integerAverageRoundUp(matchingTeamStats, TeamStats::getLoss_percentage_ovearll));
		tsNew.setLoss_percentage_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getLoss_percentage_home));
		tsNew.setLoss_percentage_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getLoss_percentage_away));
		tsNew.setOver145_corners_percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver145_corners_percentage));
		tsNew.setWins(integerAverageRoundUp(matchingTeamStats, TeamStats::getWins));
		tsNew.setWins_home(integerAverageRoundUp(matchingTeamStats, TeamStats::getWins_home));
		tsNew.setWins_away(integerAverageRoundUp(matchingTeamStats, TeamStats::getWins_away));
		return tsNew;
	}

	private int integerAverageRoundUp(Collection<TeamStats> stats, ToIntFunction<TeamStats> mapper){
		return (int)Math.round(stats.stream().mapToInt(mapper).average().getAsDouble());
	}

	private Double floatAverage(Collection<TeamStats> stats, ToDoubleFunction<TeamStats> mapper){
		var bd = stats.stream().map(ts -> new BigDecimal(mapper.applyAsDouble(ts))).collect(Collectors.toList());
		var res = BigDecimal.ZERO;
		for (BigDecimal bigDecimal : bd) {
			res = res.add(bigDecimal);
		}

		return res.divide(new BigDecimal(bd.size()), RoundingMode.HALF_UP).doubleValue();
	}

	@Override
	public Query upsertQuery(TeamStats example) {
		return Query.query(Criteria.where("country").is(example.getCountry()).and("teamName").is(example.getTeamName()).and("season").is(example.getSeason()));
	}

	@Override
	public Class<TeamStats> upsertType() {
		return TeamStats.class;
	}
}
