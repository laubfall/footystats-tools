package de.footystats.tools.services.domain;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.springframework.stereotype.Component;

@Component
public class CountryCsvConverter extends AbstractBeanField<Country, String> {

	@Override
	protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		return null;
	}
}
