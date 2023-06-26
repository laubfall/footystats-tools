package de.footystats.tools.services.footy.dls;

import de.footystats.tools.services.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConfiguredStatsDownloadTask {

	private final ConfiguredCsvDownloadService csvFileDownloadService;

	public ConfiguredStatsDownloadTask(ConfiguredCsvDownloadService csvFileDownloadService) {
		this.csvFileDownloadService = csvFileDownloadService;
	}

	@Scheduled(cron = "0 0 1 * * *")
	public void runDownloadConfiguredStats() {
		log.info("Start downloading configured stats");
		try {
			csvFileDownloadService.downloadConfiguredStats();
			log.info("Finished downloading configured stats");
		} catch (ServiceException e) {
			log.error("Failed downloading configured stats", e);
		}
	}
}
