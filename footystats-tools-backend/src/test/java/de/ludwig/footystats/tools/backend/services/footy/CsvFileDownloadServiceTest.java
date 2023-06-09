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
import org.junit.jupiter.api.BeforeEach;
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
	private MatchStatsCsvFileDownloadService fileDownloadService;

	@BeforeEach
	void setup(){
		reset(csvHttpClient);
	}

	@Test
	void login() {
		fileDownloadService.downloadMatchStatsCsvFile(LocalDate.now());
	}
}
