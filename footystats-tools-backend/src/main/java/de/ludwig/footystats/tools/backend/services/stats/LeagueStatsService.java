package de.ludwig.footystats.tools.backend.services.stats;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import de.ludwig.footystats.tools.backend.services.MongoService;
import de.ludwig.footystats.tools.backend.services.csv.CsvFileService;

@Service
public class LeagueStatsService extends MongoService<LeagueStats> {

	private CsvFileService<LeagueStats> csvFileService;

	public LeagueStatsService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter,
			CsvFileService<LeagueStats> csvFileService) {
		super(mongoTemplate, mappingMongoConverter);
		this.csvFileService = csvFileService;
	}

	public Collection<LeagueStats> readLeagueStats(InputStream data) {
		List<LeagueStats> stats = csvFileService.importFile(data, LeagueStats.class);

		stats.forEach(ls -> upsert(ls));

		return stats;
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
