package de.ludwig.footystats.tools.backend.services.prediction.quality.view;

import de.ludwig.footystats.tools.backend.services.prediction.Bet;
import de.ludwig.footystats.tools.backend.services.prediction.PredictionService;
import de.ludwig.footystats.tools.backend.services.prediction.quality.BetPredictionQuality;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
public class PredictionQualityViewService {
	private MongoTemplate mongoTemplate;

	public PredictionQualityViewService(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public Map<String, List<BetPredictionQualityInfluencerAggregate>> influencerPredictionsAggregated(Bet bet) {
		final MatchOperation matchOperation = match(new Criteria("bet").is(bet));
		UnwindOperation unwind = unwind("influencerDistribution");
		GroupOperation groupOperation = group("influencerDistribution.influencerName", "influencerDistribution.predictionPercent").sum("betSucceeded").as("betSucceeded").sum("betFailed").as("betFailed").sum("influencerDistribution.count").as("count");
		ProjectionOperation projectionOperation = project(Fields.from(Fields.field("influencerName", "$_id.influencerName"), Fields.field("predictionPercent", "$_id.predictionPercent"))).andExclude("$_id").andInclude("betSucceeded", "betFailed", "count");
		Aggregation aggregation = newAggregation(matchOperation, unwind, groupOperation, projectionOperation);
		AggregationResults<BetPredictionQualityInfluencerAggregate> aggregationResults = mongoTemplate.aggregate(aggregation, BetPredictionQuality.class, BetPredictionQualityInfluencerAggregate.class);
		return aggregationResults.getMappedResults().stream().collect(Collectors.groupingBy(BetPredictionQualityInfluencerAggregate::influencerName, HashMap::new, Collectors.toCollection(ArrayList::new)));
	}

	/**
	 * Based on the BetPredictionAggregates documents this method aggregates the counts of done prediction bet / dont bet succeeded / failed.
	 * Aggregations are done via mongodb query.
	 *
	 * @return List of aggregated values.
	 */
	public List<BetPredictionQualityAllBetsAggregate> matchPredictionQualityMeasurementCounts() {
		List<BetPredictionQualityAllBetsAggregate> result = new ArrayList<>();

		for (Bet bet : Bet.values()) {
			MatchOperation betOnThisMatch = match(
				new Criteria("predictionPercent").gt(PredictionService.LOWER_EXCLUSIVE_BORDER_BET_ON_THIS).and("bet").is(bet));
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

			betOnThisMatch = match(new Criteria("predictionPercent").lte(PredictionService.LOWER_EXCLUSIVE_BORDER_BET_ON_THIS).and("bet").is(bet));
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
}
