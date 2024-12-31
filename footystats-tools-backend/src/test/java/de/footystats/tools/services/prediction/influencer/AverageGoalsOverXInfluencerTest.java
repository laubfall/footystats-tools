package de.footystats.tools.services.prediction.influencer;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PrecheckResult;
import de.footystats.tools.services.stats.LeagueStats;
import de.footystats.tools.services.stats.MatchStats;
import de.footystats.tools.services.stats.TeamStats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

class AverageGoalsOverXInfluencerTest {

	static Stream<Object[]> averageGoalsOverXInfluencerSource() {
		return Stream.of(
			new Object[]{1.0f, Bet.OVER_ZERO_FIVE, 16},
			new Object[]{1.5f, Bet.OVER_ZERO_FIVE, 35},
			new Object[]{0.5f, Bet.OVER_ZERO_FIVE, 0},
			new Object[]{2.0f, Bet.OVER_ONE_FIVE, 33},
			new Object[]{2.5f, Bet.OVER_ONE_FIVE, 51},
			new Object[]{3.0f, Bet.OVER_TWO_FIVE, 50},
			new Object[]{3.5f, Bet.OVER_TWO_FIVE, 68},
			new Object[]{6.5f, Bet.OVER_TWO_FIVE, 100}
		);
	}

	static Stream<Object[]> averageGoalsOverXInfluencerPrecheckSource() {
		return Stream.of(
			new Object[]{Bet.OVER_ZERO_FIVE, null, PrecheckResult.NOT_ENOUGH_INFORMATION},
			new Object[]{Bet.OVER_ZERO_FIVE, 1.0f, PrecheckResult.OK},
			new Object[]{Bet.OVER_ONE_FIVE, 2.0f, PrecheckResult.OK},
			new Object[]{Bet.OVER_TWO_FIVE, 3.0f, PrecheckResult.OK},
			new Object[]{Bet.BTTS_YES, 1.0f, PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET}
		);
	}

	@ParameterizedTest
	@MethodSource("averageGoalsOverXInfluencerSource")
	void averageGoalsOverXInfluencerCalculate(Float averageGoals, Bet bet, int expectedPredictionPercent) {
		MatchStats matchStats = MatchStats.builder().dateGmt(LocalDateTime.now()).league("Bundesliga").awayTeam("away team")
			.homeTeam("home team").averageGoals(averageGoals).build();
		TeamStats teamStats = new TeamStats();
		LeagueStats leagueStats = LeagueStats.builder().numberOfClubs(18).build();
		var ctx = new BetPredictionContext(matchStats, teamStats, teamStats, leagueStats, bet);
		AverageGoalsOverXInfluencer influencer = new AverageGoalsOverXInfluencer();
		var predictionPercent = influencer.calculateInfluence(ctx);
		Assertions.assertEquals(expectedPredictionPercent, predictionPercent);
	}

	@ParameterizedTest
	@MethodSource("averageGoalsOverXInfluencerPrecheckSource")
	void averageGoalsOverXInfluencerPrecheck(Bet bet, Float averageGoals, PrecheckResult expectedPrecheckResult) {
		MatchStats matchStats = MatchStats.builder().dateGmt(LocalDateTime.now()).league("Bundesliga").awayTeam("away team")
			.homeTeam("home team").averageGoals(averageGoals).build();
		TeamStats teamStats = new TeamStats();
		LeagueStats leagueStats = LeagueStats.builder().numberOfClubs(18).build();
		var ctx = new BetPredictionContext(matchStats, teamStats, teamStats, leagueStats, bet);
		AverageGoalsOverXInfluencer influencer = new AverageGoalsOverXInfluencer();
		PrecheckResult precheckResult = influencer.preCheck(ctx);
		Assertions.assertEquals(expectedPrecheckResult, precheckResult);
	}
}
