package de.ludwig.footystats.tools.backend.services.prediction.quality;

import de.ludwig.footystats.tools.backend.services.prediction.Bet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BetPredictionAggregateRepository extends MongoRepository<BetPredictionAggregate, String> {
	BetPredictionAggregate findByBetAndPredictionPercent(Bet bet, Integer percent);
}
