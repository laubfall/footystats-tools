package de.footystats.tools.services.bet.attributes;

import de.footystats.tools.services.prediction.Bet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A chosen value for an attribute.
 * <p>
 * Only one type of value can be set at a time.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChosenAttributeValue {
	/**
	 * The attribute that was chosen.
	 */
	private Attribute chosenAttribute;

	private String value;

	private Double valueDouble;

	private Integer valueInt;

	public ChosenAttributeValue(Bet bet) {
		chosenAttribute = Attribute.BET_ATTRIBUTE;
		value = bet.name();
	}

	public ChosenAttributeValue(Attribute chosenAttribute, String value) {
		this.chosenAttribute = chosenAttribute;
		this.value = value;
	}

	public ChosenAttributeValue(Attribute chosenAttribute, Double value) {
		this.chosenAttribute = chosenAttribute;
		this.valueDouble = value;
	}

	public ChosenAttributeValue(Attribute chosenAttribute, Integer value) {
		this.chosenAttribute = chosenAttribute;
		this.valueInt = value;
	}

	public Bet getBet() {
		if (chosenAttribute != Attribute.BET_ATTRIBUTE) {
			throw new IllegalStateException("Chosen attribute is not a bet attribute");
		}
		return Bet.valueOf(value);
	}
}
