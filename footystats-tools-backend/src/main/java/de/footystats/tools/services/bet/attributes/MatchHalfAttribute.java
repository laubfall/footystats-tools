package de.footystats.tools.services.bet.attributes;

public class MatchHalfAttribute extends BaseBetAttribute<MatchHalfAttribute.MatchHalf> {

	protected MatchHalfAttribute(MatchHalf value) {
		super(value, Attributes.HALF, Attributes.HALF.attributeName + "_" + value);
	}

	@Override
	protected boolean match(ChosenAttributeValue value) {
		return false;
	}

	public enum MatchHalf {
		FIRST_HALF,
		SECOND_HALF,
		BOTH
	}
}
