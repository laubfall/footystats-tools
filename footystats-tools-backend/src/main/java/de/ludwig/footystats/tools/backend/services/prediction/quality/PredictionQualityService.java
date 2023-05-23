package de.ludwig.footystats.tools.backend.services.prediction.quality;

import com.mongodb.client.result.UpdateResult;
import de.ludwig.footystats.tools.backend.FootystatsProperties;
import de.ludwig.footystats.tools.backend.services.MongoService;
import de.ludwig.footystats.tools.backend.services.match.Match;
import de.ludwig.footystats.tools.backend.services.match.MatchRepository;
import de.ludwig.footystats.tools.backend.services.match.MatchService;
import de.ludwig.footystats.tools.backend.services.prediction.Bet;
import de.ludwig.footystats.tools.backend.services.prediction.InfluencerPercentDistribution;
import de.ludwig.footystats.tools.backend.services.prediction.PredictionAnalyze;
import de.ludwig.footystats.tools.backend.services.prediction.PredictionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

@Slf4j
@Service
public class PredictionQualityService extends MongoService<BetPredictionQuality> {

	private final MatchRepository matchRepository;

	private final MatchService matchService;

	private final BetPredictionQualityRepository betPredictionAggregateRepository;

	private final FootystatsProperties properties;

	public PredictionQualityService(MatchRepository matchRepository, MatchService matchService,
									MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter,
									BetPredictionQualityRepository betPredictionAggregateRepository, FootystatsProperties properties) {
		super(mongoTemplate, mappingMongoConverter);
		this.matchRepository = matchRepository;
		this.matchService = matchService;
		this.betPredictionAggregateRepository = betPredictionAggregateRepository;
		this.properties = properties;
	}

	public PredictionQualityRevision latestRevision() {
		final BetPredictionQualityRevisionView latest = betPredictionAggregateRepository.findTopByRevisionIsNotOrderByRevisionDesc(PredictionQualityRevision.IN_RECOMPUTATION);
		if (latest == null) {
			return new PredictionQualityRevision(0);
		}

		return latest.getRevision();
	}

	public PredictionQualityRevision nextRevision() {
		final BetPredictionQualityRevisionView latest = betPredictionAggregateRepository.findTopByRevisionIsNotOrderByRevisionDesc(PredictionQualityRevision.IN_RECOMPUTATION);
		if (latest == null) {
			return new PredictionQualityRevision(0);
		}

		return new PredictionQualityRevision(latest.getRevision().getRevision() + 1);
	}

	/**
	 * Method is intended for use when recomputing all bet prediction qualities.
	 * It updates the revision of matches that don't have a revision
	 * and the revision of bet prediction qualities that have the IN_RECOMPUTATION revision to the next revision.
	 */
	@Transactional
	public void revisionUpdateOnRecompute() {
		final PredictionQualityRevision predictionQualityRevision = nextRevision();
		final UpdateResult updateResult = mongoTemplate.updateMulti(query(where("revision").is(PredictionQualityRevision.IN_RECOMPUTATION)), update("revision", predictionQualityRevision), BetPredictionQuality.class);
		log.info("Updated " + updateResult.getMatchedCount() + " revision of bet prediction qualities due to recomputated bet predictions.");

		final UpdateResult updateResultMatches = mongoTemplate.updateMulti(query(where("revision").exists(false)), update("revision", predictionQualityRevision), Match.class);
		log.info("Updated " + updateResultMatches.getMatchedCount() + " revision of matches due to recomputated bet predictions.");
	}

	/**
	 * Method is intended for use when computing bet prediction qualities for matches without a revision.
	 * It updates the revision of matches with no revision with the latest revision.
	 */
	@Transactional
	public void markMatchesWithRevisionOnCompute() {
		final PredictionQualityRevision latestRevision = latestRevision();
		final UpdateResult updateResult = mongoTemplate.updateMulti(query(where("revision").exists(false)), update("revision", latestRevision), Match.class);
		log.info("Updated " + updateResult.getMatchedCount() + " matches revision due to computed bet predictions.");
	}

