package de.footystats.tools.services.bet.attributes;

import org.bson.types.ObjectId;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public interface BetAttributeRepository extends MongoRepository<BaseBetAttribute<?>, ObjectId> {
	@Cacheable("attributeByUniqueName")
	BaseBetAttribute<?> findByUniqueName(String uniqueName);

	@Cacheable("attributeByUniqueName")
	<B extends BaseBetAttribute<?>> B findByUniqueName(String uniqueName, Class<B> clazz);

	@Cacheable("attributeByValueAndName")
	BaseBetAttribute<?> findByValueAndName(Object value, Attribute name);

	@Cacheable("attributeByName")
	List<BaseBetAttribute<?>> findByName(Attribute name);

	<B extends BaseBetAttribute<?>> B findByNameAndIdIn(Attribute name, Collection<ObjectId> ids, Class<B> clazz);

	@Cacheable("byId")
	@Override
	Optional<BaseBetAttribute<?>> findById(ObjectId objectId);

	List<BaseBetAttribute<?>> findByNameIn(Collection<Attribute> names);

	@Cacheable("groupedAttributes")
	default Map<Attribute, List<BaseBetAttribute<?>>> findGroupedByAttribute() {
		return findAll().stream()
			.collect(Collectors.groupingBy(BaseBetAttribute::getName));
	}
}

