package de.ludwig.footystats.tools.backend.services.csv;

import de.ludwig.footystats.tools.backend.FootystatsProperties;
import de.ludwig.footystats.tools.backend.services.EncryptionService;
import de.ludwig.footystats.tools.backend.services.footy.CsvFileDownloadService;
import de.ludwig.footystats.tools.backend.services.footy.CsvFileDownloadServiceTest;
import de.ludwig.footystats.tools.backend.services.footy.dls.DownloadCountryLeagueStatsConfig;
import de.ludwig.footystats.tools.backend.services.settings.SettingsRepository;
import de.ludwig.footystats.tools.backend.services.stats.LeagueStats;
import de.ludwig.footystats.tools.backend.services.stats.MatchStats;
import de.ludwig.footystats.tools.backend.services.stats.TeamStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@TestConfiguration
@org.springframework.context.annotation.Configuration
@Import({FootystatsProperties.class,EncryptionService.class})
public class Configuration {

	@Bean
    public CsvFileService<MatchStats> csvMatchStatsService(){
        return new CsvFileService<>();
    }

	@Bean
	public CsvFileService<LeagueStats> leagueStatsCsvFileService() {return new CsvFileService<>();}

	@Bean
	public CsvFileService<TeamStats> teamStatsCsvFileService() {return new CsvFileService<>();}

	@Bean
	public CsvFileService<DownloadCountryLeagueStatsConfig> downloadConfigCsvFileService() {return new CsvFileService<>();}
}
