package de.footystats.tools.services.footy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Downloads matchStats from footystats as background task.
 * <p>
 * Doing this at 00:00 and 12:00 every day.
 */
@Component
public class MatchStatsDownloadTask {

	private static final Logger logger = LoggerFactory.getLogger(MatchStatsDownloadTask.class);

	private final MatchStatsCsvFileDownloadService matchStatsFileDownloadService;

	public MatchStatsDownloadTask(MatchStatsCsvFileDownloadService matchStatsFileDownloadService) {
		this.matchStatsFileDownloadService = matchStatsFileDownloadService;
	}

	@Scheduled(cron = "0 0 0 ? * *")
	@Scheduled(cron = "0 0 8 ? * *")
	@Scheduled(cron = "0 0 16 ? * *")
	public void runMatchStatsDownload() {
		logger.info("Start downloading matchStats from footystats as background task.");
		matchStatsFileDownloadService.downloadMatchStatsCsvFileAndImport();
		logger.info("Finished downloading matchStats from footystats as background task.");
	}
}
