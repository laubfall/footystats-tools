package de.ludwig.footystats.tools.backend.controller;

import de.ludwig.footystats.tools.backend.model.MatchStats;
import de.ludwig.footystats.tools.backend.services.match.MatchRepository;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest
@ContextConfiguration(classes = {Configuration.class})
@AutoConfigureDataMongo
public class FootyStatsCsvUploadControllerTest {

    @Autowired
    private MatchStatsRepository matchStatsRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    private void cleanup(){
        matchStatsRepository.deleteAll();
    }

    @Test
    void uploadMatchStats() throws Exception {
        var originalFileName = "matches_expanded-1630235153-expectRenamed.csv";
        try (var csvFileStream = getClass().getResourceAsStream(originalFileName);) {
            var mmf = new MockMultipartFile("file", originalFileName, null, csvFileStream);
            mvc.perform(RestDocumentationRequestBuilders.multipart("/uploadFile").file(mmf)).andExpect(status().isOk());

            var m = matchRepository.findAll();

            var msss = matchStatsRepository.findAll();
            List<MatchStats> matchStatsImported = matchStatsRepository.findByCountry("Germany");
            Assertions.assertFalse(matchStatsImported.isEmpty());

        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
}
