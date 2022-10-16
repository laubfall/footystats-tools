package de.ludwig.footystats.tools.backend.model;

import de.ludwig.footystats.tools.backend.services.stats.MatchStats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@JsonTest
@ContextConfiguration(classes = {Configuration.class})
public class MatchStatsTest {

    @Autowired
    private JacksonTester<MatchStats> json;

    @Test
    public void serialize(){

    }

    @Test
    public void deserialize(){
        /* not sure about the intention of this test
        var inputStream = getClass().getResourceAsStream("matches_expanded-1630235153-expectRenamed.csv");
        try {
            var matchStats = json.parse(IOUtils.toByteArray(inputStream));
            matchStats.assertThat().isNotNull();
            Assertions.assertNotNull(matchStats.getObject());
        } catch (IOException e) {
            Assertions.fail(e);
        }
         */
    }
}
