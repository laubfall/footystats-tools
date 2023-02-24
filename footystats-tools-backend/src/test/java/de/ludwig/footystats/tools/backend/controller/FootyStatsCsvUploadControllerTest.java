package de.ludwig.footystats.tools.backend.controller;

import de.ludwig.footystats.tools.backend.services.footy.dls.DownloadConfigRepository;
import de.ludwig.footystats.tools.backend.services.stats.LeagueStatsRepository;
import de.ludwig.footystats.tools.backend.services.stats.MatchStats;
import de.ludwig.footystats.tools.backend.services.match.MatchRepository;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatsRepository;
import de.ludwig.footystats.tools.backend.services.stats.TeamStatsRepository;
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
	private LeagueStatsRepository leagueStatsRepository;

	@Autowired
	private TeamStatsRepository teamStatsRepository;

	@Autowired
	private DownloadConfigRepository downloadConfigRepository;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void cleanup(){
        matchStatsRepository.deleteAll();
		leagueStatsRepository.deleteAll();
		teamStatsRepository.deleteAll();
		downloadConfigRepository.deleteAll();
    }

	@Test
	void uploadDownloadConfig(){
		var originalFileName = "download_config-footy-stats-152153323.csv";
		try (var csvFileStream = getClass().getResourceAsStream(originalFileName);) {
			var mmf = new MockMultipartFile("file", originalFileName, null, csvFileStream);
			mvc.perform(RestDocumentationRequestBuilders.multipart("/uploadFile").file(mmf)).andExpect(status().isOk());

			var config = downloadConfigRepository.findAllBySeasonEndsWithAndDownloadBitmaskGreaterThan("2023", 0);
			Assertions.assertNotNull(config);
			Assertions.assertEquals(2, config.size());
		} catch (Exception e) {
			Assertions.fail(e);
		}
	}

    @Test
    void uploadMatchStats() throws Exception {
        var originalFileName = "matches_expanded-1630235153-expectRenamed.csv";
        try (var csvFileStream = getClass().getResourceAsStream(originalFileName);) {
            var mmf = new MockMultipartFile("file", originalFileName, null, csvFileStream);
            mvc.perform(RestDocumentationRequestBuilders.multipart("/uploadFile").file(mmf)).andExpect(status().isOk());

            List<MatchStats> matchStatsImported = matchStatsRepository.findByCountry("Germany");
            Assertions.assertFalse(matchStatsImported.isEmpty());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

	@Test
	void uploadLeagueStats(){
		var originalFileName = "some-country-league-2020-to-2021-stats.csv";
		try (var csvFileStream = getClass().getResourceAsStream(originalFileName);) {
			var mmf = new MockMultipartFile("file", originalFileName, null, csvFileStream);
			mvc.perform(RestDocumentationRequestBuilders.multipart("/uploadFile").file(mmf)).andExpect(status().isOk());

			var allLeagueStats = leagueStatsRepository.findAll();
			Assertions.assertNotNull(allLeagueStats);
			Assertions.assertEquals(1, allLeagueStats.size());

		} catch (Exception e) {
			Assertions.fail(e);
		}
	}

	@Test
	void uploadTeamStats(){
		var originalFileName = "some-country-teams-2020-to-2021-stats.csv";
		try (var csvFileStream = getClass().getResourceAsStream(originalFileName);) {
			var mmf = new MockMultipartFile("file", originalFileName, null, csvFileStream);
			mvc.perform(RestDocumentationRequestBuilders.multipart("/uploadFile").file(mmf)).andExpect(status().isOk());

			var allLeagueStats = teamStatsRepository.findAll();
			Assertions.assertNotNull(allLeagueStats);
			Assertions.assertEquals(2, allLeagueStats.size());

		} catch (Exception e) {
			Assertions.fail(e);
		}
	}
}
