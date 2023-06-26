package de.footystats.tools.services.footy.dls;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DownloadCountryLeagueStatsConfigTest {

	@Test
	void list_file_type_bits_never_downloaded_before() {
		var config = new DownloadCountryLeagueStatsConfig("", "", 0, "", 31, null, null, null, null, null);
		var result = config.typesWithWantedDownload();
		Assertions.assertEquals(5, result.size());
	}

	@Test
	void list_file_type_bits_with_old_dls() {
		var toOld = System.currentTimeMillis() - DownloadConfigService.LAST_DOWNLOAD_MINUS_TIME_MILLIS - 1000;
		var config = new DownloadCountryLeagueStatsConfig("", "", 0, "", 1, toOld, null, null, null, null);
		var result = config.typesWithWantedDownload();
		Assertions.assertEquals(1, result.size());
	}

	@Test
	void list_file_type_bits_with_old_and_young_dls() {
		var toOld = System.currentTimeMillis() - DownloadConfigService.LAST_DOWNLOAD_MINUS_TIME_MILLIS - 1000;
		var toYoung = System.currentTimeMillis();
		var config = new DownloadCountryLeagueStatsConfig("", "", 0, "", 11, toOld, toYoung, null, toOld, null);
		var result = config.typesWithWantedDownload();
		Assertions.assertEquals(2, result.size());
	}

	@Test
	void to_old_but_not_configured_via_bitmask() {
		var toOld = System.currentTimeMillis() - DownloadConfigService.LAST_DOWNLOAD_MINUS_TIME_MILLIS - 1000;
		var config = new DownloadCountryLeagueStatsConfig("", "", 0, "", 8, toOld, null, null, toOld, null);
		var result = config.typesWithWantedDownload();
		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals(8, result.get(0).getBit());
	}
}
