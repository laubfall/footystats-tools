package de.ludwig.footystats.tools.backend.services.stats;

import de.ludwig.footystats.tools.backend.FootystatsProperties;
import de.ludwig.footystats.tools.backend.services.MongoService;
import de.ludwig.footystats.tools.backend.services.match.MatchService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchStatsService extends MongoService<MatchStats> {

	public static final String COUNTRY_ESPORTS = "Esports";
	private FootystatsProperties fsProperties;

    private final MatchService matchService;

    public MatchStatsService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter, FootystatsProperties fsProperties, MatchService matchService) {
        super(mongoTemplate, mappingMongoConverter);
		this.fsProperties = fsProperties;
		this.matchService = matchService;
    }

    @Transactional
    public void importMatchStats(MatchStats matchStats) {
		if(matchStats == null){
			return;
		}

		if(COUNTRY_ESPORTS.equals(matchStats.getCountry()) && fsProperties.isImportEsports() == false){
			return;
		}

        upsert(matchStats);
        matchService.writeMatch(matchStats);
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
