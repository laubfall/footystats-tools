package de.ludwig.footystats.tools.backend.services.footy.dls;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DownloadConfigRepository extends MongoRepository<DownloadCountryLeagueStatsConfig, String> {
	List<DownloadCountryLeagueStatsConfig> findAllBySeasonEndsWithAndDownloadBitmaskGreaterThan(String season, int downloadBitmask);
}
