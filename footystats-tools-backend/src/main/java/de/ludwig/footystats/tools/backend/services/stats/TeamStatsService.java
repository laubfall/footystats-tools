package de.ludwig.footystats.tools.backend.services.stats;

import de.ludwig.footystats.tools.backend.services.MongoService;
import de.ludwig.footystats.tools.backend.services.csv.CsvFileService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

@Service
public class TeamStatsService extends MongoService<TeamStats> {

	private CsvFileService<TeamStats> teamStatsCsvFileService;


	public TeamStatsService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter, CsvFileService<TeamStats> teamStatsCsvFileService) {
		super(mongoTemplate, mappingMongoConverter);
		this.teamStatsCsvFileService = teamStatsCsvFileService;
	}

	public Collection<TeamStats> readTeamStats(InputStream data) {
		List<TeamStats> stats = teamStatsCsvFileService.importFile(data, TeamStats.class);

		stats.forEach(ls -> upsert(ls));

		return stats;
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
