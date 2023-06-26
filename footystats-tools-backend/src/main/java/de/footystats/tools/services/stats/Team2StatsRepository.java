package de.footystats.tools.services.stats;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface Team2StatsRepository  extends MongoRepository<Team2Stats, String> {
}
