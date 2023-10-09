package de.footystats.tools.apps;

import de.footystats.tools.services.domain.DomainDataService;
import de.footystats.tools.services.match.MatchRepository;
import de.footystats.tools.services.stats.MatchStats;
import de.footystats.tools.services.stats.MatchStatsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Slf4j
@Profile("!test && apps")
@SpringBootApplication(scanBasePackages = {"de.footystats.tools"})
public class NormalizeCountryNames implements CommandLineRunner {

	private final MatchStatsRepository matchStatsRepository;

	private final MatchRepository matchRepository;

	public NormalizeCountryNames(MatchStatsRepository matchStatsRepository, DomainDataService domainDataService,
		ConfigurableApplicationContext context, MatchRepository matchRepository) {
		this.matchStatsRepository = matchStatsRepository;
		this.matchRepository = matchRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(NormalizeCountryNames.class, args);
	}

	@Override
	public void run(String... args) {
		Pageable pageRequest = PageRequest.of(0, 100);
		Page<MatchStats> all = matchStatsRepository.findAll(pageRequest);

		while (all.hasNext()) {
			matchStatsRepository.saveAll(all.getContent());
			all = matchStatsRepository.findAll(all.nextPageable());
		}

		System.exit(0);
	}
}
