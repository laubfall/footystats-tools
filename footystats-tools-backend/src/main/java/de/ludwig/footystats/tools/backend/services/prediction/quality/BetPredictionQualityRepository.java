package de.ludwig.footystats.tools.backend.services.prediction.quality;

import de.ludwig.footystats.tools.backend.services.prediction.Bet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BetPredictionQualityRepository extends MongoRepository<BetPredictionQuality, String> {
	BetPredictionQuality findByBetAndPredictionPercentAndRevision(Bet bet, Integer percent, PredictionQualityRevision revision);
	<T extends IBetPredictionBaseData> List<T> findAllByBetAndRevisionAndPredictionPercentGreaterThanEqual(Bet bet, PredictionQualityRevision revision, Integer betOnThisPercentLimit, Class<T> target);
	<T extends IBetPredictionBaseData> List<T> findAllByBetAndRevisionAndPredictionPercentLessThan(Bet bet, PredictionQualityRevision revision, Integer betOnThisPercentLimit, Class<T> target);
	BetPredictionQualityRevisionView findTopByRevisionIsNotOrderByRevisionDesc(PredictionQualityRevision notThisRevision);
	void deleteByRevisionEquals(PredictionQualityRevision revision);
}
