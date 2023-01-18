package de.ludwig.footystats.tools.backend.services.footy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MatchStatsDownloadTask {

	private static final Logger logger = LoggerFactory.getLogger(MatchStatsDownloadTask.class);

	private CsvFileDownloadService matchStatsFileDownloadService;

	public MatchStatsDownloadTask(CsvFileDownloadService matchStatsFileDownloadService) {
		this.matchStatsFileDownloadService = matchStatsFileDownloadService;
	}

	@Scheduled(cron = "0 0 0,12 * * *")
	public void runMatchStatsDownload() {
		logger.info("Start downloading matchStats from footystats as background task.");
		matchStatsFileDownloadService.downloadMatchStatsCsvFileAndImport(LocalDate.now());
		matchStatsFileDownloadService.downloadMatchStatsCsvFileAndImport(LocalDate.now().plusDays(1));
		logger.info("Finished downloading matchStats from footystats as background task.");
	}
}