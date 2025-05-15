package de.footystats.tools.services.bet;

import org.bson.types.ObjectId;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BetSeriesRepository extends MongoRepository<BetSeries, String> {
	@Cacheable(value = "itemsCache", key = "#attributeIds.![toString()].stream().sorted().toString()")
	@Query("{ 'attributeIds': {$all : ?0, $size: ?#{[0].size()} }}")
	BetSeries searchByAttributeIds(List<ObjectId> attributeIds);
}
