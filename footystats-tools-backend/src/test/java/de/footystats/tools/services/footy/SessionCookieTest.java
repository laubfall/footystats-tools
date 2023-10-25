package de.footystats.tools.services.footy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class SessionCookieTest {

	static Object[][] validCookieParams() {
		return new Object[][]{
			{"Optional[PHPSESSID=324324fdrdfs32; expires=Thu, 19-Oct-2023 05:16:51 GMT; Max-Age=129600; path=/]", false},
			{"Optional[PHPSESSID=324324fdrdfs32; expires=Thu, 22-Oct-2099 05:16:51 GMT; Max-Age=129600; path=/]", true}
		};
	}

	@Test
	void expireCookie() {
		SessionCookie cookie = new SessionCookie("Optional[PHPSESSID=324324fdrdfs32; expires=Thu, 26-Oct-2023 05:16:51 GMT; Max-Age=129600; path=/]");
		LocalDateTime localDateTime = cookie.validUntil();
		Assertions.assertNotNull(localDateTime);
	}

	@ParameterizedTest
	@MethodSource("validCookieParams")
	void validCookie(String phpSessionId, boolean expected) {
		var cookie = new SessionCookie(phpSessionId);
		Assertions.assertEquals(expected, cookie.cookieIsValid());
	}
}
