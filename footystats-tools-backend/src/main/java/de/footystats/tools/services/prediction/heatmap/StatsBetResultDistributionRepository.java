package de.footystats.tools.services.prediction.heatmap;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatsBetResultDistributionRepository<S extends StatsBetResultDistribution<?>> extends MongoRepository<S, String> {

//	Optional<S> findByExample(Example<S> example);
}
