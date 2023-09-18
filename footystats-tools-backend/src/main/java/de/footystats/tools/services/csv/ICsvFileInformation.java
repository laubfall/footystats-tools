package de.footystats.tools.services.csv;

import de.footystats.tools.services.domain.Country;

public interface ICsvFileInformation {

	CsvFileType type();

	Country country();
}
