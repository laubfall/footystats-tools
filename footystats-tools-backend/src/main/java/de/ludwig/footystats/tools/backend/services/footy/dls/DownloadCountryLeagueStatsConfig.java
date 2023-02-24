package de.ludwig.footystats.tools.backend.services.footy.dls;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@CompoundIndexes({
	@CompoundIndex(name = "unique", def = "{'country': 1, 'league': 1, 'footyStatsDlId': 1, 'season': 1}")
})
@Document
public class DownloadCountryLeagueStatsConfig {
	@Getter
	@Setter
	private String country;

	@Getter
	@Setter
	private String league;

	/**
	 * The id provided by footystats that identifies the downloas for a specific country and season.
	 */
	@Getter
	@Setter
	private Integer footyStatsDlId;

	/**
	 * e.G.: 2022 or a range 2021/2022
	 */
	@Getter
	@Setter
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
	private Integer downloadBitmask;
}
