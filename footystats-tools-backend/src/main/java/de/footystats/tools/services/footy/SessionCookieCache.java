package de.footystats.tools.services.footy;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Caches session cookies for users.
 */
@Slf4j
@Service
public class SessionCookieCache {

	private final Map<String, SessionCookie> cookies = new HashMap<>();

	public Optional<SessionCookie> validCookieFor(String username) {
		synchronized (cookies) {
			if (!cookies.containsKey(username)) {
				log.info("No cookie for user {}", username);
				return Optional.empty();
			}

			SessionCookie sessionCookie = cookies.get(username);
			if (sessionCookie.cookieIsValid()) {
				return Optional.of(sessionCookie);
			}

			log.info("Cookie for user {} is invalid, removing it", username);
			cookies.remove(username);
		}

		return Optional.empty();
	}

	public void addCookie(SessionCookie cookie, String username) {
		synchronized (cookies) {
			cookies.put(username, cookie);
			log.info("Added cookie for user {}", username);
		}
	}

	public void invalidateCookie(String username) {
		synchronized (cookies) {
			if (cookies.containsKey(username)) {
				cookies.remove(username);
				log.info("Invalidated (removed from cache) cookie for user {}", username);
			} else {
				log.info("No cookie for user {} to invalidate.", username);
			}
		}
	}
}
