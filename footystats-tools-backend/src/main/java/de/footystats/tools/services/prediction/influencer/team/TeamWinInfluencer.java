package de.footystats.tools.services.prediction.influencer.team;

import de.footystats.tools.services.prediction.PrecheckResult;
import de.footystats.tools.services.prediction.influencer.BetPredictionContext;
import de.footystats.tools.services.prediction.influencer.BetResultInfluencer;
import lombok.Getter;

/**
 * Influencer for a team win. It has a focus on team bet Home or Away win.
 * This class can be used for any influencer that targets a team win (home or away).
 */
@Getter
abstract class TeamWinInfluencer implements BetResultInfluencer {

	@Override
	public final PrecheckResult preCheck(BetPredictionContext ctx) {
		return betRelatedPreCheck(ctx);
	}

	abstract PrecheckResult betRelatedPreCheck(BetPredictionContext ctx);
}
