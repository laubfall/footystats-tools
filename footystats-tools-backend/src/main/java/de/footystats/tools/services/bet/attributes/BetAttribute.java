package de.footystats.tools.services.bet.attributes;

import de.footystats.tools.services.prediction.Bet;

public class BetAttribute extends BaseBetAttribute<Bet> {

	public static final String BET_ATTR = "bet_";

	public BetAttribute(Bet value) {
		super(value, Attribute.BET_ATTRIBUTE, uniqueName(value));
	}

	public static String uniqueName(Bet value) {
		return BET_ATTR + value.name();
	}

	@Override
	protected boolean match(ChosenAttributeValue value) {
		return false;
	}
}
