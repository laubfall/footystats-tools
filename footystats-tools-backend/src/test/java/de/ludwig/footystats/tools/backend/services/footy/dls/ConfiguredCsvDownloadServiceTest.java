package de.ludwig.footystats.tools.backend.services.footy.dls;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import de.ludwig.footystats.tools.backend.services.csv.Configuration;
import de.ludwig.footystats.tools.backend.services.footy.CsvFileDownloadService;
import de.ludwig.footystats.tools.backend.services.footy.CsvHttpClient;
import java.io.IOException;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(classes = {Configuration.class})
class ConfiguredCsvDownloadServiceTest {

	@MockBean
	private CsvHttpClient csvHttpClient;

	@Autowired
	private ConfiguredCsvDownloadService fileDownloadService;

	@Autowired
	private DownloadConfigRepository downloadConfigRepository;

	@BeforeEach
	void setup(){
		reset(csvHttpClient);
		downloadConfigRepository.deleteAll();
	}

	@Test
	void download_configured_csv_and_mark_as_dld() throws IOException {
		var currentYear = LocalDate.now().getYear();
		downloadConfigRepository.insert(DownloadCountryLeagueStatsConfig.builder().footyStatsDlId(4711).downloadBitmask(31).country("Germany").season(
			String.valueOf(currentYear)).build());

		fileDownloadService.downloadConfiguredStats();
		// five calls to footystats.org cause we have five different csv files.
		verify(csvHttpClient, times(5)).connectToFootystatsAndRetrieveFileContent(any(), any());

		DownloadCountryLeagueStatsConfig downloadCountryLeagueStatsConfig = downloadConfigRepository.findAll().get(0);
		Assertions.assertNotNull(downloadCountryLeagueStatsConfig.getLastTeamsDownload());
		Assertions.assertNotNull(downloadCountryLeagueStatsConfig.getLastTeams2Download());
		Assertions.assertNotNull(downloadCountryLeagueStatsConfig.getLastLeagueDownload());

		fileDownloadService.downloadConfiguredStats();
		reset(csvHttpClient);
		// No download cause last download is not more than 30 days ago.
		verify(csvHttpClient, times(0)).connectToFootystatsAndRetrieveFileContent(any(), any());

		// Actually not implemented yet
		//Assertions.assertNotNull(downloadCountryLeagueStatsConfig.getLastPlayerDownload());
		//Assertions.assertNotNull(downloadCountryLeagueStatsConfig.getLastMatchDownload());
	}

	@Test
	void download_configured_prev_season_csv_and_mark_as_dld() throws IOException {
		downloadConfigRepository.insert(DownloadCountryLeagueStatsConfig.builder().footyStatsDlId(4711).downloadBitmask(31).country("Germany").season(
			"2022").build());

		fileDownloadService.downloadConfiguredStats();
		// five calls to footystats.org cause we have five different csv files.
		verify(csvHttpClient, times(5)).connectToFootystatsAndRetrieveFileContent(any(), any());

		DownloadCountryLeagueStatsConfig downloadCountryLeagueStatsConfig = downloadConfigRepository.findAll().get(0);
		Assertions.assertNotNull(downloadCountryLeagueStatsConfig.getLastTeamsDownload());
		Assertions.assertNotNull(downloadCountryLeagueStatsConfig.getLastTeams2Download());
		Assertions.assertNotNull(downloadCountryLeagueStatsConfig.getLastLeagueDownload());

		fileDownloadService.downloadConfiguredStats();
		reset(csvHttpClient);
		// no download cause csv files of old seasons are only downloaded once
		verify(csvHttpClient, times(0)).connectToFootystatsAndRetrieveFileContent(any(), any());

		// Actually not implemented yet
		//Assertions.assertNotNull(downloadCountryLeagueStatsConfig.getLastPlayerDownload());
		//Assertions.assertNotNull(downloadCountryLeagueStatsConfig.getLastMatchDownload());
	}
}
