package de.footystats.tools.services.bet.attributes;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import java.util.List;
import java.util.Optional;

@Aspect
@Slf4j
public class AttributeSeriesRepositoryAspect {

	private final BetAttributeRepository betAttributeRepository;

	public AttributeSeriesRepositoryAspect(BetAttributeRepository betAttributeRepository) {
		this.betAttributeRepository = betAttributeRepository;
	}

	@AfterReturning(
		pointcut = "execution(* de.footystats.tools.services.bet.attributes.AttributeSeriesRepository.*(..))",
		returning = "result"
	)
	public void enrichAttributeSeries(Object result) {
		if (result instanceof AttributeSeries series) {
			enrichAttributes(series);
		} else if (result instanceof List<?> list) {
			list.stream()
				.filter(AttributeSeries.class::isInstance)
				.map(AttributeSeries.class::cast)
				.forEach(this::enrichAttributes);
		}
	}

	private void enrichAttributes(AttributeSeries series) {
		if (series.getAttributes() == null || series.getAttributes().isEmpty()) {
			var attributes = series.getAttributeIds().stream()
				.map(betAttributeRepository::findById)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.toList();
			series.setAttributes(attributes);
		}
	}
}
