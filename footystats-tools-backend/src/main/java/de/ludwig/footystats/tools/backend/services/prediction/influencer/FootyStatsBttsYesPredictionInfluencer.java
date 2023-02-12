package de.ludwig.footystats.tools.backend.services.prediction.influencer;

import de.ludwig.footystats.tools.backend.services.prediction.Bet;
import de.ludwig.footystats.tools.backend.services.prediction.PrecheckResult;

public class FootyStatsBttsYesPredictionInfluencer implements BetResultInfluencer {
    public PrecheckResult preCheck(BetPredictionContext ctx) {
        if (ctx.bet() != Bet.BTTS_YES) {
            return PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET;
        }

        if (ctx.match().getBTTSAverage() == null) {
            return PrecheckResult.NOT_ENOUGH_INFORMATION;
        }

        return PrecheckResult.OK;
    }

    // eslint-disable-next-line class-methods-use-this
    public Integer calculateInfluence(BetPredictionContext ctx) {
        return ctx.match().getBTTSAverage();
    }

    // eslint-disable-next-line class-methods-use-this
    public String influencerName() {
        return "FootyStatsBttsYesPredictionInfluencer";
    }
}
