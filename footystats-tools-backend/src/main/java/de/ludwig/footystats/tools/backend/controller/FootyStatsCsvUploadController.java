package de.ludwig.footystats.tools.backend.controller;

import de.ludwig.footystats.tools.backend.services.stats.MatchStats;
import de.ludwig.footystats.tools.backend.services.csv.CsvFileInformation;
import de.ludwig.footystats.tools.backend.services.csv.CsvFileService;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class FootyStatsCsvUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FootyStatsCsvUploadController.class);

    private final CsvFileService<MatchStats> csvFileService;

    private final MatchStatsService matchStatsService;

    public FootyStatsCsvUploadController(CsvFileService<MatchStats> fileStorageService, MatchStatsService matchStatsService) {
        this.csvFileService = fileStorageService;
        this.matchStatsService = matchStatsService;
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


    private void store(MultipartFile file) throws IOException {

        if (file.getOriginalFilename().endsWith(".csv") == false) {
            logger.info(MessageFormat.format("File does not have csv extension: {0}", file.getOriginalFilename()));
            return;
        }

        CsvFileInformation csvFileInformation = CsvFileService.csvFileInformationByFileName(file.getOriginalFilename());

        logger.debug(MessageFormat.format("Try to store csv data from file: {0}", file.getOriginalFilename()));
        switch (csvFileInformation.type()) {
            case LEAGUE_STATS:
                //this.leagueStatsService.readLeagueStats(pathToFile);
                break;
            case MATCH_STATS:
                List<MatchStats> matchStats = this.csvFileService.importFile(file.getInputStream(), MatchStats.class);
                matchStats.forEach(this.matchStatsService::importMatchStats);
                break;
            case TEAM_STATS:
                //this.teamStatsService.readTeamStats(pathToFile);
                break;
            default:
                logger.warn(
                        MessageFormat.format("Don't know how to store csv data for file type {0} of file ${1}", file.getOriginalFilename(), csvFileInformation.type()
                        ));
                break;
        }
    }


}