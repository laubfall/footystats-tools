package de.ludwig.footystats.tools.backend.services.prediction.quality;

import de.ludwig.footystats.tools.backend.services.prediction.Bet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BetPredictionQualityRepository extends MongoRepository<BetPredictionQuality, String> {
	BetPredictionQuality findByBetAndPredictionPercent(Bet bet, Integer percent);

	<T extends IBetPredictionBaseData> List<T> findAllByBetAndRevision(Bet bet, PredictionQualityRevision revision, Class<T> target);
}
