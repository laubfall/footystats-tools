package de.footystats.tools.services.prediction.influencer.team;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.influencer.BetPredictionContext;
import de.footystats.tools.services.stats.MatchStats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public class HomeTeamWinOddsInfluencerTest {
	private final HomeTeamWinOddsInfluencer influencer = new HomeTeamWinOddsInfluencer();

	static Stream<Object[]> oddsTeamWins() {
		return Stream.of(
			new Object[]{1.0f, 1.0f, 0, Bet.HOME_WIN},
			new Object[]{2.0f, 1.0f, 25, Bet.HOME_WIN},
			new Object[]{3.0f, 1.0f, 50, Bet.HOME_WIN},
			new Object[]{4.0f, 1.0f, 75, Bet.HOME_WIN},
			new Object[]{5.0f, 1.0f, 100, Bet.HOME_WIN},
			new Object[]{5.1f, 1.0f, 100, Bet.HOME_WIN},
			new Object[]{1.0f, 1.0f, 0, Bet.AWAY_WIN},
			new Object[]{1.0f, 2.0f, 25, Bet.AWAY_WIN},
			new Object[]{1.0f, 3.0f, 50, Bet.AWAY_WIN},
			new Object[]{1.0f, 4.0f, 75, Bet.AWAY_WIN},
			new Object[]{1.0f, 5.0f, 100, Bet.AWAY_WIN},
			new Object[]{1.0f, 5.1f, 100, Bet.AWAY_WIN}
		);
	}

	@ParameterizedTest
	@MethodSource("oddsTeamWins")
	public void zero_influencer(float oddsHomeWin, float oddsAwayWin, int expectedInfluence, Bet homeOrAwayWin) {
		MatchStats matchStats = MatchStats.builder().dateGmt(LocalDateTime.now()).league("Bundesliga").awayTeam(
				"away team")
			.homeTeam("home team").oddsHomeWin(oddsHomeWin).oddsAwayWin(oddsAwayWin).build();
		var ctx = new BetPredictionContext(matchStats, null, null, null, homeOrAwayWin);
		Integer influence = influencer.calculateInfluence(ctx);

		Assertions.assertEquals(expectedInfluence, influence);
	}
}
