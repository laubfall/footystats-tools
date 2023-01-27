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
	}

	public static class PredictionQualityProperties
	{
		// Count of matches to retrieve when finding matches for calculating prediction quality
		@Getter
		@Setter
		private int pageSizeFindingRevisionMatches;
	}
}
