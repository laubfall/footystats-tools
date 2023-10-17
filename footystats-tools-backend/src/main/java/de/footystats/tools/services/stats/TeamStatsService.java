package de.footystats.tools.services.stats;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.services.MongoService;
import de.footystats.tools.services.csv.CsvFileService;
import de.footystats.tools.services.domain.Country;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TeamStatsService extends MongoService<TeamStats> {

	private final CsvFileService<TeamStats> teamStatsCsvFileService;

	private final TeamStatsRepository teamStatsRepository;

	private final FootystatsProperties footystatsProperties;

	public TeamStatsService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter,
		CsvFileService<TeamStats> teamStatsCsvFileService, TeamStatsRepository teamStatsRepository, FootystatsProperties footystatsProperties) {
		super(mongoTemplate, mappingMongoConverter);
		this.teamStatsCsvFileService = teamStatsCsvFileService;
		this.teamStatsRepository = teamStatsRepository;
		this.footystatsProperties = footystatsProperties;
	}

	public Collection<TeamStats> readTeamStats(InputStream data) {
		List<TeamStats> stats = teamStatsCsvFileService.importFile(data, TeamStats.class);
		log.info("Read {} team stats and upsert them into the database.", stats.size());
		stats.forEach(this::upsert);
		return stats;
	}

	public Collection<TeamStats> latestThree(String team, Country country, Integer year) {
		// Holds the seasons for the last 3 years, the current year and the next year, e.g. year is 2022: 2021/2022, 2020/2021, 2022/2023, 2022, 2023
		var seasons = new String[]{year - 1 + "/" + year, year - 2 + "/" + (year - 1), year + "/" + (year + 1), year + "", year + 1 + "",
			year - 1 + ""};
		Query query = Query.query(Criteria.where("country").is(country).and("season").in(seasons)
			.orOperator(Criteria.where("teamName").is(team), Criteria.where("commonName").is(team)));
		return mongoTemplate.find(query, TeamStats.class);
	}

	public TeamStats aggregate(String team, Country country, Integer year) {
		return aggregate(latestThree(team, country, year));
	}

	public TeamStats aggregate(Collection<TeamStats> teamStats) {
		if (teamStats == null || teamStats.isEmpty()) {
			return null;
		}
		var matchingTeamStats = teamStats.stream()
			.filter(ts -> ts.getMatchesPlayed() >= footystatsProperties.getIgnoreTeamStatsWithGamesPlayedLowerThan()).toList();
		if (matchingTeamStats.isEmpty()) {
			return null;
		}

		var probe = matchingTeamStats.get(0);

		var tsNew = new TeamStats();
		tsNew.setTeamName(probe.getTeamName());
		tsNew.setCommonName(probe.getCommonName());
		tsNew.setSeason("");
		tsNew.setCountry(probe.getCountry());
		tsNew.setMatchesPlayed(integerAverageRoundUp(matchingTeamStats, TeamStats::getMatchesPlayed));
		tsNew.setMatchesPlayedHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getMatchesPlayedHome));
		tsNew.setMatchesPlayedAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getMatchesPlayedAway));
		tsNew.setSuspendedMatches(integerAverageRoundUp(matchingTeamStats, TeamStats::getSuspendedMatches));
		tsNew.setWins(integerAverageRoundUp(matchingTeamStats, TeamStats::getWins));
		tsNew.setWinsHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getWinsHome));
		tsNew.setWinsAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getWinsAway));
		tsNew.setDraws(integerAverageRoundUp(matchingTeamStats, TeamStats::getDraws));
		tsNew.setDrawsHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getDrawsHome));
		tsNew.setDrawsAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getDrawsAway));
		tsNew.setLosses(integerAverageRoundUp(matchingTeamStats, TeamStats::getLosses));
		tsNew.setLossesHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getLossesHome));
		tsNew.setLossesAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getLossesAway));
		tsNew.setPointsPerGame(doubleAverage(matchingTeamStats, TeamStats::getPointsPerGame));
		tsNew.setPointsPerGameHome(doubleAverage(matchingTeamStats, TeamStats::getPointsPerGameHome));
		tsNew.setPointsPerGameAway(doubleAverage(matchingTeamStats, TeamStats::getPointsPerGameAway));
		tsNew.setLeaguePosition(integerAverageRoundUp(matchingTeamStats, TeamStats::getLeaguePosition));
		tsNew.setLeaguePositionHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getLeaguePositionHome));
		tsNew.setLeaguePositionAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getLeaguePositionAway));
		tsNew.setPerformanceRank(integerAverageRoundUp(matchingTeamStats, TeamStats::getPerformanceRank));
		tsNew.setGoalsScored(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsScored));
		tsNew.setGoalsConceded(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsConceded));
		tsNew.setGoalDifference(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalDifference));
		tsNew.setTotalGoalCount(integerAverageRoundUp(matchingTeamStats, TeamStats::getTotalGoalCount));
		tsNew.setTotalGoalCountHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getTotalGoalCountHome));
		tsNew.setTotalGoalCountAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getTotalGoalCountAway));
		tsNew.setGoalsScoredHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsScoredHome));
		tsNew.setGoalsScoredAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsScoredAway));
		tsNew.setGoalsConcededHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsConcededHome));
		tsNew.setGoalsConcededAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsConcededAway));
		tsNew.setGoalDifferenceHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalDifferenceHome));
		tsNew.setGoalDifferenceAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalDifferenceAway));
		tsNew.setMinutesPerGoalScored(integerAverageRoundUp(matchingTeamStats, TeamStats::getMinutesPerGoalScored));
		tsNew.setMinutesPerGoalScoredHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getMinutesPerGoalScoredHome));
		tsNew.setMinutesPerGoalScoredAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getMinutesPerGoalScoredAway));
		tsNew.setMinutesPerGoalConceded(integerAverageRoundUp(matchingTeamStats, TeamStats::getMinutesPerGoalConceded));
		tsNew.setMinutesPerGoalConcededHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getMinutesPerGoalConcededHome));
		tsNew.setMinutesPerGoalConcededAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getMinutesPerGoalConcededAway));
		tsNew.setCleanSheets(integerAverageRoundUp(matchingTeamStats, TeamStats::getCleanSheets));
		tsNew.setCleanSheetsHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getCleanSheetsHome));
		tsNew.setCleanSheetsAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getCleanSheetsAway));
		tsNew.setBttsCount(integerAverageRoundUp(matchingTeamStats, TeamStats::getBttsCount));
		tsNew.setBttsCountHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getBttsCountHome));
		tsNew.setBttsCountAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getBttsCountAway));
		tsNew.setFtsCount(integerAverageRoundUp(matchingTeamStats, TeamStats::getFtsCount));
		tsNew.setFtsCountHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getFtsCountHome));
		tsNew.setFtsCountAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getFtsCountAway));
		tsNew.setFirstTeamToScoreCount(integerAverageRoundUp(matchingTeamStats, TeamStats::getFirstTeamToScoreCount));
		tsNew.setFirstTeamToScoreCountHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getFirstTeamToScoreCountHome));
		tsNew.setFirstTeamToScoreCountAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getFirstTeamToScoreCountAway));
		tsNew.setCornersTotal(integerAverageRoundUp(matchingTeamStats, TeamStats::getCornersTotal));
		tsNew.setCornersTotalHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getCornersTotalHome));
		tsNew.setCornersTotalAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getCornersTotalAway));
		tsNew.setCardsTotal(integerAverageRoundUp(matchingTeamStats, TeamStats::getCardsTotal));
		tsNew.setCardsTotalHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getCardsTotalHome));
		tsNew.setCardsTotalAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getCardsTotalAway));
		tsNew.setAveragePossession(integerAverageRoundUp(matchingTeamStats, TeamStats::getAveragePossession));
		tsNew.setAveragePossessionHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getAveragePossessionHome));
		tsNew.setAveragePossessionAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getAveragePossessionAway));
		tsNew.setShots(integerAverageRoundUp(matchingTeamStats, TeamStats::getShots));
		tsNew.setShotsHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getShotsHome));
		tsNew.setShotsAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getShotsAway));
		tsNew.setShotsOnTarget(integerAverageRoundUp(matchingTeamStats, TeamStats::getShotsOnTarget));
		tsNew.setShotsOnTargetHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getShotsOnTargetHome));
		tsNew.setShotsOnTargetAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getShotsOnTargetAway));
		tsNew.setShotsOffTarget(integerAverageRoundUp(matchingTeamStats, TeamStats::getShotsOffTarget));
		tsNew.setShotsOffTargetHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getShotsOffTargetHome));
		tsNew.setShotsOffTargetAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getShotsOffTargetAway));
		tsNew.setFouls(integerAverageRoundUp(matchingTeamStats, TeamStats::getFouls));
		tsNew.setFoulsHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getFoulsHome));
		tsNew.setFoulsAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getFoulsAway));
		tsNew.setGoalsScoredHalfTime(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsScoredHalfTime));
		tsNew.setGoalsScoredHalfTimeHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsScoredHalfTimeHome));
		tsNew.setGoalsScoredHalfTimeAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsScoredHalfTimeAway));
		tsNew.setGoalsConcededHalfTime(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsConcededHalfTime));
		tsNew.setGoalsConcededHalfTimeHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsConcededHalfTimeHome));
		tsNew.setGoalsConcededHalfTimeAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsConcededHalfTimeAway));
		tsNew.setGoalDifferenceHalfTime(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalDifferenceHalfTime));
		tsNew.setGoalDifferenceHalfTimeHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalDifferenceHalfTimeHome));
		tsNew.setGoalDifferenceHalfTimeAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalDifferenceHalfTimeAway));
		tsNew.setLeadingAtHalfTime(integerAverageRoundUp(matchingTeamStats, TeamStats::getLeadingAtHalfTime));
		tsNew.setLeadingAtHalfTimeHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getLeadingAtHalfTimeHome));
		tsNew.setLeadingAtHalfTimeAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getLeadingAtHalfTimeAway));
		tsNew.setDrawAtHalfTime(integerAverageRoundUp(matchingTeamStats, TeamStats::getDrawAtHalfTime));
		tsNew.setDrawAtHalfTimeHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getDrawAtHalfTimeHome));
		tsNew.setDrawAtHalfTimeAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getDrawAtHalfTimeAway));
		tsNew.setLosingAtHalfTime(integerAverageRoundUp(matchingTeamStats, TeamStats::getLosingAtHalfTime));
		tsNew.setLosingAtHalfTimeHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getLosingAtHalfTimeHome));
		tsNew.setLosingAtHalfTimeAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getLosingAtHalfTimeAway));
		tsNew.setPointsPerGameHalfTime(doubleAverage(matchingTeamStats, TeamStats::getPointsPerGameHalfTime));
		tsNew.setPointsPerGameHalfTimeHome(doubleAverage(matchingTeamStats, TeamStats::getPointsPerGameHalfTimeHome));
		tsNew.setPointsPerGameHalfTimeAway(doubleAverage(matchingTeamStats, TeamStats::getPointsPerGameHalfTimeAway));
		tsNew.setAverageTotalGoalsPerMatch(doubleAverage(matchingTeamStats, TeamStats::getAverageTotalGoalsPerMatch));
		tsNew.setAverageTotalGoalsPerMatchHome(doubleAverage(matchingTeamStats, TeamStats::getAverageTotalGoalsPerMatchHome));
		tsNew.setAverageTotalGoalsPerMatchAway(doubleAverage(matchingTeamStats, TeamStats::getAverageTotalGoalsPerMatchAway));
		tsNew.setGoalsScoredPerMatch(doubleAverage(matchingTeamStats, TeamStats::getGoalsScoredPerMatch));
		tsNew.setGoalsScoredPerMatchHome(doubleAverage(matchingTeamStats, TeamStats::getGoalsScoredPerMatchHome));
		tsNew.setGoalsScoredPerMatchAway(doubleAverage(matchingTeamStats, TeamStats::getGoalsScoredPerMatchAway));
		tsNew.setGoalsConcededPerMatch(doubleAverage(matchingTeamStats, TeamStats::getGoalsConcededPerMatch));
		tsNew.setGoalsConcededPerMatchHome(doubleAverage(matchingTeamStats, TeamStats::getGoalsConcededPerMatchHome));
		tsNew.setGoalsConcededPerMatchAway(doubleAverage(matchingTeamStats, TeamStats::getGoalsConcededPerMatchAway));
		tsNew.setTotalGoalsPerMatchHalfTime(doubleAverage(matchingTeamStats, TeamStats::getTotalGoalsPerMatchHalfTime));
		tsNew.setTotalGoalsPerMatchHalfTimeHome(doubleAverage(matchingTeamStats, TeamStats::getTotalGoalsPerMatchHalfTimeHome));
		tsNew.setTotalGoalsPerMatchHalfTimeAway(doubleAverage(matchingTeamStats, TeamStats::getTotalGoalsPerMatchHalfTimeAway));
		tsNew.setGoalsScoredPerMatchHalfTime(doubleAverage(matchingTeamStats, TeamStats::getGoalsScoredPerMatchHalfTime));
		tsNew.setGoalsScoredPerMatchHalfTimeHome(doubleAverage(matchingTeamStats, TeamStats::getGoalsScoredPerMatchHalfTimeHome));
		tsNew.setGoalsScoredPerMatchHalfTimeAway(doubleAverage(matchingTeamStats, TeamStats::getGoalsScoredPerMatchHalfTimeAway));
		tsNew.setGoalsConcededPerMatchHalfTime(doubleAverage(matchingTeamStats, TeamStats::getGoalsConcededPerMatchHalfTime));
		tsNew.setGoalsConcededPerMatchHalfTimeHome(doubleAverage(matchingTeamStats, TeamStats::getGoalsConcededPerMatchHalfTimeHome));
		tsNew.setGoalsConcededPerMatchHalfTimeAway(doubleAverage(matchingTeamStats, TeamStats::getGoalsConcededPerMatchHalfTimeAway));
		tsNew.setOver05Count(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver05Count));
		tsNew.setOver15Count(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver15Count));
		tsNew.setOver25Count(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver25Count));
		tsNew.setOver35Count(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver35Count));
		tsNew.setOver45Count(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver45Count));
		tsNew.setOver55Count(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver55Count));
		tsNew.setOver05CountHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver05CountHome));
		tsNew.setOver15CountHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver15CountHome));
		tsNew.setOver25CountHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver25CountHome));
		tsNew.setOver35CountHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver35CountHome));
		tsNew.setOver45CountHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver45CountHome));
		tsNew.setOver55CountHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver55CountHome));
		tsNew.setOver05CountAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver05CountAway));
		tsNew.setOver15CountAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver15CountAway));
		tsNew.setOver25CountAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver25CountAway));
		tsNew.setOver35CountAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver35CountAway));
		tsNew.setOver45CountAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver45CountAway));
		tsNew.setOver55CountAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver55CountAway));
		tsNew.setUnder05Count(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder05Count));
		tsNew.setUnder15Count(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder15Count));
		tsNew.setUnder25Count(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder25Count));
		tsNew.setUnder35Count(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder35Count));
		tsNew.setUnder45Count(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder45Count));
		tsNew.setUnder55Count(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder55Count));
		tsNew.setUnder05CountHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder05CountHome));
		tsNew.setUnder15CountHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder15CountHome));
		tsNew.setUnder25CountHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder25CountHome));
		tsNew.setUnder35CountHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder35CountHome));
		tsNew.setUnder45CountHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder45CountHome));
		tsNew.setUnder55CountHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder55CountHome));
		tsNew.setUnder05CountAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder05CountAway));
		tsNew.setUnder15CountAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder15CountAway));
		tsNew.setUnder25CountAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder25CountAway));
		tsNew.setUnder35CountAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder35CountAway));
		tsNew.setUnder45CountAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder45CountAway));
		tsNew.setUnder55CountAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder55CountAway));
		tsNew.setOver05Percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver05Percentage));
		tsNew.setOver15Percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver15Percentage));
		tsNew.setOver25Percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver25Percentage));
		tsNew.setOver35Percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver35Percentage));
		tsNew.setOver45Percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver45Percentage));
		tsNew.setOver55Percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver55Percentage));
		tsNew.setOver05PercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver05PercentageHome));
		tsNew.setOver15PercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver15PercentageHome));
		tsNew.setOver25PercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver25PercentageHome));
		tsNew.setOver35PercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver35PercentageHome));
		tsNew.setOver45PercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver45PercentageHome));
		tsNew.setOver55PercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver55PercentageHome));
		tsNew.setOver05PercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver05PercentageAway));
		tsNew.setOver15PercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver15PercentageAway));
		tsNew.setOver25PercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver25PercentageAway));
		tsNew.setOver35PercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver35PercentageAway));
		tsNew.setOver45PercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver45PercentageAway));
		tsNew.setOver55PercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver55PercentageAway));
		tsNew.setUnder05Percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder05Percentage));
		tsNew.setUnder15Percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder15Percentage));
		tsNew.setUnder25Percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder25Percentage));
		tsNew.setUnder35Percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder35Percentage));
		tsNew.setUnder45Percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder45Percentage));
		tsNew.setUnder55Percentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder55Percentage));
		tsNew.setUnder05PercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder05PercentageHome));
		tsNew.setUnder15PercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder15PercentageHome));
		tsNew.setUnder25PercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder25PercentageHome));
		tsNew.setUnder35PercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder35PercentageHome));
		tsNew.setUnder45PercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder45PercentageHome));
		tsNew.setUnder55PercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder55PercentageHome));
		tsNew.setUnder05PercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder05PercentageAway));
		tsNew.setUnder15PercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder15PercentageAway));
		tsNew.setUnder25PercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder25PercentageAway));
		tsNew.setUnder35PercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder35PercentageAway));
		tsNew.setUnder45PercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder45PercentageAway));
		tsNew.setUnder55PercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getUnder55PercentageAway));
		tsNew.setOver05CountHalfTime(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver05CountHalfTime));
		tsNew.setOver15CountHalfTime(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver15CountHalfTime));
		tsNew.setOver25CountHalfTime(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver25CountHalfTime));
		tsNew.setOver05CountHalfTimeHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver05CountHalfTimeHome));
		tsNew.setOver15CountHalfTimeHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver15CountHalfTimeHome));
		tsNew.setOver25CountHalfTimeHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver25CountHalfTimeHome));
		tsNew.setOver05CountHalfTimeAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver05CountHalfTimeAway));
		tsNew.setOver15CountHalfTimeAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver15CountHalfTimeAway));
		tsNew.setOver25CountHalfTimeAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver25CountHalfTimeAway));
		tsNew.setOver05HalfTimePercentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver05HalfTimePercentage));
		tsNew.setOver15HalfTimePercentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver15HalfTimePercentage));
		tsNew.setOver25HalfTimePercentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver25HalfTimePercentage));
		tsNew.setOver05HalfTimePercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver05HalfTimePercentageHome));
		tsNew.setOver15HalfTimePercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver15HalfTimePercentageHome));
		tsNew.setOver25HalfTimePercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver25HalfTimePercentageHome));
		tsNew.setOver05HalfTimePercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver05HalfTimePercentageAway));
		tsNew.setOver15HalfTimePercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver15HalfTimePercentageAway));
		tsNew.setOver25HalfTimePercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver25HalfTimePercentageAway));
		tsNew.setWinPercentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getWinPercentage));
		tsNew.setWinPercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getWinPercentageHome));
		tsNew.setWinPercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getWinPercentageAway));
		tsNew.setHomeAdvantagePercentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getHomeAdvantagePercentage));
		tsNew.setCleanSheetPercentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getCleanSheetPercentage));
		tsNew.setCleanSheetPercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getCleanSheetPercentageHome));
		tsNew.setCleanSheetPercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getCleanSheetPercentageAway));
		tsNew.setBttsPercentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getBttsPercentage));
		tsNew.setBttsPercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getBttsPercentageHome));
		tsNew.setBttsPercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getBttsPercentageAway));
		tsNew.setFtsPercentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getFtsPercentage));
		tsNew.setFtsPercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getFtsPercentageHome));
		tsNew.setFtsPercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getFtsPercentageAway));
		tsNew.setFirstTeamToScorePercentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getFirstTeamToScorePercentage));
		tsNew.setFirstTeamToScorePercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getFirstTeamToScorePercentageHome));
		tsNew.setFirstTeamToScorePercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getFirstTeamToScorePercentageAway));
		tsNew.setCleanSheetHalfTime(integerAverageRoundUp(matchingTeamStats, TeamStats::getCleanSheetHalfTime));
		tsNew.setCleanSheetHalfTimeHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getCleanSheetHalfTimeHome));
		tsNew.setCleanSheetHalfTimeAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getCleanSheetHalfTimeAway));
		tsNew.setCleanSheetHalfTimePercentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getCleanSheetHalfTimePercentage));
		tsNew.setCleanSheetHalfTimePercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getCleanSheetHalfTimePercentageHome));
		tsNew.setCleanSheetHalfTimePercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getCleanSheetHalfTimePercentageAway));
		tsNew.setFtsHalfTime(integerAverageRoundUp(matchingTeamStats, TeamStats::getFtsHalfTime));
		tsNew.setFtsHalfTimeHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getFtsHalfTimeHome));
		tsNew.setFtsHalfTimeAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getFtsHalfTimeAway));
		tsNew.setFtsHalfTimePercentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getFtsHalfTimePercentage));
		tsNew.setFtsHalfTimePercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getFtsHalfTimePercentageHome));
		tsNew.setFtsHalfTimePercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getFtsHalfTimePercentageAway));
		tsNew.setBttsHalfTime(integerAverageRoundUp(matchingTeamStats, TeamStats::getBttsHalfTime));
		tsNew.setBttsHalfTimeHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getBttsHalfTimeHome));
		tsNew.setBttsHalfTimeAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getBttsHalfTimeAway));
		tsNew.setBttsHalfTimePercentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getBttsHalfTimePercentage));
		tsNew.setBttsHalfTimePercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getBttsHalfTimePercentageHome));
		tsNew.setBttsHalfTimePercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getBttsHalfTimePercentageAway));
		tsNew.setLeadingAtHalfTimePercentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getLeadingAtHalfTimePercentage));
		tsNew.setLeadingAtHalfTimePercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getLeadingAtHalfTimePercentageHome));
		tsNew.setLeadingAtHalfTimePercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getLeadingAtHalfTimePercentageAway));
		tsNew.setDrawAtHalfTimePercentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getDrawAtHalfTimePercentage));
		tsNew.setDrawAtHalfTimePercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getDrawAtHalfTimePercentageHome));
		tsNew.setDrawAtHalfTimePercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getDrawAtHalfTimePercentageAway));
		tsNew.setLosingAtHalfTimePercentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getLosingAtHalfTimePercentage));
		tsNew.setLosingAtHalfTimePercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getLosingAtHalfTimePercentageHome));
		tsNew.setLosingAtHalfTimePercentageAway(doubleAverage(matchingTeamStats, TeamStats::getLosingAtHalfTimePercentageAway));
		tsNew.setCornersPerMatch(doubleAverage(matchingTeamStats, TeamStats::getCornersPerMatch));
		tsNew.setCornersPerMatchHome(doubleAverage(matchingTeamStats, TeamStats::getCornersPerMatchHome));
		tsNew.setCornersPerMatchAway(doubleAverage(matchingTeamStats, TeamStats::getCornersPerMatchAway));
		tsNew.setCardsPerMatch(doubleAverage(matchingTeamStats, TeamStats::getCardsPerMatch));
		tsNew.setCardsPerMatchHome(doubleAverage(matchingTeamStats, TeamStats::getCardsPerMatchHome));
		tsNew.setCardsPerMatchAway(doubleAverage(matchingTeamStats, TeamStats::getCardsPerMatchAway));
		tsNew.setOver65CornersPercentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver65CornersPercentage));
		tsNew.setOver75CornersPercentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver75CornersPercentage));
		tsNew.setOver85CornersPercentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver85CornersPercentage));
		tsNew.setOver95CornersPercentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver95CornersPercentage));
		tsNew.setOver105CornersPercentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver105CornersPercentage));
		tsNew.setOver115CornersPercentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver115CornersPercentage));
		tsNew.setOver125CornersPercentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver125CornersPercentage));
		tsNew.setOver135CornersPercentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver135CornersPercentage));
		tsNew.setXgForAvgOverall(doubleAverage(matchingTeamStats, TeamStats::getXgForAvgOverall));
		tsNew.setXgForAvgHome(doubleAverage(matchingTeamStats, TeamStats::getXgForAvgHome));
		tsNew.setXgForAvgAway(doubleAverage(matchingTeamStats, TeamStats::getXgForAvgAway));
		tsNew.setXgAgainstAvgOverall(doubleAverage(matchingTeamStats, TeamStats::getXgAgainstAvgOverall));
		tsNew.setXgAgainstAvgHome(doubleAverage(matchingTeamStats, TeamStats::getXgAgainstAvgHome));
		tsNew.setXgAgainstAvgAway(doubleAverage(matchingTeamStats, TeamStats::getXgAgainstAvgAway));
		tsNew.setPredictionRisk(integerAverageRoundUp(matchingTeamStats, TeamStats::getPredictionRisk));
		tsNew.setGoalsScoredMin0To10(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsScoredMin0To10));
		tsNew.setGoalsScoredMin11To20(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsScoredMin11To20));
		tsNew.setGoalsScoredMin21To30(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsScoredMin21To30));
		tsNew.setGoalsScoredMin31To40(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsScoredMin31To40));
		tsNew.setGoalsScoredMin41To50(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsScoredMin41To50));
		tsNew.setGoalsScoredMin51To60(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsScoredMin51To60));
		tsNew.setGoalsScoredMin61To70(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsScoredMin61To70));
		tsNew.setGoalsScoredMin71To80(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsScoredMin71To80));
		tsNew.setGoalsScoredMin81To90(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsScoredMin81To90));
		tsNew.setGoalsConcededMin0To10(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsConcededMin0To10));
		tsNew.setGoalsConcededMin11To20(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsConcededMin11To20));
		tsNew.setGoalsConcededMin21To30(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsConcededMin21To30));
		tsNew.setGoalsConcededMin31To40(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsConcededMin31To40));
		tsNew.setGoalsConcededMin41To50(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsConcededMin41To50));
		tsNew.setGoalsConcededMin51To60(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsConcededMin51To60));
		tsNew.setGoalsConcededMin61To70(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsConcededMin61To70));
		tsNew.setGoalsConcededMin71To80(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsConcededMin71To80));
		tsNew.setGoalsConcededMin81To90(integerAverageRoundUp(matchingTeamStats, TeamStats::getGoalsConcededMin81To90));
		tsNew.setDrawPercentageOverall(integerAverageRoundUp(matchingTeamStats, TeamStats::getDrawPercentageOverall));
		tsNew.setDrawPercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getDrawPercentageHome));
		tsNew.setDrawPercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getDrawPercentageAway));
		tsNew.setLossPercentageOvearll(integerAverageRoundUp(matchingTeamStats, TeamStats::getLossPercentageOvearll));
		tsNew.setLossPercentageHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getLossPercentageHome));
		tsNew.setLossPercentageAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getLossPercentageAway));
		tsNew.setOver145CornersPercentage(integerAverageRoundUp(matchingTeamStats, TeamStats::getOver145CornersPercentage));
		tsNew.setWins(integerAverageRoundUp(matchingTeamStats, TeamStats::getWins));
		tsNew.setWinsHome(integerAverageRoundUp(matchingTeamStats, TeamStats::getWinsHome));
		tsNew.setWinsAway(integerAverageRoundUp(matchingTeamStats, TeamStats::getWinsAway));
		return tsNew;
	}

	private int integerAverageRoundUp(Collection<TeamStats> stats, ToIntFunction<TeamStats> mapper) {
		return (int) Math.round(stats.stream().mapToInt(mapper).average().getAsDouble());
	}

	private Double doubleAverage(Collection<TeamStats> stats, ToDoubleFunction<TeamStats> mapper) {
		var bd = stats.stream().map(ts -> BigDecimal.valueOf(mapper.applyAsDouble(ts))).toList();
		var res = BigDecimal.ZERO;
		for (BigDecimal bigDecimal : bd) {
			res = res.add(bigDecimal);
		}

		return res.divide(new BigDecimal(bd.size())).doubleValue();
	}

	@Override
	public Query upsertQuery(TeamStats example) {
		return Query.query(
			Criteria.where("country").is(example.getCountry()).and("teamName").is(example.getTeamName()).and("season").is(example.getSeason()));
	}

	@Override
	public Class<TeamStats> upsertType() {
		return TeamStats.class;
	}
}
