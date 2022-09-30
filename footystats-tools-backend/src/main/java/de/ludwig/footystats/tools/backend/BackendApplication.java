package de.ludwig.footystats.tools.backend;

import de.ludwig.footystats.tools.backend.model.MatchStats;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
@EnableMongoRepositories
public class BackendApplication implements CommandLineRunner {

    @Autowired
    private FootystatsProperties fsProperties;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(fsProperties.getAllowedOrigins());
            }
        };
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
