package de.footystats.tools.services.prediction.influencer;

import de.footystats.tools.services.domain.DomainDataService;
import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PrecheckResult;
import de.footystats.tools.services.stats.LeagueStats;
import de.footystats.tools.services.stats.MatchStats;
import de.footystats.tools.services.stats.TeamStats;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {DomainDataService.class})
class LeaguePositionInfluencerTest {

	@Autowired
	private DomainDataService domainDataService;

	static Object[][] leaguePositionInfluencerSource() {
		return new Object[][]{
			{9, 9, 18, Bet.OVER_ZERO_FIVE, new HomeTeamLeaguePosInfluencer(), 53},
			{1, 1, 18, Bet.OVER_ZERO_FIVE, new HomeTeamLeaguePosInfluencer(), 100},
			{18, 18, 18, Bet.OVER_ZERO_FIVE, new HomeTeamLeaguePosInfluencer(), 0},
			{9, 9, 18, Bet.OVER_ZERO_FIVE, new AwayTeamLeaguePosInfluencer(), 53},
			{1, 1, 18, Bet.OVER_ZERO_FIVE, new AwayTeamLeaguePosInfluencer(), 100},
			{18, 18, 18, Bet.OVER_ZERO_FIVE, new AwayTeamLeaguePosInfluencer(), 0},
			{1, 18, 18, Bet.OVER_ZERO_FIVE, new AwayTeamLeaguePosInfluencer(), 0},
			{1, 2, 0, Bet.OVER_ZERO_FIVE, new AwayTeamLeaguePosInfluencer(), 0},
		};
	}

	static Object[][] leaguePositionInfluencerPrecheckSource() {
		return new Object[][]{
			{10, Bet.OVER_ZERO_FIVE, new HomeTeamLeaguePosInfluencer(), teamStatsBuilder(2, 5, 10), null, PrecheckResult.OK},
			{10, Bet.OVER_ZERO_FIVE, new HomeTeamLeaguePosInfluencer(), teamStatsBuilder(0, 0, 10), null, PrecheckResult.NOT_ENOUGH_INFORMATION},
			{10, Bet.OVER_ZERO_FIVE, new HomeTeamLeaguePosInfluencer(), null, teamStatsBuilder(2, 5, 10), PrecheckResult.NOT_ENOUGH_INFORMATION},
			{5, Bet.OVER_ZERO_FIVE, new HomeTeamLeaguePosInfluencer(), teamStatsBuilder(2, 5, 4), null, PrecheckResult.NOT_ENOUGH_INFORMATION},
			{5, Bet.BTTS_YES, new HomeTeamLeaguePosInfluencer(), teamStatsBuilder(2, 5, 10), null,
				PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET},
			{5, Bet.OVER_ZERO_FIVE, new HomeTeamLeaguePosInfluencer(), null, null, PrecheckResult.NOT_ENOUGH_INFORMATION},
			{10, Bet.OVER_ZERO_FIVE, new AwayTeamLeaguePosInfluencer(), null, teamStatsBuilder(2, 5, 10), PrecheckResult.OK},
			{10, Bet.OVER_ZERO_FIVE, new AwayTeamLeaguePosInfluencer(), teamStatsBuilder(2, 5, 10), null, PrecheckResult.NOT_ENOUGH_INFORMATION},
			{5, Bet.OVER_ZERO_FIVE, new AwayTeamLeaguePosInfluencer(), teamStatsBuilder(2, 5, 4), null, PrecheckResult.NOT_ENOUGH_INFORMATION},
			{5, Bet.BTTS_YES, new AwayTeamLeaguePosInfluencer(), teamStatsBuilder(2, 5, 10), null,
				PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET},
			{5, Bet.OVER_ZERO_FIVE, new AwayTeamLeaguePosInfluencer(), null, null, PrecheckResult.NOT_ENOUGH_INFORMATION}
		};
	}

	private static TeamStats teamStatsBuilder(Integer leaguePositionHome, Integer leaguePositionAway, Integer matchesPlayed) {
		var ts = new TeamStats();
		ts.setLeaguePositionHome(leaguePositionHome);
		ts.setLeaguePositionAway(leaguePositionAway);
		ts.setMatchesPlayed(matchesPlayed);
		return ts;
	}

	@ParameterizedTest
	@MethodSource("leaguePositionInfluencerSource")
	void leaguePositionInfluencerCalculate(int homeTeamLeaguePosition, int awayTeamLeaguePosition, int numberOfClubs, Bet bet,
		LeaguePositionInfluencer influencerUnderTest,
		int expectedPredictionPercent) {
		var germany = domainDataService.countryByNormalizedName("Germany");
		MatchStats matchStats = MatchStats.builder().dateGmt(LocalDateTime.now()).country(germany).league("Bundesliga").awayTeam("away team")
			.homeTeam("home team").build();
		TeamStats teamStats = new TeamStats();
		teamStats.setLeaguePositionHome(homeTeamLeaguePosition);
		teamStats.setLeaguePositionAway(awayTeamLeaguePosition);
		LeagueStats leagueStats = LeagueStats.builder().numberOfClubs(numberOfClubs).build();
		var ctx = new BetPredictionContext(matchStats, teamStats, teamStats, leagueStats, bet);
		var predictionPercent = influencerUnderTest.calculateInfluence(ctx);
		Assertions.assertEquals(expectedPredictionPercent, predictionPercent);
	}

	@ParameterizedTest
	@MethodSource("leaguePositionInfluencerPrecheckSource")
	void leaguePositionInfluencerPrecheck(int numberOfClubs, Bet bet, LeaguePositionInfluencer influencerUnderTest,
		TeamStats homeTeamStats, TeamStats awayTeamStats,
		PrecheckResult expectedPrecheckResult) {
		// Assertions for LeaguePositionInfluencer.preCheck
		var germany = domainDataService.countryByNormalizedName("Germany");
		MatchStats matchStats = MatchStats.builder().dateGmt(LocalDateTime.now()).country(germany).league("Bundesliga").awayTeam("away team")
			.homeTeam("home team").build();
		LeagueStats leagueStats = LeagueStats.builder().numberOfClubs(numberOfClubs).build();
		var ctx = new BetPredictionContext(matchStats, homeTeamStats, awayTeamStats, leagueStats, bet);
		PrecheckResult precheckResult = influencerUnderTest.preCheck(ctx);
		Assertions.assertEquals(expectedPrecheckResult, precheckResult);
	}
}
