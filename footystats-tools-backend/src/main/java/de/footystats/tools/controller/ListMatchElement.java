package de.footystats.tools.controller;

import de.footystats.tools.services.match.Match;
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
