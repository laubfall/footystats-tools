package de.footystats.tools.services.bet;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BetSeriesRepository extends MongoRepository<BetSeries, String> {

}
