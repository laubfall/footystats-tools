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

	/*
	public Collection<TeamStats> latestThree(String team, String country, Integer year) {
		const baseYear = year || getYear(new Date());

		const season = [
			`${baseYear - 1}/${baseYear}`,
			`${baseYear - 2}/${baseYear - 1}`,
			`${baseYear}/${baseYear + 1}`,
		baseYear,
			baseYear + 1,
			baseYear - 1,
		];

		const result: UniqueTeamStats[] = (await this.dbService.asyncFind({
			$and: [
		{ country },
		{ $or: [{ team_name: team }, { common_name: team }] },
		{ season: { $in: season } },
			],
		})) as UniqueTeamStats[];

		return Promise.resolve(result);
	}*/

	@Override
	public Query upsertQuery(TeamStats example) {
		return Query.query(Criteria.where("country").is(example.getCountry()).and("teamName").is(example.getTeamName()).and("season").is(example.getSeason()));
	}

	@Override
	public Class<TeamStats> upsertType() {
		return TeamStats.class;
	}
}
