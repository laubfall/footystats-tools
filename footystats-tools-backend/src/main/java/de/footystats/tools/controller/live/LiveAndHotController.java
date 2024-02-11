package de.footystats.tools.controller.live;

import de.footystats.tools.services.match.Match;
import de.footystats.tools.services.match.MatchSearch;
import de.footystats.tools.services.match.MatchService;
import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.outcome.InfluencerStatisticalResultOutcome;
import de.footystats.tools.services.prediction.outcome.Ranking;
import de.footystats.tools.services.prediction.outcome.StatisticalResultOutcome;
import de.footystats.tools.services.prediction.outcome.StatisticalResultOutcomeService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint that provides information about running matches with bets that have a high probability of success.
 */
@RestController
@RequestMapping("/match")
public class LiveAndHotController {

	private final MatchService matchService;

	private final StatisticalResultOutcomeService statisticalResultOutcomeService;

	public LiveAndHotController(MatchService matchService, StatisticalResultOutcomeService statisticalResultOutcomeService) {
		this.matchService = matchService;
		this.statisticalResultOutcomeService = statisticalResultOutcomeService;
	}

	@GetMapping(value = "/liveandhot")
	public List<LiveAndHotMatches> list() {
		Page<Match> matches = matchService.find(
			MatchSearch.builder().pageable(Pageable.unpaged()).start(LocalDateTime.now().minusMinutes(80)).end(LocalDateTime.now()).build());
		return filterTheBoringMatches(matches);
	}

	/**
	 * Iterate over all matches, compute the statistical result outcome for each match and filter out the boring matches. Boring matches are these
	 * where the statistical result outcome is not in the top 10%.
	 *
	 * @param searchResult the search result containing all matches.
	 * @return Filtered matches or empty list, never null.
	 */
	private List<LiveAndHotMatches> filterTheBoringMatches(Page<Match> searchResult) {
		List<Match> matches = searchResult.stream().toList();
		List<LiveAndHotMatches> result = new ArrayList<>();
		for (Match match : matches) {
			var hotBets = new HashSet<Bet>();
			Bet.activeBets().forEach(bet -> {
				var hotBet = hotBet(match, bet);
				if (hotBet != null) {
					hotBets.add(hotBet);
				}
			});
			if (hotBets.isEmpty()) {
				continue;
			}
			result.add(new LiveAndHotMatches(match.getDateGMT(), match.getHomeTeam(), match.getAwayTeam(), match.getCountry(), hotBets));
		}

		return result;
	}

	private Bet hotBet(Match match, Bet bet) {
		StatisticalResultOutcome outcome = statisticalResultOutcomeService.compute(match.forBet(bet), bet);
		if (outcome == null) {
			return null;
		}
		if (determineInterestingMatch(outcome)) {
			return bet;
		}

		return null;
	}

	private boolean determineInterestingMatch(StatisticalResultOutcome statisticalResultOutcome) {
		if (statisticalResultOutcome.ranking() == null) {
			return false;
		}

		long b10InfluencerCount = 0;
		if (statisticalResultOutcome.influencerStatisticalResultOutcomes() != null) {
			b10InfluencerCount = statisticalResultOutcome.influencerStatisticalResultOutcomes()
				.stream()
				.map(InfluencerStatisticalResultOutcome::ranking)
				.filter(Ranking::best10Percent)
				.count();
		}

		return statisticalResultOutcome.ranking().best10Percent() || b10InfluencerCount > 0;
	}
}
