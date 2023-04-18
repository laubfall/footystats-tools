package de.ludwig.footystats.tools.backend;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("footystats")
public class FootystatsProperties {

	@Getter
	@Setter
	private String allowedOrigins;

	@Getter
	@Setter
	private boolean importEsports;

	@Getter
	@Setter
	private int ignoreTeamStatsWithGamesPlayedLowerThan;

	@Getter
	@Setter
	private String encryptionSecret;

	@Getter
	@Setter
	private FootystatsWebpageProperties webpage;

	@Getter
	@Setter
	private PredictionQualityProperties predictionQuality;

	@Getter
	@Setter
	private CsvFileDownloadProperties csvFileDownloadProperties;

	public static class FootystatsWebpageProperties {
		@Getter
		@Setter
		private String baseUrl;

		@Getter
		@Setter
		private String matchStatsDownloadRessource;

		@Getter
		@Setter
		private String loginRessource;

		@Getter
		@Setter
		private String leagueStatsRessource;

		@Getter
		@Setter
		private String teamStatsRessource;

		@Getter
		@Setter
		private String team2StatsRessource;

		@Getter
		@Setter
		private String playerStatsRessource;

		@Getter
		@Setter
		private String matchStatsLeagueRessource;
	}

	public static class PredictionQualityProperties {
		// Count of matches to retrieve when finding matches for calculating prediction quality
		@Getter
		@Setter
		private int pageSizeFindingRevisionMatches;
	}

	public static class CsvFileDownloadProperties {
		/**
		 * Path were to store downloaded csv files. Only used if keepCsvFiles is true.
		 */
		@Getter
		@Setter
		private String pathForKeepingCsvFiles;

		/**
		 * True if downloaded csv files should be saved. In this case provide a valid path in pathForKeepingCsvFiles.
		 */
		@Getter
		@Setter
		private boolean keepCsvFiles;
	}
}
