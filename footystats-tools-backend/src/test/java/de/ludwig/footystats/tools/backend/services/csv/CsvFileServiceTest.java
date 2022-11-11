package de.ludwig.footystats.tools.backend.services.csv;

import de.ludwig.footystats.tools.backend.services.stats.LeagueStats;
import de.ludwig.footystats.tools.backend.services.stats.MatchStats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

@SpringBootTest
@ContextConfiguration(classes = {Configuration.class})
public class CsvFileServiceTest {

    @Autowired
    private CsvFileService<MatchStats> csvFileService;

	@Autowired
	private CsvFileService<LeagueStats> leagueStatsCsvFileService;

    @Test
    public void importMatchStats(){
        try(var inputStream = getClass().getResourceAsStream("matches_expanded-1630235153-expectRenamed.csv");) {
            var entries = csvFileService.importFile(inputStream, MatchStats.class);
            Assertions.assertNotNull(entries);
            Assertions.assertEquals(1, entries.size());

            var onlyMatchStats = entries.get(0);
            Assertions.assertEquals("Germany", onlyMatchStats.getCountry());
            Assertions.assertEquals("Bundesliga", onlyMatchStats.getLeague());
            Assertions.assertNotNull(onlyMatchStats.getDateGmt());
            Assertions.assertNotNull(onlyMatchStats.getDateUnix());
        } catch (IOException e) {
            Assertions.fail();
        }

    }

	@Test
	public void importLeagueStats(){
		try(var inputStream = getClass().getResourceAsStream("some-country-league-2020-to-2021-stats.csv");) {
			var entries = leagueStatsCsvFileService.importFile(inputStream, LeagueStats.class);
			Assertions.assertNotNull(entries);
			Assertions.assertEquals(1, entries.size());

			var leagueStats = entries.get(0);
			Assertions.assertEquals("Someleague", leagueStats.getName());
		} catch (IOException e) {
			Assertions.fail();
		}
	}
}
