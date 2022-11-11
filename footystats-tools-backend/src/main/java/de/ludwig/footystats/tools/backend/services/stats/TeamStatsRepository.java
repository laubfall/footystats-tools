package de.ludwig.footystats.tools.backend.services.stats;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamStatsRepository extends MongoRepository<TeamStats, String> {
}
