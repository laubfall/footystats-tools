package de.ludwig.footystats.tools.backend.services.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.split;

@Service
public class CsvFileService<T> {
	public List<T> importFile(InputStream data, Class<T> clazz) {
		try (var isr = new InputStreamReader(data)) {
			return new CsvToBeanBuilder<T>(isr).withType(clazz).build().parse();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static CsvFileType csvFileTypeByName(String name) {
		switch (name) {
			case "matches":
				return CsvFileType.MATCH_STATS;
			case "league":
				return CsvFileType.LEAGUE_STATS;
			case "teams":
				return CsvFileType.TEAM_STATS;
			case "teams2":
				return CsvFileType.TEAM_2_STATS;
			case "matches_expanded":
				return CsvFileType.LEAGUE_MATCH_STATS;
			case "download_onfig":
				return CsvFileType.DOWNLOAD_CONFIG;
			default:
				return null;
		}
	}

	public static CsvFileInformation csvFileInformationByFileName(String fileName) {
		if (StringUtils.startsWith(fileName, "matches_expanded")) {
			return new CsvFileInformation(CsvFileType.MATCH_STATS, null);
		}

		if (StringUtils.startsWith(fileName, "download_config")) {
			return new CsvFileInformation(CsvFileType.DOWNLOAD_CONFIG, null);
		}

		var fileNameWithoutExt = fileName.substring(0, fileName.length() - 3);
		var splittedFileName = split(fileNameWithoutExt, '-');
		var length = splittedFileName.length;

		CsvFileInformation cfi = new CsvFileInformation(csvFileTypeByName(splittedFileName[length - 5]),
				splittedFileName[0]);
		return cfi;
	}
}
