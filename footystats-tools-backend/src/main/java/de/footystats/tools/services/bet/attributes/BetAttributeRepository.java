package de.footystats.tools.services.bet.attributes;

import org.bson.types.ObjectId;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;

public interface BetAttributeRepository extends MongoRepository<BaseBetAttribute<?>, ObjectId> {
	@Cacheable("byUniqueName")
	BaseBetAttribute<?> findByUniqueName(String uniqueName);

	@Cacheable("byUniqueName")
	<B extends BaseBetAttribute<?>> B findByUniqueName(String uniqueName, Class<B> clazz);

	@Cacheable("byValueAndName")
	BaseBetAttribute<?> findByValueAndName(Object value, Attributes name);

	@Cacheable("byValueAndNameAndType")
	<B extends BaseBetAttribute<?>> B findByValueAndName(Object value, Attributes name, Class<B> clazz);

	<B extends BaseBetAttribute<?>> B findByNameAndIdIn(Attributes name, Collection<ObjectId> ids, Class<B> clazz);
}

