package de.footystats.tools.services.bet.attributes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChosenAttributeValue {
	private ObjectId attributeId;

	private String value;

	private Double valueDouble;

	private Integer valueInt;

	public ChosenAttributeValue(ObjectId attributeId, String value) {
		this.attributeId = attributeId;
		this.value = value;
	}

	public ChosenAttributeValue(ObjectId attributeId, Double value) {
		this.attributeId = attributeId;
		this.valueDouble = value;
	}

	public ChosenAttributeValue(ObjectId attributeId, Integer value) {
		this.attributeId = attributeId;
		this.valueInt = value;
	}
}
