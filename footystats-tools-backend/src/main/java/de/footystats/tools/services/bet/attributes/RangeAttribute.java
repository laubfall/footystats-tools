package de.footystats.tools.services.bet.attributes;

public abstract class RangeAttribute<N extends Number> extends BaseBetAttribute<RangeAttribute.RangeAttributeRecord<N>> {
	protected RangeAttribute(N start, N end, Attribute name) {
		super(new RangeAttributeRecord<>(start, end), name, uniqueName(start, end, name));
	}

	private static String uniqueName(Number start, Number end, Attribute name) {
		if (start == null || end == null) {
			throw new IllegalArgumentException("Start and end values must not be null");
		}

		var typeName = start.getClass().getSimpleName().toLowerCase();

		return "range_" + typeName + "_" + start + "-" + end + "_" + name.attributeName;
	}

	@Override
	protected boolean match(ChosenAttributeValue value) {
		return false;
	}

	public record RangeAttributeRecord<N extends Number>(N start, N end) {

	}
}
