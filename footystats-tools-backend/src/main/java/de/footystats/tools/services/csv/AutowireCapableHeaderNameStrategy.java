package de.footystats.tools.services.csv;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.bean.BeanField;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.exceptions.CsvBadConverterException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @param <CSVTYPE>
 */
@Component
public class AutowireCapableHeaderNameStrategy<CSVTYPE> extends HeaderColumnNameMappingStrategy<CSVTYPE> {

	private final ApplicationContext context;

	public AutowireCapableHeaderNameStrategy(ApplicationContext context) {
		super();
		this.context = context;
	}

	@Override
	protected BeanField<CSVTYPE, String> instantiateCustomConverter(Class<? extends AbstractBeanField<CSVTYPE, String>> converter)
		throws CsvBadConverterException {
		final Component springComponent = converter.getAnnotation(Component.class);
		if (springComponent != null) {
			return context.getBean(converter);
		}

		return super.instantiateCustomConverter(converter);
	}
}
