package de.footystats.tools.services.footy.dls;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.services.ServiceException;
import de.footystats.tools.services.csv.CsvFileInformation;
import de.footystats.tools.services.csv.CsvFileType;
import de.footystats.tools.services.domain.Country;
import de.footystats.tools.services.footy.CsvFileDownloadService;
import de.footystats.tools.services.footy.CsvHttpClient;
import de.footystats.tools.services.footy.SessionCookie;
import de.footystats.tools.services.stats.LeagueMatchStatsService;
import de.footystats.tools.services.stats.LeagueStatsService;
import de.footystats.tools.services.stats.Team2StatsService;
import de.footystats.tools.services.stats.TeamStatsService;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Does the download for configured downloads.
 * <p>
 * Configured downloads means that the user provided a csv file with the download configs and this service will download the stats csv files as
 * configured in the csv file. See README.md for more details about the csv file.
 */
@Slf4j
@Service
public class ConfiguredCsvDownloadService extends CsvFileDownloadService {

	private final DownloadConfigService downloadConfigService;
	private final LeagueStatsService leagueStatsService;
	private final TeamStatsService teamStatsService;
	private final Team2StatsService team2StatsService;
	private final LeagueMatchStatsService leagueMatchStatsService;

	protected ConfiguredCsvDownloadService(FootystatsProperties properties,
		CsvHttpClient csvHttpClient, DownloadConfigService downloadConfigService, LeagueStatsService leagueStatsService,
		TeamStatsService teamStatsService, Team2StatsService team2StatsService,
		LeagueMatchStatsService leagueMatchStatsService) {
		super(properties, csvHttpClient);
		this.downloadConfigService = downloadConfigService;
		this.leagueStatsService = leagueStatsService;
		this.teamStatsService = teamStatsService;
		this.team2StatsService = team2StatsService;
		this.leagueMatchStatsService = leagueMatchStatsService;
	}

	public void downloadConfiguredStats() {
		List<DownloadCountryLeagueStatsConfig> currentYearConfigs = downloadConfigService.configsWhoWantADownloadForCurrentYear();
		List<DownloadCountryLeagueStatsConfig> olderConfigs = downloadConfigService.configsWhoWantADownloadForPreviousYears();
		if (currentYearConfigs.isEmpty() && olderConfigs.isEmpty()) {
			log.info("Currently there are no configured downloads, so no stats are going to be downloaded from footystats.org.");
			return;
		}

		SessionCookie login = csvHttpClient.login();
		if (!currentYearConfigs.isEmpty()) {
			log.info("Start downloading configured stats csv files for the current year.");
			doDownloadingByConfig(currentYearConfigs, login);
		}

		if (!olderConfigs.isEmpty()) {
			log.info("Start downloading configured stats csf files for older seasons.");
			doDownloadingByConfig(olderConfigs, login);
		}
	}

	public void downloadConfiguredStats(Country country, String league) {
		// Now download the stats csv as configured for the given country and league for the current year and the previous years.
		DownloadCountryLeagueStatsConfig currentYearConfigs = downloadConfigService.configForCountryLeagueSeasonForCurrentYear(country, league);
		DownloadCountryLeagueStatsConfig olderConfigs = downloadConfigService.configForCountryLeagueSeasonForPreviousYears(country, league);
		if (currentYearConfigs == null && olderConfigs == null) {
			log.info("Currently there are no configured downloads for country: " + country.getCountryNameByFootystats() + " league: " + league
				+ " so no stats are going to be downloaded from footystats.org.");
			return;
		}

		SessionCookie login = csvHttpClient.login();
		if (currentYearConfigs != null) {
			log.info("Start downloading configured stats csv files for the current year for country: " + country + " league: " + league);
			doDownloadingByConfig(List.of(currentYearConfigs), login);
		}

		if (olderConfigs != null) {
			log.info("Start downloading configured stats csf files for older seasons for country: " + country + " league: " + league);
			doDownloadingByConfig(List.of(olderConfigs), login);
		}
	}

