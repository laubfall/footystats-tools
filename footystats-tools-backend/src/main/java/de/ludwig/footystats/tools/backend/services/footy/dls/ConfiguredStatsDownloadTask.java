package de.ludwig.footystats.tools.backend.services.footy.dls;

import de.ludwig.footystats.tools.backend.services.ServiceException;
import de.ludwig.footystats.tools.backend.services.footy.CsvFileDownloadService;
import de.ludwig.footystats.tools.backend.services.footy.MatchStatsDownloadTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConfiguredStatsDownloadTask {
	private static final Logger logger = LoggerFactory.getLogger(MatchStatsDownloadTask.class);

	private CsvFileDownloadService csvFileDownloadService;

	public ConfiguredStatsDownloadTask(CsvFileDownloadService csvFileDownloadService) {
		this.csvFileDownloadService = csvFileDownloadService;
	}

	@Scheduled(cron = "0 0 1 * * *")
	public void runDownloadConfiguredStats(){
		logger.info("Start downloading configured stats");
		try {
			csvFileDownloadService.downloadConfiguredStats();
			logger.info("Finished downloading configured stats");
		} catch (ServiceException e){
			logger.error("Failed downloading configured stats", e);
		}
	}
}
