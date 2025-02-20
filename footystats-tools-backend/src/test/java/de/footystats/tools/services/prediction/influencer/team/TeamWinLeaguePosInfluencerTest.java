package de.footystats.tools.services.prediction.influencer.team;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PrecheckResult;
import de.footystats.tools.services.prediction.influencer.BetPredictionContext;
import de.footystats.tools.services.stats.LeagueStats;
import de.footystats.tools.services.stats.MatchStats;
import de.footystats.tools.services.stats.TeamStats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public class TeamWinLeaguePosInfluencerTest {
	public static Stream<Arguments> data() {
		return Stream.of(
			Arguments.of()
		);
	}

	@Disabled
	@ParameterizedTest
	@MethodSource("data")
	public void expected_precheck_and_result(int homeTeamLeaguePosition, int awayTeamLeaguePosition, int numberOfClubs, Bet bet, TeamWinLeaguePosInfluencer influencer, int expectedInfluence, PrecheckResult expectedPrecheckResult) {
		MatchStats matchStats = MatchStats.builder().dateGmt(LocalDateTime.now()).league(
				"Bundesliga").awayTeam("away team")
			.homeTeam("home team").build();
		TeamStats teamStats = new TeamStats();
		teamStats.setLeaguePositionHome(homeTeamLeaguePosition);
		teamStats.setLeaguePositionAway(awayTeamLeaguePosition);
		LeagueStats leagueStats = LeagueStats.builder().numberOfClubs(numberOfClubs).build();
		var ctx = new BetPredictionContext(matchStats, teamStats, teamStats, leagueStats, bet);
		Assertions.assertEquals(expectedPrecheckResult, influencer.betRelatedPreCheck(ctx));
		Assertions.assertEquals(expectedInfluence, influencer.calculateInfluence(ctx));
	}
}