	public Collection<BetPredictionQuality> measure(Match match, PredictionQualityRevision revision) {
		final Collection<BetPredictionQuality> measurements = new ArrayList<>();
		Function<PredictionResult, Boolean> relevantPredictionResult = (
			PredictionResult prediction) -> prediction != null &&
			(PredictionAnalyze.SUCCESS.equals(prediction.analyzeResult()) ||
				PredictionAnalyze.FAILED.equals(prediction.analyzeResult()));
		if (match.getO05() != null && relevantPredictionResult.apply(match.getO05())) {
			var predictionResult = match.getO05();
			BetPredictionQuality aggregate = BetPredictionQuality.builder().count(1L)
				.revision(revision)
				.betSucceeded(PredictionAnalyze.SUCCESS.equals(match.getO05().analyzeResult()) ? 1L : 0L)
				.betFailed(PredictionAnalyze.FAILED.equals(match.getO05().analyzeResult()) ? 1L : 0L)
				.predictionPercent(predictionResult.betSuccessInPercent()).bet(Bet.OVER_ZERO_FIVE)
				.influencerDistribution(addInfluencerDistribution(predictionResult)).build();
			measurements.add(aggregate);
		}

		if (match.getBttsYes() != null && relevantPredictionResult.apply(match.getBttsYes())) {
			var predictionResult = match.getBttsYes();
			BetPredictionQuality aggregate = BetPredictionQuality.builder().count(1L)
				.revision(revision)
				.betSucceeded(PredictionAnalyze.SUCCESS.equals(match.getBttsYes().analyzeResult()) ? 1L : 0L)
				.betFailed(PredictionAnalyze.FAILED.equals(match.getBttsYes().analyzeResult()) ? 1L : 0L)
				.predictionPercent(predictionResult.betSuccessInPercent()).bet(Bet.BTTS_YES)
				.influencerDistribution(addInfluencerDistribution(predictionResult)).build();
			measurements.add(aggregate);
		}

		return measurements;
	}

	private List<InfluencerPercentDistribution> addInfluencerDistribution(PredictionResult prediction) {

		return prediction.influencerDetailedResult().stream()
			.map(influencerResult -> new InfluencerPercentDistribution(influencerResult.influencerPredictionValue(),
				PredictionAnalyze.SUCCESS.equals(prediction.analyzeResult()) ? 1L : 0L, PredictionAnalyze.FAILED.equals(prediction.analyzeResult()) ? 1L : 0L, influencerResult.influencerName(), influencerResult.precheckResult()))
			.collect(Collectors.toList());
	}

	public void merge(Collection<BetPredictionQuality> measurements, PredictionQualityRevision revision) {
		measurements.forEach((betPredictionQuality) -> {
			var existingBetPredictionQuality = betPredictionAggregateRepository.findByBetAndPredictionPercentAndRevision(betPredictionQuality.getBet(),
				betPredictionQuality.getPredictionPercent(), revision);
			if (existingBetPredictionQuality == null) {
				betPredictionAggregateRepository.insert(betPredictionQuality);
			} else {
				existingBetPredictionQuality.setCount(existingBetPredictionQuality.getCount() + 1);
				existingBetPredictionQuality.setBetSucceeded(existingBetPredictionQuality.getBetSucceeded() + betPredictionQuality.getBetSucceeded());
				existingBetPredictionQuality.setBetFailed(existingBetPredictionQuality.getBetFailed() + betPredictionQuality.getBetFailed());
				mergeInfluencerDistribution(existingBetPredictionQuality, betPredictionQuality);
				betPredictionAggregateRepository.save(existingBetPredictionQuality);
			}
		});
	}


	private void mergeInfluencerDistribution(BetPredictionQuality target, BetPredictionQuality source) {
		if (target.getInfluencerDistribution() == null) {
			target.setInfluencerDistribution(new ArrayList<>());
		}
		source.getInfluencerDistribution().forEach((sourceInfluencerDistribution) -> {
			var optTId = target.getInfluencerDistribution().stream().filter(
					(InfluencerPercentDistribution tId) -> tId.getInfluencerName().equals(sourceInfluencerDistribution.getInfluencerName()) &&
						tId.getPredictionPercent().equals(sourceInfluencerDistribution.getPredictionPercent()) &&
						tId.getPrecheckResult() == sourceInfluencerDistribution.getPrecheckResult())
				.findAny();

			if (optTId.isPresent()) {
				var tIpd = optTId.get();
				tIpd.setBetSucceeded(tIpd.getBetSucceeded() + sourceInfluencerDistribution.getBetSucceeded());
				tIpd.setBetFailed(tIpd.getBetFailed() + sourceInfluencerDistribution.getBetFailed());
			} else {
				target.getInfluencerDistribution().add(sourceInfluencerDistribution);
			}
		});
	}

	@Override
	public Query upsertQuery(BetPredictionQuality example) {
		return query(where("revision").is(example.getRevision()));
	}

	@Override
	public Class<BetPredictionQuality> upsertType() {
		return BetPredictionQuality.class;
	}
}
