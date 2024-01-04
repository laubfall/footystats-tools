package de.footystats.tools.services.footy;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.services.ServiceException;
import de.footystats.tools.services.csv.CsvFileService;
import de.footystats.tools.services.stats.MatchStats;
import de.footystats.tools.services.stats.MatchStatsService;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MatchStatsCsvFileDownloadService extends CsvFileDownloadService {


	private final CsvFileService<MatchStats> csvFileService;
	private final MatchStatsService matchStatsService;

	protected MatchStatsCsvFileDownloadService(FootystatsProperties properties, CsvHttpClient csvHttpClient,
		CsvFileService<MatchStats> csvFileService, MatchStatsService matchStatsService) {
		super(properties, csvHttpClient);
		this.csvFileService = csvFileService;
		this.matchStatsService = matchStatsService;
	}

	public void downloadMatchStatsCsvFileAndImport() {
		var rawMatches = downloadMatchStatsCsvFile(LocalDate.now());

		workingWithTempFile(fileStream -> {
			List<MatchStats> matchStats = csvFileService.importFile(fileStream, MatchStats.class);
			log.info("MatchStatc csv ile contains " + matchStats.size() + " matches.");
			matchStats.forEach(matchStatsService::importMatchStats);
		}, rawMatches, "match_expanded");
	}

	private List<String> downloadMatchStatsCsvFile(LocalDate matchStatsForDay, SessionCookie sessionCookie) {
		try {
			final URL url = URI.create(
				properties.getWebpage().getBaseUrl() + properties.getWebpage().getMatchStatsDownloadRessource() + matchStatsForDay.toEpochSecond(
					LocalTime.now(), ZoneId.of("Europe/Berlin").getRules().getOffset(LocalDateTime.now()))).toURL();
			return csvHttpClient.connectToFootystatsAndRetrieveFileContent(sessionCookie, url);
		} catch (IOException e) {
			throw new ServiceException(ServiceException.Type.CSV_FILE_DOWNLOAD_SERVICE_DL_FAILED, e);
		}
	}


	List<String> downloadMatchStatsCsvFile(LocalDate matchStatsForDay) {
		var sessionCookie = csvHttpClient.login();
		return downloadMatchStatsCsvFile(matchStatsForDay, sessionCookie);
	}
}
