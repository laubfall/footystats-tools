package de.footystats.tools.apps;

import de.footystats.tools.BackendApplication;
import de.footystats.tools.services.match.Match;
import de.footystats.tools.services.match.MatchRepository;
import de.footystats.tools.services.stats.MatchStats;
import de.footystats.tools.services.stats.MatchStatsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * This class is used to normalize country names in the database. The first version of footystats tool stored country names as provided by the csv
 * files. It turned out that league-stats csv files does not contain the country name but provide it via the filename in a slightly different format.
 * This format is a more technical representation and now used all over the application.
 * <p/>
 * Call the class from the final bundle with this cmd:
 * <p/>
 * java -cp backend-1.1.3.jar -Dspring.profiles.active=apps,dev -Dloader.main=de.footystats.tools.apps.NormalizeCountryNames
 * org.springframework.boot.loader.PropertiesLauncher
 */
@Slf4j
@Profile("!test && apps")
@SpringBootApplication(scanBasePackageClasses = BackendApplication.class)
public class NormalizeCountryNames implements CommandLineRunner {

	private final MatchStatsRepository matchStatsRepository;

	private final MatchRepository matchRepository;

	public NormalizeCountryNames(MatchRepository matchRepository, MatchStatsRepository matchStatsRepository) {
		this.matchStatsRepository = matchStatsRepository;
		this.matchRepository = matchRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(NormalizeCountryNames.class, args);
	}

	@Override
	public void run(String... args) {
		Pageable pageRequest = PageRequest.of(0, 100);

		Page<MatchStats> all;
		do {
			all = matchStatsRepository.findAll(pageRequest);
			matchStatsRepository.saveAll(all.getContent());
			pageRequest = all.nextPageable();
		} while (all.hasNext());

		pageRequest = PageRequest.of(0, 100);
		Page<Match> allMatches;
		do {
			allMatches = matchRepository.findAll(pageRequest);
			matchRepository.saveAll(allMatches.getContent());
			pageRequest = allMatches.nextPageable();
		} while (allMatches.hasNext());

		System.exit(0);
	}
}
