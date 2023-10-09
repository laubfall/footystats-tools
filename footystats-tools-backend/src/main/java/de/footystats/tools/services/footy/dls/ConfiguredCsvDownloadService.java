package de.footystats.tools.services.footy.dls;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.services.ServiceException;
import de.footystats.tools.services.csv.CsvFileService;
import de.footystats.tools.services.domain.Country;
import de.footystats.tools.services.footy.CsvFileDownloadService;
import de.footystats.tools.services.footy.CsvHttpClient;
import de.footystats.tools.services.footy.SessionCookie;
import de.footystats.tools.services.stats.LeagueMatchStats;
import de.footystats.tools.services.stats.LeagueStats;
import de.footystats.tools.services.stats.Team2Stats;
import de.footystats.tools.services.stats.TeamStats;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Does the download for configured downloads.
 */
@Slf4j
@Service
public class ConfiguredCsvDownloadService extends CsvFileDownloadService {

	private final DownloadConfigService downloadConfigService;
	private final CsvFileService<LeagueStats> leagueStatsCsvFileService;
	private final CsvFileService<TeamStats> teamStatsCsvFileService;
	private final CsvFileService<Team2Stats> team2StatsCsvFileService;
	private final CsvFileService<LeagueMatchStats> leagueMatchStats;

	protected ConfiguredCsvDownloadService(FootystatsProperties properties,
		CsvHttpClient csvHttpClient, DownloadConfigService downloadConfigService, CsvFileService<LeagueStats> leagueStatsCsvFileService,
		CsvFileService<TeamStats> teamStatsCsvFileService, CsvFileService<Team2Stats> team2StatsCsvFileService,
		CsvFileService<LeagueMatchStats> leagueMatchStats) {
		super(properties, csvHttpClient);
		this.downloadConfigService = downloadConfigService;
		this.leagueStatsCsvFileService = leagueStatsCsvFileService;
		this.teamStatsCsvFileService = teamStatsCsvFileService;
		this.team2StatsCsvFileService = team2StatsCsvFileService;
		this.leagueMatchStats = leagueMatchStats;
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
			log.info("Currently there are no configured downloads for country: " + country + " league: " + league
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
					this.leagueStatsCsvFileService.importFile(fis, LeagueStats.class);
					config.setLastLeagueDownload(System.currentTimeMillis());
					downloadConfigService.upsert(config);
				};
				workingWithTempFile(leagueConsumer, lines, "leagueStats");
			}
			case TEAM -> {
				Consumer<FileInputStream> leagueConsumer = (fis) -> {
					this.teamStatsCsvFileService.importFile(fis, TeamStats.class);
					config.setLastTeamsDownload(System.currentTimeMillis());
					downloadConfigService.upsert(config);
				};
				workingWithTempFile(leagueConsumer, lines, "teamStats");
			}
			case TEAM2 -> {
				Consumer<FileInputStream> leagueConsumer = (fis) -> {
					this.team2StatsCsvFileService.importFile(fis, Team2Stats.class);
					config.setLastTeams2Download(System.currentTimeMillis());
					downloadConfigService.upsert(config);
				};
				workingWithTempFile(leagueConsumer, lines, "team2stats");
			}
			case PLAYER -> {

			}
			case MATCH -> {
				Consumer<FileInputStream> leagueConsumer = (fis) -> {
					this.leagueMatchStats.importFile(fis, LeagueMatchStats.class);
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
			return csvHttpClient.connectToFootystatsAndRetrieveFileContent(sessionCookie, url);
		} catch (IOException e) {
			throw new ServiceException(ServiceException.Type.CSV_FILE_DOWNLOAD_SERVICE_DL_FAILED, e);
		}
	}
}
