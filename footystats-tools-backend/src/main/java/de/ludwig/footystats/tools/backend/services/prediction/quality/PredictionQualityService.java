package de.ludwig.footystats.tools.backend.services.prediction.quality;

import de.ludwig.footystats.tools.backend.FootystatsProperties;
import de.ludwig.footystats.tools.backend.services.MongoService;
import de.ludwig.footystats.tools.backend.services.match.Match;
import de.ludwig.footystats.tools.backend.services.match.MatchRepository;
import de.ludwig.footystats.tools.backend.services.match.MatchService;
import de.ludwig.footystats.tools.backend.services.prediction.*;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PredictionQualityService extends MongoService<PredictionQualityReport> {

	private static final Logger logger = LoggerFactory.getLogger(PredictionQualityService.class);

	private final MatchRepository matchRepository;

	private final MatchService matchService;

	private final PredictionQualityReportRepository predictionQualityReportRepository;

	private final BetPredictionAggregateRepository betPredictionAggregateRepository;

	private final FootystatsProperties properties;

	public PredictionQualityService(MatchRepository matchRepository, MatchService matchService,
									PredictionQualityReportRepository predictionQualityReportRepository, MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter, BetPredictionAggregateRepository betPredictionAggregateRepository, FootystatsProperties properties) {
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

	public PredictionQualityReport computeQuality() {
		/**
		var latestReport = predictionQualityReportRepository.findTopByOrderByRevisionDesc();
		PredictionQualityRevision latestRevision;
		if (latestReport == null) {
			latestRevision = new PredictionQualityRevision(0);
			latestReport = new PredictionQualityReport(latestRevision, new ArrayList<>());
		}
		 */

		PageRequest pageRequest = PageRequest.of(0, properties.getPredictionQuality().getPageSizeFindingRevisionMatches());
		Page<Match> matchesPage;
		var pageCnt = 1;
		matchesPage = matchRepository.findMatchesByStateAndRevision_RevisionIsNull(MatchStatus.complete, pageRequest);
		while (matchesPage.hasContent()) {
			logger.info("Start computing prediction quality.");
			var matchesByRevision = matchesPage.getContent();
			for (Match match : matchesByRevision) {
				var predictionAggregates = measure(match);
				merge(predictionAggregates);

				// update match with revision number
				match.setRevision(new PredictionQualityRevision(0));
				matchService.upsert(match);
			}

			logger.info("Quality computed for page " + pageCnt + " of a total of " + matchesPage.getTotalPages());
			// we don't increment the page for pageRequest because the result entities are modified in that way that the query won't match them anymore.
			matchesPage = matchRepository.findMatchesByStateAndRevision_RevisionIsNull(MatchStatus.complete, pageRequest);
			pageCnt += 1;
		}

		return null; // TODO what do we return.
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

	Collection<BetPredictionAggregate> measure(Match match) {

		Collection<BetPredictionAggregate> measurements = new ArrayList<>();
		Function<PredictionResult, Boolean> relevantPredictionResult = (
			PredictionResult prediction) -> prediction != null &&
			(PredictionAnalyze.SUCCESS.equals(prediction.analyzeResult()) ||
				PredictionAnalyze.FAILED.equals(prediction.analyzeResult()));
		if (match.getO05() != null && relevantPredictionResult.apply(match.getO05())) {
			var predictionResult = match.getO05();
			BetPredictionAggregate aggregate = BetPredictionAggregate.builder().count(1L)
				.betSucceeded(PredictionAnalyze.SUCCESS.equals(match.getO05().analyzeResult()) ? 1L : 0L)
				.betFailed(PredictionAnalyze.FAILED.equals(match.getO05().analyzeResult()) ? 1L : 0L)
				.predictionPercent(predictionResult.betSuccessInPercent()).bet(Bet.OVER_ZERO_FIVE)
				.influencerDistribution(addInfluencerDistribution(predictionResult)).build();
			measurements.add(aggregate);
		}

		if (match.getBttsYes() != null && relevantPredictionResult.apply(match.getBttsYes())) {
			var predictionResult = match.getBttsYes();
			BetPredictionAggregate aggregate = BetPredictionAggregate.builder().count(1L)
				.betSucceeded(PredictionAnalyze.SUCCESS.equals(match.getBttsYes().analyzeResult()) ? 1L : 0L)
				.betFailed(PredictionAnalyze.FAILED.equals(match.getBttsYes().analyzeResult()) ? 1L : 0L)
				.predictionPercent(predictionResult.betSuccessInPercent()).bet(Bet.BTTS_YES)
				.influencerDistribution(addInfluencerDistribution(predictionResult)).build();
			measurements.add(aggregate);
		}

		return measurements;
	}

	private void addDistribution(
		BetPredictionQuality.BetPredictionQualityBuilder quality,
		PredictionResult prediction) {
		BetPredictionDistribution dist = new BetPredictionDistribution(prediction.betSuccessInPercent(), 1L,
			addInfluencerDistribution(prediction));

		// Initialize every list.
		quality.distributionBetSuccessful(new ArrayList<>()).distributionBetOnThis(new ArrayList<>()).distributionBetOnThisFailed(new ArrayList<>()).distributionDontBetOnThis(new ArrayList<>()).distributionDontBetOnThisFailed(new ArrayList<>());

		if (prediction.betOnThis() == true &&
			PredictionAnalyze.SUCCESS.equals(prediction.analyzeResult())) {
			quality.distributionBetOnThis(new ArrayList<>(List.of(dist)));
		} else if (prediction.betOnThis() == false &&
			PredictionAnalyze.SUCCESS.equals(prediction.analyzeResult())) {
			quality.distributionDontBetOnThis(new ArrayList<>(List.of(dist)));
		}

		if (prediction.betOnThis() && PredictionAnalyze.FAILED.equals(prediction.analyzeResult())) {
			quality.distributionBetOnThisFailed(new ArrayList<>(List.of(dist)));
		}

		if (prediction.betOnThis() == false &&
			PredictionAnalyze.FAILED.equals(prediction.analyzeResult())) {
			quality.distributionDontBetOnThisFailed(new ArrayList<>(List.of(dist)));
		}

		if ((prediction.betOnThis() == true &&
			PredictionAnalyze.SUCCESS.equals(prediction.analyzeResult())) ||
			(prediction.betOnThis() == false &&
				PredictionAnalyze.FAILED.equals(prediction.analyzeResult()))) {
			quality.distributionBetSuccessful(new ArrayList<>(List.of(dist)));
		}
	}

	private List<InfluencerPercentDistribution> addInfluencerDistribution(PredictionResult prediction) {
		return prediction.influencerDetailedResult().stream()
			.map(influencerResult -> new InfluencerPercentDistribution(influencerResult.influencerPredictionValue(),
				1L, influencerResult.influencerName(), influencerResult.precheckResult()))
			.collect(Collectors.toList());
	}

	private void merge(Collection<BetPredictionAggregate> measurements) {
		measurements.forEach((betPredictionAggregate) -> {

			var existingAggregate = betPredictionAggregateRepository.findByBetAndPredictionPercent(betPredictionAggregate.getBet(), betPredictionAggregate.getPredictionPercent());
			if(existingAggregate == null){
				betPredictionAggregateRepository.insert(betPredictionAggregate);
			} else {
				existingAggregate.setCount(existingAggregate.getCount()+1);
				existingAggregate.setBetSucceeded(existingAggregate.getBetSucceeded() + betPredictionAggregate.getBetSucceeded());
				existingAggregate.setBetFailed(existingAggregate.getBetFailed() + betPredictionAggregate.getBetFailed());
				mergeInfluencerDistribution(existingAggregate, betPredictionAggregate);
				betPredictionAggregateRepository.save(existingAggregate);
			}
		});
	}


	private void mergeInfluencerDistribution(
		BetPredictionAggregate target,
		BetPredictionAggregate source) {
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

	@Transactional
	public PredictionQualityReport recomputeQuality(PredictionQualityRevision revision) {
		var report = predictionQualityReportRepository.findByRevision(revision);
		if (report == null) {
			return null;
		}

		PageRequest pageRequest = PageRequest.of(0, properties.getPredictionQuality().getPageSizeFindingRevisionMatches());
		Page<Match> result = matchRepository.findMatchesByStateAndRevision(MatchStatus.complete, revision, pageRequest);
		report.setMeasurements(new ArrayList<>());
		var pageCnt = 1;
		while (result.hasContent()) {
			result.forEach((match) -> {
				var msm = this.measure(match);
				//this.merge(report, msm);
			});

			pageRequest = PageRequest.of(pageCnt, properties.getPredictionQuality().getPageSizeFindingRevisionMatches());
			result = matchRepository.findMatchesByStateAndRevision(MatchStatus.complete, revision, pageRequest);
			pageCnt += 1;
		}
		;

		predictionQualityReportRepository.delete(report);
		predictionQualityReportRepository.save(report);

		return report;
	}

	@Override
	public Query upsertQuery(PredictionQualityReport example) {
		return Query.query(Criteria.where("revision").is(example.getRevision()));
	}

	@Override
	public Class<PredictionQualityReport> upsertType() {
		return PredictionQualityReport.class;
	}
}
