package de.footystats.tools.services.bet.attributes;

public class DoubleAttribute extends BaseBetAttribute<Double> {
	public DoubleAttribute(Double value, Attributes name) {
		super(value, name);
	}

	@Override
	protected boolean match(ChosenAttributeValue value) {
		return false;
	}
}
