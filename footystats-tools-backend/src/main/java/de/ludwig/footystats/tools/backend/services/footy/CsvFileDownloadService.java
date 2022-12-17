package de.ludwig.footystats.tools.backend.services.footy;

import de.ludwig.footystats.tools.backend.FootystatsProperties;
import de.ludwig.footystats.tools.backend.services.EncryptionService;
import de.ludwig.footystats.tools.backend.services.ServiceException;
import de.ludwig.footystats.tools.backend.services.settings.Settings;
import de.ludwig.footystats.tools.backend.services.settings.SettingsRepository;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class CsvFileDownloadService {

	private static final Logger logger = LoggerFactory.getLogger(CsvFileDownloadService.class);

	private final FootystatsProperties properties;

	private final SettingsRepository settingsRepository;

	private final EncryptionService encryptionService;

	public CsvFileDownloadService(FootystatsProperties properties, SettingsRepository settingsRepository, EncryptionService encryptionService) {
		this.properties = properties;
		this.settingsRepository = settingsRepository;
		this.encryptionService = encryptionService;
	}

	public List<String> downloadMatchStatsCsvFile(LocalDate matchStatsForDay) {
		var sessionCookie = login();
		return downloadMatchStatsCsvFile(matchStatsForDay, sessionCookie);
	}

	private List<String> downloadMatchStatsCsvFile(LocalDate matchStatsForDay, SessionCookie sessionCookie) {
		try {
			final URL url = new URL(properties.getWebpage().getBaseUrl() + properties.getWebpage().getMatchStatsDownloadRessource() + matchStatsForDay.toEpochSecond(LocalTime.of(0, 0, 0), ZoneOffset.UTC));
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

	public SessionCookie login() {

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

			Optional<String> phpsessid = http.getHeaderFields().get("Set-Cookie").stream().filter(sc -> sc.startsWith("PHPSESSID")).findAny();
			if(phpsessid.isEmpty()){
				throw new ServiceException(ServiceException.Type.CSV_FILE_DOWNLOAD_SERVICE_SESSION_ID_MISSING);
			}
			var sc = new SessionCookie(phpsessid.get());
			return sc;
		} catch (IOException e) {
			throw new ServiceException(ServiceException.Type.CSV_FILE_DOWNLOAD_SERVICE_RETRIEVING_SESSION_FAILED, e);
		}
	}
}
