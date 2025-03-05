package de.footystats.tools.services.bet;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BetSeriesRepository extends MongoRepository<BetSeries, String> {
	@Query("{ 'attributeSeries': { $all: [ { $elemMatch: { $in: ?0 } } ] } }")
	List<BetSeries> findByAllBetAttributes(List<BaseBetAttribute<?>> attributes);

	List<BetSeries> findAllByAttributeSeriesContaining(List<BaseBetAttribute<?>> attributeSeries);
}
