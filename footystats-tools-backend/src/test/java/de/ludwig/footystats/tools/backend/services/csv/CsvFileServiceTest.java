package de.ludwig.footystats.tools.backend.services.csv;

import de.ludwig.footystats.tools.backend.services.stats.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

@SpringBootTest
@ContextConfiguration(classes = {Configuration.class})
@ActiveProfiles("test")
public class CsvFileServiceTest {

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

	@Test
	public void importTeamStats(){
		try(var inputStream = getClass().getResourceAsStream("some-country-teams-2020-to-2021-stats.csv");) {
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
	public void importTeam2Stats(){
		try(var inputStream = getClass().getResourceAsStream("some-country-teams2-stats.csv");) {
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
	public void playerStats(){
		try(var inputStream = getClass().getResourceAsStream("some-player-stats.csv");) {
			var entries = playerStatsCsvFileService.importFile(inputStream, PlayerStats.class);
			Assertions.assertNotNull(entries);
			Assertions.assertEquals(1, entries.size());

			var playerStats = entries.get(0);
			Assertions.assertEquals("Some Club", playerStats.getCurrent_Club());
			Assertions.assertEquals("2020/2021", playerStats.getSeason());
		} catch (IOException e) {
			Assertions.fail();
		}
	}
}
