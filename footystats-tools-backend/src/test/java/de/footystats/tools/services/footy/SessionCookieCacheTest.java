package de.footystats.tools.services.footy;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class SessionCookieCacheTest {

	static Object[][] validCookieParams() {
		return new Object[][]{
			{"Optional[PHPSESSID=324324fdrdfs32; expires=Thu, 19-Oct-2023 05:16:51 GMT; Max-Age=129600; path=/]", false},
			{"Optional[PHPSESSID=324324fdrdfs32; expires=Thu, 22-Oct-2099 05:16:51 GMT; Max-Age=129600; path=/]", true}
		};
	}

	@ParameterizedTest
	@MethodSource("validCookieParams")
	void test(String sessionId, boolean expected) {
		SessionCookieCache sessionCookieCache = new SessionCookieCache();
		var sc = new SessionCookie(sessionId);
		sessionCookieCache.addCookie(sc, "test");
		Optional<SessionCookie> validCookieFor = sessionCookieCache.validCookieFor("test");
		Assertions.assertEquals(expected, validCookieFor.isPresent());
	}
}
