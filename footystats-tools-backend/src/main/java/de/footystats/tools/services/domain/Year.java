package de.footystats.tools.services.domain;

import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class Year implements Comparable<Year> {

	@Getter
	private final int year;

	public Year(int year) {
		this.year = year;
		if (year < 1900 || year > 2300) {
			throw new IllegalArgumentException("Year must be between 1900 and 2300");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Year year1 = (Year) o;

		return new EqualsBuilder().append(year, year1.year).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(year).toHashCode();
	}

	@Override
	public int compareTo(Year o) {
		return Integer.compare(year, o.year);
	}
}
