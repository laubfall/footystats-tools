package de.ludwig.footystats.tools.backend.services.prediction.quality;

import de.ludwig.footystats.tools.backend.services.match.Match;
import de.ludwig.footystats.tools.backend.services.match.MatchRepository;
import de.ludwig.footystats.tools.backend.services.match.MatchService;
import de.ludwig.footystats.tools.backend.services.prediction.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PredictionQualityService {

	private MatchRepository matchRepository;

	private MatchService matchService;

	private PredictionQualityReportRepository predictionQualityReportRepository;

	public PredictionQualityService(MatchRepository matchRepository, MatchService matchService,
			PredictionQualityReportRepository predictionQualityReportRepository) {
		this.matchRepository = matchRepository;
		this.predictionQualityReportRepository = predictionQualityReportRepository;
		this.matchService = matchService;
	}

	public Precast precast(PredictionQualityRevision revision) {
		var result = matchRepository.findMatchesByRevision_Revision(revision);
		var nextRevision = nextRevision(revision);
		var precast = new Precast();
		precast.setRevision(nextRevision);
		precast.setPredictionsToAssess(result.size());
		return precast;
	}

	public PredictionQualityReport computeQuality() {
		var latestReport = predictionQualityReportRepository.findTopByOrderByRevisionDesc();
		PredictionQualityRevision latestRevision;
		if (latestReport == null) {
			latestRevision = new PredictionQualityRevision(0);
			latestReport = new PredictionQualityReport(latestRevision, new ArrayList<>());
			predictionQualityReportRepository.insert(latestReport);
		}

		var matchesByRevision = matchRepository.findMatchesByRevision_RevisionIsNull();
		for (Match match : matchesByRevision) {
			var msm = measure(match);
			merge(latestReport, msm);

			// update match with revision number
			match.setRevision(latestReport.getRevision());
			matchService.upsert(match);
		}

		predictionQualityReportRepository.save(latestReport);
		return latestReport;
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

			var predictionBuilder = BetPredictionQuality.builder().bet(Bet.OVER_ZERO_FIVE).countAssessed(1L)
					.countSuccess(PredictionAnalyze.SUCCESS.equals(match.getO05().analyzeResult())
							&& match.getO05().betOnThis() ? 1L : 0L)
					.countFailed(PredictionAnalyze.FAILED.equals(match.getO05().analyzeResult())
							&& match.getO05().betOnThis() ? 1L : 0L)
					.countSuccessDontBet(PredictionAnalyze.SUCCESS.equals(match.getO05().analyzeResult())
							&& match.getO05().betOnThis() == false ? 1L : 0L)
					.countFailedDontBet(PredictionAnalyze.FAILED.equals(match.getO05().analyzeResult())
							&& match.getO05().betOnThis() == false ? 1L : 0L);

			addDistribution(predictionBuilder, match.getO05());
			measurements.add(predictionBuilder.build());
		}

		if (match.getBttsYes() != null && relevantPredictionResult.apply(match.getBttsYes())) {
			var bttsYesAnalyze = match.getBttsYes();
			var predictionBuilder = BetPredictionQuality.builder().bet(Bet.BTTS_YES).countAssessed(1l)
					.countFailed(PredictionAnalyze.FAILED.equals(bttsYesAnalyze.analyzeResult())
							&& bttsYesAnalyze.betOnThis() ? 1l : 0L)
					.countSuccess(PredictionAnalyze.SUCCESS.equals(bttsYesAnalyze.analyzeResult())
							&& bttsYesAnalyze.betOnThis() ? 1L : 0L)
					.countFailedDontBet(PredictionAnalyze.FAILED.equals(bttsYesAnalyze.analyzeResult())
							&& bttsYesAnalyze.betOnThis() == false ? 1L : 0L)
					.countSuccessDontBet(PredictionAnalyze.SUCCESS.equals(bttsYesAnalyze.analyzeResult())
							&& bttsYesAnalyze.betOnThis() == false ? 1L : 0L);

			addDistribution(predictionBuilder, match.getBttsYes());
			measurements.add(predictionBuilder.build());
		}

		return measurements;
	}

	private void addDistribution(
			BetPredictionQuality.BetPredictionQualityBuilder quality,
			PredictionResult prediction) {
		BetPredictionDistribution dist = new BetPredictionDistribution(prediction.betSuccessInPercent(), 1L,
				addInfluencerDistribution(prediction));

		if (prediction.betOnThis() == true &&
				PredictionAnalyze.SUCCESS.equals(prediction.analyzeResult())) {
			quality.distributionBetOnThis(List.of(dist));
		} else if (prediction.betOnThis() == false &&
				PredictionAnalyze.SUCCESS.equals(prediction.analyzeResult())) {
			quality.distributionDontBetOnThis(List.of(dist));
		}

		if (prediction.betOnThis() && PredictionAnalyze.FAILED.equals(prediction.analyzeResult())) {
			quality.distributionBetOnThisFailed(List.of(dist));
		}

		if (prediction.betOnThis() == false &&
				PredictionAnalyze.FAILED.equals(prediction.analyzeResult())) {
			quality.distributionDontBetOnThisFailed(List.of(dist));
		}

		if ((prediction.betOnThis() == true &&
				PredictionAnalyze.SUCCESS.equals(prediction.analyzeResult())) ||
				(prediction.betOnThis() == false &&
						PredictionAnalyze.FAILED.equals(prediction.analyzeResult()))) {
			quality.distributionBetSuccessful(List.of(dist));
		}
	}

	private List<InfluencerPercentDistribution> addInfluencerDistribution(PredictionResult prediction) {
		return prediction.influencerDetailedResult().stream()
				.map(influencerResult -> new InfluencerPercentDistribution(influencerResult.influencerPredictionValue(),
						1L, influencerResult.influencerName(), influencerResult.precheckResult()))
				.collect(Collectors.toList());
	}

	private void merge(
			PredictionQualityReport report,
			Collection<BetPredictionQuality> measurements) {
		measurements.forEach((msm) -> {
			var reportMsmOpt = report.getMeasurements().stream()
					.filter((BetPredictionQuality rMsm) -> rMsm.getBet() == msm.getBet()).findAny();
			var reportMsm = new BetPredictionQuality();
			if (reportMsmOpt.isEmpty()) {
				reportMsm = msm.toBuilder().build();
				report.getMeasurements().add(reportMsm);
			} else {
				reportMsm.setCountAssessed(reportMsm.getCountAssessed() + msm.getCountAssessed());
				reportMsm.setCountSuccess(reportMsm.getCountSuccess() + msm.getCountSuccess());
				reportMsm.setCountFailed(reportMsm.getCountFailed() + msm.getCountFailed());
				reportMsm.setCountSuccessDontBet(reportMsm.getCountSuccessDontBet() + msm.getCountSuccessDontBet());
				reportMsm.setCountFailedDontBet(reportMsm.getCountFailedDontBet() + msm.getCountFailedDontBet());
				mergeDistributions(reportMsm, msm);
			}
		});
	}

	private void mergeDistributions(
			BetPredictionQuality fullReport,
			BetPredictionQuality matchReport) {
		if (matchReport.getDistributionBetOnThis() != null) {
			mergeDistribution(
					fullReport.getDistributionBetOnThis(),
					matchReport.getDistributionBetOnThis());
		}

		if (matchReport.getDistributionDontBetOnThis() != null) {
			this.mergeDistribution(
					fullReport.getDistributionDontBetOnThis(),
					matchReport.getDistributionDontBetOnThis());
		}

		if (matchReport.getDistributionBetSuccessful() != null) {
			this.mergeDistribution(
					fullReport.getDistributionBetSuccessful(),
					matchReport.getDistributionBetSuccessful());
		}

		if (matchReport.getDistributionBetOnThisFailed() != null) {
			this.mergeDistribution(
					fullReport.getDistributionBetOnThisFailed(),
					matchReport.getDistributionBetOnThisFailed());
		}

		if (matchReport.getDistributionDontBetOnThisFailed() != null) {
			this.mergeDistribution(
					fullReport.getDistributionDontBetOnThisFailed(),
					matchReport.getDistributionDontBetOnThisFailed());
		}
	}

	private void mergeDistribution(
			List<BetPredictionDistribution> target,
			List<BetPredictionDistribution> source) {
		source.forEach((sp) -> {
			var idx = target.stream().filter((tp) -> tp.getPredictionPercent() == sp.getPredictionPercent()).findAny();

			if (idx.isPresent()) {
				var ppd = idx.get();
				ppd.setCount(ppd.getCount() + sp.getCount());
				this.mergeInfluencerDistribution(ppd, sp);
			} else {
				sp.setInfluencerDistribution(new ArrayList<>());
				target.add(sp);
			}
		});
	}

	private void mergeInfluencerDistribution(
			BetPredictionDistribution target,
			BetPredictionDistribution source) {
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

	public PredictionQualityReport recomputeQuality(PredictionQualityRevision revision) {
		var report = predictionQualityReportRepository.findByRevision_Revision(revision);
		if (report == null) {
			return null;
		}

		Collection<Match> result = matchRepository.findMatchesByRevision_Revision(revision);

		report.setMeasurements(new ArrayList<>());

		result.forEach((match) -> {
			var msm = this.measure(match);
			this.merge(report, msm);
		});

		predictionQualityReportRepository.save(report);

		return report;
	}
}
