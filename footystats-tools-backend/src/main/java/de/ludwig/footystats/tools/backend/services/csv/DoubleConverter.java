package de.ludwig.footystats.tools.backend.services.csv;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class DoubleConverter  extends AbstractBeanField<Double, String> {
	@Override
	protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		if("N/A".equals(s)){
			return 0d;
		}

		try{
			return Double.parseDouble(s);
		} catch (NumberFormatException e) {
			throw new CsvConstraintViolationException(e);
		}
	}
}

