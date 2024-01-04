package de.footystats.tools.services.footy;

import static org.mockito.Mockito.reset;

import de.footystats.tools.services.csv.Configuration;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
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
class CsvFileDownloadServiceTest {

	@MockBean
	CsvHttpClient csvHttpClient;

	@Autowired
	private MatchStatsCsvFileDownloadService fileDownloadService;

	@BeforeEach
	void setup() {
		reset(csvHttpClient);
	}

	@Test
	void login() {
		fileDownloadService.downloadMatchStatsCsvFile(LocalDate.now());
	}

	// Test if the Method CsvFileDownloadService.workingWithTemplateFile write correct utf-8 characters to the tempfile.
	@Test
	@SneakyThrows
	void workingWithTemplateFile() {
		List<String> rawData = IOUtils.readLines(getClass().getResourceAsStream("matches_expanded-withspecialchars.csv"),
			StandardCharsets.UTF_8.name());
		var expectedTeamName = "Ştefăneşti";
		fileDownloadService.workingWithTempFile((fis) -> {
			// Read the fileInputStream fis to a list of strings and assert that the third line contains the value blah.
			List<String> lines = null;
			lines = IOUtils.readLines(fis, "UTF-8");
			Assertions.assertTrue(lines.get(3).contains(expectedTeamName));

		}, rawData, "test");
	}

}
