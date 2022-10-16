package de.ludwig.footystats.tools.backend.services.match;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ludwig.footystats.tools.backend.services.MongoService;
import de.ludwig.footystats.tools.backend.services.prediction.Bet;
import de.ludwig.footystats.tools.backend.services.prediction.PredictionResult;
import de.ludwig.footystats.tools.backend.services.prediction.PredictionService;
import de.ludwig.footystats.tools.backend.services.prediction.influencer.BetPredictionContext;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityRevision;
import de.ludwig.footystats.tools.backend.services.stats.MatchStats;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class MatchService extends MongoService<Match> {

	private MatchRepository matchRepository;

	private ObjectMapper objectMapper;

	private PredictionService predictionService;

	public MatchService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter, MatchRepository matchRepository, ObjectMapper objectMapper, PredictionService predictionService) {
		super(mongoTemplate, mappingMongoConverter);
		this.matchRepository = matchRepository;
		this.objectMapper = objectMapper;
		this.predictionService = predictionService;
	}

	public void writeMatch(MatchStats matchStats) {
		var match = Match.builder()
			.country(matchStats.getCountry())
			.league(matchStats.getLeague())
			.dateUnix(matchStats.getDateUnix())
			.dateGMT(matchStats.getDateGmt())
			.footyStatsUrl(matchStats.getMatchFootyStatsURL())
			.state(matchStats.getMatchStatus())
			.revision(PredictionQualityRevision.NO_REVISION)
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
		return Query.query(Criteria.where("country").is(example.getCountry()).and("league").is(example.getLeague()).and("dateUnix").is(example.getDateUnix()));
	}

	@Override
	public Class<Match> upsertType() {
		return Match.class;
	}

	private PredictionResult calculatePrediction(
		Bet bet,
		MatchStats ms
	) {
        /*
		const teamStats = await this.teamStatsService.latestThree(
                ms['Home Team'],
                ms.Country,
                getYear(new Date())
        );

        teamStats.push(
			...(await this.teamStatsService.latestThree(
                ms['Away Team'],
                ms.Country,
                getYear(new Date())
        ))
		);
		*/

		var predictionNumber = predictionService.prediction(new BetPredictionContext(ms, null, null, bet));
		return predictionNumber;
	}
}
