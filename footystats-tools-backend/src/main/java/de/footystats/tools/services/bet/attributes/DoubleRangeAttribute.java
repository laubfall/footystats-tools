package de.footystats.tools.services.bet.attributes;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DoubleRangeAttribute extends RangeAttribute<Double> {

	protected DoubleRangeAttribute(Double start, Double end, Attribute name) {
		super(start, end, name);
	}

	protected DoubleRangeAttribute(RangeAttributeRecord<Double> value, Attribute name) {
		super(value, name);
	}


	@Override
	protected boolean match(ChosenAttributeValue value) {
		return getValue().getStart() >= value.getValueDouble() && getValue().getEnd() > value.getValueDouble();
	}
}
