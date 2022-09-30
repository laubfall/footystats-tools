package de.ludwig.footystats.tools.backend.services.prediction.influencer;

import de.ludwig.footystats.tools.backend.services.prediction.PrecheckResult;

public class FootyStatsOverFTPredictionInfluencer implements BetResultInfluencer{
    public PrecheckResult preCheck(BetPredictionContext ctx) {
        switch (ctx.bet()) {
            case OVER_ZERO_FIVE:
            case OVER_ONE_FIVE:
                break;
            default:
                return PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET;
        }
        if (ctx.match().getOver05Average() == null) {
            return PrecheckResult.NOT_ENOUGH_INFORMATION;
        }

        return PrecheckResult.OK;
    }

    // eslint-disable-next-line class-methods-use-this
    public Float calculateInfluence(BetPredictionContext ctx) {
        return ctx.match().getOver05Average();
    }

    // eslint-disable-next-line class-methods-use-this
    public String influencerName() {
        return "FootyStatsOverFTPredictionInfluencer";
    }
}
