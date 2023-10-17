package de.footystats.tools.services.stats;

import de.footystats.tools.services.MongoService;
import de.footystats.tools.services.csv.CsvFileService;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LeagueMatchStatsService extends MongoService<LeagueMatchStats> {

	private final CsvFileService<LeagueMatchStats> leagueMatchStats;

	public LeagueMatchStatsService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter,
		CsvFileService<LeagueMatchStats> leagueMatchStats) {
		super(mongoTemplate, mappingMongoConverter);
		this.leagueMatchStats = leagueMatchStats;
	}

	public Collection<LeagueMatchStats> readLeagueMatchStats(InputStream data) {
		List<LeagueMatchStats> stats = leagueMatchStats.importFile(data, LeagueMatchStats.class);
		log.info("Read {} league match stats and upserted them.", stats.size());
		stats.forEach(this::upsert);
		return stats;
	}

	@Override
	public Query upsertQuery(LeagueMatchStats example) {
		return Query.query(
			Criteria.where("homeTeamName").is(example.getHomeTeamName()).and("awayTeamName").is(example.getAwayTeamName()).and("timestamp")
				.is(example.getTimestamp()));
	}

	@Override
	public Class<LeagueMatchStats> upsertType() {
		return LeagueMatchStats.class;
	}
}
