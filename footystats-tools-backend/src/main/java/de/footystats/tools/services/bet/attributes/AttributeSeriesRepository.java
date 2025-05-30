package de.footystats.tools.services.bet.attributes;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AttributeSeriesRepository extends MongoRepository<AttributeSeries, ObjectId> {
	@Query("{ 'attributeIds': {$all : ?0, $size: ?#{[0].size()} }}")
	AttributeSeries findByAttributeIds(List<ObjectId> attributeIds);

	List<AttributeSeries> findByAttributeIdsContains(ObjectId attributeId);

}
