package de.ludwig.footystats.tools.backend.services.footy.dls;

import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@CompoundIndexes({
	@CompoundIndex(name = "unique", def = "{'country': 1, 'league': 1, 'footyStatsDlId': 1, 'season': 1, 'downloadBitmask': 1, 'lastLeagueDownload': 1, 'lastTeamsDownload': 1, 'lastTeams2Download': 1, 'lastPlayerDownload': 1, 'lastMatchDownload': 1}")
})
@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DownloadCountryLeagueStatsConfig {
	@Getter
	@Setter
	private String country;

	@Getter
	@Setter
	private String league;

	/**
	 * The id provided by footystats that identifies the downloads for a specific country and season.
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

	@Getter
	@Setter
	private Long lastLeagueDownload;

	@Getter
	@Setter
	private Long lastTeamsDownload;

	@Getter
	@Setter
	private Long lastTeams2Download;

	@Getter
	@Setter
	private Long lastPlayerDownload;

	@Getter
	@Setter
	private Long lastMatchDownload;
}
