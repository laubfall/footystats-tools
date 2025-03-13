package de.footystats.tools.services.bet.attributes;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@EqualsAndHashCode(of = {"value", "name"})
public abstract class BaseBetAttribute<A> {
	protected final A value;
	@Setter
	protected Attributes name;
	@Id
	private ObjectId id;

	protected BaseBetAttribute(A value, Attributes name) {
		this.value = value;
		this.name = name;
	}

	protected final boolean baseMatch(ChosenAttributeValue value) {
		return value.getAttributeId().equals(id) && match(value);
	}

	/**
	 * Check if the value of this attribute matches the given value.
	 *
	 * @param value The value to check against.
	 * @return True if the value matches, false otherwise.
	 */
	protected abstract boolean match(ChosenAttributeValue value);
}
