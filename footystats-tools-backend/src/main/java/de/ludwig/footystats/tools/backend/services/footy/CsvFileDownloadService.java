package de.ludwig.footystats.tools.backend.services.footy;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class CsvFileDownloadService {

	public void download(LocalDate matchStatsForDay){
		var sessionCookie = login();
		download(matchStatsForDay, sessionCookie);
	}

	public void download(LocalDate matchStatsForDay, SessionCookie sessionCookie){
		URL url = null;
		try {
			url = new URL("https://footystats.org/c-dl.php?type=custom&t=matches_expanded&comp=1&un=" + matchStatsForDay.toEpochSecond(LocalTime.of(0,0,0), ZoneOffset.UTC));
			URLConnection con = url.openConnection();
			HttpURLConnection http = (HttpURLConnection)con;
			http.setRequestMethod("GET"); // PUT is another valid option
			http.setDoOutput(true);
			http.setRequestProperty("cookie", sessionCookie.cookie());

			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			http.connect();
			var lines = IOUtils.readLines(http.getInputStream(), StandardCharsets.UTF_8);
			System.out.println(lines);

		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (ProtocolException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public SessionCookie login(){
		URL url = null;
		try {
			url = new URL("https://footystats.org/login?log_me_in=1");
			URLConnection con = url.openConnection();
			HttpURLConnection http = (HttpURLConnection)con;
			http.setRequestMethod("POST"); // PUT is another valid option
			http.setDoOutput(true);

			Map<String,String> arguments = new HashMap<>();
			arguments.put("username", "");
			arguments.put("password", "");
			StringJoiner sj = new StringJoiner("&");
			for(Map.Entry<String,String> entry : arguments.entrySet())
				sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
					+ URLEncoder.encode(entry.getValue(), "UTF-8"));
			byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
			int length = out.length;

			http.setFixedLengthStreamingMode(length);
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			http.connect();
			try(OutputStream os = http.getOutputStream()) {
				os.write(out);
			}

			Optional<String> phpsessid = http.getHeaderFields().get("Set-Cookie").stream().filter(sc -> sc.startsWith("PHPSESSID")).findAny();
			var sc = new SessionCookie(phpsessid.get());
			return sc;

		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (ProtocolException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
