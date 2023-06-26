package de.footystats.tools.services.csv;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class FloatConverter extends AbstractBeanField<Float, String> {
    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        if("N/A".equals(s)){
            return 0F;
        }

        try{
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            throw new CsvConstraintViolationException(e);
        }
    }
}
