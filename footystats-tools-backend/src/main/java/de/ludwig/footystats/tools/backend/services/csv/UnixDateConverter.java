package de.ludwig.footystats.tools.backend.services.csv;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import java.util.Date;

public class UnixDateConverter extends AbstractBeanField<Date, String> {
    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        try {
            var rawUnixDate = Long.parseLong(s);
            return new Date(rawUnixDate);
        } catch (Exception e) {
            throw new CsvDataTypeMismatchException(e.getMessage());
        }
    }
}
