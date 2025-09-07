package de.footystats.tools.services.bet.attributes;

public class MatchHalfAttribute extends BaseBetAttribute<MatchHalfAttribute.MatchHalf> {

	protected MatchHalfAttribute(MatchHalf value) {
		super(value, Attribute.HALF, Attribute.HALF.attributeName + "_" + value);
	}

	@Override
	protected boolean match(ChosenAttributeValue value) {
		if (!value.getChosenAttribute().equals(Attribute.HALF)) {
			return false;
		}

		try {
			MatchHalf.valueOf(value.getValue());
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public enum MatchHalf {
		FIRST_HALF,
		SECOND_HALF,
		BOTH
	}
}
