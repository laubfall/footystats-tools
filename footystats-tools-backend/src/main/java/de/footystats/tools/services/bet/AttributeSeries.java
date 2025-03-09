package de.footystats.tools.services.bet;


import de.footystats.tools.services.bet.attributes.BetAttribute;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Represents the functional logic behind an attribute series.
 */
@Getter
public class AttributeSeries {
	private final List<BaseBetAttribute<?>> attributes;

	public AttributeSeries(List<BaseBetAttribute<?>> attributes) {
		Assert.isTrue(attributes.stream().filter(attr -> attr instanceof BetAttribute).count() == 1,
			"Exact one attribute must be of type BetAttribute");
		this.attributes = attributes;
	}

	/**
	 * Get all possible bet attribute series that can be created from this attribute series.
	 * <p>
	 * Every possible series contains at least the BetAttribute.
	 *
	 * @return A list of possible bet attribute series based on the whole list.
	 */
	public List<List<BaseBetAttribute<?>>> possibleBetAttributeSeries() {


		return null;
	}
}
