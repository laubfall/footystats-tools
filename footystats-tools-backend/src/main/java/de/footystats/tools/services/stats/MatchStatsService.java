package de.footystats.tools.services.stats;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.services.MongoService;
import de.footystats.tools.services.match.MatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchStatsService extends MongoService<MatchStats> {
	private static final Logger logger = LoggerFactory.getLogger(MatchStatsService.class);

	public static final String COUNTRY_ESPORTS = "Esports";

	private FootystatsProperties fsProperties;

	private final MatchService matchService;

	private MatchStatsRepository matchStatsRepository;

	public MatchStatsService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter, FootystatsProperties fsProperties, MatchService matchService, MatchStatsRepository matchStatsRepository) {
		super(mongoTemplate, mappingMongoConverter);
		this.fsProperties = fsProperties;
		this.matchService = matchService;
		this.matchStatsRepository = matchStatsRepository;
	}

	@Transactional
	public void importMatchStats(MatchStats matchStats) {
		if (matchStats == null) {
			return;
		}

		if (COUNTRY_ESPORTS.equals(matchStats.getCountry()) && fsProperties.isImportEsports() == false) {
			return;
		}

		upsert(matchStats);
		matchService.writeMatch(matchStats);
	}

	@Transactional
	public void reimportMatchStats() {
		var pageSize = 100;
		Pageable pageable = PageRequest.of(0, pageSize);

		do {
			logger.info("Doing reimport for page " + pageable.getPageNumber());
			var page = matchStatsRepository.findAll(pageable);
			if(page.hasNext() == false){
				break;
			}
			pageable = page.nextPageable();

			page.getContent().forEach(ms -> matchService.writeMatch(ms));
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
