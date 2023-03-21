package de.ludwig.footystats.tools.backend.controller;

import de.ludwig.footystats.tools.backend.services.match.Match;
import de.ludwig.footystats.tools.backend.services.prediction.outcome.StatisticalResultOutcome;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MatchListElement extends Match {
	@Getter
	@Setter
	private List<StatisticalResultOutcome> statisticalResultOutcome;
}
