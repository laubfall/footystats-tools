package de.footystats.tools.services.footy;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.services.ServiceException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * Base functionality when working with downloaded footystats csv files.
 * <p>
 * Provides the http client and methods to store downloaded csv files to filesystem.
 */
@Slf4j
public abstract class CsvFileDownloadService {

	protected final FootystatsProperties properties;

	protected final CsvHttpClient csvHttpClient;

	protected CsvFileDownloadService(FootystatsProperties properties, CsvHttpClient csvHttpClient) {
		this.properties = properties;
		this.csvHttpClient = csvHttpClient;
	}

	protected void workingWithTempFile(Consumer<FileInputStream> consumer, List<String> rawCsvData, String tmpFilePrefix) {
		File tmpFile = null;
		FileInputStream fis = null;
		try {
			tmpFile = File.createTempFile(tmpFilePrefix, "csv");
			FileUtils.writeLines(tmpFile, StandardCharsets.UTF_8.name(), rawCsvData);
			fis = new FileInputStream(tmpFile);
			consumer.accept(fis);
			log.info("Successfully consumed csv tmp file: " + tmpFile.getAbsoluteFile().getAbsolutePath());
			saveCsvFileIfWanted(tmpFile, tmpFilePrefix);
		} catch (IOException e) {
			log.error("An exception occurred while creating csv tmp file", e);
		} catch (ServiceException e) {
			if (tmpFile != null && tmpFile.exists()) {
				log.error("An exception occurred while working with csv tmp file: " + tmpFile.getAbsolutePath(), e);
			}
			throw e;
		} finally {
			IOUtils.closeQuietly(fis);
			if (tmpFile != null && tmpFile.exists()) {
				FileUtils.deleteQuietly(tmpFile);
			}
		}
	}

	private void saveCsvFileIfWanted(File downloadedCsvFile, String prefix) {
		if (!properties.getCsvFileDownloadProperties().isKeepCsvFiles()) {
			return;
		}

		var path = properties.getCsvFileDownloadProperties().getPathForKeepingCsvFiles();
		var storePath = new File(path);
		if (!storePath.exists()) {
			log.warn("Saving csv file wanted but path does not seem to exist: " + path);
			return;
		}

		try {
			String pointInTimeOfSave = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss"));
			File fileToSave = new File(path, prefix + "_" + pointInTimeOfSave + ".csv");
			byte[] bytes = FileUtils.readFileToByteArray(downloadedCsvFile);
			Files.write(fileToSave.toPath(), bytes);
			log.info("Saved csv file: " + fileToSave.getAbsolutePath());
		} catch (IOException e) {
			log.error("Failed copying temp csv file to wanted destination.", e);
		}
	}
}
