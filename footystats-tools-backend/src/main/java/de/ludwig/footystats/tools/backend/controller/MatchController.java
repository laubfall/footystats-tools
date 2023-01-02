package de.ludwig.footystats.tools.backend.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import de.ludwig.footystats.tools.backend.services.csv.CsvFileService;
import de.ludwig.footystats.tools.backend.services.stats.MatchStats;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.ludwig.footystats.tools.backend.services.footy.CsvFileDownloadService;
import de.ludwig.footystats.tools.backend.services.match.Match;
import de.ludwig.footystats.tools.backend.services.match.MatchRepository;
import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/match")
public class MatchController {
	private MatchRepository matchRepository;

	public MatchController(MatchRepository matchRepository, CsvFileDownloadService csvFileDownloadService, CsvFileService<MatchStats> matchStatsCsvFileService) {
		this.matchRepository = matchRepository;
	}

	@PostMapping(value = "/list", consumes = { "application/json" }, produces = { "application/json" })
	public PagingResponse<Match> listMatches(@RequestBody ListMatchRequest request) {
		var pageRequest = request.paging.convert();
		var matcher = ExampleMatcher.matching();
		var sampleMatch = new Match();
		var example = Example.of(sampleMatch, matcher);
		var matches = matchRepository.findAll(example, pageRequest);
		var pr = new PagingResponse<>(matches.getTotalPages(), matches.getTotalElements(), matches.stream().toList());
		return pr;
	}

	@JsonComponent
	public static class ListMatchRequest {
		@Setter
		@Getter
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
		private LocalDateTime start;

		@Setter
		@Getter
		private String country;

		@Setter
		@Getter
		private String league;

		@Setter
		@Getter
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
		private LocalDateTime end;

		@Setter
		@Getter
		private Paging paging;
	}
}
