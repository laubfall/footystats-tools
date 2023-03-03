package de.ludwig.footystats.tools.backend.controller;

import de.ludwig.footystats.tools.backend.services.csv.CsvFileInformation;
import de.ludwig.footystats.tools.backend.services.csv.CsvFileService;
import de.ludwig.footystats.tools.backend.services.footy.CsvFileDownloadService;
import de.ludwig.footystats.tools.backend.services.footy.dls.DownloadConfigService;
import de.ludwig.footystats.tools.backend.services.stats.LeagueStatsService;
import de.ludwig.footystats.tools.backend.services.stats.MatchStats;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatsService;
import de.ludwig.footystats.tools.backend.services.stats.TeamStatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FootyStatsCsvUploadController {

	private static final Logger logger = LoggerFactory.getLogger(FootyStatsCsvUploadController.class);

	private final CsvFileService<MatchStats> csvFileService;
	private final CsvFileDownloadService matchStatsFileDownloadService;
	private final MatchStatsService matchStatsService;
	private final LeagueStatsService leagueStatsService;
	private final TeamStatsService teamStatsService;
	private final DownloadConfigService downloadConfigService;

	public FootyStatsCsvUploadController(CsvFileService<MatchStats> fileStorageService,
										 CsvFileDownloadService matchStatsFileDownloadService, MatchStatsService matchStatsService, LeagueStatsService leagueStatsService, TeamStatsService teamStatsService, DownloadConfigService downloadConfigService) {
		this.csvFileService = fileStorageService;
		this.matchStatsFileDownloadService = matchStatsFileDownloadService;
		this.matchStatsService = matchStatsService;
		this.leagueStatsService = leagueStatsService;
		this.teamStatsService = teamStatsService;
		this.downloadConfigService = downloadConfigService;
	}

	@PostMapping(path = "/uploadFile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public UploadFileResponse uploadFile(@RequestPart MultipartFile file) {
		try {
			store(file);
		} catch (IOException e) {
			logger.error("Failed storing csv data", e);
			throw new RuntimeException(e);
		}

		return new UploadFileResponse(file.getName(),
			file.getContentType(), file.getSize());
	}

	@PostMapping(path = "/uploadMultipleFiles", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public List<UploadFileResponse> uploadMultipleFiles(@RequestPart MultipartFile[] files) {
		return Arrays.asList(files)
			.stream()
			.map(file -> uploadFile(file))
			.collect(Collectors.toList());
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@GetMapping(value = "/loadMatchesOfTheDayFromFooty")
	public void loadMatchesOfTheDayFromFooty() {
		matchStatsFileDownloadService.downloadMatchStatsCsvFileAndImport();
	}

	private void store(MultipartFile file) throws IOException {

		if (file.getOriginalFilename().endsWith(".csv") == false) {
			logger.info(MessageFormat.format("File does not have csv extension: {0}", file.getOriginalFilename()));
			return;
		}

		CsvFileInformation csvFileInformation = CsvFileService.csvFileInformationByFileName(file.getOriginalFilename());

		logger.debug(MessageFormat.format("Try to store csv data from file: {0}", file.getOriginalFilename()));
		switch (csvFileInformation.type()) {
			case LEAGUE_STATS:
				this.leagueStatsService.readLeagueStats(file.getInputStream());
				break;
			case MATCH_STATS:
				List<MatchStats> matchStats = this.csvFileService.importFile(file.getInputStream(), MatchStats.class);
				matchStats.forEach(this.matchStatsService::importMatchStats);
				break;
			case TEAM_STATS:
				this.teamStatsService.readTeamStats(file.getInputStream());
				break;
			case DOWNLOAD_CONFIG:
				this.downloadConfigService.readDownloadConfigs(file.getInputStream());
				break;
			default:
				logger.warn(
					MessageFormat.format("Don't know how to store csv data for file type {0} of file ${1}",
						file.getOriginalFilename(), csvFileInformation.type()));
				break;
		}
	}

}
