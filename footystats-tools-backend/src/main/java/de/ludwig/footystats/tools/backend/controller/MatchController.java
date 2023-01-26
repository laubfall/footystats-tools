package de.ludwig.footystats.tools.backend.controller;

import de.ludwig.footystats.tools.backend.services.csv.CsvFileService;
import de.ludwig.footystats.tools.backend.services.footy.CsvFileDownloadService;
import de.ludwig.footystats.tools.backend.services.match.Match;
import de.ludwig.footystats.tools.backend.services.match.MatchRepository;
import de.ludwig.footystats.tools.backend.services.stats.MatchStats;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
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
	private MatchRepository matchRepository;

	public MatchController(MatchRepository matchRepository, CsvFileDownloadService csvFileDownloadService, CsvFileService<MatchStats> matchStatsCsvFileService) {
		this.matchRepository = matchRepository;
	}

	@PostMapping(value = "/list", consumes = {"application/json"}, produces = {"application/json"})
	public PagingResponse<Match> listMatches(@RequestBody ListMatchRequest request) {
		Page<Match> matches = null;
		var pageRequest = request.paging.convert();
		if(CollectionUtils.isEmpty(request.country) && CollectionUtils.isEmpty(request.league) && request.start == null && request.end == null){
			matches = matchRepository.findAll(pageRequest);
		} else if(CollectionUtils.isNotEmpty(request.country) && CollectionUtils.isEmpty(request.league) && request.start == null && request.end == null){
			matches = matchRepository.findMatchesByCountryIn(request.country, pageRequest);
		} else if(CollectionUtils.isNotEmpty(request.country) && CollectionUtils.isNotEmpty(request.league) && request.start == null && request.end == null){
			matches = matchRepository.findMatchesByCountryInAndLeagueIn(request.country, request.league, pageRequest);
		} else if(CollectionUtils.isNotEmpty(request.country) && CollectionUtils.isNotEmpty(request.league) && request.start != null && request.end != null){
			matches = matchRepository.findMatchesByCountryInAndLeagueInAndDateGMTBetween(request.country, request.league, request.start, request.end, pageRequest);
		} else if(CollectionUtils.isEmpty(request.country) && CollectionUtils.isEmpty(request.league) && request.start!= null && request.end == null){
			matches = matchRepository.findMatchesByDateGMTGreaterThanEqual(request.start, pageRequest);
		}

		if(matches != null){
			var pr = new PagingResponse<>(matches.getTotalPages(), matches.getTotalElements(), matches.stream().toList());
			return pr;
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
