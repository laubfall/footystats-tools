package de.footystats.tools.services.footy.dls;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import de.footystats.tools.services.domain.Country;
import de.footystats.tools.services.domain.CountryCsvConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is used to store the configuration for the download of a specific country and league.
 * <p>
 * The configuration is used to determine which files should be downloaded and when the last download of a file was. The configuration is also used to
 * determine if a file should be downloaded again.
 * <p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DownloadCountryLeagueStatsCsvEntry {

	@CsvCustomBindByName(column = "country", required = true, converter = CountryCsvConverter.class)
	private Country country;

	@CsvBindByName(required = true)
	private String league;

	/**
	 * The id provided by footystats that identifies the downloads for a specific country and season.
	 */
	@CsvBindByName(required = true)
	private Integer footyStatsDlId;

	/**
	 * e.G.: 2022 or a range 2021/2022
	 */
	@CsvBindByName(required = true)
	private String season;

	/**
	 * 1: League 2: Teams 4: Teams2 8: Player 16: Match
	 */
	@CsvBindByName(capture = "(\\d{1})")
	private Integer downloadBitmask;
}
