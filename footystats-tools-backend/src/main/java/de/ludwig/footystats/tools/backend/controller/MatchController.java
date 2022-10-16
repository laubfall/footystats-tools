package de.ludwig.footystats.tools.backend.controller;

import de.ludwig.footystats.tools.backend.services.match.Match;
import de.ludwig.footystats.tools.backend.services.match.MatchRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/match")
public class MatchController {
    private MatchRepository matchRepository;

    public MatchController(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @PostMapping(value = "/list", consumes = {"application/json"}, produces = {"application/json"})
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
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) private LocalDateTime end;

        @Setter
        @Getter
        private Paging paging;
    }
}

