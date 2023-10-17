package de.footystats.tools.services.match;

import static org.springframework.data.mongodb.core.query.Query.query;

import de.footystats.tools.services.MongoService;
import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PredictionResult;
import de.footystats.tools.services.prediction.PredictionService;
import de.footystats.tools.services.prediction.influencer.BetPredictionContext;
import de.footystats.tools.services.stats.LeagueStats;
import de.footystats.tools.services.stats.LeagueStatsService;
import de.footystats.tools.services.stats.MatchStats;
import de.footystats.tools.services.stats.TeamStats;
import de.footystats.tools.services.stats.TeamStatsService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MatchService extends MongoService<Match> {

	private final PredictionService predictionService;

	private final CachedConfiguredStatsService cachedConfiguredStatsService;

	private final LeagueStatsService leagueStatsService;

	private final TeamStatsService teamStatsService;

	public MatchService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter, PredictionService predictionService,
		CachedConfiguredStatsService cachedConfiguredStatsService, LeagueStatsService leagueStatsService, TeamStatsService teamStatsService) {
		super(mongoTemplate, mappingMongoConverter);

		this.predictionService = predictionService;
		this.cachedConfiguredStatsService = cachedConfiguredStatsService;
		this.leagueStatsService = leagueStatsService;
		this.teamStatsService = teamStatsService;
	}

	public Page<Match> find(final MatchSearch search) {
		List<Criteria> searchCriterias = new ArrayList<>();
		if (!CollectionUtils.isEmpty(search.getCountries())) {
			searchCriterias.add(Criteria.where("country").in(search.getCountries()));
		}

		if (!CollectionUtils.isEmpty(search.getLeagues())) {
			searchCriterias.add(Criteria.where("league").in(search.getLeagues()));
		}

		if (search.getStart() != null) {
			searchCriterias.add(Criteria.where("dateGMT").gte(search.getStart()));
		}

		if (search.getEnd() != null) {
			searchCriterias.add(Criteria.where("dateGMT").lte(search.getEnd()));
		}

		var c = new Criteria();
		if (!searchCriterias.isEmpty()) {
			c = c.andOperator(searchCriterias);
		}

		Query countQuery = query(c);
		applyFullTextCriteria(search, countQuery);
		var matchCount = mongoTemplate.count(countQuery, Match.class);

		Query matchSearchQuery = query(c).with(search.getPageable());
		applyFullTextCriteria(search, matchSearchQuery);
		var result = mongoTemplate.find(matchSearchQuery, Match.class);
		return new PageImpl<>(result, search.getPageable(), matchCount);
	}

	public void writeMatch(MatchStats matchStats) {
		Match match = convert(matchStats);
		upsert(match);
	}

	public Match convert(MatchStats matchStats) {
		return Match.builder()
			.country(matchStats.getCountry())
			.league(matchStats.getLeague())
			.dateUnix(matchStats.getDateUnix())
			.dateGMT(matchStats.getDateGmt())
			.footyStatsUrl(matchStats.getMatchFootyStatsURL())
			.state(matchStats.getMatchStatus())
			.awayTeam(matchStats.getAwayTeam())
			.homeTeam(matchStats.getHomeTeam())
			.goalsAwayTeam(matchStats.getResultAwayTeamGoals())
			.goalsHomeTeam(matchStats.getResultHomeTeamGoals())
			.bttsYes(calculatePrediction(Bet.BTTS_YES, matchStats))
			.o05(calculatePrediction(Bet.OVER_ZERO_FIVE, matchStats))
			.o15(calculatePrediction(Bet.OVER_ONE_FIVE, matchStats))
			.build();
	}

	@Override
	public Query upsertQuery(Match example) {
		return query(Criteria.where("country").is(example.getCountry()).and("league").is(example.getLeague())
			.and("dateUnix").is(example.getDateUnix()).and("awayTeam").is(example.getAwayTeam()).and("homeTeam").is(example.getHomeTeam()));
	}

	@Override
	public Class<Match> upsertType() {
		return Match.class;
	}

	private PredictionResult calculatePrediction(
		Bet bet,
		MatchStats ms) {

		// Update stats like league-, team-stats etc. if necessary we download the latest stats from footystats.org.
		cachedConfiguredStatsService.updateConfiguredStats(ms.getCountry(), ms.getLeague());

		// Load Team and League stats and add them to the context (if they exist).
		final LeagueStats aggregatedLeagueStats = leagueStatsService.aggregate(ms.getLeague(), ms.getCountry(), ms.getDateGmt().getYear());
		final TeamStats homeTeam = teamStatsService.aggregate(ms.getHomeTeam(), ms.getCountry(), ms.getDateGmt().getYear());
		final TeamStats awayTeam = teamStatsService.aggregate(ms.getAwayTeam(), ms.getCountry(), ms.getDateGmt().getYear());
		return predictionService.prediction(new BetPredictionContext(ms, homeTeam, awayTeam, aggregatedLeagueStats, bet));
	}

	private void applyFullTextCriteria(MatchSearch search, Query query) {
		if (!CollectionUtils.isEmpty(search.getFullTextSearchTerms())) {

			List<Criteria> searchCritierias = new ArrayList<>();
			for (String term : search.getFullTextSearchTerms()) {
				searchCritierias.add(Criteria.where("homeTeam").regex(".*(?i)" + term + ".*"));
				searchCritierias.add(Criteria.where("awayTeam").regex(".*(?i)" + term + ".*"));
			}
			query.addCriteria(Criteria.where("").orOperator(searchCritierias.toArray(new Criteria[0])));
		}
	}
}
