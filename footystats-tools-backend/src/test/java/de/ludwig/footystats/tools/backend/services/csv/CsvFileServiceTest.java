package de.ludwig.footystats.tools.backend.services.csv;

import de.ludwig.footystats.tools.backend.services.stats.LeagueMatchStats;
import de.ludwig.footystats.tools.backend.services.stats.LeagueStats;
import de.ludwig.footystats.tools.backend.services.stats.MatchStats;
import de.ludwig.footystats.tools.backend.services.stats.PlayerStats;
import de.ludwig.footystats.tools.backend.services.stats.Team2Stats;
import de.ludwig.footystats.tools.backend.services.stats.TeamStats;
import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {Configuration.class})
class CsvFileServiceTest {

	@Autowired
	private CsvFileService<MatchStats> csvFileService;

	@Autowired
	private CsvFileService<LeagueStats> leagueStatsCsvFileService;

	@Autowired
	private CsvFileService<TeamStats> teamStatsCsvFileService;

	@Autowired
	private CsvFileService<Team2Stats> team2StatsCsvFileService;

	@Autowired
	private CsvFileService<PlayerStats> playerStatsCsvFileService;

	@Test
	void importMatchStats() {
		try (var inputStream = getClass().getResourceAsStream("matches_expanded-1630235153-expectRenamed.csv");) {
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
	void importLeagueStats() {
		try (var inputStream = getClass().getResourceAsStream("some-country-league-2020-to-2021-stats.csv");) {
			var entries = leagueStatsCsvFileService.importFile(inputStream, LeagueStats.class);
			Assertions.assertNotNull(entries);
			Assertions.assertEquals(1, entries.size());

			var leagueStats = entries.get(0);
			Assertions.assertEquals("Someleague", leagueStats.getName());
		} catch (IOException e) {
			Assertions.fail();
		}
	}

	@Test
	void importTeamStats() {
		try (var inputStream = getClass().getResourceAsStream("some-country-teams-2020-to-2021-stats.csv");) {
			var entries = teamStatsCsvFileService.importFile(inputStream, TeamStats.class);
			Assertions.assertNotNull(entries);
			Assertions.assertEquals(2, entries.size());

			var teamStats = entries.get(0);
			Assertions.assertEquals("Somecountry", teamStats.getCountry());
			Assertions.assertEquals("2020/2021", teamStats.getSeason());
			Assertions.assertEquals(30, teamStats.getMatchesPlayed());
		} catch (IOException e) {
			Assertions.fail();
		}
	}

	@Test
	void importTeam2Stats() {
		try (var inputStream = getClass().getResourceAsStream("some-country-teams2-stats.csv");) {
			var entries = team2StatsCsvFileService.importFile(inputStream, Team2Stats.class);
			Assertions.assertNotNull(entries);
			Assertions.assertEquals(1, entries.size());

			var team2Stats = entries.get(0);
			Assertions.assertEquals("Nania", team2Stats.getCountry());
			Assertions.assertEquals("2020/2021", team2Stats.getSeason());
			Assertions.assertEquals(56, team2Stats.getMinutesPerGoalScoredOverall());
		} catch (IOException e) {
			Assertions.fail();
		}
	}

	@Test
	void playerStats() {
		try (var inputStream = getClass().getResourceAsStream("some-player-stats.csv");) {
			var entries = playerStatsCsvFileService.importFile(inputStream, PlayerStats.class);
			Assertions.assertNotNull(entries);
			Assertions.assertEquals(1, entries.size());

			var playerStats = entries.get(0);
			Assertions.assertEquals("Some Club", playerStats.getCurrentClub());
			Assertions.assertEquals("2020/2021", playerStats.getSeason());
			Assertions.assertNull(playerStats.getPassesTotalOverall());
		} catch (IOException e) {
			Assertions.fail();
		}
	}

	@ParameterizedTest
	@MethodSource("determineCsvTypeParams")
	void determineCsvType(String filename, CsvFileType expectedType) {
		ICsvFileInformation csvFileInformation = CsvFileService.csvFileInformationByFileName(filename);
		Assertions.assertNotNull(csvFileInformation);
		Assertions.assertEquals(expectedType, csvFileInformation.type());
	}

	private static Stream<Arguments> determineCsvTypeParams(){
		return Stream.of(
			Arguments.of("austria-landesliga-matches-2020-to-2021-stats.csv", CsvFileType.LEAGUE_MATCH_STATS),
			Arguments.of("austrial-landesliga-teams-2020-to-2021-stats.csv", CsvFileType.TEAM_STATS),
			Arguments.of("austrial-landesliga-teams2-2020-to-2021-stats.csv", CsvFileType.TEAM_2_STATS),
			Arguments.of("austrial-landesliga-league-2020-to-2021-stats.csv", CsvFileType.LEAGUE_STATS),
			Arguments.of("austrial-landesliga-players-2020-to-2021-stats.csv", CsvFileType.PLAYER_STATS),
			Arguments.of("australia-northern-nsw-npl-matches-2020-to-2021-stats.csv", CsvFileType.LEAGUE_MATCH_STATS),
			Arguments.of("australia-northern-nsw-npl-teams-2020-to-2021-stats.csv", CsvFileType.TEAM_STATS),
			Arguments.of("australia-northern-nsw-npl-teams2-2020-to-2021-stats.csv", CsvFileType.TEAM_2_STATS),
			Arguments.of("australia-northern-nsw-npl-league-2020-to-2021-stats.csv", CsvFileType.LEAGUE_STATS),
			Arguments.of("australia-northern-nsw-npl-players-2020-to-2021-stats.csv", CsvFileType.PLAYER_STATS)
		);
	}
}
