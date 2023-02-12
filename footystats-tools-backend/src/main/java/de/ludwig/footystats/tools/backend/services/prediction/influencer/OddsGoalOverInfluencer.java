package de.ludwig.footystats.tools.backend.services.prediction.influencer;

import de.ludwig.footystats.tools.backend.services.prediction.Bet;
import de.ludwig.footystats.tools.backend.services.prediction.PrecheckResult;

public class OddsGoalOverInfluencer implements BetResultInfluencer {

    // eslint-disable-next-line class-methods-use-this
    public PrecheckResult preCheck(BetPredictionContext ctx) {
        switch (ctx.bet()) {
            case OVER_ZERO_FIVE:
            case OVER_ONE_FIVE:
                break;
            default:
                return PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET;
        }
        if (ctx.match() == null || ctx.match().getOddsOver15() == null) {
            return PrecheckResult.NOT_ENOUGH_INFORMATION;
        }

        return PrecheckResult.OK;
    }

    // eslint-disable-next-line class-methods-use-this
    public Integer calculateInfluence(
            BetPredictionContext ctx
    ) {
        var amount = 0;
        // eslint-disable-next-line default-case
        switch (ctx.bet()) {
            case OVER_ZERO_FIVE:
            case OVER_ONE_FIVE:
                amount = calculateFor05And15(ctx);
                break;
        }

        return amount;
    }

    private Integer calculateFor05And15(BetPredictionContext ctx) {
        var zeroFiveModifier = ctx.bet() == Bet.OVER_ZERO_FIVE ? 0.2 : 0;
        var oddsOver15 = ctx.match().getOddsOver15(); // Odds Over05 info does not exist in csv
        var diffOddToOdd2 = 2 - oddsOver15 - zeroFiveModifier; // e.g.: 2 - 1,3 = 0,7, the subtraction of 0.2 is cause of the stat
        if (diffOddToOdd2 < 0) {
            return 0; // odds for over 1.5 are so high that it seems to be impossible for even over 05
        }

        if (diffOddToOdd2 > 1) {
            return 100;
        }

        return (int)(100 * diffOddToOdd2);
    }

    // eslint-disable-next-line class-methods-use-this
    public String influencerName() {
        return "OddsGoalsOverInfluencer";
    }
}
