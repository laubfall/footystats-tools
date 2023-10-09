package de.footystats.tools.controller;

import de.footystats.tools.services.domain.DomainDataService;
import de.footystats.tools.services.stats.MatchStats;
import de.footystats.tools.services.stats.MatchStatsRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: Is this controller still needed?
 */
@RestController
@RequestMapping("/matchstats")
public class MatchStatsController {

	private final MatchStatsRepository matchStatsRepository;

	private final DomainDataService domainDataService;

	public MatchStatsController(MatchStatsRepository matchStatsRepository, DomainDataService domainDataService) {
		this.matchStatsRepository = matchStatsRepository;
		this.domainDataService = domainDataService;
	}

	@GetMapping("/{country}")
	public List<MatchStats> findByCountry(@PathVariable String country) {
		var countryObj = domainDataService.countryByName(country);
		return matchStatsRepository.findByCountry(countryObj);
	}
}
