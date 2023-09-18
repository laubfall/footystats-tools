package de.footystats.tools.services.csv;

import static org.apache.commons.lang3.StringUtils.split;

import com.opencsv.bean.CsvToBeanBuilder;
import de.footystats.tools.services.domain.Country;
import de.footystats.tools.services.domain.DomainDataService;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Service for importing csv files.
 */
@Slf4j
@Service
public class CsvFileService<T> {

	public static final int FILE_EXT_RS_OFFSET = 3;
	public static final int FILE_SEASON_RS_OFFSET = 5;
	public static final int FILE_LEAGUE_RS_OFFSET = 6;
	public static final char SEPARATOR_CHAR = '-';
	private final DomainDataService domainDataService;

	public CsvFileService(DomainDataService domainDataService) {
		this.domainDataService = domainDataService;
	}

	/**
	 * Returns the CsvFileType for the given name.
	 *
	 * @param name the name of the CsvFileType.
	 * @return the CsvFileType for the given name.
	 */
	private static CsvFileType csvFileTypeByName(String name) {
		return switch (name) {
			case "league" -> CsvFileType.LEAGUE_STATS;
			case "teams" -> CsvFileType.TEAM_STATS;
			case "teams2" -> CsvFileType.TEAM_2_STATS;
			case "matches" -> CsvFileType.LEAGUE_MATCH_STATS;
			case "players" -> CsvFileType.PLAYER_STATS;
			case "download_config" -> CsvFileType.DOWNLOAD_CONFIG;
			default -> null;
		};
	}

	/**
	 * Returns the CsvFileInformation for the given fileName.
	 *
	 * @param fileName the name of the file as downloaded from footystats.org.
	 * @return the CsvFileInformation for the given fileName.
	 */
	public ICsvFileInformation csvFileInformationByFileName(String fileName) {
		if (StringUtils.startsWith(fileName, "matches_expanded")) {
			return new CsvFileInformation(CsvFileType.MATCH_STATS, null);
		}

		if (StringUtils.startsWith(fileName, "download_config")) {
			return new CsvFileInformation(CsvFileType.DOWNLOAD_CONFIG, null);
		}

		var fileNameWithoutExt = fileName.substring(0, fileName.length() - FILE_EXT_RS_OFFSET);
		var splittedFileName = split(fileNameWithoutExt, SEPARATOR_CHAR);
		var length = splittedFileName.length;

		CsvFileType type = csvFileTypeByName(splittedFileName[length - FILE_SEASON_RS_OFFSET]);

		return new CsvFileInformation(type, countryBySplit(splittedFileName));
	}

	private Country countryBySplit(String[] splittedFileName) {

		// Maybe that's the league name if it consists of one word
		var possibleLeagueOffset = splittedFileName.length - FILE_LEAGUE_RS_OFFSET;

		// So start from zero to possibleLeagueOffset and check if the name is a country name
		var currentIdx = 0;
		String countryName = splittedFileName[currentIdx];
		do {
			try {
				return domainDataService.countryByName(countryName);
			} catch (IllegalArgumentException e) {
				currentIdx++;
				countryName += SEPARATOR_CHAR + splittedFileName[currentIdx];
			}
		} while (currentIdx <= possibleLeagueOffset);

		return null;
	}

	public List<T> importFile(InputStream data, Class<T> clazz) {
		try (var isr = new InputStreamReader(data, StandardCharsets.UTF_8)) {
			return new CsvToBeanBuilder<T>(isr).withType(clazz).build().parse();
		} catch (IOException e) {
			log.error("Failed importing csv file.", e);
			throw new RuntimeException(e);
		}
	}
}

