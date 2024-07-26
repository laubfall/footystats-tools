package de.footystats.tools.services.heatmap;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for {@link StatsBetResultDistribution} entities.
 *
 * @param <S> The type of the {@link StatsBetResultDistribution} entity.
 */
public interface StatsBetResultDistributionRepository<S extends StatsBetResultDistribution<?>> extends MongoRepository<S, String> {

}
