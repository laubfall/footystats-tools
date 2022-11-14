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

	private final CsvFileService<TeamStats> teamStatsCsvFileService;

	private final TeamStatsRepository teamStatsRepository;

	public TeamStatsService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter, CsvFileService<TeamStats> teamStatsCsvFileService, TeamStatsRepository teamStatsRepository) {
		super(mongoTemplate, mappingMongoConverter);
		this.teamStatsCsvFileService = teamStatsCsvFileService;
		this.teamStatsRepository = teamStatsRepository;
	}

	public Collection<TeamStats> readTeamStats(InputStream data) {
		List<TeamStats> stats = teamStatsCsvFileService.importFile(data, TeamStats.class);
		stats.forEach(ls -> upsert(ls));
		return stats;
	}

	public Collection<TeamStats> latestThree(String team, String country, Integer year) {
		var seasons = new String[]{year -1 + "/" +year, year-2+"/"+(year-1),year+"/"+(year+1), year +"", year +1+"",year-1+""};
		Query query = Query.query(Criteria.where("country").is(country).and("season").in(seasons).orOperator(Criteria.where("teamName").is(team), Criteria.where("commonName").is(team)));
		return mongoTemplate.find(query, TeamStats.class);
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
