package de.footystats.tools.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.footystats.tools.services.domain.DomainDataService;
import de.footystats.tools.services.footy.dls.DownloadConfigRepository;
import de.footystats.tools.services.stats.LeagueMatchStats;
import de.footystats.tools.services.stats.LeagueMatchStatsRepository;
import de.footystats.tools.services.stats.LeagueStatsRepository;
import de.footystats.tools.services.stats.MatchStats;
import de.footystats.tools.services.stats.MatchStatsRepository;
import de.footystats.tools.services.stats.Team2StatsRepository;
import de.footystats.tools.services.stats.TeamStatsRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

class FootyStatsCsvUploadControllerTest extends BaseControllerTest {

	@Autowired
	private MatchStatsRepository matchStatsRepository;

	@Autowired
	private LeagueStatsRepository leagueStatsRepository;

	@Autowired
	private TeamStatsRepository teamStatsRepository;

	@Autowired
	private Team2StatsRepository team2StatsRepository;

	@Autowired
	private LeagueMatchStatsRepository leagueMatchStatsRepository;

	@Autowired
	private DownloadConfigRepository downloadConfigRepository;

	@Autowired
	private DomainDataService domainDataService;

	@Autowired
	private MockMvc mvc;

	@BeforeEach
	public void cleanup() {
		matchStatsRepository.deleteAll();
		leagueStatsRepository.deleteAll();
		teamStatsRepository.deleteAll();
		downloadConfigRepository.deleteAll();
		leagueMatchStatsRepository.deleteAll();
	}

	@Test
	void uploadLeagueMatchStats() {
		var originalFileName = "some-country-league-matches-2020-to-2021-stats.csv";
		try (var csvFileStream = getClass().getResourceAsStream(originalFileName);) {
			var mmf = new MockMultipartFile("file", originalFileName, null, csvFileStream);
			mvc.perform(RestDocumentationRequestBuilders.multipart("/uploadFile").file(mmf)).andExpect(status().isOk());

			List<LeagueMatchStats> matchStatsImported = leagueMatchStatsRepository.findLeagueMatchStatsByHomeTeamNameAndAwayTeamName("St. Jakob",
				"Wolfsberg");
			Assertions.assertFalse(matchStatsImported.isEmpty());
		} catch (Exception e) {
			Assertions.fail(e);
		}
	}

	@Test
	void uploadDownloadConfig() {
		var originalFileName = "download_config-footy-stats-152153323.csv";
		try (var csvFileStream = getClass().getResourceAsStream(originalFileName);) {
			var mmf = new MockMultipartFile("file", originalFileName, null, csvFileStream);
			mvc.perform(RestDocumentationRequestBuilders.multipart("/uploadFile").file(mmf)).andExpect(status().isOk());

			var config = downloadConfigRepository.findAllBySeasonEndsWithAndDownloadBitmaskGreaterThan("2023", 0);
			Assertions.assertNotNull(config);
			Assertions.assertEquals(2, config.size());
			Assertions.assertEquals(2, config.stream().filter(dlconfig -> dlconfig.getCountry() != null).count());
			Assertions.assertEquals(2,
				config.stream().filter(dlconfig -> "africa".equals(dlconfig.getCountry().getCountryNameByFootystats())).count());
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
			var germany = domainDataService.countryByNormalizedName("Germany");
			List<MatchStats> matchStatsImported = matchStatsRepository.findByCountry(germany);
			Assertions.assertFalse(matchStatsImported.isEmpty());
		} catch (Exception e) {
			Assertions.fail(e);
		}
	}

	@Test
	void uploadLeagueStats() {
		var originalFileName = "germany-division-league-2020-to-2021-stats.csv";
		try (var csvFileStream = getClass().getResourceAsStream(originalFileName);) {
			var mmf = new MockMultipartFile("file", originalFileName, null, csvFileStream);
			mvc.perform(RestDocumentationRequestBuilders.multipart("/uploadFile").file(mmf)).andExpect(status().isOk());

			var allLeagueStats = leagueStatsRepository.findAll();
			Assertions.assertNotNull(allLeagueStats);
			Assertions.assertEquals(1, allLeagueStats.size());
			Assertions.assertEquals("germany", allLeagueStats.get(0).getCountry().getCountryNameByFootystats());
		} catch (Exception e) {
			Assertions.fail(e);
		}
	}

	@Test
	void uploadTeamStats() {
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

	@Test
	void uploadTeam2Stats() {
		var originalFileName = "some-country-teams2-2020-to-2021-stats.csv";
		try (var csvFileStream = getClass().getResourceAsStream(originalFileName);) {
			var mmf = new MockMultipartFile("file", originalFileName, null, csvFileStream);
			mvc.perform(RestDocumentationRequestBuilders.multipart("/uploadFile").file(mmf)).andExpect(status().isOk());

			var allLeagueStats = team2StatsRepository.findAll();
			Assertions.assertNotNull(allLeagueStats);
			Assertions.assertEquals(1, allLeagueStats.size());

		} catch (Exception e) {
			Assertions.fail(e);
		}
	}
}
