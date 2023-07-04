package de.footystats.tools.services.stats;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.services.MongoService;
import de.footystats.tools.services.match.MatchService;
import de.footystats.tools.services.stats.batch.IMatchStatsJobService;
import de.footystats.tools.services.stats.batch.MatchStatsJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class MatchStatsService extends MongoService<MatchStats> {
	public static final String COUNTRY_ESPORTS = "Esports";

	private final FootystatsProperties fsProperties;

	private final MatchService matchService;

	private final MatchStatsRepository matchStatsRepository;

	private final IMatchStatsJobService matchStatsJobService;

	public MatchStatsService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter, FootystatsProperties fsProperties, MatchService matchService, MatchStatsRepository matchStatsRepository,
		IMatchStatsJobService matchStatsJobService) {
		super(mongoTemplate, mappingMongoConverter);
		this.fsProperties = fsProperties;
		this.matchService = matchService;
		this.matchStatsRepository = matchStatsRepository;
		this.matchStatsJobService = matchStatsJobService;
	}

	@Transactional
	public void importMatchStats(MatchStats matchStats) {
		if (matchStats == null) {
			return;
		}

		if (COUNTRY_ESPORTS.equals(matchStats.getCountry()) && !fsProperties.isImportEsports()) {
			return;
		}

		upsert(matchStats);
		matchService.writeMatch(matchStats);
	}

	@Deprecated // Commiting transaction fails in case of large sets of matchStats.
	@Transactional
	public void reimportMatchStats() {
		var pageSize = 100;
		Pageable pageable = PageRequest.of(0, pageSize);

		do {
			log.info("Doing reimport for page " + pageable.getPageNumber());
			var page = matchStatsRepository.findAll(pageable);
			if(!page.hasNext()){
				break;
			}
			pageable = page.nextPageable();

			page.getContent().forEach(matchService::writeMatch);
		} while (true);
	}

	@Override
	public Query upsertQuery(MatchStats example) {
		return Query.query(Criteria.where("country").is(example.getCountry()).and("league").is(example.getLeague()).and("dateUnix").is(example.getDateUnix()).and("homeTeam").is(example.getHomeTeam()).and("awayTeam").is(example.getAwayTeam()));
	}

	@Override
	public Class<MatchStats> upsertType() {
		return MatchStats.class;
	}
}
