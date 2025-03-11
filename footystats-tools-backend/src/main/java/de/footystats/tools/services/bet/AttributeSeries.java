package de.footystats.tools.services.bet;


import de.footystats.tools.services.bet.attributes.BetAttribute;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Represents the functional logic behind an attribute series.
 */
@Getter
public class AttributeSeries {
	private final List<BaseBetAttribute<?>> attributes;

	public AttributeSeries(BaseBetAttribute<?>... attributes) {
		this(List.of(attributes));
	}

	public AttributeSeries(List<BaseBetAttribute<?>> attributes) {
		Assert.isTrue(attributes.stream().filter(attr -> attr instanceof BetAttribute).count() == 1,
			"Exact one attribute must be of type BetAttribute");
		this.attributes = attributes;
	}

	public final List<ObjectId> computeAttributeIds() {
		return attributes.stream().map(BaseBetAttribute::getId).toList();
	}

	public final BetAttribute findBetAttribute() {
		return (BetAttribute) attributes.stream().filter(
			attr -> attr instanceof BetAttribute).findFirst().orElseThrow();
	}
}
