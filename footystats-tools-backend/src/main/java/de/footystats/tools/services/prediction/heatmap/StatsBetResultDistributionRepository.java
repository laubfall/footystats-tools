package de.footystats.tools.services.prediction.heatmap;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatsBetResultDistributionRepository extends MongoRepository<StatsBetResultDistribution<?>, String> {

}
