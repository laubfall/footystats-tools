package de.ludwig.footystats.tools.backend.services.csv;

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
}
