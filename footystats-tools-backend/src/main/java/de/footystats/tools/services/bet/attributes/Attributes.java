package de.footystats.tools.services.bet.attributes;

/**
 *
 */
public enum Attributes {
	BET_ATTRIBUTE("betAttribute"),
	ODDS("odds"),
	HALF("half"),
	;

	public final String attributeName;

	Attributes(String attributeName) {
		this.attributeName = attributeName;
	}
}
