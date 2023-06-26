package de.footystats.tools.services.csv;

public record CsvFileInformation(CsvFileType type, String country) implements ICsvFileInformation {
}
