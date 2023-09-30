package de.footystats.tools.services.domain;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@ReadingConverter
@Component
public class CountryReadingConverter implements Converter<String, Country> {

	private final DomainDataService domainDataService;

	public CountryReadingConverter(DomainDataService domainDataService) {
		this.domainDataService = domainDataService;
	}

	@Override
	public Country convert(String source) {
		if (StringUtils.isBlank(source)) {
			return null;
		}

		return domainDataService.countryByNormalizedName(source);
	}
}
