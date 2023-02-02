package de.ludwig.footystats.tools.backend.controller;

import de.ludwig.footystats.tools.backend.services.csv.CsvFileService;
import de.ludwig.footystats.tools.backend.services.footy.CsvFileDownloadService;
import de.ludwig.footystats.tools.backend.services.match.Match;
import de.ludwig.footystats.tools.backend.services.match.MatchRepository;
import de.ludwig.footystats.tools.backend.services.match.MatchSearch;
import de.ludwig.footystats.tools.backend.services.match.MatchService;
import de.ludwig.footystats.tools.backend.services.stats.MatchStats;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchController {

	private MatchService matchService;

	public MatchController(MatchService matchService) {
		this.matchService = matchService;
	}

	@PostMapping(value = "/list", consumes = {"application/json"}, produces = {"application/json"})
	public PagingResponse<Match> listMatches(@RequestBody ListMatchRequest request) {
		var matches = matchService.find(MatchSearch.builder().countries(request.country).leagues(request.league).start(request.start).end(request.end).pageable(request.paging.convert()).build());

		if(matches != null){
			return new PagingResponse<>(matches.getTotalPages(), matches.getTotalElements(), matches.stream().toList());
		}
		return new PagingResponse<>(0, 0, List.of());
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
