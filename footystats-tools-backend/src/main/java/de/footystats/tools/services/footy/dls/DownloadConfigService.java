package de.footystats.tools.services.footy.dls;

import static de.footystats.tools.services.footy.dls.DownloadCountryLeagueStatsConfig.FIELD_COUNTRY;
import static de.footystats.tools.services.footy.dls.DownloadCountryLeagueStatsConfig.FIELD_LAST_LEAGUE_DOWNLOAD;
import static de.footystats.tools.services.footy.dls.DownloadCountryLeagueStatsConfig.FIELD_LAST_MATCH_DOWNLOAD;
import static de.footystats.tools.services.footy.dls.DownloadCountryLeagueStatsConfig.FIELD_LAST_PLAYER_DOWNLOAD;
import static de.footystats.tools.services.footy.dls.DownloadCountryLeagueStatsConfig.FIELD_LAST_TEAMS_2_DOWNLOAD;
import static de.footystats.tools.services.footy.dls.DownloadCountryLeagueStatsConfig.FIELD_LAST_TEAMS_DOWNLOAD;
import static de.footystats.tools.services.footy.dls.DownloadCountryLeagueStatsConfig.FIELD_LEAGUE;
import static de.footystats.tools.services.footy.dls.DownloadCountryLeagueStatsConfig.FIELD_SEASON;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import de.footystats.tools.services.MongoService;
import de.footystats.tools.services.csv.CsvFileService;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class DownloadConfigService extends MongoService<DownloadCountryLeagueStatsConfig> {

	/**
	 * Bitmask for the download. See {@link FileTypeBit} for the bits.
	 */
	public static final String DOWNLOAD_BITMASK = "downloadBitmask";
	public static final long LAST_DOWNLOAD_MINUS_TIME_MILLIS = 1000L * 60L * 60L * 24L * 30L; // thirty days.
	private final CsvFileService<DownloadCountryLeagueStatsCsvEntry> csvFileService;

	public DownloadConfigService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter,
		CsvFileService<DownloadCountryLeagueStatsCsvEntry> csvFileService) {
		super(mongoTemplate, mappingMongoConverter);
		this.csvFileService = csvFileService;
	}

	public List<DownloadCountryLeagueStatsCsvEntry> readDownloadConfigs(InputStream csv) {
		List<DownloadCountryLeagueStatsCsvEntry> configs = csvFileService.importFile(csv, DownloadCountryLeagueStatsCsvEntry.class);
		configs.forEach(this::importDownloadConfig);
		return configs;
	}

	/**
	 * All configs for the current year who want a download and there was no dl before or the last dl is longer then
	 * {@link #LAST_DOWNLOAD_MINUS_TIME_MILLIS} days ago.
	 *
	 * @return configs as described inside the method description.
	 */
	List<DownloadCountryLeagueStatsConfig> configsWhoWantADownloadForCurrentYear() {
		Criteria[] criterias = criteriasForConfigsWhoWantADownloadForCurrentYear();

		var query = new Query(criterias[0].and(DOWNLOAD_BITMASK).ne(null)
			.orOperator(ArrayUtils.subarray(criterias, 1, criterias.length)));
		return mongoTemplate.find(query, DownloadCountryLeagueStatsConfig.class);
	}

	/**
	 * All configs of years in the past who wanted a dl but dl never happened before.
	 *
	 * @return configs as described inside the method description.
	 */
	List<DownloadCountryLeagueStatsConfig> configsWhoWantADownloadForPreviousYears() {
		Criteria[] criterias = criteriasForConfigsWhoWantADownloadForPreviousYears();
		var query = new Query(criterias[0].and(DOWNLOAD_BITMASK).ne(null)
			.orOperator(ArrayUtils.subarray(criterias, 1, criterias.length)));
		return mongoTemplate.find(query, DownloadCountryLeagueStatsConfig.class);
	}

	DownloadCountryLeagueStatsConfig configForCountryLeagueSeasonForCurrentYear(String country, String league) {
		Criteria[] criterias = criteriasForConfigsWhoWantADownloadForCurrentYear();
		var query = new Query(
			criterias[0].and(FIELD_COUNTRY).is(country).and(FIELD_LEAGUE).is(league).orOperator(ArrayUtils.subarray(criterias, 1, criterias.length)));
		return mongoTemplate.findOne(query, DownloadCountryLeagueStatsConfig.class);
	}

	DownloadCountryLeagueStatsConfig configForCountryLeagueSeasonForPreviousYears(String country, String league) {
		Criteria[] criterias = criteriasForConfigsWhoWantADownloadForPreviousYears();
		var query = new Query(
			criterias[0].and(FIELD_COUNTRY).is(country).and(FIELD_LEAGUE).is(league).orOperator(ArrayUtils.subarray(criterias, 1, criterias.length)));
		return mongoTemplate.findOne(query, DownloadCountryLeagueStatsConfig.class);
	}

	private Criteria[] criteriasForConfigsWhoWantADownloadForCurrentYear() {
		var year = LocalDate.now().getYear();
		var currentTimeMillis = System.currentTimeMillis();
		var seasonCrit = where(FIELD_SEASON).regex(year
			+ "$"); // match the last year in cases when season is represented as a tuple: 2022/2023. This regeex also matches single years (e.g.: 2021)
		var leagueConfigCrit = where(DOWNLOAD_BITMASK).bits().anySet(FileTypeBit.LEAGUE.getBit())
			.orOperator(where(FIELD_LAST_LEAGUE_DOWNLOAD).lt(currentTimeMillis - LAST_DOWNLOAD_MINUS_TIME_MILLIS),
				where(FIELD_LAST_LEAGUE_DOWNLOAD).isNull());
		var playerConfigCrit = where(DOWNLOAD_BITMASK).bits().anySet(FileTypeBit.PLAYER.getBit())
			.orOperator(where(FIELD_LAST_PLAYER_DOWNLOAD).lt(currentTimeMillis - LAST_DOWNLOAD_MINUS_TIME_MILLIS),
				where(FIELD_LAST_PLAYER_DOWNLOAD).isNull());
		var teamConfigCrit = where(DOWNLOAD_BITMASK).bits().anySet(FileTypeBit.TEAM.getBit())
			.orOperator(where(FIELD_LAST_TEAMS_DOWNLOAD).lt(currentTimeMillis - LAST_DOWNLOAD_MINUS_TIME_MILLIS),
				where(FIELD_LAST_TEAMS_DOWNLOAD).isNull());
		var teamTwoConfigCrit = where(DOWNLOAD_BITMASK).bits().anySet(FileTypeBit.TEAM2.getBit())
			.orOperator(where(FIELD_LAST_TEAMS_2_DOWNLOAD).lt(currentTimeMillis - LAST_DOWNLOAD_MINUS_TIME_MILLIS),
				where(FIELD_LAST_TEAMS_2_DOWNLOAD).isNull());
		var matchConfigCrit = where(DOWNLOAD_BITMASK).bits().anySet(FileTypeBit.MATCH.getBit())
			.orOperator(where(FIELD_LAST_MATCH_DOWNLOAD).lt(currentTimeMillis - LAST_DOWNLOAD_MINUS_TIME_MILLIS),
				where(FIELD_LAST_MATCH_DOWNLOAD).isNull());

		// Return all the criterias as an array
		return new Criteria[]{seasonCrit, leagueConfigCrit, playerConfigCrit, teamConfigCrit, teamTwoConfigCrit, matchConfigCrit};
	}

	/**
	 * Method that returns the mongodb criterias for configs of previous yearys who want a download but never had one.
	 *
	 * @return Array of criterias as described inside the method description. First element is the season criteria.
	 */
	private Criteria[] criteriasForConfigsWhoWantADownloadForPreviousYears() {
		var year = LocalDate.now().getYear();
		// the not ensures that we do not get the current year. E.g. it's 2023 so 2022/2023 and 2023 won't match but 2022 or 2021/2022 would
		var seasonCrit = where(FIELD_SEASON).not().regex(year
			+ "$"); // match the last year in cases when season is represented as a tuple: 2022/2023. This regeex also matches single years (e.g.: 2021)
		var leagueConfigCrit = where(DOWNLOAD_BITMASK).bits().anySet(FileTypeBit.LEAGUE.getBit()).and(FIELD_LAST_LEAGUE_DOWNLOAD).isNull();
		var playerConfigCrit = where(DOWNLOAD_BITMASK).bits().anySet(FileTypeBit.PLAYER.getBit()).and(FIELD_LAST_PLAYER_DOWNLOAD).isNull();
		var teamConfigCrit = where(DOWNLOAD_BITMASK).bits().anySet(FileTypeBit.TEAM.getBit()).and(FIELD_LAST_TEAMS_DOWNLOAD).isNull();
		var teamTwoConfigCrit = where(DOWNLOAD_BITMASK).bits().anySet(FileTypeBit.TEAM2.getBit()).and(FIELD_LAST_TEAMS_2_DOWNLOAD).isNull();
		var matchConfigCrit = where(DOWNLOAD_BITMASK).bits().anySet(FileTypeBit.MATCH.getBit()).and(FIELD_LAST_MATCH_DOWNLOAD).isNull();
		return new Criteria[]{seasonCrit, leagueConfigCrit, playerConfigCrit, teamConfigCrit, teamTwoConfigCrit, matchConfigCrit};
	}

	private void importDownloadConfig(DownloadCountryLeagueStatsCsvEntry config) {
		var document = DownloadCountryLeagueStatsConfig.builder().downloadBitmask(config.getDownloadBitmask()).league(config.getLeague())
			.country(config.getCountry()).footyStatsDlId(config.getFootyStatsDlId()).season(config.getSeason()).build();
		upsert(document);
	}

	@Override
	public Query upsertQuery(DownloadCountryLeagueStatsConfig example) {
		return Query.query(
			where(FIELD_COUNTRY).is(example.getCountry()).and(FIELD_LEAGUE).is(example.getLeague()).and(FIELD_SEASON).is(example.getSeason())
				.and("footyStatsDlId").is(example.getFootyStatsDlId()));
	}

	@Override
	public Class<DownloadCountryLeagueStatsConfig> upsertType() {
		return DownloadCountryLeagueStatsConfig.class;
	}
}
