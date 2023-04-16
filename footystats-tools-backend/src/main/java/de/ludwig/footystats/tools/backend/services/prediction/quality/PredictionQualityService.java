package de.ludwig.footystats.tools.backend.services.prediction.quality;

import de.ludwig.footystats.tools.backend.FootystatsProperties;
import de.ludwig.footystats.tools.backend.services.MongoService;
import de.ludwig.footystats.tools.backend.services.match.Match;
import de.ludwig.footystats.tools.backend.services.match.MatchRepository;
import de.ludwig.footystats.tools.backend.services.match.MatchService;
import de.ludwig.footystats.tools.backend.services.prediction.*;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatus;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Slf4j
@Service
public class PredictionQualityService extends MongoService<BetPredictionQuality> {

	private final MatchRepository matchRepository;

	private final MatchService matchService;

	private final PredictionQualityReportRepository predictionQualityReportRepository;

	private final BetPredictionQualityRepository betPredictionAggregateRepository;

	private final FootystatsProperties properties;

	public PredictionQualityService(MatchRepository matchRepository, MatchService matchService,
									PredictionQualityReportRepository predictionQualityReportRepository, MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter,
									BetPredictionQualityRepository betPredictionAggregateRepository, FootystatsProperties properties) {
		super(mongoTemplate, mappingMongoConverter);
		this.matchRepository = matchRepository;
		this.predictionQualityReportRepository = predictionQualityReportRepository;
		this.matchService = matchService;
		this.betPredictionAggregateRepository = betPredictionAggregateRepository;
		this.properties = properties;
	}

	public Precast precast(PredictionQualityRevision revision) {
		var probe = new Match();
		probe.setRevision(revision);
		var matchCount = matchRepository.count(Example.of(probe));
		var nextRevision = nextRevision(revision);
		var precast = new Precast();
		precast.setRevision(nextRevision);
		precast.setPredictionsToAssess(matchCount);
		return precast;
	}

	public void computeQuality() {
		PageRequest pageRequest = PageRequest.of(0, properties.getPredictionQuality().getPageSizeFindingRevisionMatches());
		Page<Match> matchesPage;
		var pageCnt = 1;
		matchesPage = matchRepository.findMatchesByStateAndRevision_RevisionIsNull(MatchStatus.complete, pageRequest);
		while (matchesPage.hasContent()) {
			log.info("Start computing prediction quality.");
			var matchesByRevision = matchesPage.getContent();
			for (Match match : matchesByRevision) {
				var predictionAggregates = measure(match);
				merge(predictionAggregates);

				// update match with revision number
				match.setRevision(PredictionQualityRevision.NO_REVISION);
				matchService.upsert(match);
			}

			log.info("Quality computed for page " + pageCnt + " of a total of " + matchesPage.getTotalPages());
			// we don't increment the page for pageRequest because the result entities are modified in that way that the query won't match them anymore.
			matchesPage = matchRepository.findMatchesByStateAndRevision_RevisionIsNull(MatchStatus.complete, pageRequest);
			pageCnt += 1;
		}
	}

	@Transactional
	public void recomputeQuality() {
		PageRequest pageRequest = PageRequest.of(0, properties.getPredictionQuality().getPageSizeFindingRevisionMatches());
		Page<Match> result = matchRepository.findMatchesByState(MatchStatus.complete, pageRequest);
		var pageCnt = 1;
		while (result.hasContent()) {
			result.forEach((match) -> {
				var msm = this.measure(match);
				this.merge(msm);

				// update match with revision number
				match.setRevision(PredictionQualityRevision.NO_REVISION);
				matchService.upsert(match);
			});

			pageRequest = PageRequest.of(pageCnt, properties.getPredictionQuality().getPageSizeFindingRevisionMatches());
			result = matchRepository.findMatchesByState(MatchStatus.complete, pageRequest);
			pageCnt += 1;
		}
	}

