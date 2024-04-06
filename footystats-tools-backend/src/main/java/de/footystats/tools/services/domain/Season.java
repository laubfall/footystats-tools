package de.footystats.tools.services.domain;

import lombok.Getter;

@Getter
public class Season {

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
		String[] years = season.split("/");
		this.start = new Year(Integer.parseInt(years[0]));
		this.end = new Year(Integer.parseInt(years[1]));
		compareYears(start, end);
	}

	private void compareYears(Year year1, Year year2) {
		if (year1.compareTo(year2) > 0) {
			throw new IllegalArgumentException("End year must be before than start year");
		}
	}
}
