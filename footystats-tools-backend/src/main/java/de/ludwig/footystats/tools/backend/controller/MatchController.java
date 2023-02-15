package de.ludwig.footystats.tools.backend.controller;

import de.ludwig.footystats.tools.backend.services.match.Match;
import de.ludwig.footystats.tools.backend.services.match.MatchSearch;
import de.ludwig.footystats.tools.backend.services.match.MatchService;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatsService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchController {

	private MatchService matchService;

	private MatchStatsService matchStatsService;

	public MatchController(MatchService matchService, MatchStatsService matchStatsService) {
		this.matchService = matchService;
		this.matchStatsService = matchStatsService;
	}

	@PostMapping(value = "/list", consumes = {"application/json"}, produces = {"application/json"})
	public PagingResponse<Match> listMatches(@RequestBody ListMatchRequest request) {
		var matches = matchService.find(MatchSearch.builder().countries(request.country).leagues(request.league).start(request.start).end(request.end).pageable(request.paging.convert()).build());

		if(matches != null){
			return new PagingResponse<>(matches.getTotalPages(), matches.getTotalElements(), matches.stream().toList());
		}
		return new PagingResponse<>(0, 0, List.of());
	}

	@PatchMapping(value = "/stats", consumes = {"application/json"}, produces = {"application/json"})
	public PagingResponse<Match> reimportMatchStats(@RequestBody ListMatchRequest request){
		matchStatsService.reimportMatchStats();

		if(request == null) {
			return new PagingResponse<>(0, 0, List.of());
		}

		return listMatches(request);
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
