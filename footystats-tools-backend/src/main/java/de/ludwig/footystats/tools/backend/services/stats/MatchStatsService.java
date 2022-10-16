package de.ludwig.footystats.tools.backend.services.stats;

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

    private final MatchService matchService;

    public MatchStatsService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter, MatchService matchService) {
        super(mongoTemplate, mappingMongoConverter);
        this.matchService = matchService;
    }

    @Transactional
    public void importMatchStats(MatchStats matchStats) {
        upsert(matchStats);
        matchService.writeMatch(matchStats);
    }

    @Override
    public Query upsertQuery(MatchStats example) {
        return Query.query(Criteria.where("country").is(example.getCountry()).and("league").is(example.getLeague()).and("dateUnix").is(example.getDateUnix()));
    }

    @Override
    public Class<MatchStats> upsertType() {
        return MatchStats.class;
    }
}
