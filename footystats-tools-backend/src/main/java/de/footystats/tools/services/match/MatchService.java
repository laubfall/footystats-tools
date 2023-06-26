package de.footystats.tools.services.match;

import de.footystats.tools.services.prediction.influencer.BetPredictionContext;
import de.footystats.tools.services.stats.MatchStats;
import de.footystats.tools.services.MongoService;
import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PredictionResult;
import de.footystats.tools.services.prediction.PredictionService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class MatchService extends MongoService<Match> {

	private final PredictionService predictionService;

	public MatchService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter, PredictionService predictionService) {
		super(mongoTemplate, mappingMongoConverter);

		this.predictionService = predictionService;
	}

	public Page<Match> find(final MatchSearch search) {
		List<Criteria> searchCriterias = new ArrayList<>();
		if (CollectionUtils.isEmpty(search.getCountries()) == false) {
			searchCriterias.add(Criteria.where("country").in(search.getCountries()));
		}

		if (CollectionUtils.isEmpty(search.getLeagues()) == false) {
			searchCriterias.add(Criteria.where("league").in(search.getLeagues()));
		}

		if (search.getStart() != null) {
			searchCriterias.add(Criteria.where("dateGMT").gte(search.getStart()));
		}

		if (search.getEnd() != null) {
			searchCriterias.add(Criteria.where("dateGMT").lte(search.getEnd()));
		}

		var c = new Criteria();
		if (searchCriterias.isEmpty() == false) {
			c = c.andOperator(searchCriterias);
		}
		Query countQuery = query(c);
		var matchCount = mongoTemplate.count(countQuery, Match.class);

		Query matchSearchQuery = query(c).with(search.getPageable());

		var result = mongoTemplate.find(matchSearchQuery, Match.class);
		return new PageImpl<>(result, search.getPageable(), matchCount);
	}

	public void writeMatch(MatchStats matchStats) {
		var match = Match.builder()
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
			.build();

		upsert(match);
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
		/*
		 * const teamStats = await this.teamStatsService.latestThree(
		 * ms['Home Team'],
		 * ms.Country,
		 * getYear(new Date())
		 * );
		 *
		 * teamStats.push(
		 * ...(await this.teamStatsService.latestThree(
		 * ms['Away Team'],
		 * ms.Country,
		 * getYear(new Date())
		 * ))
		 * );
		 */

		var predictionNumber = predictionService.prediction(new BetPredictionContext(ms, null, null, bet));
		return predictionNumber;
	}
}
