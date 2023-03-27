package de.ludwig.footystats.tools.backend.services.prediction.outcome;

import lombok.Data;

import java.io.Serializable;

@Data
public record InfluencerStatisticalResultOutcomeKey(Integer percent, String influencerName) implements Serializable {
}
