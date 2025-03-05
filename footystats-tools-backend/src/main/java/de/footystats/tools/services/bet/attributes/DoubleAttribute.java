package de.footystats.tools.services.bet.attributes;

import de.footystats.tools.services.bet.BaseBetAttribute;

public class DoubleAttribute extends BaseBetAttribute<Double> {
	public DoubleAttribute(Double value, Attributes name) {
		super(value, name);
	}

	@Override
	protected boolean match(Double value) {
		return false;
	}
}
