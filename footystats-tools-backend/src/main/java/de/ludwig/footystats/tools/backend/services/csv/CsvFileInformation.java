package de.ludwig.footystats.tools.backend.services.csv;

public record CsvFileInformation(CsvFileType type, String country) implements ICsvFileInformation {
}
