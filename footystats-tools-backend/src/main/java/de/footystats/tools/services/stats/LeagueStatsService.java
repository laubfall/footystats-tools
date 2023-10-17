package de.footystats.tools.services.stats;

import de.footystats.tools.services.MongoService;
import de.footystats.tools.services.csv.CsvFileService;
import de.footystats.tools.services.csv.ICsvFileInformation;
import de.footystats.tools.services.domain.Country;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LeagueStatsService extends MongoService<LeagueStats> {

	private final CsvFileService<LeagueStats> csvFileService;

	public LeagueStatsService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter,
		CsvFileService<LeagueStats> csvFileService) {
		super(mongoTemplate, mappingMongoConverter);
		this.csvFileService = csvFileService;
	}

	public Collection<LeagueStats> readLeagueStats(ICsvFileInformation fileInformation, InputStream data) {
		List<LeagueStats> stats = csvFileService.importFile(data, LeagueStats.class);
		log.info("Read {} league stats from file", stats.size());
		if (fileInformation.country() == null) {
			log.error("Country is null for file {}", fileInformation.type());
			return Collections.emptyList();
		}
		stats.forEach(ls -> {
			ls.setCountry(fileInformation.country());
			upsert(ls);

		});
		return stats;
	}

	public Collection<LeagueStats> latestThree(String league, Country country, Integer year) {
		// Holds the seasons for the last 3 years, the current year and the next year, e.g. year is 2022: 2021/2022, 2020/2021, 2022/2023, 2022, 2023
		var seasons = new String[]{year - 1 + "/" + year, year - 2 + "/" + (year - 1), year + "/" + (year + 1), year + "", year + 1 + "",
			year - 1 + ""};
		Query query = Query.query(Criteria.where("name").is(league).and("country").is(country).and("season").in(seasons));
		return mongoTemplate.find(query, LeagueStats.class);
	}

	public LeagueStats aggregate(String league, Country country, Integer year) {
		Collection<LeagueStats> leagueStats = latestThree(league, country, year);
		return aggregate(leagueStats);
	}

	public LeagueStats aggregate(Collection<LeagueStats> leagueStats) {
		Optional<LeagueStats> leagueStatsSampler = leagueStats.stream().findFirst();
		if (leagueStatsSampler.isEmpty()) {
			return null;
		}

		var leagueStatsFirst = leagueStatsSampler.get();
		var stats = new LeagueStats();
		stats.setCountry(leagueStatsFirst.getCountry());
		stats.setName(leagueStatsFirst.getName());
		stats.setSeason(leagueStatsFirst.getSeason());
		// Now do the aggregation for each field and set it in the stats object.

		stats.setNumberOfClubs(integerAverageRoundUp(leagueStats, LeagueStats::getNumberOfClubs));

		stats.setTotalMatches(integerAverageRoundUp(leagueStats, LeagueStats::getTotalMatches));

		stats.setMatchesCompleted(integerAverageRoundUp(leagueStats, LeagueStats::getMatchesCompleted));

		stats.setGameWeek(integerAverageRoundUp(leagueStats, LeagueStats::getGameWeek));

		stats.setTotalGameWeek(integerAverageRoundUp(leagueStats, LeagueStats::getTotalGameWeek));

		stats.setProgress(integerAverageRoundUp(leagueStats, LeagueStats::getProgress));
		stats.setAverageGoalsPerMatch(doubleAverage(leagueStats, LeagueStats::getAverageGoalsPerMatch));
		stats.setAverageScoredHomeTeam(doubleAverage(leagueStats, LeagueStats::getAverageScoredHomeTeam));
		stats.setAverageScoredAwayTeam(doubleAverage(leagueStats, LeagueStats::getAverageScoredAwayTeam));

		stats.setBttsPercentage(integerAverageRoundUp(leagueStats, LeagueStats::getBttsPercentage));

		stats.setCleanSheetsPercentage(integerAverageRoundUp(leagueStats, LeagueStats::getCleanSheetsPercentage));

		stats.setPredictionRisk(integerAverageRoundUp(leagueStats, LeagueStats::getPredictionRisk));

		stats.setHomeScoredAdvantagePercentage(integerAverageRoundUp(leagueStats, LeagueStats::getHomeScoredAdvantagePercentage));

		stats.setHomeDefenceAdvantagePercentage(integerAverageRoundUp(leagueStats, LeagueStats::getHomeDefenceAdvantagePercentage));

		stats.setHomeAdvantagePercentage(integerAverageRoundUp(leagueStats, LeagueStats::getHomeAdvantagePercentage));
		stats.setAverageCornersPerMatch(doubleAverage(leagueStats, LeagueStats::getAverageCornersPerMatch));
		stats.setAverageCornersPerMatchHomeTeam(doubleAverage(leagueStats, LeagueStats::getAverageCornersPerMatchHomeTeam));
		stats.setAverageCornersPerMatchAwayTeam(doubleAverage(leagueStats, LeagueStats::getAverageCornersPerMatchAwayTeam));

		stats.setTotalCornersForSeason(integerAverageRoundUp(leagueStats, LeagueStats::getTotalCornersForSeason));
		stats.setAverageCardsPerMatch(doubleAverage(leagueStats, LeagueStats::getAverageCardsPerMatch));
		stats.setAverageCardsPerMatchHomeTeam(doubleAverage(leagueStats, LeagueStats::getAverageCardsPerMatchHomeTeam));

		stats.setTotalCardsForSeason(integerAverageRoundUp(leagueStats, LeagueStats::getTotalCardsForSeason));

		stats.setOver05Percentage(integerAverageRoundUp(leagueStats, LeagueStats::getOver05Percentage));

		stats.setOver15Percentage(integerAverageRoundUp(leagueStats, LeagueStats::getOver15Percentage));

		stats.setOver25Percentage(integerAverageRoundUp(leagueStats, LeagueStats::getOver25Percentage));

		stats.setOver35Percentage(integerAverageRoundUp(leagueStats, LeagueStats::getOver35Percentage));

		stats.setOver45Percentage(integerAverageRoundUp(leagueStats, LeagueStats::getOver45Percentage));

		stats.setOver55Percentage(integerAverageRoundUp(leagueStats, LeagueStats::getOver55Percentage));

		stats.setUnder05Percentage(integerAverageRoundUp(leagueStats, LeagueStats::getUnder05Percentage));

		stats.setUnder15Percentage(integerAverageRoundUp(leagueStats, LeagueStats::getUnder15Percentage));

		stats.setUnder25Percentage(integerAverageRoundUp(leagueStats, LeagueStats::getUnder25Percentage));

		stats.setUnder35Percentage(integerAverageRoundUp(leagueStats, LeagueStats::getUnder35Percentage));

		stats.setUnder45Percentage(integerAverageRoundUp(leagueStats, LeagueStats::getUnder45Percentage));

		stats.setUnder55Percentage(integerAverageRoundUp(leagueStats, LeagueStats::getUnder55Percentage));

		stats.setOver65CornersPercentage(integerAverageRoundUp(leagueStats, LeagueStats::getOver65CornersPercentage));

		stats.setOver75CornersPercentage(integerAverageRoundUp(leagueStats, LeagueStats::getOver75CornersPercentage));

		stats.setOver85CornersPercentage(integerAverageRoundUp(leagueStats, LeagueStats::getOver85CornersPercentage));

		stats.setOver95CornersPercentage(integerAverageRoundUp(leagueStats, LeagueStats::getOver95CornersPercentage));

		stats.setOver105CornersPercentage(integerAverageRoundUp(leagueStats, LeagueStats::getOver105CornersPercentage));

		stats.setOver115CornersPercentage(integerAverageRoundUp(leagueStats, LeagueStats::getOver115CornersPercentage));

		stats.setOver125CornersPercentage(integerAverageRoundUp(leagueStats, LeagueStats::getOver125CornersPercentage));

		stats.setOver135CornersPercentage(integerAverageRoundUp(leagueStats, LeagueStats::getOver135CornersPercentage));

		stats.setOver05CardsPercentage(integerAverageRoundUp(leagueStats, LeagueStats::getOver05CardsPercentage));

		stats.setOver15CardsPercentage(integerAverageRoundUp(leagueStats, LeagueStats::getOver15CardsPercentage));

		stats.setOver25CardsPercentage(integerAverageRoundUp(leagueStats, LeagueStats::getOver25CardsPercentage));

		stats.setOver35CardsPercentage(integerAverageRoundUp(leagueStats, LeagueStats::getOver35CardsPercentage));

		stats.setOver45CardsPercentage(integerAverageRoundUp(leagueStats, LeagueStats::getOver45CardsPercentage));

		stats.setOver55CardsPercentage(integerAverageRoundUp(leagueStats, LeagueStats::getOver55CardsPercentage));

		stats.setOver65CardsPercentage(integerAverageRoundUp(leagueStats, LeagueStats::getOver65CardsPercentage));

		stats.setOver75CardsPercentage(integerAverageRoundUp(leagueStats, LeagueStats::getOver75CardsPercentage));

		stats.setGoalsMin0To10(integerAverageRoundUp(leagueStats, LeagueStats::getGoalsMin0To10));

		stats.setGoalsMin11To20(integerAverageRoundUp(leagueStats, LeagueStats::getGoalsMin11To20));

		stats.setGoalsMin21To30(integerAverageRoundUp(leagueStats, LeagueStats::getGoalsMin21To30));

		stats.setGoalsMin31To40(integerAverageRoundUp(leagueStats, LeagueStats::getGoalsMin31To40));

		stats.setGoalsMin41To50(integerAverageRoundUp(leagueStats, LeagueStats::getGoalsMin41To50));

		stats.setGoalsMin51To60(integerAverageRoundUp(leagueStats, LeagueStats::getGoalsMin51To60));

		stats.setGoalsMin61To70(integerAverageRoundUp(leagueStats, LeagueStats::getGoalsMin61To70));

		stats.setGoalsMin71To80(integerAverageRoundUp(leagueStats, LeagueStats::getGoalsMin71To80));

		stats.setGoalsMin81To90(integerAverageRoundUp(leagueStats, LeagueStats::getGoalsMin81To90));

		stats.setGoalsMin0To15(integerAverageRoundUp(leagueStats, LeagueStats::getGoalsMin0To15));

		stats.setGoalsMin16To30(integerAverageRoundUp(leagueStats, LeagueStats::getGoalsMin16To30));

		stats.setGoalsMin31To45(integerAverageRoundUp(leagueStats, LeagueStats::getGoalsMin31To45));

		stats.setGoalsMin46To60(integerAverageRoundUp(leagueStats, LeagueStats::getGoalsMin46To60));
		stats.setGoalsMin61To75(integerAverageRoundUp(leagueStats, LeagueStats::getGoalsMin61To75));
		stats.setGoalsMin76To90(integerAverageRoundUp(leagueStats, LeagueStats::getGoalsMin76To90));
		stats.setXgAvgPerMatch(doubleAverage(leagueStats, LeagueStats::getXgAvgPerMatch));

		return stats;
	}

	private int integerAverageRoundUp(Collection<LeagueStats> stats, ToIntFunction<LeagueStats> mapper) {
		return (int) Math.round(stats.stream().mapToInt(mapper).average().getAsDouble());
	}

	private Double doubleAverage(Collection<LeagueStats> stats, Function<LeagueStats, Double> supplier) {
		var bd = stats.stream().map(ts -> {
			Double v = supplier.apply(ts);
			if (v == null) {
				return null;
			}
			return BigDecimal.valueOf(v);
		}).filter(Objects::nonNull).toList();

		var res = BigDecimal.ZERO;
		for (BigDecimal bigDecimal : bd) {
			res = res.add(bigDecimal);
		}

		if (bd.isEmpty()) {
			return 0D;
		}

		return res.divide(new BigDecimal(bd.size())).doubleValue();
	}

	@Override
	public Query upsertQuery(LeagueStats example) {
		return Query.query(Criteria.where("name").is(example.getName()).and("season").is(example.getSeason()));
	}

	@Override
	public Class<LeagueStats> upsertType() {
		return LeagueStats.class;
	}

}
