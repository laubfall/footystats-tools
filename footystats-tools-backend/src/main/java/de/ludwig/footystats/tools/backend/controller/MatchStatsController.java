package de.ludwig.footystats.tools.backend.controller;

import de.ludwig.footystats.tools.backend.services.stats.MatchStats;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/matchstats")
public class MatchStatsController {

    private final MatchStatsRepository matchStatsRepository;

    public MatchStatsController(MatchStatsRepository matchStatsRepository) {
        this.matchStatsRepository = matchStatsRepository;
    }

    @GetMapping("/{country}")
    public List<MatchStats> findByCountry(@PathVariable String country){
        return matchStatsRepository.findByCountry(country);
    }
}
