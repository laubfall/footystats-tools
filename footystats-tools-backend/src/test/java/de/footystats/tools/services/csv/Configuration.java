package de.footystats.tools.services.csv;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.services.EncryptionService;
import de.footystats.tools.services.domain.DomainDataService;
import de.footystats.tools.services.footy.dls.DownloadCountryLeagueStatsCsvEntry;
import de.footystats.tools.services.stats.LeagueStats;
import de.footystats.tools.services.stats.MatchStats;
import de.footystats.tools.services.stats.Team2Stats;
import de.footystats.tools.services.stats.TeamStats;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({FootystatsProperties.class, EncryptionService.class, AutowireCapableHeaderNameStrategy.class, DomainDataService.class})
public class Configuration {

	@Bean
	public CsvFileService<MatchStats> csvMatchStatsService(DomainDataService domainDataService,
		AutowireCapableHeaderNameStrategy<MatchStats> headerNameStrategy) {
		return new CsvFileService<>(domainDataService, headerNameStrategy);
	}

	@Bean
	public CsvFileService<LeagueStats> leagueStatsCsvFileService(DomainDataService domainDataService,
		AutowireCapableHeaderNameStrategy<LeagueStats> headerNameStrategy) {
		return new CsvFileService<>(domainDataService, headerNameStrategy);
	}

	@Bean
	public CsvFileService<TeamStats> teamStatsCsvFileService(DomainDataService domainDataService,
		AutowireCapableHeaderNameStrategy<TeamStats> headerNameStrategy) {
		return new CsvFileService<>(domainDataService, headerNameStrategy);
	}

	@Bean
	public CsvFileService<Team2Stats> team2StatsCsvFileService(DomainDataService domainDataService,
		AutowireCapableHeaderNameStrategy<Team2Stats> headerNameStrategy) {
		return new CsvFileService<>(domainDataService, headerNameStrategy);
	}

	@Bean
	public CsvFileService<DownloadCountryLeagueStatsCsvEntry> downloadConfigCsvFileService(DomainDataService domainDataService,
		AutowireCapableHeaderNameStrategy<DownloadCountryLeagueStatsCsvEntry> headerNameStrategy) {
		return new CsvFileService<>(domainDataService, headerNameStrategy);
	}
}
