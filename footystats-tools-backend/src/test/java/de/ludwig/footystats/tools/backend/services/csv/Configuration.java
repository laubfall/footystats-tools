package de.ludwig.footystats.tools.backend.services.csv;

import de.ludwig.footystats.tools.backend.model.MatchStats;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    public CsvFileService<MatchStats> csvMatchStatsService(){
        return new CsvFileService<>();
    }
}
