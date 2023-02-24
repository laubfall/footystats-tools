package de.ludwig.footystats.tools.backend.services.footy.dls;

import de.ludwig.footystats.tools.backend.services.MongoService;
import de.ludwig.footystats.tools.backend.services.csv.CsvFileService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class DownloadConfigService extends MongoService<DownloadCountryLeagueStatsConfig> {

	private CsvFileService<DownloadCountryLeagueStatsConfig> csvFileService;

	public DownloadConfigService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter, CsvFileService<DownloadCountryLeagueStatsConfig> csvFileService) {
		super(mongoTemplate, mappingMongoConverter);
		this.csvFileService = csvFileService;
	}

	public List<DownloadCountryLeagueStatsConfig> readDownloadConfigs(InputStream csv){
		List<DownloadCountryLeagueStatsConfig> configs = csvFileService.importFile(csv, DownloadCountryLeagueStatsConfig.class);
		configs.forEach(this::importDownloadConfig);
		return configs;
	}

	private void importDownloadConfig(DownloadCountryLeagueStatsConfig config){
		upsert(config);
	}

	@Override
	public Query upsertQuery(DownloadCountryLeagueStatsConfig example) {
		return Query.query(Criteria.where("country").is(example.getCountry()).and("league").is(example.getLeague()).and("season").is(example.getSeason()).and("footyStatsDlId").is(example.getFootyStatsDlId()));
	}

	@Override
	public Class<DownloadCountryLeagueStatsConfig> upsertType() {
		return DownloadCountryLeagueStatsConfig.class;
	}
}
