package de.footystats.tools.services.bet.attributes;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BetAttributeRepository extends MongoRepository<BaseBetAttribute<?>, ObjectId> {
	BaseBetAttribute<?> findByValueAndName(Object value, Attributes name);

	<B extends BaseBetAttribute<?>> B findByValueAndName(Object value, Attributes name, Class<B> clazz);
}

