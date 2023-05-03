package de.ludwig.footystats.tools.backend.services.prediction.quality.view;

import de.ludwig.footystats.tools.backend.services.prediction.Bet;
import de.ludwig.footystats.tools.backend.services.prediction.PredictionService;
import de.ludwig.footystats.tools.backend.services.prediction.quality.BetPredictionQuality;
import de.ludwig.footystats.tools.backend.services.prediction.quality.BetPredictionQualityRepository;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityRevision;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityService;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class PredictionQualityViewService {
	private final MongoTemplate mongoTemplate;

	private final BetPredictionQualityRepository betPredictionQualityRepository;

	public PredictionQualityViewService(MongoTemplate mongoTemplate, BetPredictionQualityRepository betPredictionQualityRepository) {
		this.mongoTemplate = mongoTemplate;
		this.betPredictionQualityRepository = betPredictionQualityRepository;
	}

	public Map<String, List<BetPredictionQualityInfluencerAggregate>> influencerPredictionsAggregated(Bet bet, boolean betOnThis, PredictionQualityRevision revision) {
		final MatchOperation matchOperation = match(new Criteria("bet").is(bet));
		MatchOperation revisionMatch = match(where("revision").is(revision));
		MatchOperation predictionPercentMatch;
		if(betOnThis){
			predictionPercentMatch = match(where("predictionPercent").gte(PredictionService.LOWER_EXCLUSIVE_BORDER_BET_ON_THIS));
		} else{
			predictionPercentMatch = match(where("predictionPercent").lt(PredictionService.LOWER_EXCLUSIVE_BORDER_BET_ON_THIS));
		}
		final UnwindOperation unwind = unwind("influencerDistribution");
		final GroupOperation groupOperation = group("influencerDistribution.influencerName", "influencerDistribution.predictionPercent")
			.sum("betSucceeded").as("betSucceeded")
			.sum("betFailed").as("betFailed")
			.sum("influencerDistribution.count").as("count");
		final ProjectionOperation projectionOperation = project(Fields.from(
			Fields.field("influencerName", "$_id.influencerName"),
			Fields.field("predictionPercent", "$_id.predictionPercent")))
				.andExclude("$_id")
				.andInclude("betSucceeded", "betFailed", "count");
		final Aggregation aggregation = newAggregation(revisionMatch, matchOperation, predictionPercentMatch, unwind, groupOperation, projectionOperation);
		final AggregationResults<BetPredictionQualityInfluencerAggregate> aggregationResults = mongoTemplate.aggregate(aggregation, BetPredictionQuality.class, BetPredictionQualityInfluencerAggregate.class);
		return aggregationResults.getMappedResults().stream().collect(Collectors.groupingBy(BetPredictionQualityInfluencerAggregate::influencerName, HashMap::new, Collectors.toCollection(ArrayList::new)));
	}

	/**
	 * Based on the BetPredictionAggregates documents this method aggregates the counts of done prediction bet / dont bet succeeded / failed.
	 * Aggregations are done via mongodb query.
	 *
	 * @return List of aggregated values.
	 */
	public List<BetPredictionQualityAllBetsAggregate> matchPredictionQualityMeasurementCounts(PredictionQualityRevision revision) {
		List<BetPredictionQualityAllBetsAggregate> result = new ArrayList<>();

		final EnumSet<Bet> betsWithPrediction = EnumSet.of(Bet.OVER_ZERO_FIVE, Bet.BTTS_YES);
		for (Bet bet : betsWithPrediction) {
			MatchOperation betOnThisMatch = match(
				new Criteria("predictionPercent").gt(PredictionService.LOWER_EXCLUSIVE_BORDER_BET_ON_THIS).and("bet").is(bet).and("revision").is(revision));
			GroupOperation betCount = group("bet").sum("betSucceeded").as("betSucceeded").sum("betFailed").as("betFailed");
			Aggregation aggregation = newAggregation(betOnThisMatch, betCount);
			AggregationResults<Document> aggregationResults = mongoTemplate.aggregate(aggregation, BetPredictionQuality.class, Document.class);

			var doc = aggregationResults.getUniqueMappedResult();
			Long betSuccess = 0L;
			Long betFailed = 0L;
			if (doc != null) {
				betSuccess = doc.get("betSucceeded", Long.class);
				betFailed = doc.get("betFailed", Long.class);
			}

			betOnThisMatch = match(new Criteria("predictionPercent").lte(PredictionService.LOWER_EXCLUSIVE_BORDER_BET_ON_THIS).and("bet").is(bet).and("revision").is(revision));
			aggregation = newAggregation(betOnThisMatch, betCount);
			aggregationResults = mongoTemplate.aggregate(aggregation, BetPredictionQuality.class, Document.class);
			doc = aggregationResults.getUniqueMappedResult();

			Long dontBetSuccess = 0L;
			Long dontBetFailed = 0L;
			if (doc != null) {
				dontBetSuccess = doc.get("betSucceeded", Long.class);
				dontBetFailed = doc.get("betFailed", Long.class);
			}

			Long assessed = betSuccess + betFailed + dontBetSuccess + dontBetFailed;
			result.add(new BetPredictionQualityAllBetsAggregate(bet, assessed, betSuccess, betFailed, dontBetSuccess, dontBetFailed));
		}

		return result;
	}

	public List<BetPredictionQualityBetAggregate> betPredictionQuality(Bet bet, PredictionQualityRevision revision) {
		return betPredictionQualityRepository.findAllByBetAndRevisionAndPredictionPercentGreaterThanEqual(bet, revision, PredictionService.LOWER_EXCLUSIVE_BORDER_BET_ON_THIS, BetPredictionQualityBetAggregate.class);
	}

	public List<BetPredictionQualityBetAggregate> dontBetPredictionQuality(Bet bet, PredictionQualityRevision revision) {
		return betPredictionQualityRepository.findAllByBetAndRevisionAndPredictionPercentLessThan(bet, revision, PredictionService.LOWER_EXCLUSIVE_BORDER_BET_ON_THIS, BetPredictionQualityBetAggregate.class);
	}
}
