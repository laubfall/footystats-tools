package de.footystats.tools.services.footy.dls;

import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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

	public List<FileTypeBit> typesWithWantedDownload(){
		List<FileTypeBit> result = new ArrayList<>(5);
		Function<Long, Boolean> dlTimeReached = (lastDownload) -> lastDownload == null || lastDownload < System.currentTimeMillis() - DownloadConfigService.LAST_DOWNLOAD_MINUS_TIME_MILLIS;
		var fileBitToCheck = FileTypeBit.LEAGUE;
		if((downloadBitmask & fileBitToCheck.getBit()) == fileBitToCheck.getBit() && dlTimeReached.apply(lastLeagueDownload)){
			result.add(fileBitToCheck);
		}

		fileBitToCheck = FileTypeBit.TEAM;
		if((downloadBitmask & fileBitToCheck.getBit()) == fileBitToCheck.getBit() && dlTimeReached.apply(lastTeamsDownload)){
			result.add(fileBitToCheck);
		}

		fileBitToCheck = FileTypeBit.TEAM2;
		if((downloadBitmask & fileBitToCheck.getBit()) == fileBitToCheck.getBit() && dlTimeReached.apply(lastTeams2Download)){
			result.add(fileBitToCheck);
		}

		fileBitToCheck = FileTypeBit.PLAYER;
		if((downloadBitmask & fileBitToCheck.getBit()) == fileBitToCheck.getBit() && dlTimeReached.apply(lastPlayerDownload)){
			result.add(fileBitToCheck);
		}

		fileBitToCheck = FileTypeBit.MATCH;
		if((downloadBitmask & fileBitToCheck.getBit()) == fileBitToCheck.getBit() && dlTimeReached.apply(lastMatchDownload)){
			result.add(fileBitToCheck);
		}

		return result;
	}

	@Override
	public String toString() {
		return "DownloadCountryLeagueStatsConfig{" +
			"country='" + country + '\'' +
			", league='" + league + '\'' +
			", footyStatsDlId=" + footyStatsDlId +
			", season='" + season + '\'' +
			", downloadBitmask=" + downloadBitmask +
			", lastLeagueDownload=" + lastLeagueDownload +
			", lastTeamsDownload=" + lastTeamsDownload +
			", lastTeams2Download=" + lastTeams2Download +
			", lastPlayerDownload=" + lastPlayerDownload +
			", lastMatchDownload=" + lastMatchDownload +
			'}';
	}
}
