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
}
