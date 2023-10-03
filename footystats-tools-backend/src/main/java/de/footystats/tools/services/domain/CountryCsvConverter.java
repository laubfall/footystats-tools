package de.footystats.tools.services.domain;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.springframework.stereotype.Component;

/**
 * Converts a country name from a CSV file to a Country object.
 */
@Component
public class CountryCsvConverter extends AbstractBeanField<Country, String> {

	private final DomainDataService domainDataService;

	public CountryCsvConverter(DomainDataService domainDataService) {
		this.domainDataService = domainDataService;
	}

	@Override
	protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		try {
			return domainDataService.countryByNormalizedName(value);
		} catch (IllegalArgumentException e) {
			throw new CsvDataTypeMismatchException(e.getMessage());
		}
	}
}
