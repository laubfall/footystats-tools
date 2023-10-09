package de.footystats.tools.services.match;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.services.domain.Country;
import de.footystats.tools.services.domain.DomainDataService;
import de.footystats.tools.services.footy.dls.ConfiguredCsvDownloadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class CachedConfiguredStatsServiceTest {

	@Autowired
	private FootystatsProperties properties;

	@MockBean
	private ConfiguredCsvDownloadService configuredCsvDownloadService;

	@Autowired
	private DomainDataService domainDataService;

	static Object[][] downloadCacheLifetimeValues() {
		return new Object[][]{
			{100L, 2},
			{250L, 2},
			{50L, 1}
		};
	}

	@BeforeEach
	void cleanUp() {
		Mockito.reset(configuredCsvDownloadService);
	}

	@ParameterizedTest
	@MethodSource("downloadCacheLifetimeValues")
	void downloadDependingOnCacheLifetime(Long waitForSecondCall, int expectedCalls) throws InterruptedException {
		Country germany = domainDataService.countryByName("germany");
		properties.setMaxCacheTimeConfiguredStatsCache(80L);
		CachedConfiguredStatsService cachedConfiguredStatsService = new CachedConfiguredStatsService(configuredCsvDownloadService, properties);
		cachedConfiguredStatsService.updateConfiguredStats(germany, "Yala");
		Thread.sleep(waitForSecondCall);
		cachedConfiguredStatsService.updateConfiguredStats(germany, "Yala");
		verify(configuredCsvDownloadService, times(expectedCalls)).downloadConfiguredStats(any(Country.class), anyString());
	}

	@Test
	void downloadIfCacheIsMissing() {
		Country germany = domainDataService.countryByName("germany");
		properties.setMaxCacheTimeConfiguredStatsCache(600000L);
		CachedConfiguredStatsService cachedConfiguredStatsService = new CachedConfiguredStatsService(configuredCsvDownloadService, properties);
		cachedConfiguredStatsService.updateConfiguredStats(germany, "Yala");
		cachedConfiguredStatsService.updateConfiguredStats(germany, "Yala");
		verify(configuredCsvDownloadService, times(1)).downloadConfiguredStats(any(Country.class), anyString());
	}

	@Test
	void downloadTriggeredTwoTimesInCaseOfDifferentMatches() {
		Country germany = domainDataService.countryByName("germany");
		properties.setMaxCacheTimeConfiguredStatsCache(600000L);
		CachedConfiguredStatsService cachedConfiguredStatsService = new CachedConfiguredStatsService(configuredCsvDownloadService, properties);
		cachedConfiguredStatsService.updateConfiguredStats(germany, "Yala");
		cachedConfiguredStatsService.updateConfiguredStats(germany, "Yala 2");
		verify(configuredCsvDownloadService, times(2)).downloadConfiguredStats(any(Country.class), anyString());
	}
}
