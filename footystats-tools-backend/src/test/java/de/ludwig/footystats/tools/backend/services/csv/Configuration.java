package de.ludwig.footystats.tools.backend.services.csv;

import de.ludwig.footystats.tools.backend.services.footy.CsvFileDownloadService;
import de.ludwig.footystats.tools.backend.services.stats.LeagueStats;
import de.ludwig.footystats.tools.backend.services.stats.MatchStats;
import de.ludwig.footystats.tools.backend.services.stats.TeamStats;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
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
	public CsvFileDownloadService fileDownloadService(){return new CsvFileDownloadService();}
}
