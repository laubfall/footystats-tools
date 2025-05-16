package de.footystats.tools.services.bet.attributes;

import de.footystats.tools.services.prediction.Bet;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class AttributeSeriesTest {

	@Test
	void combinations() {
		// Arrange: Erstelle eine AttributeSeries mit einer Liste von BaseBetAttributes
		BetAttribute betAttribute = new BetAttribute(Bet.OVER_ZERO_FIVE);
		betAttribute.setId(new ObjectId());
		var attr1 = new DoubleAttribute(1.0, Attribute.ODDS);
		attr1.setId(new ObjectId());
		var attr2 = new MatchHalfAttribute(MatchHalfAttribute.MatchHalf.FIRST_HALF);
		attr2.setId(new ObjectId());

		AttributeSeries attributeSeries = new AttributeSeries();
		attributeSeries.setAttributes(List.of(betAttribute, attr1, attr2));

		// Act: Rufe die combinations-Methode auf
		List<List<BaseBetAttribute<?>>> combinations = attributeSeries.generateCombinations();

		// Assert: Überprüfe die generierten Kombinationen
		Assertions.assertNotNull(combinations);
		Assertions.assertFalse(combinations.isEmpty());
		Assertions.assertEquals(3, combinations.size());
		Assertions.assertTrue(combinations.stream().allMatch(list ->
			list.stream().filter(attr -> attr instanceof BetAttribute).count() == 1
		));
	}

	@Test
	void series_only_with_bet_attribute() {
		BetAttribute betAttribute = new BetAttribute(Bet.OVER_ZERO_FIVE);
		betAttribute.setId(new ObjectId());
		AttributeSeries attributeSeries = new AttributeSeries();
		attributeSeries.setAttributes(List.of(betAttribute));

		List<List<BaseBetAttribute<?>>> combinations = attributeSeries.generateCombinations();
		Assertions.assertTrue(combinations.isEmpty(), "No more combinations available");
	}
}
