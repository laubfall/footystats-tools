package de.ludwig.footystats.tools.backend.services.csv;

import de.ludwig.footystats.tools.backend.FootystatsProperties;
import de.ludwig.footystats.tools.backend.services.EncryptionService;
import de.ludwig.footystats.tools.backend.services.footy.dls.DownloadCountryLeagueStatsCsvEntry;
import de.ludwig.footystats.tools.backend.services.stats.LeagueStats;
import de.ludwig.footystats.tools.backend.services.stats.MatchStats;
import de.ludwig.footystats.tools.backend.services.stats.Team2Stats;
import de.ludwig.footystats.tools.backend.services.stats.TeamStats;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({FootystatsProperties.class, EncryptionService.class})
public class Configuration {

	@Bean
	public CsvFileService<MatchStats> csvMatchStatsService() {
		return new CsvFileService<>();
	}

	@Bean
	public CsvFileService<LeagueStats> leagueStatsCsvFileService() {
		return new CsvFileService<>();
	}

	@Bean
	public CsvFileService<TeamStats> teamStatsCsvFileService() {
		return new CsvFileService<>();
	}

	@Bean
	public CsvFileService<Team2Stats> team2StatsCsvFileService() {
		return new CsvFileService<>();
	}

	@Bean
	public CsvFileService<DownloadCountryLeagueStatsCsvEntry> downloadConfigCsvFileService() {
		return new CsvFileService<>();
	}
}
