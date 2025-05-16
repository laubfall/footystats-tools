package de.footystats.tools.services.bet.attributes;

public class DoubleAttribute extends BaseBetAttribute<Double> {

	public static final String DOUBLE_ATTR = "double_";

	public DoubleAttribute(Double value, Attribute name) {
		super(value, name, uniqueName(value, name));
	}

	public static String uniqueName(Double value, Attribute name) {
		return DOUBLE_ATTR + value + "_" + name;
	}

	@Override
	protected boolean match(ChosenAttributeValue value) {
		return false;
	}
}
