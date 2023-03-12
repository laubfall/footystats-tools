package de.ludwig.footystats.tools.backend.services.footy.dls;

import de.ludwig.footystats.tools.backend.services.MongoService;
import de.ludwig.footystats.tools.backend.services.csv.CsvFileService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class DownloadConfigService extends MongoService<DownloadCountryLeagueStatsConfig> {

	public static final String FIELD_LAST_LEAGUE_DOWNLOAD = "lastLeagueDownload";
	public static final String FIELD_LAST_PLAYER_DOWNLOAD = "lastPlayerDownload";
	public static final String FIELD_LAST_TEAMS_DOWNLOAD = "lastTeamsDownload";
	public static final String FIELD_LAST_TEAMS_2_DOWNLOAD = "lastTeams2Download";
	public static final String FIELD_LAST_MATCH_DOWNLOAD = "lastMatchDownload";
	private CsvFileService<DownloadCountryLeagueStatsCsvEntry> csvFileService;
	public static final long LAST_DOWNLOAD_MINUS_TIME_MILLIS = 1000l * 60l * 60l * 24l * 30l;

	public DownloadConfigService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter, CsvFileService<DownloadCountryLeagueStatsCsvEntry> csvFileService) {
		super(mongoTemplate, mappingMongoConverter);
		this.csvFileService = csvFileService;
	}

	public List<DownloadCountryLeagueStatsCsvEntry> readDownloadConfigs(InputStream csv) {
		List<DownloadCountryLeagueStatsCsvEntry> configs = csvFileService.importFile(csv, DownloadCountryLeagueStatsCsvEntry.class);
		configs.forEach(this::importDownloadConfig);
		return configs;
	}

	/**
	 * All configs for the current year who want a download and there was no dl before or the last dl is longer
	 * then 30 days ago.
	 * @return configs as described inside the method description.
	 */
	public List<DownloadCountryLeagueStatsConfig> configsWhoWantADownloadForCurrentYear() {
		var year = LocalDate.now().getYear();

		var currentTimeMillis = System.currentTimeMillis();
		var seasonCrit = where("season").regex(year + "$"); // match the last year in cases when season is represented as a tuple: 2022/2023. This regeex also matches single years (e.g.: 2021)
		var leagueConfigCrit = where("downloadBitmask").bits().anySet(FileTypeBit.LEAGUE.getBit()).orOperator(where(FIELD_LAST_LEAGUE_DOWNLOAD).lt(currentTimeMillis - LAST_DOWNLOAD_MINUS_TIME_MILLIS),  where(FIELD_LAST_LEAGUE_DOWNLOAD).isNull());
		var playerConfigCrit = where("downloadBitmask").bits().anySet(FileTypeBit.PLAYER.getBit()).orOperator(where(FIELD_LAST_PLAYER_DOWNLOAD).lt(currentTimeMillis - LAST_DOWNLOAD_MINUS_TIME_MILLIS),  where(FIELD_LAST_PLAYER_DOWNLOAD).isNull());
		var teamConfigCrit = where("downloadBitmask").bits().anySet(FileTypeBit.TEAM.getBit()).orOperator(where(FIELD_LAST_TEAMS_DOWNLOAD).lt(currentTimeMillis - LAST_DOWNLOAD_MINUS_TIME_MILLIS), where(FIELD_LAST_TEAMS_DOWNLOAD).isNull());
		var teamTwoConfigCrit = where("downloadBitmask").bits().anySet(FileTypeBit.TEAM2.getBit()).orOperator(where(FIELD_LAST_TEAMS_2_DOWNLOAD).lt(currentTimeMillis - LAST_DOWNLOAD_MINUS_TIME_MILLIS), where(FIELD_LAST_TEAMS_2_DOWNLOAD).isNull());
		var matchConfigCrit = where("downloadBitmask").bits().anySet(FileTypeBit.MATCH.getBit()).orOperator(where(FIELD_LAST_MATCH_DOWNLOAD).lt(currentTimeMillis - LAST_DOWNLOAD_MINUS_TIME_MILLIS), where(FIELD_LAST_MATCH_DOWNLOAD).isNull());

		var query = new Query(seasonCrit.and("downloadBitmask").ne(null).orOperator(leagueConfigCrit, playerConfigCrit, teamConfigCrit, teamTwoConfigCrit, matchConfigCrit));
		return mongoTemplate.find(query, DownloadCountryLeagueStatsConfig.class);
	}

	/**
	 * All configs of years in the past who wanted a dl but dl never happened before.
	 * @return configs as described inside the method description.
	 */
	public List<DownloadCountryLeagueStatsConfig> configsWhoWantADownloadForPreviousYears() {
		var year = LocalDate.now().getYear();
		// the not ensures that we do not get the current year. E.g. it's 2023 so 2022/2023 and 2023 won't match but 2022 or 2021/2022 would
		var seasonCrit = where("season").not().regex(year + "$"); // match the last year in cases when season is represented as a tuple: 2022/2023. This regeex also matches single years (e.g.: 2021)
		var leagueConfigCrit = where("downloadBitmask").bits().anySet(FileTypeBit.LEAGUE.getBit()).and(FIELD_LAST_LEAGUE_DOWNLOAD).isNull();
		var playerConfigCrit = where("downloadBitmask").bits().anySet(FileTypeBit.PLAYER.getBit()).and(FIELD_LAST_PLAYER_DOWNLOAD).isNull();
		var teamConfigCrit = where("downloadBitmask").bits().anySet(FileTypeBit.TEAM.getBit()).and(FIELD_LAST_TEAMS_DOWNLOAD).isNull();
		var teamTwoConfigCrit = where("downloadBitmask").bits().anySet(FileTypeBit.TEAM2.getBit()).and(FIELD_LAST_TEAMS_2_DOWNLOAD).isNull();
		var matchConfigCrit = where("downloadBitmask").bits().anySet(FileTypeBit.MATCH.getBit()).and(FIELD_LAST_MATCH_DOWNLOAD).isNull();

		var query = new Query(seasonCrit.and("downloadBitmask").ne(null).orOperator(leagueConfigCrit, playerConfigCrit, teamConfigCrit, teamTwoConfigCrit, matchConfigCrit));
		return mongoTemplate.find(query, DownloadCountryLeagueStatsConfig.class);
	}

	private void importDownloadConfig(DownloadCountryLeagueStatsCsvEntry config) {
		var document = DownloadCountryLeagueStatsConfig.builder().downloadBitmask(config.getDownloadBitmask()).league(config.getLeague()).country(config.getCountry()).footyStatsDlId(config.getFootyStatsDlId()).season(config.getSeason()).build();
		upsert(document);
	}

	@Override
	public Query upsertQuery(DownloadCountryLeagueStatsConfig example) {
		return Query.query(where("country").is(example.getCountry()).and("league").is(example.getLeague()).and("season").is(example.getSeason()).and("footyStatsDlId").is(example.getFootyStatsDlId()));
	}

	@Override
	public Class<DownloadCountryLeagueStatsConfig> upsertType() {
		return DownloadCountryLeagueStatsConfig.class;
	}
}
