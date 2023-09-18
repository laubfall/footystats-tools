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
@Import({FootystatsProperties.class, EncryptionService.class})
public class Configuration {

	@Bean
	public CsvFileService<MatchStats> csvMatchStatsService(DomainDataService domainDataService) {
		return new CsvFileService<>(domainDataService);
	}

	@Bean
	public CsvFileService<LeagueStats> leagueStatsCsvFileService(DomainDataService domainDataService) {
		return new CsvFileService<>(domainDataService);
	}

	@Bean
	public CsvFileService<TeamStats> teamStatsCsvFileService(DomainDataService domainDataService) {
		return new CsvFileService<>(domainDataService);
	}

	@Bean
	public CsvFileService<Team2Stats> team2StatsCsvFileService(DomainDataService domainDataService) {
		return new CsvFileService<>(domainDataService);
	}

	@Bean
	public CsvFileService<DownloadCountryLeagueStatsCsvEntry> downloadConfigCsvFileService(DomainDataService domainDataService) {
		return new CsvFileService<>(domainDataService);
	}
}
