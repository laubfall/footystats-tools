package de.ludwig.footystats.tools.backend.services.stats;

import de.ludwig.footystats.tools.backend.services.MongoService;
import de.ludwig.footystats.tools.backend.services.csv.CsvFileService;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class LeagueMatchStatsService extends MongoService<LeagueMatchStats> {
	private final CsvFileService<LeagueMatchStats> leagueMatchStats;

	public LeagueMatchStatsService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter,
		CsvFileService<LeagueMatchStats> leagueMatchStats) {
		super(mongoTemplate, mappingMongoConverter);
		this.leagueMatchStats = leagueMatchStats;
	}

	public Collection<LeagueMatchStats> readTeamStats(InputStream data) {
		List<LeagueMatchStats> stats = leagueMatchStats.importFile(data, LeagueMatchStats.class);
		stats.forEach(this::upsert);
		return stats;
	}

	@Override
	public Query upsertQuery(LeagueMatchStats example) {
		return Query.query(Criteria.where("homeTeamName").is(example.getHomeTeamName()).and("awayTeamName").is(example.getAwayTeamName()).and("timestamp").is(example.getTimestamp()));
	}

	@Override
	public Class<LeagueMatchStats> upsertType() {
		return LeagueMatchStats.class;
	}
}
