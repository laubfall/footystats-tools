package de.ludwig.footystats.tools.backend.services.footy;

import de.ludwig.footystats.tools.backend.FootystatsProperties;
import de.ludwig.footystats.tools.backend.services.ServiceException;
import de.ludwig.footystats.tools.backend.services.csv.CsvFileService;
import de.ludwig.footystats.tools.backend.services.settings.Settings;
import de.ludwig.footystats.tools.backend.services.settings.SettingsRepository;
import de.ludwig.footystats.tools.backend.services.stats.MatchStats;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatsService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class CsvFileDownloadService {

	private static final Logger logger = LoggerFactory.getLogger(CsvFileDownloadService.class);

	private final FootystatsProperties properties;

	private final SettingsRepository settingsRepository;

	private final CsvFileService<MatchStats> csvFileService;

	private final MatchStatsService matchStatsService;

	public CsvFileDownloadService(FootystatsProperties properties, SettingsRepository settingsRepository, CsvFileService<MatchStats> csvFileService, MatchStatsService matchStatsService) {
		this.properties = properties;
		this.settingsRepository = settingsRepository;
		this.csvFileService = csvFileService;
		this.matchStatsService = matchStatsService;
	}

	public void downloadConfiguredStats(){

	}

	public void downloadMatchStatsCsvFileAndImport() {
		var rawMatches = downloadMatchStatsCsvFile(LocalDate.now());
		File tmpFile = null;
		try {
			tmpFile = File.createTempFile("matchStats", "csv");
			FileUtils.writeLines(tmpFile, rawMatches);

			List<MatchStats> matchStats = csvFileService.importFile(new FileInputStream(tmpFile), MatchStats.class);
			matchStats.forEach(matchStatsService::importMatchStats);
		} catch (IOException e) {
			logger.error("An exception occured while reading match stats", e);
			if (tmpFile != null && tmpFile.exists()) {
				FileUtils.deleteQuietly(tmpFile);
			}
		}
	}

	List<String> downloadMatchStatsCsvFile(LocalDate matchStatsForDay) {
		var sessionCookie = login();
		return downloadMatchStatsCsvFile(matchStatsForDay, sessionCookie);
	}

	private List<String> downloadMatchStatsCsvFile(LocalDate matchStatsForDay, SessionCookie sessionCookie) {
		try {
			LocalDateTime now = LocalDateTime.now();
			final URL url = new URL(
				properties.getWebpage().getBaseUrl() + properties.getWebpage().getMatchStatsDownloadRessource()
					+ matchStatsForDay.toEpochSecond(LocalTime.now(), ZoneId.of("Europe/Berlin").getRules().getOffset(LocalDateTime.now())));
			URLConnection con = url.openConnection();
			HttpURLConnection http = (HttpURLConnection) con;
			http.setRequestMethod("GET"); // PUT is another valid option
			http.setDoOutput(true);
			http.setRequestProperty("cookie", sessionCookie.cookie());

			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			http.connect();
			var lines = IOUtils.readLines(http.getInputStream(), StandardCharsets.UTF_8);
			return lines;
		} catch (IOException e) {
			throw new ServiceException(ServiceException.Type.CSV_FILE_DOWNLOAD_SERVICE_DL_FAILED, e);
		}
	}

	private SessionCookie login() {
		Optional<Settings> optSettings = settingsRepository.findAll().stream().findAny();
		if (optSettings.isEmpty()) {
			throw new ServiceException(ServiceException.Type.CSV_FILE_DOWNLOAD_SERVICE_SETTINGS_MISSING);
		}

		var settings = optSettings.get();

		try {
			final URL url = new URL(properties.getWebpage().getBaseUrl() + properties.getWebpage().getLoginRessource());
			URLConnection con = url.openConnection();
			HttpURLConnection http = (HttpURLConnection) con;
			http.setRequestMethod("POST"); // PUT is another valid option
			http.setDoOutput(true);

			Map<String, String> arguments = new HashMap<>();
			arguments.put("username", settings.getFootyStatsUsername());
			arguments.put("password", settings.getFootyStatsPassword().getPassword());
			StringJoiner sj = new StringJoiner("&");
			for (Map.Entry<String, String> entry : arguments.entrySet())
				sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
					+ URLEncoder.encode(entry.getValue(), "UTF-8"));
			byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
			int length = out.length;

			http.setFixedLengthStreamingMode(length);
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			http.connect();
			try (OutputStream os = http.getOutputStream()) {
				os.write(out);
			}

			checkLogin(http);

			Optional<String> phpsessid = http.getHeaderFields().get("Set-Cookie").stream()
				.filter(sc -> sc.startsWith("PHPSESSID")).findAny();
			if (phpsessid.isEmpty()) {
				throw new ServiceException(ServiceException.Type.CSV_FILE_DOWNLOAD_SERVICE_SESSION_ID_MISSING);
			}
			var sc = new SessionCookie(phpsessid.get());
			return sc;
		} catch (IOException e) {
			throw new ServiceException(ServiceException.Type.CSV_FILE_DOWNLOAD_SERVICE_RETRIEVING_SESSION_FAILED, e);
		}
	}

	private static void checkLogin(HttpURLConnection http) throws IOException {
		var content = IOUtils.toString(http.getInputStream(), "UTF-8");
		if (content.contains("Your Username, Email or Password is incorrect.")) {
			throw new ServiceException(ServiceException.Type.CSV_FILE_DOWNLOAD_SERVICE_LOGIN_FAILED);
		}
	}
}
