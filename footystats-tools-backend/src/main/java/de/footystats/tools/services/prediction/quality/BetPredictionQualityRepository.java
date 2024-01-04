package de.footystats.tools.services.prediction.quality;

import de.footystats.tools.services.prediction.Bet;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BetPredictionQualityRepository extends MongoRepository<BetPredictionQuality, String> {

	BetPredictionQuality findByBetAndPredictionPercentAndRevision(Bet bet, Integer percent, PredictionQualityRevision revision);

	<T extends IBetPredictionBaseData> List<T> findAllByBetAndRevision(Bet bet, PredictionQualityRevision revision, Class<T> target);

	<T extends IBetPredictionBaseData> List<T> findAllByBetAndRevisionAndPredictionPercentGreaterThanEqual(Bet bet,
		PredictionQualityRevision revision, Integer betOnThisPercentLimit, Class<T> target);

	<T extends IBetPredictionBaseData> List<T> findAllByBetAndRevisionAndPredictionPercentLessThan(Bet bet, PredictionQualityRevision revision,
		Integer betOnThisPercentLimit, Class<T> target);

	BetPredictionQualityRevisionView findTopByRevisionIsNotOrderByRevisionDesc(PredictionQualityRevision notThisRevision);

	void deleteByRevisionEquals(PredictionQualityRevision revision);
}
