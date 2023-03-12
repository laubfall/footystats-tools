package de.ludwig.footystats.tools.backend.services.csv;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class IntegerConverter extends AbstractBeanField<Integer, String> {
	@Override
	protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		if("N/A".equals(s)){
			return null;
		}

		try{
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			throw new CsvConstraintViolationException(e);
		}
	}
}
