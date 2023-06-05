package de.ludwig.footystats.tools.backend.services.footy.dls;

import de.ludwig.footystats.tools.backend.services.ServiceException;
import de.ludwig.footystats.tools.backend.services.footy.CsvFileDownloadService;
import de.ludwig.footystats.tools.backend.services.footy.MatchStatsDownloadTask;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ConfiguredStatsDownloadTask {

	private final CsvFileDownloadService csvFileDownloadService;

	public ConfiguredStatsDownloadTask(CsvFileDownloadService csvFileDownloadService) {
		this.csvFileDownloadService = csvFileDownloadService;
	}

	@Scheduled(cron = "0 0 1 * * *")
	public void runDownloadConfiguredStats(){
		log.info("Start downloading configured stats");
		try {
			csvFileDownloadService.downloadConfiguredStats();
			log.info("Finished downloading configured stats");
		} catch (ServiceException e){
			log.error("Failed downloading configured stats", e);
		}
	}
}
