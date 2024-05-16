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
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Season(new Year(2019), new Year(2018)));

		Assertions.assertThrows(IllegalArgumentException.class, () -> new Season("2019/2018"));
	}

	@Test
	void start_lt_1900() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Season(new Year(1899), new Year(1900)));

		Assertions.assertThrows(IllegalArgumentException.class, () -> new Season("1899/1900"));
	}

	@Test
	void check_to_string() {
		Season season = new Season(new Year(2018), new Year(2019));
		Assertions.assertEquals("2018/2019", season.toString());

		season = new Season(new Year(2018));
		Assertions.assertEquals("2018", season.toString());
	}

	@Test
	void check_string_constructor() {
		Season season = new Season("2018/2019");
		Assertions.assertEquals(2018, season.getStart().getYear());
		Assertions.assertEquals(2019, season.getEnd().getYear());

		season = new Season("2018");
		Assertions.assertEquals(2018, season.getStart().getYear());
		Assertions.assertNull(season.getEnd());
	}

	@Test
	void check_invalid_season_string_constructor() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Season("2018/2019/2020"));

		Assertions.assertThrows(IllegalArgumentException.class, () -> new Season("/2018"));

		Assertions.assertThrows(IllegalArgumentException.class, () -> new Season(""));
	}
}
