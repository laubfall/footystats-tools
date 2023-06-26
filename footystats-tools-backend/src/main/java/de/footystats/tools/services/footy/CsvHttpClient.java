package de.footystats.tools.services.footy;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.services.ServiceException;
import de.footystats.tools.services.settings.Settings;
import de.footystats.tools.services.settings.SettingsRepository;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

/**
 * Does the communication via http with footystats.org.
 */
@Service
public class CsvHttpClient {

	public static final String UTF_8 = "UTF-8";
	private final FootystatsProperties properties;
	private final SettingsRepository settingsRepository;

	CsvHttpClient(FootystatsProperties properties, SettingsRepository settingsRepository) {
		this.properties = properties;
		this.settingsRepository = settingsRepository;
	}

	public List<String> connectToFootystatsAndRetrieveFileContent(SessionCookie sessionCookie, URL url) throws IOException {
		URLConnection con = url.openConnection();
		HttpURLConnection http = (HttpURLConnection) con;
		http.setRequestMethod("GET");
		http.setDoOutput(true);
		http.setRequestProperty("cookie", sessionCookie.cookie());
		http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		http.connect();
		return IOUtils.readLines(http.getInputStream(), StandardCharsets.UTF_8);
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
				sj.add(URLEncoder.encode(entry.getKey(), UTF_8) + "="
					+ URLEncoder.encode(entry.getValue(), UTF_8));
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
			return new SessionCookie(phpsessid.get());
		} catch (IOException e) {
			throw new ServiceException(ServiceException.Type.CSV_FILE_DOWNLOAD_SERVICE_RETRIEVING_SESSION_FAILED, e);
		}
	}

	private static void checkLogin(HttpURLConnection http) throws IOException {
		var content = IOUtils.toString(http.getInputStream(), UTF_8);
		if (content.contains("Your Username, Email or Password is incorrect.")) {
			throw new ServiceException(ServiceException.Type.CSV_FILE_DOWNLOAD_SERVICE_LOGIN_FAILED);
		}
	}
}
