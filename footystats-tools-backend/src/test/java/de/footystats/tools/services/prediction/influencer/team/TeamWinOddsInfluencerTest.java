package de.footystats.tools.services.prediction.influencer.team;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PrecheckResult;
import de.footystats.tools.services.prediction.influencer.BetPredictionContext;
import de.footystats.tools.services.stats.MatchStats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public class TeamWinOddsInfluencerTest {

	static Stream<Object[]> oddsTeamWins() {
		return Stream.of(
			new Object[]{1.0f, 1.0f, PrecheckResult.OK, 0, Bet.HOME_WIN, new HomeTeamWinOddsInfluencer()},
			new Object[]{2.0f, 1.0f, PrecheckResult.OK, 25, Bet.HOME_WIN, new HomeTeamWinOddsInfluencer()},
			new Object[]{3.0f, 1.0f, PrecheckResult.OK, 50, Bet.HOME_WIN, new HomeTeamWinOddsInfluencer()},
			new Object[]{4.0f, 1.0f, PrecheckResult.OK, 75, Bet.HOME_WIN, new HomeTeamWinOddsInfluencer()},
			new Object[]{5.0f, 1.0f, PrecheckResult.OK, 100, Bet.HOME_WIN, new HomeTeamWinOddsInfluencer()},
			new Object[]{5.1f, 1.0f, PrecheckResult.OK, 100, Bet.HOME_WIN, new HomeTeamWinOddsInfluencer()},
			new Object[]{1.0f, 1.0f, PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET, 0, Bet.AWAY_WIN, new HomeTeamWinOddsInfluencer()},
			new Object[]{1.0f, 2.0f, PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET, 0, Bet.AWAY_WIN, new HomeTeamWinOddsInfluencer()},
			new Object[]{1.0f, 3.0f, PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET, 0, Bet.AWAY_WIN, new HomeTeamWinOddsInfluencer()},
			new Object[]{1.0f, 4.0f, PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET, 0, Bet.AWAY_WIN, new HomeTeamWinOddsInfluencer()},
			new Object[]{1.0f, 5.0f, PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET, 0, Bet.AWAY_WIN, new HomeTeamWinOddsInfluencer()},
			new Object[]{1.0f, 5.1f, PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET, 0, Bet.AWAY_WIN, new HomeTeamWinOddsInfluencer()},
			//
			new Object[]{1.0f, 1.0f, PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET, 0, Bet.HOME_WIN, new AwayTeamWinOddsInfluencer()},
			new Object[]{2.0f, 1.0f, PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET, 0, Bet.HOME_WIN, new AwayTeamWinOddsInfluencer()},
			new Object[]{3.0f, 1.0f, PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET, 0, Bet.HOME_WIN, new AwayTeamWinOddsInfluencer()},
			new Object[]{4.0f, 1.0f, PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET, 0, Bet.HOME_WIN, new AwayTeamWinOddsInfluencer()},
			new Object[]{5.0f, 1.0f, PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET, 0, Bet.HOME_WIN, new AwayTeamWinOddsInfluencer()},
			new Object[]{5.1f, 1.0f, PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET, 0, Bet.HOME_WIN, new AwayTeamWinOddsInfluencer()},
			new Object[]{1.0f, 1.0f, PrecheckResult.OK, 0, Bet.AWAY_WIN, new AwayTeamWinOddsInfluencer()},
			new Object[]{1.0f, 2.0f, PrecheckResult.OK, 25, Bet.AWAY_WIN, new AwayTeamWinOddsInfluencer()},
			new Object[]{1.0f, 3.0f, PrecheckResult.OK, 50, Bet.AWAY_WIN, new AwayTeamWinOddsInfluencer()},
			new Object[]{1.0f, 4.0f, PrecheckResult.OK, 75, Bet.AWAY_WIN, new AwayTeamWinOddsInfluencer()},
			new Object[]{1.0f, 5.0f, PrecheckResult.OK, 100, Bet.AWAY_WIN, new AwayTeamWinOddsInfluencer()},
			new Object[]{1.0f, 5.1f, PrecheckResult.OK, 100, Bet.AWAY_WIN, new AwayTeamWinOddsInfluencer()}
		);
	}

	@ParameterizedTest
	@MethodSource("oddsTeamWins")
	public void zero_influencer(float oddsHomeWin, float oddsAwayWin, PrecheckResult expectedPrecheckResult, int expectedInfluence, Bet homeOrAwayWin, TeamWinOddsInfluencer influencer) {

		var matchStats = MatchStats.builder().dateGmt(LocalDateTime.now()).league("Bundesliga").awayTeam(
				"away team")
			.homeTeam("home team").oddsHomeWin(oddsHomeWin).oddsAwayWin(oddsAwayWin).oddsDraw(1.0f).build();
		var ctx = new BetPredictionContext(matchStats, null, null, null, homeOrAwayWin);

		Assertions.assertEquals(expectedPrecheckResult, influencer.preCheck(ctx));

		Integer influence = influencer.calculateInfluence(ctx);

		Assertions.assertEquals(expectedInfluence, influence);
	}
}
