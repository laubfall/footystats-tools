package de.ludwig.footystats.tools.backend.services.footy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Component
public class MatchStatsDownloadTask {

	private static final Logger logger = LoggerFactory.getLogger(MatchStatsDownloadTask.class);

	private CsvFileDownloadService matchStatsFileDownloadService;

	public MatchStatsDownloadTask(CsvFileDownloadService matchStatsFileDownloadService) {
		this.matchStatsFileDownloadService = matchStatsFileDownloadService;
	}

	@Scheduled(timeUnit = TimeUnit.MINUTES, fixedRate = 15)
	public void runMatchStatsDownload() {
		logger.info("Start downloading matchStats from footystats as background task.");
		try {
			matchStatsFileDownloadService.downloadMatchStatsCsvFile(LocalDate.now());
		} catch (RuntimeException e){
			logger.error("Failed executing download service", e);
		}
		logger.info("Finished downloading matchStats from footystats as background task.");
	}
}
