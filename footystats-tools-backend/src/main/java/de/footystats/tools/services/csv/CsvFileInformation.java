package de.footystats.tools.services.csv;

import de.footystats.tools.services.domain.Country;

public record CsvFileInformation(CsvFileType type, Country country) implements ICsvFileInformation {

}
