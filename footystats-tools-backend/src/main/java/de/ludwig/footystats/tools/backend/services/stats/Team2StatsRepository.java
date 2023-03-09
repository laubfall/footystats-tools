package de.ludwig.footystats.tools.backend.services.stats;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface Team2StatsRepository  extends MongoRepository<Team2Stats, String> {
}
