package de.footystats.tools.services.footy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.StringUtils;

public record SessionCookie(String cookie) {

	public LocalDateTime validUntil() {
		// Optional[PHPSESSID=eb47fq8lde9qijvgmmv3j0ktr3; expires=Thu, 26-Oct-2023 05:16:51 GMT; Max-Age=129600; path=/]

		String expiresRaw = StringUtils.substringBetween(cookie, "expires=", ";");
		expiresRaw = StringUtils.replace(expiresRaw, "-", " ");

		return LocalDateTime.parse(expiresRaw, DateTimeFormatter.RFC_1123_DATE_TIME);
	}

	public boolean cookieIsValid() {
		return validUntil().isAfter(LocalDateTime.now());
	}
}
