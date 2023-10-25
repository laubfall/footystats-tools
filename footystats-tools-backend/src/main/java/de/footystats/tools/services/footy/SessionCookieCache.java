package de.footystats.tools.services.footy;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class SessionCookieCache {

	private final Map<String, SessionCookie> cookies = new HashMap<>();

	public Optional<SessionCookie> validCookieFor(String username) {
		synchronized (cookies) {
			if (!cookies.containsKey(username)) {
				return Optional.empty();
			}

			SessionCookie sessionCookie = cookies.get(username);
			if (sessionCookie.cookieIsValid()) {
				return Optional.of(sessionCookie);
			}

			cookies.remove(username);
		}

		return Optional.empty();
	}

	public void addCookie(SessionCookie cookie, String username) {
		synchronized (cookies) {
			cookies.put(username, cookie);
		}
	}

}
