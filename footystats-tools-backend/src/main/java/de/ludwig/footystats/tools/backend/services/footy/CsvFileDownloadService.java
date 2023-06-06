package de.ludwig.footystats.tools.backend.services.footy;

import de.ludwig.footystats.tools.backend.FootystatsProperties;
import de.ludwig.footystats.tools.backend.services.ServiceException;
import de.ludwig.footystats.tools.backend.services.csv.CsvFileService;
import de.ludwig.footystats.tools.backend.services.footy.dls.DownloadConfigService;
import de.ludwig.footystats.tools.backend.services.footy.dls.DownloadCountryLeagueStatsConfig;
import de.ludwig.footystats.tools.backend.services.footy.dls.FileTypeBit;
import de.ludwig.footystats.tools.backend.services.settings.SettingsRepository;
import de.ludwig.footystats.tools.backend.services.stats.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;

@Slf4j
@Service
public class CsvFileDownloadService {

	private final FootystatsProperties properties;

	private final SettingsRepository settingsRepository;

	private final CsvFileService<MatchStats> csvFileService;

	private final CsvFileService<TeamStats> teamStatsCsvFileService;

	private final CsvFileService<Team2Stats> team2StatsCsvFileService;

	private final CsvFileService<LeagueStats> leagueStatsCsvFileService;

	private final MatchStatsService matchStatsService;

	private final DownloadConfigService downloadConfigService;

	private final CsvHttpClient csvHttpClient;

	public CsvFileDownloadService(FootystatsProperties properties, SettingsRepository settingsRepository, CsvFileService<MatchStats> csvFileService, CsvFileService<TeamStats> teamStatsCsvFileService, CsvFileService<Team2Stats> team2StatsCsvFileService, CsvFileService<LeagueStats> leagueStatsCsvFileService, MatchStatsService matchStatsService, DownloadConfigService downloadConfigService,
		CsvHttpClient csvHttpClient) {
		this.properties = properties;
		this.settingsRepository = settingsRepository;
		this.csvFileService = csvFileService;
		this.teamStatsCsvFileService = teamStatsCsvFileService;
		this.team2StatsCsvFileService = team2StatsCsvFileService;
		this.leagueStatsCsvFileService = leagueStatsCsvFileService;
		this.matchStatsService = matchStatsService;
		this.downloadConfigService = downloadConfigService;
		this.csvHttpClient = csvHttpClient;
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

		if(!olderConfigs.isEmpty()){
			log.info("Start downloading configured stats csf files for older seasons.");
			doDownloadingByConfig(olderConfigs, login);
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
				log.info("Start downloading configured stats of type " + fileTypeBit.getBit() + " country: " + downloadCfg.getCountry() + " league: " + downloadCfg.getLeague() + " season: " + downloadCfg.getSeason());
				String dlResource = fileTypeBitToFootystatsRessource(fileTypeBit);
				List<String> csvLines = downloadConfiguredStats(login, dlResource, downloadCfg.getFootyStatsDlId());
				importConfiguredStats(downloadCfg, csvLines, fileTypeBit);
			}
		}
	}

	public void downloadMatchStatsCsvFileAndImport() {
		var rawMatches = downloadMatchStatsCsvFile(LocalDate.now());

		workingWithTempFile(fileStream -> {
			List<MatchStats> matchStats = csvFileService.importFile(fileStream, MatchStats.class);
			log.info("MatchStatc csv ile contains " + matchStats.size() + " matches.");
			matchStats.forEach(matchStatsService::importMatchStats);
		}, rawMatches, "matchStats");
	}

	List<String> downloadMatchStatsCsvFile(LocalDate matchStatsForDay) {
		var sessionCookie = csvHttpClient.login();
		return downloadMatchStatsCsvFile(matchStatsForDay, sessionCookie);
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
				/*
				Consumer<FileInputStream> leagueConsumer = (fis) -> {
					this.csvFileService.importFile(fis, MatchStats.class);
				};
				workingWithTempFile(leagueConsumer, lines, "matchStats");
				*/
			}
		}
	}

	private void workingWithTempFile(Consumer<FileInputStream> consumer, List<String> rawCsvData, String tmpFilePrefix) {
		File tmpFile = null;
		FileInputStream fis = null;
		try {
			tmpFile = File.createTempFile(tmpFilePrefix, "csv");
			FileUtils.writeLines(tmpFile, rawCsvData);
			fis = new FileInputStream(tmpFile);
			saveCsvFileIfWanted(tmpFile, tmpFilePrefix);
			consumer.accept(fis);
		} catch (IOException e) {
			log.error("An exception occured while processing csv tmp file", e);
		} finally {
			IOUtils.closeQuietly(fis);
			if (tmpFile != null && tmpFile.exists()) {
				FileUtils.deleteQuietly(tmpFile);
			}
		}
	}

	private void saveCsvFileIfWanted(File downloadedCsvFile, String prefix) {
		if (!properties.getCsvFileDownloadProperties().isKeepCsvFiles()){
			return;
		}

		var path = properties.getCsvFileDownloadProperties().getPathForKeepingCsvFiles();
		var storePath = new File(path);
		if(!storePath.exists()){
			log.warn("Saving csv file wanted but path does not seem to exist: " + path);
			return;
		}

		try {
			String pointInTimeOfSave = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss"));
			File fileToSave = new File(path, prefix + "_" + pointInTimeOfSave + ".csv");
			FileUtils.copyFile(downloadedCsvFile, fileToSave);
			log.info("Saved csv file: " + fileToSave.getAbsolutePath());
		} catch (IOException e) {
			log.error("Failed copying temp csv file to wanted destination.", e);
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

	private List<String> downloadMatchStatsCsvFile(LocalDate matchStatsForDay, SessionCookie sessionCookie) {
		try {
			final URL url = new URL(
				properties.getWebpage().getBaseUrl() + properties.getWebpage().getMatchStatsDownloadRessource()
					+ matchStatsForDay.toEpochSecond(LocalTime.now(), ZoneId.of("Europe/Berlin").getRules().getOffset(LocalDateTime.now())));
			return csvHttpClient.connectToFootystatsAndRetrieveFileContent(sessionCookie, url);
		} catch (IOException e) {
			throw new ServiceException(ServiceException.Type.CSV_FILE_DOWNLOAD_SERVICE_DL_FAILED, e);
		}
	}
}
