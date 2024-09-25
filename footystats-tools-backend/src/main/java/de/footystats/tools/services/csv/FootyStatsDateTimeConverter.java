package de.footystats.tools.services.csv;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * The opencsv CsvDate Annotation did not work for date in september. Probably because of the used locale. This class is a workaround for this issue.
 * More information can be found here:
 * https://stackoverflow.com/questions/69267710/septembers-short-form-sep-no-longer-parses-in-java-17-in-en-gb-locale
 */
public class FootyStatsDateTimeConverter extends AbstractBeanField<LocalDateTime, String> {

	@Override
	protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		try {
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy - h:mma", Locale.ENGLISH);
			var rawDate = s;
			rawDate = rawDate.replace("am", "AM").replace("pm", "PM");

			return LocalDateTime.parse(rawDate, dateTimeFormatter);
		} catch (Exception e) {
			throw new CsvDataTypeMismatchException(e.getMessage());
		}
	}
}
