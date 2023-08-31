package de.footystats.tools;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * This class is used to store the properties of the application.
 */
@Component
@ConfigurationProperties("footystats")
@Getter
@Setter
public class FootystatsProperties {

	private String allowedOrigins;

	private boolean importEsports;

	private int ignoreTeamStatsWithGamesPlayedLowerThan;

	private String encryptionSecret;

	private Long maxCacheTimeConfiguredStatsCache;

	private FootystatsWebpageProperties webpage;

	private PredictionQualityProperties predictionQuality;

	private CsvFileDownloadProperties csvFileDownloadProperties;

	@Getter
	@Setter
	public static class FootystatsWebpageProperties {

		private String baseUrl;

		private String matchStatsDownloadRessource;

		private String loginRessource;

		private String leagueStatsRessource;

		private String teamStatsRessource;

		private String team2StatsRessource;

		private String playerStatsRessource;

		private String matchStatsLeagueRessource;
	}

	@Getter
	@Setter
	public static class PredictionQualityProperties {

		// Count of matches to retrieve when finding matches for calculating prediction quality
		private int pageSizeFindingRevisionMatches;
	}

	@Getter
	@Setter
	public static class CsvFileDownloadProperties {

		/**
		 * Path were to store downloaded csv files. Only used if keepCsvFiles is true.
		 */
		private String pathForKeepingCsvFiles;

		/**
		 * True if downloaded csv files should be saved. In this case provide a valid path in pathForKeepingCsvFiles.
		 */
		private boolean keepCsvFiles;
	}
}
