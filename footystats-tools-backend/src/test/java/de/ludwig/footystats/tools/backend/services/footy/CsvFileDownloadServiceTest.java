package de.ludwig.footystats.tools.backend.services.footy;

import de.ludwig.footystats.tools.backend.services.csv.Configuration;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;

@ActiveProfiles("integration")
@Profile("integration")
@SpringBootTest
@ContextConfiguration(classes = {Configuration.class})
public class CsvFileDownloadServiceTest {
	@Autowired
	private CsvFileDownloadService fileDownloadService;

	@Disabled
	@Test
	public void login() {
		fileDownloadService.downloadMatchStatsCsvFile(LocalDate.now());
	}
}
