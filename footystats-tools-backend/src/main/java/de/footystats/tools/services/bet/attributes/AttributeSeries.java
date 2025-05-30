package de.footystats.tools.services.bet.attributes;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the functional logic behind an attribute series.
 */
@Document
@NoArgsConstructor
@Getter
public class AttributeSeries {
	private List<ObjectId> attributeIds;

	@Id
	private ObjectId id;

	/**
	 * The attributes that are part of this series and matches the attributeIds.
	 * Filled by aspect AttributeSeriesRepositoryAspect.
	 */
	@Transient
	private List<? extends BaseBetAttribute<?>> attributes;

	/**
	 * Used for deserialization.
	 *
	 * @param attributeIds attribute ids.
	 * @param id           id of the attribute series.
	 */
	AttributeSeries(List<ObjectId> attributeIds, ObjectId id) {
		this.attributeIds = attributeIds;
		this.id = id;
	}

	public static AttributeSeries of(List<? extends BaseBetAttribute<?>> attributes) {
		var series = new AttributeSeries();
		series.setAttributes(attributes);
		return series;
	}

	public void setAttributes(List<? extends BaseBetAttribute<?>> attributes) {
		Assert.isInstanceOf(BetAttribute.class, attributes.getFirst(), "First attribute must be a BetAttribute");
		Assert.isTrue(attributes.stream().noneMatch(attr -> attr.getId() == null), "All attributes must have an id");
		this.attributes = attributes;
		attributeIds = attributes.stream().map(BaseBetAttribute::getId).toList();
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

	public List<List<BaseBetAttribute<?>>> generateCombinations() {
		List<List<BaseBetAttribute<?>>> combinations = new ArrayList<>();
		if (attributes.size() == 1) {
			return List.of();
		}
		var withoutBetAttribute = new ArrayList<BaseBetAttribute<?>>(attributes);
		var betAttribute = withoutBetAttribute.removeFirst();
		generateCombinationsRecursive(withoutBetAttribute, combinations);

		for (List<BaseBetAttribute<?>> combination : combinations) {
			combination.addFirst(betAttribute);
		}

		combinations.add(List.of(betAttribute));

		return combinations;
	}

	private void generateCombinationsRecursive(List<BaseBetAttribute<?>> currentList, List<List<BaseBetAttribute<?>>> combinations) {
		if (currentList.size() <= 1) {
			return;
		}

		// Die BetAttribute immer in den Kombinationen beibehalten
		BaseBetAttribute<?> betAttribute = findBetAttribute();

		for (int i = 0; i < currentList.size(); i++) {
			if (currentList.get(i).equals(betAttribute)) {
				continue; // BetAttribute nicht entfernen
			}

			List<BaseBetAttribute<?>> newCombination = new ArrayList<>(currentList);
			newCombination.remove(i);
			combinations.add(newCombination);
			generateCombinationsRecursive(newCombination, combinations);
		}
	}
}
