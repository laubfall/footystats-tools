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

	public AttributeSeries(List<BaseBetAttribute<?>> attributes) {
		Assert.isInstanceOf(BetAttribute.class, attributes.getFirst(), "First attribute must be a BetAttribute");
		Assert.isTrue(attributes.stream().noneMatch(attr -> attr.getId() == null), "All attributes must have an id");
		this.attributes = attributes;
	}

	public final List<ObjectId> computeAttributeIds() {
		return attributes.stream().map(BaseBetAttribute::getId).toList();
	}

	/**
	 * Method computes the bet attribute of type BetAttribute.
	 *
	 * @return see description. Never null because there is always a bet attribute secured by constructor.
	 */
	public final BetAttribute findBetAttribute() {
		return (BetAttribute) attributes.stream().filter(
			attr -> attr instanceof BetAttribute).findFirst().orElseThrow();
	}

	/**
	 * Method provides the bet attribute paired with the chosen attribute value.
	 *
	 * @param chosenAttributeValues Mandatory.
	 * @return List of pairs of bet attribute and chosen attribute value.
	 */
	@Deprecated // Possibly not needed anymore.
	public List<Pair<BaseBetAttribute<?>, ChosenAttributeValue>> groupById(List<ChosenAttributeValue> chosenAttributeValues) {
		Assert.notNull(chosenAttributeValues, "Chosen attribute values must not be null");
		if (chosenAttributeValues.isEmpty()) {
			return List.of();
		}

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

		var fewer = new ArrayList<>(attributes);
		fewer.removeLast();
		return Optional.of(new AttributeSeries(fewer));
	}
}
