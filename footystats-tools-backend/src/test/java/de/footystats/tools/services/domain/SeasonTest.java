package de.footystats.tools.services.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SeasonTest {

	@Test
	void start_lt_end() {
		Season season = new Season(new Year(2018), new Year(2019));
		Assertions.assertNotNull(season);

		season = new Season("2018/2019");
		Assertions.assertNotNull(season);
	}

	@Test
	void start_eq_end() {
		Season season = new Season(new Year(2018), new Year(2018));
		Assertions.assertNotNull(season);

		season = new Season("2018/2018");
		Assertions.assertNotNull(season);
	}

	@Test
	void start_gt_end() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Season(new Year(2019), new Year(2018));
		});

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Season("2019/2018");
		});
	}
}
