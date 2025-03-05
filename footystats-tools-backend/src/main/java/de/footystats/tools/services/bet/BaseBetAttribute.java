package de.footystats.tools.services.bet;

import de.footystats.tools.services.bet.attributes.Attributes;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@EqualsAndHashCode(of = {"value", "name"})
public abstract class BaseBetAttribute<A> {
	protected final A value;
	@Setter
	protected Attributes name;

	public BaseBetAttribute(A value, Attributes name) {
		this.value = value;
		this.name = name;
	}

	/**
	 * Check if the value of this attribute matches the given value.
	 *
	 * @param value The value to check against.
	 * @return True if the value matches, false otherwise.
	 */
	protected abstract boolean match(A value);
}
