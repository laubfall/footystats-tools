package de.ludwig.footystats.tools.backend.services.footy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import de.ludwig.footystats.tools.backend.services.csv.Configuration;
import de.ludwig.footystats.tools.backend.services.footy.dls.DownloadConfigRepository;
import de.ludwig.footystats.tools.backend.services.footy.dls.DownloadCountryLeagueStatsConfig;
import java.io.IOException;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(classes = {Configuration.class})
class CsvFileDownloadServiceTest {

	@MockBean
	CsvHttpClient csvHttpClient;

	@Autowired
	private CsvFileDownloadService fileDownloadService;

	@Autowired
	private DownloadConfigRepository downloadConfigRepository;

	@Test
	void login() {
		fileDownloadService.downloadMatchStatsCsvFile(LocalDate.now());
	}

	@Test
	void download_configured_csv_and_mark_as_dld() throws IOException {

		downloadConfigRepository.insert(DownloadCountryLeagueStatsConfig.builder().footyStatsDlId(4711).downloadBitmask(31).country("Germany").season("2023").build());

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
}
