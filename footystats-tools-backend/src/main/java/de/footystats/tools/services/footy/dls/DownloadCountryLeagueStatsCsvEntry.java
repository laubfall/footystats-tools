package de.footystats.tools.services.footy.dls;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

public class DownloadCountryLeagueStatsCsvEntry {
	@Getter
	@Setter
	@CsvBindByName(required = true)
	private String country;

	@Getter
	@Setter
	@CsvBindByName(required = true)
	private String league;

	/**
	 * The id provided by footystats that identifies the downloads for a specific country and season.
	 */
	@Getter
	@Setter
	@CsvBindByName(required = true)
	private Integer footyStatsDlId;

	/**
	 * e.G.: 2022 or a range 2021/2022
	 */
	@Getter
	@Setter
	@CsvBindByName(required = true)
	private String season;

	/**
	 * 1: League
	 * 2: Teams
	 * 4: Teams2
	 * 8: Player
	 * 16: Match
	 */
	@Getter
	@Setter
	@CsvBindByName(required = true, capture = "(\\d{1})")
	private Integer downloadBitmask;
}
