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

	public List<DownloadCountryLeagueStatsConfig> configsWhoWantADownload() {
		var year = LocalDate.now().getYear();

		var currentTimeMillis = System.currentTimeMillis();
		var seasonCrit = where("season").regex(year + "$"); // match the last year in cases when season is represented as a tuple: 2022/2023. This regeex also matches single years (e.g.: 2021)
		var leagueConfigCrit = where("downloadBitmask").bits().anySet(FileTypeBit.LEAGUE.getBit()).orOperator(where("lastLeagueDownload").lt(currentTimeMillis - LAST_DOWNLOAD_MINUS_TIME_MILLIS),  where("lastLeagueDownload").isNull());
		var playerConfigCrit = where("downloadBitmask").bits().anySet(FileTypeBit.PLAYER.getBit()).orOperator(where("lastPlayerDownload").lt(currentTimeMillis - LAST_DOWNLOAD_MINUS_TIME_MILLIS),  where("lastPlayerDownload").isNull());
		var teamConfigCrit = where("downloadBitmask").bits().anySet(FileTypeBit.TEAM.getBit()).orOperator(where("lastTeamsDownload").lt(currentTimeMillis - LAST_DOWNLOAD_MINUS_TIME_MILLIS), where("lastTeamsDownload").isNull());
		var teamTwoConfigCrit = where("downloadBitmask").bits().anySet(FileTypeBit.TEAM2.getBit()).orOperator(where("lastTeams2Download").lt(currentTimeMillis - LAST_DOWNLOAD_MINUS_TIME_MILLIS), where("lastTeams2Download").isNull());
		var matchConfigCrit = where("downloadBitmask").bits().anySet(FileTypeBit.MATCH.getBit()).orOperator(where("lastMatchDownload").lt(currentTimeMillis - LAST_DOWNLOAD_MINUS_TIME_MILLIS), where("lastMatchDownload").isNull());

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
