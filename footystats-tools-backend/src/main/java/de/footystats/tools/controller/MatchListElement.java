package de.footystats.tools.controller;

import de.footystats.tools.services.match.Match;
import de.footystats.tools.services.prediction.outcome.StatisticalResultOutcome;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class MatchListElement extends Match implements Serializable {

	@Getter
	@Setter
	private List<StatisticalResultOutcome> statisticalResultOutcome;
}
