package de.footystats.tools.services.domain;

import lombok.Getter;

@Getter
public class Season {

	/**
	 * Delimiter between start and end year. Only present if the season has an end year.
	 */
	public static final String SEASON_YEAR_DELIMITER = "/";

	private final Year start;

	private Year end;

	public Season(Year start) {
		this.start = start;
	}

	public Season(Year start, Year end) {
		compareYears(start, end);
		this.start = start;
		this.end = end;
	}

	public Season(String season) {
		String[] years = season.split(SEASON_YEAR_DELIMITER);

		if (years.length > 2 || years.length == 0) {
			throw new IllegalArgumentException("Invalid season format");
		}

		if (years.length == 1) {
			this.start = new Year(Integer.parseInt(years[0]));
			return;
		}

		this.start = new Year(Integer.parseInt(years[0]));
		this.end = new Year(Integer.parseInt(years[1]));
		compareYears(start, end);
	}

	private void compareYears(Year year1, Year year2) {
		if (year1.compareTo(year2) > 0) {
			throw new IllegalArgumentException("End year must be before than start year");
		}
	}

	@Override
	public String toString() {
		if (end != null) {
			return start.getYear() + SEASON_YEAR_DELIMITER + end.getYear();
		}
		return start.toString();
	}
}
