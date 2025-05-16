package de.footystats.tools.services.bet.attributes;

/**
 *
 */
public enum Attribute {
	BET_ATTRIBUTE("betAttribute"),
	ODDS("odds"),
	HALF("half"),
	;

	public final String attributeName;

	Attribute(String attributeName) {
		this.attributeName = attributeName;
	}
}
