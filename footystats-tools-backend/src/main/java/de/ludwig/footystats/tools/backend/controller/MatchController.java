package de.ludwig.footystats.tools.backend.controller;

import de.ludwig.footystats.tools.backend.services.match.Match;
import de.ludwig.footystats.tools.backend.services.match.MatchSearch;
import de.ludwig.footystats.tools.backend.services.match.MatchService;
import de.ludwig.footystats.tools.backend.services.prediction.Bet;
import de.ludwig.footystats.tools.backend.services.prediction.BetPredictionQuality;
import de.ludwig.footystats.tools.backend.services.prediction.outcome.StatisticalResultOutcome;
import de.ludwig.footystats.tools.backend.services.prediction.outcome.StatisticalResultOutcomeService;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityReport;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityReportRepository;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatsService;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/match")
public class MatchController {

	private MatchService matchService;

	private MatchStatsService matchStatsService;

	private PredictionQualityReportRepository predictionQualityReportRepository;

	private StatisticalResultOutcomeService statisticalResultOutcomeService;

	public MatchController(MatchService matchService, MatchStatsService matchStatsService, PredictionQualityReportRepository predictionQualityReportRepository, StatisticalResultOutcomeService statisticalResultOutcomeService) {
		this.matchService = matchService;
		this.matchStatsService = matchStatsService;
		this.predictionQualityReportRepository = predictionQualityReportRepository;
		this.statisticalResultOutcomeService = statisticalResultOutcomeService;
	}

	@PostMapping(value = "/list", consumes = {"application/json"}, produces = {"application/json"})
	public PagingResponse<MatchListElement> listMatches(@RequestBody ListMatchRequest request) {
		var matches = matchService.find(MatchSearch.builder().countries(request.country).leagues(request.league).start(request.start).end(request.end).pageable(request.paging.convert()).build());

		var modelMapper = new ModelMapper();
		List<MatchListElement> result = matches.map(m -> modelMapper.map(m, MatchListElement.class)).map(m -> {
			var statisticalOutcomes = new ArrayList<StatisticalResultOutcome>();
			statisticalOutcomes.add(statisticalResultOutcomeService.compute(m.getO05(), Bet.OVER_ZERO_FIVE));
			statisticalOutcomes.add(statisticalResultOutcomeService.compute(m.getBttsYes(), Bet.BTTS_YES));
			m.setStatisticalResultOutcome(statisticalOutcomes);
			return m;
		}).stream().collect(Collectors.toList());


		if(matches != null){
			return new PagingResponse<>(matches.getTotalPages(), matches.getTotalElements(), result);
		}
		return new PagingResponse<>(0, 0, List.of());
	}

	@PatchMapping(value = "/stats", consumes = {"application/json"}, produces = {"application/json"})
	public PagingResponse<MatchListElement> reimportMatchStats(@RequestBody ListMatchRequest request){
		matchStatsService.reimportMatchStats();

		if(request == null) {
			return new PagingResponse<>(0, 0, List.of());
		}

		return listMatches(request);
	}

	private List<ListMatchElement> convert(Page<Match> matches) {
		PredictionQualityReport report = predictionQualityReportRepository.findTopByOrderByRevisionDesc();

		var result = matches.getContent().stream().map(m -> ListMatchElement.builder().match(m).build()).toList();

		if (report != null) {
			var betPredictionMeasurements = new HashMap<Bet, BetPredictionQuality>();
			report.getMeasurements().forEach(measurement -> betPredictionMeasurements.put(measurement.getBet(), measurement));
			result.forEach(listMatchElement -> {
				BetPredictionQuality qualityBttsYes = betPredictionMeasurements.get(Bet.BTTS_YES);
				//listMatchElement.setPredictionSuccessChance(qualityBttsYes.);
			});
		}

		return result;
	}

	@JsonComponent
	public static class ListMatchRequest {
		@Setter
		@Getter
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
		private LocalDateTime start;

		@Setter
		@Getter
		private List<String> country;

		@Setter
		@Getter
		private List<String> league;

		@Setter
		@Getter
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
		private LocalDateTime end;

		@Setter
		@Getter
		private Paging paging;
	}
}
