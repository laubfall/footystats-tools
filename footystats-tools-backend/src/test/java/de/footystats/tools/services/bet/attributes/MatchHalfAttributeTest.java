package de.footystats.tools.services.bet.attributes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MatchHalfAttributeTest {
	@Test
	void not_matchHalf_chosen() {
		var matchHalfAttribute = new MatchHalfAttribute(MatchHalfAttribute.MatchHalf.SECOND_HALF);
		var chosenAttributeValue = new ChosenAttributeValue(Attribute.COUNTRY, "Germany");
		Assertions.assertFalse(matchHalfAttribute.match(chosenAttributeValue));
	}

	@Test
	void wrong_attribute_value() {
		var matchHalfAttribute = new MatchHalfAttribute(MatchHalfAttribute.MatchHalf.SECOND_HALF);
		var chosenAttributeValue = new ChosenAttributeValue(Attribute.HALF, "WRONG_VALUE");
		Assertions.assertFalse(matchHalfAttribute.match(chosenAttributeValue));
	}

	@Test
	void not_the_same_value() {
		var matchHalfAttribute = new MatchHalfAttribute(MatchHalfAttribute.MatchHalf.SECOND_HALF);
		var chosenAttributeValue = new ChosenAttributeValue(Attribute.HALF,
			MatchHalfAttribute.MatchHalf.FIRST_HALF.toString());
		Assertions.assertTrue(matchHalfAttribute.match(chosenAttributeValue));
	}

	@Test
	void the_same_value() {
		var matchHalfAttribute = new MatchHalfAttribute(MatchHalfAttribute.MatchHalf.SECOND_HALF);
		var chosenAttributeValue = new ChosenAttributeValue(Attribute.HALF,
			MatchHalfAttribute.MatchHalf.SECOND_HALF.toString());
		Assertions.assertTrue(matchHalfAttribute.match(chosenAttributeValue));
	}
}
