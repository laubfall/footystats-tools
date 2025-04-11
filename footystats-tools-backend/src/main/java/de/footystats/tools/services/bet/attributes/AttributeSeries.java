package de.footystats.tools.services.bet.attributes;


import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import org.bson.types.ObjectId;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		Assert.isInstanceOf(BetAttribute.class, attributes.getFirst(), "First attribute must be a BetAttribute");
		Assert.isTrue(attributes.stream().noneMatch(attr -> attr.getId() == null), "All attributes must have an id");
		this.attributes = attributes;
	}

	public final List<ObjectId> computeAttributeIds() {
		return attributes.stream().map(BaseBetAttribute::getId).toList();
	}

	public final BetAttribute findBetAttribute() {
		return (BetAttribute) attributes.stream().filter(
			attr -> attr instanceof BetAttribute).findFirst().orElseThrow();
	}

	public List<Pair<BaseBetAttribute<?>, ChosenAttributeValue>> groupById(List<ChosenAttributeValue> chosenAttributeValues) {
		var result = new ArrayList<Pair<BaseBetAttribute<?>, ChosenAttributeValue>>(attributes.size());
		for (ChosenAttributeValue chosenAttributeValue : chosenAttributeValues) {
			for (BaseBetAttribute<?> attribute : attributes) {
				if (attribute.getId().equals(chosenAttributeValue.getAttributeId())) {
					result.add(Pair.of(attribute, chosenAttributeValue));
					break;
				}
			}
		}

		return result;
	}

	public Optional<AttributeSeries> fewerAttributes() {
		if (attributes.size() == 1) {
			return Optional.empty();
		}

		var fewer = new ArrayList<BaseBetAttribute<?>>(attributes);
		fewer.removeLast();
		return Optional.of(new AttributeSeries(fewer));
	}
}
