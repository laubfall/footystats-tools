package de.footystats.tools.controller;

import de.footystats.tools.services.match.Match;
import de.footystats.tools.services.prediction.outcome.StatisticalResultOutcome;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MatchListElement extends Match {
	@Getter
	@Setter
	private List<StatisticalResultOutcome> statisticalResultOutcome;
}
