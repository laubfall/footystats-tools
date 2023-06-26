package de.footystats.tools.services.footy.dls;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@DataMongoTest
@AutoConfigureDataMongo
@ContextConfiguration(classes = {DownloadConfigServiceConfiguration.class})
class DownloadConfigServiceTest {

	@Autowired
	private DownloadConfigService downloadConfigService;

	@Autowired
	private DownloadConfigRepository downloadConfigRepository;

	@BeforeEach
	public void cleanup() {
		downloadConfigRepository.deleteAll();
	}

	@Test
	void match_dl_bitmask_and_last_download_is_null_or_older_thirty_days() {
		var currentYear = LocalDate.now().getYear();

		var bundesliga = new DownloadCountryLeagueStatsConfig("Germany", "Bundesliga", 4711, "2022/" + currentYear, 4, null, null, null, null, null);
		// Should not be inside the result set cause dl bitmask is null
		var bundesliga2 = new DownloadCountryLeagueStatsConfig("Germany", "2. Bundesliga", 4711, "2022/" + currentYear, null, null, null, null, null,
			null);
		// Should be inside the result set cause last download of player stats is older then thirty days.
		var dlOldThenThirtyDays = System.currentTimeMillis() - DownloadConfigService.LAST_DOWNLOAD_MINUS_TIME_MILLIS;
		var bundesliga3 = new DownloadCountryLeagueStatsConfig("Germany", "3. Bundesliga", 4711, "2022/" + currentYear, 1, dlOldThenThirtyDays,
			dlOldThenThirtyDays, dlOldThenThirtyDays, dlOldThenThirtyDays, dlOldThenThirtyDays);

		downloadConfigRepository.insert(List.of(bundesliga, bundesliga2, bundesliga3));

		List<DownloadCountryLeagueStatsConfig> configs = downloadConfigService.configsWhoWantADownloadForCurrentYear();
		Assertions.assertNotNull(configs);
		Assertions.assertEquals(2, configs.size());

		Assertions.assertEquals("Bundesliga", configs.get(0).getLeague());
		Assertions.assertEquals("3. Bundesliga", configs.get(1).getLeague());
	}

	@Test
	void season_does_not_match() {
		var currentYear = LocalDate.now().getYear() + 1;
		var regionalliga = new DownloadCountryLeagueStatsConfig("Germany", "Regionalliga", 4711, currentYear + "/" + (currentYear + 1), null, null,
			null, null, null, null);
		downloadConfigRepository.insert(regionalliga);

		List<DownloadCountryLeagueStatsConfig> configs = downloadConfigService.configsWhoWantADownloadForCurrentYear();
		Assertions.assertNotNull(configs);
		Assertions.assertTrue(configs.isEmpty());
	}

	@Test
	void last_download_to_young_so_no_match() {
		var currentYear = LocalDate.now().getYear();
		// Should be inside the result set cause last download of player stats is older then thirty days.
		var bundesliga3 = new DownloadCountryLeagueStatsConfig("Germany", "3. Bundesliga", 4711, "2022/" + currentYear, 1,
			System.currentTimeMillis() - 1000L, null, null, null, null);
		downloadConfigRepository.insert(bundesliga3);
		List<DownloadCountryLeagueStatsConfig> configs = downloadConfigService.configsWhoWantADownloadForCurrentYear();
		Assertions.assertNotNull(configs);
		Assertions.assertTrue(configs.isEmpty());
	}

	@Test
	void configs_of_past_years_with_and_without_a_dl() {
		var currentYear = LocalDate.now().getYear();
		// last year, wants all dls but no dl so far.
		var bundesliga = new DownloadCountryLeagueStatsConfig("Germany", "Bundesliga", 4711, String.valueOf(currentYear - 1), 31, null, null, null,
			null, null);
		downloadConfigRepository.insert(bundesliga);

		// last year, but already downloaded team stats
		var bundesliga2 = new DownloadCountryLeagueStatsConfig("Germany", "2. Bundesliga", 4711, String.valueOf(currentYear - 1), 2, null,
			System.currentTimeMillis(), null, null, null);
		downloadConfigRepository.insert(bundesliga2);

		// last year, wanted league dl but no dl so far.
		var bundesliga3 = new DownloadCountryLeagueStatsConfig("Germany", "3. Bundesliga", 4711, String.valueOf(currentYear - 1), 1, null, null, null,
			null, null);
		downloadConfigRepository.insert(bundesliga3);

		List<DownloadCountryLeagueStatsConfig> configs = downloadConfigService.configsWhoWantADownloadForPreviousYears();
		Assertions.assertNotNull(configs);
		Assertions.assertEquals(2, configs.size());
	}

	@Test
	void config_current_year_but_search_for_older_ones() {
		var currentYear = LocalDate.now().getYear();
		// wants a download for team2stats but is for the current year
		var bundesliga2 = new DownloadCountryLeagueStatsConfig("Germany", "2. Bundesliga", 4711, String.valueOf(currentYear), 8, null, null, null,
			null, null);
		downloadConfigRepository.insert(bundesliga2);

		List<DownloadCountryLeagueStatsConfig> configs = downloadConfigService.configsWhoWantADownloadForPreviousYears();
		Assertions.assertTrue(configs.isEmpty());
	}
}
