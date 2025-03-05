package de.footystats.tools.services.bet.attributes;

import de.footystats.tools.services.bet.BaseBetAttribute;
import de.footystats.tools.services.prediction.Bet;

public class BetAttribute extends BaseBetAttribute<Bet> {
	public BetAttribute(Bet value) {
		super(value, Attributes.BET_ATTRIBUTE);
	}

	@Override
	protected boolean match(Bet value) {
		return false;
	}
}
