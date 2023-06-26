package de.footystats.tools.services.csv;

import static org.apache.commons.lang3.StringUtils.split;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CsvFileService<T> {

	public List<T> 	importFile(InputStream data, Class<T> clazz) {
		try (var isr = new InputStreamReader(data)) {
			return new CsvToBeanBuilder<T>(isr).withType(clazz).build().parse();
		} catch (IOException e) {
			log.error("Failed importing csv file.", e);
			throw new RuntimeException(e);
		}
	}

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

	public static ICsvFileInformation csvFileInformationByFileName(String fileName) {
		if (StringUtils.startsWith(fileName, "matches_expanded")) {
			return new CsvFileInformation(CsvFileType.MATCH_STATS, null);
		}

		if (StringUtils.startsWith(fileName, "download_config")) {
			return new CsvFileInformation(CsvFileType.DOWNLOAD_CONFIG, null);
		}

		var fileNameWithoutExt = fileName.substring(0, fileName.length() - 3);
		var splittedFileName = split(fileNameWithoutExt, '-');
		var length = splittedFileName.length;

		CsvFileType type = csvFileTypeByName(splittedFileName[length - 5]);
		return new CsvFileInformation(type, splittedFileName[0]);
	}
}

