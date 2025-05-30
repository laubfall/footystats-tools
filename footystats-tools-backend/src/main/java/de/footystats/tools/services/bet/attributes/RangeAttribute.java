package de.footystats.tools.services.bet.attributes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * WIP
 *
 * @param <N>
 */
@NoArgsConstructor
public abstract class RangeAttribute<N extends Number> extends BaseBetAttribute<RangeAttribute.RangeAttributeRecord<N>> {

	/**
	 * The start and end values of the range.
	 *
	 * @param start Mandatory. The inclusive start value of the range.
	 * @param end   Mandatory. The exclusive end value of the range.
	 * @param name  Mandatory. The name of the attribute.
	 */
	protected RangeAttribute(N start, N end, Attribute name) {
		super(new RangeAttributeRecord<>(start, end), name, uniqueName(start, end, name));
	}

	protected RangeAttribute(RangeAttributeRecord<N> value, Attribute name) {
		super(value, name, uniqueName(value.getStart(), value.getEnd(), name));
	}

	private static String uniqueName(Number start, Number end, Attribute name) {
		if (start == null || end == null) {
			throw new IllegalArgumentException("Start and end values must not be null");
		}

		var typeName = start.getClass().getSimpleName().toLowerCase();

		return "range_" + typeName + "_" + start + "-" + end + "_" + name;
	}

	@Document
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	public static class RangeAttributeRecord<S> {
		private S start;
		private S end;
	}
}
