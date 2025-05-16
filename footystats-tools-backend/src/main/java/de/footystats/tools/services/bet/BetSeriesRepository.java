package de.footystats.tools.services.bet;

import de.footystats.tools.services.bet.attributes.AttributeSeries;
import org.bson.types.ObjectId;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface BetSeriesRepository extends MongoRepository<BetSeries, String> {
	@Cacheable(value = "itemsCache", key = "#attributeSeriesId")
	BetSeries findBetSeriesByAttributeSeriesId(ObjectId attributeSeriesId);

	@Cacheable(value = "itemsCache", key = "#series.id")
	@Query("{ 'attributeSeriesId': ?#{[0].getId()} }")
	BetSeries findBetSeriesByAttributeSeriesId(AttributeSeries series);
}
