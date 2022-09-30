package de.ludwig.footystats.tools.backend.services.prediction.influencer;

import de.ludwig.footystats.tools.backend.services.prediction.Bet;
import de.ludwig.footystats.tools.backend.services.prediction.PrecheckResult;

public class OddsBttsYesInfluencer implements BetResultInfluencer {
        public PrecheckResult preCheck(BetPredictionContext ctx) {
            if (ctx.bet() != Bet.BTTS_YES) {
                return PrecheckResult.DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET;
            }

            if (
                    ctx.match().getOddsBTTS_Yes() == null) {
                return PrecheckResult.NOT_ENOUGH_INFORMATION;
            }

            return PrecheckResult.OK;
        }

        // eslint-disable-next-line class-methods-use-this
        public Float calculateInfluence(BetPredictionContext ctx) {
            // odds between 1 and 3 are relevant, other odds are extremas.
            if (ctx.match().getOddsBTTS_Yes() > 3) {
                return 0F;
            }

            return 100 * ((3 - ctx.match().getOddsBTTS_Yes()) / 3);
        }

        // eslint-disable-next-line class-methods-use-this
        public String influencerName() {
            return "OddsBttsYesInfluencer";
        }
}
