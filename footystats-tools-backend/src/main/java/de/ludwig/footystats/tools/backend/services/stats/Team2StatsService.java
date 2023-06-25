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
public class Team2StatsService extends MongoService<Team2Stats> {
	private final CsvFileService<Team2Stats> teamStatsCsvFileService;

	public Team2StatsService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter, CsvFileService<Team2Stats> teamStatsCsvFileService) {
		super(mongoTemplate, mappingMongoConverter);
		this.teamStatsCsvFileService = teamStatsCsvFileService;
	}

	public Collection<Team2Stats> readTeamStats(InputStream data) {
		List<Team2Stats> stats = teamStatsCsvFileService.importFile(data, Team2Stats.class);
		stats.forEach(this::upsert);
		return stats;
	}

	@Override
	public Query upsertQuery(Team2Stats example) {
		return Query.query(Criteria.where("country").is(example.getCountry()).and("teamName").is(example.getTeamName()).and("season").is(example.getSeason()));
	}

	@Override
	public Class<Team2Stats> upsertType() {
		return Team2Stats.class;
	}
}
