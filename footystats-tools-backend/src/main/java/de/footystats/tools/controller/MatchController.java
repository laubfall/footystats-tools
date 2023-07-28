package de.footystats.tools.controller;

import de.footystats.tools.controller.jobs.JobInformation;
import de.footystats.tools.services.match.MatchSearch;
import de.footystats.tools.services.match.MatchService;
import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.outcome.StatisticalResultOutcome;
import de.footystats.tools.services.prediction.outcome.StatisticalResultOutcomeService;
import de.footystats.tools.services.stats.batch.IMatchStatsJobService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.batch.core.JobExecution;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/match")
public class MatchController {

	private final MatchService matchService;

	private final IMatchStatsJobService matchStatsJobService;

	private final StatisticalResultOutcomeService statisticalResultOutcomeService;

	public MatchController(MatchService matchService, IMatchStatsJobService matchStatsJobService,
		StatisticalResultOutcomeService statisticalResultOutcomeService) {
		this.matchService = matchService;
		this.matchStatsJobService = matchStatsJobService;
		this.statisticalResultOutcomeService = statisticalResultOutcomeService;
	}

	@PostMapping(value = "/list", consumes = {"application/json"}, produces = {"application/json"})
	public PagingResponse<MatchListElement> listMatches(@RequestBody ListMatchRequest request) {
		// If fullTextSearchterm is not empty we split it by space and add the resulting list to the fullTextSearchTerms list
		final List<String> fullTextSearchTerms = new ArrayList<>();
		if (request.fullTextSearchTerms != null && !request.fullTextSearchTerms.isEmpty()) {
			fullTextSearchTerms.addAll(List.of(request.fullTextSearchTerms.split(" ")));
		}
		var matches = matchService.find(MatchSearch.builder().countries(request.country).leagues(request.league).start(request.start).end(request.end)
			.fullTextSearchTerms(fullTextSearchTerms).pageable(request.paging.convert()).build());

		var modelMapper = new ModelMapper();
		List<MatchListElement> result = matches.map(m -> modelMapper.map(m, MatchListElement.class)).map(m -> {
			final List<StatisticalResultOutcome> statisticalOutcomes = new ArrayList<>();
			statisticalOutcomes.add(statisticalResultOutcomeService.compute(m.getO05(), Bet.OVER_ZERO_FIVE));
			statisticalOutcomes.add(statisticalResultOutcomeService.compute(m.getO15(), Bet.OVER_ONE_FIVE));
			statisticalOutcomes.add(statisticalResultOutcomeService.compute(m.getBttsYes(), Bet.BTTS_YES));
			m.setStatisticalResultOutcome(statisticalOutcomes);
			return m;
		}).stream().toList();

		return new PagingResponse<>(matches.getTotalPages(), matches.getTotalElements(), result);
	}

	@PatchMapping(value = "/stats", produces = {"application/json"})
	public JobInformation reimportMatchStats() {
		final JobExecution jobExecution = matchStatsJobService.startReimportMatchStatsJob();
		return JobInformation.convert(jobExecution);
	}

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

		@Getter
		@Setter
		private String fullTextSearchTerms;

		@Setter
		@Getter
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
		private LocalDateTime end;

		@Setter
		@Getter
		private Paging paging;
	}
}
