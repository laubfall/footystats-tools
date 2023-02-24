package de.ludwig.footystats.tools.backend.controller;

import de.ludwig.footystats.tools.backend.services.match.Match;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class ListMatchElement {
	@Getter
	@Setter
	private Float predictionSuccessChance;

	@Getter
	@Setter
	private Match match;
}