	private void doDownloadingByConfig(List<DownloadCountryLeagueStatsConfig> configs, SessionCookie login) {
		for (DownloadCountryLeagueStatsConfig downloadCfg : configs) {
			List<FileTypeBit> typesWithWantedDownload = downloadCfg.typesWithWantedDownload();
			if (typesWithWantedDownload.isEmpty()) {
				log.warn("Download config retrieved from db without wanted downloads, this seems to be a coding problem: " + downloadCfg);
				continue;
			}

			for (FileTypeBit fileTypeBit : typesWithWantedDownload) {
				log.info("Start downloading configured stats of type " + fileTypeBit.getBit() + " country: " + downloadCfg.getCountry() + " league: "
					+ downloadCfg.getLeague() + " season: " + downloadCfg.getSeason());
				String dlResource = fileTypeBitToFootystatsRessource(fileTypeBit);
				List<String> csvLines = downloadConfiguredStats(login, dlResource, downloadCfg.getFootyStatsDlId());
				importConfiguredStats(downloadCfg, csvLines, fileTypeBit);
			}
		}
	}

	private void importConfiguredStats(DownloadCountryLeagueStatsConfig config, List<String> lines, FileTypeBit bit) {
		switch (bit) {
			case LEAGUE -> {
				Consumer<FileInputStream> leagueConsumer = (fis) -> {
					this.leagueStatsService.readLeagueStats(new CsvFileInformation(CsvFileType.LEAGUE_STATS, config.getCountry()), fis);
					config.setLastLeagueDownload(System.currentTimeMillis());
					downloadConfigService.upsert(config);
				};
				workingWithTempFile(leagueConsumer, lines, "leagueStats");
			}
			case TEAM -> {
				Consumer<FileInputStream> leagueConsumer = (fis) -> {
					this.teamStatsService.readTeamStats(fis);
					config.setLastTeamsDownload(System.currentTimeMillis());
					downloadConfigService.upsert(config);
				};
				workingWithTempFile(leagueConsumer, lines, "teamStats");
			}
			case TEAM2 -> {
				Consumer<FileInputStream> leagueConsumer = (fis) -> {
					this.team2StatsService.readTeamStats(fis);
					config.setLastTeams2Download(System.currentTimeMillis());
					downloadConfigService.upsert(config);
				};
				workingWithTempFile(leagueConsumer, lines, "team2stats");
			}
			case PLAYER -> {

			}
			case MATCH -> {
				Consumer<FileInputStream> leagueConsumer = (fis) -> {
					this.leagueMatchStatsService.readLeagueMatchStats(fis);
					config.setLastTeams2Download(System.currentTimeMillis());
					downloadConfigService.upsert(config);
				};
				workingWithTempFile(leagueConsumer, lines, "leagueMatchStats");
			}
		}
	}

	private String fileTypeBitToFootystatsRessource(FileTypeBit bit) {
		var webpageProperties = properties.getWebpage();
		switch (bit) {
			case LEAGUE -> {
				return webpageProperties.getLeagueStatsRessource();
			}
			case TEAM -> {
				return webpageProperties.getTeamStatsRessource();
			}
			case TEAM2 -> {
				return webpageProperties.getTeam2StatsRessource();
			}
			case PLAYER -> {
				return webpageProperties.getPlayerStatsRessource();
			}
			case MATCH -> {
				return webpageProperties.getMatchStatsLeagueRessource();
			}
			default -> throw new IllegalArgumentException("Unknown bit type");
		}
	}

	private List<String> downloadConfiguredStats(SessionCookie sessionCookie, String resource, int footyStatsDlId) {
		try {
			final URL url = new URL(properties.getWebpage().getBaseUrl() + resource + footyStatsDlId);
			log.info("Downloading csv file from footystats.org with url: " + url);
			return csvHttpClient.connectToFootystatsAndRetrieveFileContent(sessionCookie, url);
		} catch (IOException e) {
			throw new ServiceException(ServiceException.Type.CSV_FILE_DOWNLOAD_SERVICE_DL_FAILED, e);
		}
	}
}
