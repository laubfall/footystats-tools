package de.footystats.tools.services.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class YearTest {

	@Test
	void lowerBound_gte_1900() {
		Year year = new Year(1900);
		Assertions.assertNotNull(year);

		year = new Year(1901);
		Assertions.assertNotNull(year);
	}

	@Test
	void lowerBound_lt_1900() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Year(1899);
		});
	}

	@Test
	void upperBound_lte_2300() {
		Year year = new Year(2300);
		Assertions.assertNotNull(year);

		year = new Year(2299);
		Assertions.assertNotNull(year);
	}

	@Test
	void upperBound_gt_2300() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Year(2301);
		});
	}
}
