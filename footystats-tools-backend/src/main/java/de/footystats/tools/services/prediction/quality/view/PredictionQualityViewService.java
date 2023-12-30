package de.footystats.tools.services.prediction.quality.view;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PredictionService;
import de.footystats.tools.services.prediction.quality.BetPredictionQuality;
import de.footystats.tools.services.prediction.quality.BetPredictionQualityRepository;
import de.footystats.tools.services.prediction.quality.PredictionQualityRevision;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.bson.Document;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

@Service
public class PredictionQualityViewService {

	private final MongoTemplate mongoTemplate;

	private final BetPredictionQualityRepository betPredictionQualityRepository;

	public PredictionQualityViewService(MongoTemplate mongoTemplate, BetPredictionQualityRepository betPredictionQualityRepository) {
		this.mongoTemplate = mongoTemplate;
		this.betPredictionQualityRepository = betPredictionQualityRepository;
	}

	/**
	 * Sum up the betSucceeded and betFailed values for each influencer and predictionPercent.
	 *
	 * @param bet       The bet to filter for.
	 * @param betOnThis If true the predictionPercent must be greater than or equal to the lower exclusive border of the betOnThis range.
	 * @param revision  The revision to filter for.
	 * @return Map of influencerName to List of BetPredictionQualityInfluencerAggregate that holds the aggregation of betSucceeded and betFailed for
	 * each predictionPercent of the influencer.
	 */
	@Cacheable(cacheNames = "betPredictionQualityInfluencerAggregate", key = "{#bet, #betOnThis, #revision.revision.intValue()}")
	public Map<String, List<BetPredictionQualityInfluencerAggregate>> influencerPredictionsAggregated(Bet bet, boolean betOnThis,
		PredictionQualityRevision revision) {
		MatchOperation revisionMatch = match(where("revision_revision").is(revision.getRevision()).and("bet").is(bet));
		MatchOperation predictionPercentMatch;
		if (betOnThis) {
			predictionPercentMatch = match(where("predictionPercent").gte(PredictionService.LOWER_EXCLUSIVE_BORDER_BET_ON_THIS));
		} else {
			predictionPercentMatch = match(where("predictionPercent").lt(PredictionService.LOWER_EXCLUSIVE_BORDER_BET_ON_THIS));
		}
		final UnwindOperation unwind = unwind("influencerDistribution");
		final GroupOperation groupOperation = group("influencerDistribution.influencerName", "influencerDistribution.predictionPercent")
			.sum("influencerDistribution.betSucceeded").as("betSucceeded")
			.sum("influencerDistribution.betFailed").as("betFailed");
		final ProjectionOperation projectionOperation = project(Fields.from(
			Fields.field("influencerName", "$_id.influencerName"),
			Fields.field("predictionPercent", "$_id.predictionPercent")))
			.andExclude("$_id")
			.andInclude("betSucceeded", "betFailed");
		final Aggregation aggregation = newAggregation(revisionMatch, predictionPercentMatch, unwind, groupOperation, projectionOperation);
		final AggregationResults<BetPredictionQualityInfluencerAggregate> aggregationResults = mongoTemplate.aggregate(aggregation,
			BetPredictionQuality.class, BetPredictionQualityInfluencerAggregate.class);
		return aggregationResults.getMappedResults().stream().collect(
			Collectors.groupingBy(BetPredictionQualityInfluencerAggregate::influencerName, HashMap::new, Collectors.toCollection(ArrayList::new)));
	}

	/**
	 * Based on the BetPredictionAggregates documents this method aggregates the counts of done prediction bet / dont bet succeeded / failed.
	 * Aggregations are done via mongodb query.
	 *
	 * @param revision The revision to filter for.
	 * @return List of aggregated values.
	 */
	public List<BetPredictionQualityAllBetsAggregate> matchPredictionQualityMeasurementCounts(PredictionQualityRevision revision) {
		List<BetPredictionQualityAllBetsAggregate> result = new ArrayList<>();

		final EnumSet<Bet> betsWithPrediction = Bet.activeBets();
		for (Bet bet : betsWithPrediction) {
			MatchOperation betOnThisMatch = match(
				new Criteria("predictionPercent").gt(PredictionService.LOWER_EXCLUSIVE_BORDER_BET_ON_THIS).and("bet").is(bet).and("revision")
					.is(revision));
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

			betOnThisMatch = match(
				new Criteria("predictionPercent").lte(PredictionService.LOWER_EXCLUSIVE_BORDER_BET_ON_THIS).and("bet").is(bet).and("revision")
					.is(revision));
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

	/**
	 * This method provides a view of success / failed counts of prediction for bet on this.
	 *
	 * @param bet      The bet to filter for.
	 * @param revision The revision to filter for.
	 * @return List of aggregated values.
	 */
	@Cacheable(cacheNames = "betPredictionQualityBetAggregate", key = "{#bet, #revision.revision.intValue()}")
	public List<BetPredictionQualityBetAggregate> betPredictionQuality(Bet bet, PredictionQualityRevision revision) {
		return betPredictionQualityRepository.findAllByBetAndRevision(bet, revision, BetPredictionQualityBetAggregate.class);
	}

	/**
	 * This method provides a view of success / failed counts of prediction for don't bet on this.
	 *
	 * @param bet      The bet to filter for.
	 * @param revision The revision to filter for.
	 * @return List of aggregated values.
	 */
	@Deprecated
	@Cacheable(cacheNames = "dontBetPredictionQualityBetAggregate", key = "{#bet, #revision.revision.intValue()}")
	public List<BetPredictionQualityBetAggregate> dontBetPredictionQuality(Bet bet, PredictionQualityRevision revision) {
		return betPredictionQualityRepository.findAllByBetAndRevisionAndPredictionPercentLessThan(bet, revision,
			PredictionService.LOWER_EXCLUSIVE_BORDER_BET_ON_THIS, BetPredictionQualityBetAggregate.class);
	}
}
