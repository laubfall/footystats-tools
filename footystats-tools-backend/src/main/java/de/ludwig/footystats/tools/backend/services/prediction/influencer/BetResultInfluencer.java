package de.ludwig.footystats.tools.backend.services.prediction.influencer;

import de.ludwig.footystats.tools.backend.services.prediction.PrecheckResult;

public interface BetResultInfluencer {
    PrecheckResult preCheck(BetPredictionContext ctx);
    Float calculateInfluence(BetPredictionContext ctx);
    String influencerName();
}