	PredictionQualityRevision nextRevision(PredictionQualityRevision revision) {
		var nextRevision = -1;
		if (revision != null) {
			nextRevision = revision.getRevision() + 1;
		} else {
			var report = predictionQualityReportRepository.findTopByOrderByRevisionDesc();
			if (report == null) {
				nextRevision = 0;
			}
		}

		return new PredictionQualityRevision(nextRevision);
	}

	Collection<BetPredictionQuality> measure(Match match) {

		Collection<BetPredictionQuality> measurements = new ArrayList<>();
		Function<PredictionResult, Boolean> relevantPredictionResult = (
			PredictionResult prediction) -> prediction != null &&
			(PredictionAnalyze.SUCCESS.equals(prediction.analyzeResult()) ||
				PredictionAnalyze.FAILED.equals(prediction.analyzeResult()));
		if (match.getO05() != null && relevantPredictionResult.apply(match.getO05())) {
			var predictionResult = match.getO05();
			BetPredictionQuality aggregate = BetPredictionQuality.builder().count(1L)
				.revision(PredictionQualityRevision.NO_REVISION)
				.betSucceeded(PredictionAnalyze.SUCCESS.equals(match.getO05().analyzeResult()) ? 1L : 0L)
				.betFailed(PredictionAnalyze.FAILED.equals(match.getO05().analyzeResult()) ? 1L : 0L)
				.predictionPercent(predictionResult.betSuccessInPercent()).bet(Bet.OVER_ZERO_FIVE)
				.influencerDistribution(addInfluencerDistribution(predictionResult)).build();
			measurements.add(aggregate);
		}

		if (match.getBttsYes() != null && relevantPredictionResult.apply(match.getBttsYes())) {
			var predictionResult = match.getBttsYes();
			BetPredictionQuality aggregate = BetPredictionQuality.builder().count(1L)
				.revision(PredictionQualityRevision.NO_REVISION)
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
				1L, influencerResult.influencerName(), influencerResult.precheckResult()))
			.collect(Collectors.toList());
	}

	private void merge(Collection<BetPredictionQuality> measurements) {
		measurements.forEach((betPredictionAggregate) -> {

			var existingAggregate = betPredictionAggregateRepository.findByBetAndPredictionPercent(betPredictionAggregate.getBet(),
				betPredictionAggregate.getPredictionPercent());
			if (existingAggregate == null) {
				betPredictionAggregateRepository.insert(betPredictionAggregate);
			} else {
				existingAggregate.setCount(existingAggregate.getCount() + 1);
				existingAggregate.setBetSucceeded(existingAggregate.getBetSucceeded() + betPredictionAggregate.getBetSucceeded());
				existingAggregate.setBetFailed(existingAggregate.getBetFailed() + betPredictionAggregate.getBetFailed());
				mergeInfluencerDistribution(existingAggregate, betPredictionAggregate);
				betPredictionAggregateRepository.save(existingAggregate);
			}
		});
	}


	private void mergeInfluencerDistribution(
		BetPredictionQuality target,
		BetPredictionQuality source) {
		source.getInfluencerDistribution().forEach((sId) -> {
			if (target.getInfluencerDistribution() == null) {
				target.setInfluencerDistribution(new ArrayList<>());
			}
			var idx = target.getInfluencerDistribution().stream().filter(
					(InfluencerPercentDistribution tId) -> tId.getInfluencerName() == sId.getInfluencerName() &&
						tId.getPredictionPercent() == sId.getPredictionPercent() &&
						tId.getPrecheckResult() == sId.getPrecheckResult())
				.findAny();

			if (idx.isPresent()) {
				var ipd = idx.get();
				ipd.setCount(ipd.getCount() + sId.getCount());
			} else {
				target.getInfluencerDistribution().add(sId);
			}
		});
	}

	@Override
	public Query upsertQuery(BetPredictionQuality example) {
		return Query.query(Criteria.where("revision").is(example.getRevision()));
	}

	@Override
	public Class<BetPredictionQuality> upsertType() {
		return BetPredictionQuality.class;
	}
}
