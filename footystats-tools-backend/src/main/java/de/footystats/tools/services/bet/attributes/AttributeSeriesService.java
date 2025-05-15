package de.footystats.tools.services.bet.attributes;

import de.footystats.tools.services.prediction.Bet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.RepositoriesPopulatedEvent;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates the attribute series and provides methods to access them.
 */
@Slf4j
@Service
public class AttributeSeriesService implements ApplicationListener<RepositoriesPopulatedEvent> {

	private final BetAttributeRepository attributeRepository;

	private final AttributeSeriesRepository attributeSeriesRepository;

	@Value("classpath:data/initialAttributeSeries.csv")
	private Resource initialAttributeSeriesResource;

	public AttributeSeriesService(BetAttributeRepository attributeRepository, AttributeSeriesRepository attributeSeriesRepository) {
		this.attributeRepository = attributeRepository;
		this.attributeSeriesRepository = attributeSeriesRepository;
	}

	public List<AttributeSeries> by(Bet bet) {
		var betAttribute = attributeRepository.findByValueAndName(bet, Attributes.BET_ATTRIBUTE);
		if (betAttribute == null) {
			return List.of();
		}
		return attributeSeriesRepository.findByAttributeIdsContains(betAttribute.getId());
	}

	public AttributeSeries byChosenValues(List<ChosenAttributeValue> chosenAttributeValues) {
		if (chosenAttributeValues.isEmpty()) {
			return null;
		}

		var attributeIds = chosenAttributeValues.stream().map(ChosenAttributeValue::getAttributeId).toList();
		return by(attributeIds);
	}

	public AttributeSeries by(List<ObjectId> attributeIds) {
		return attributeSeriesRepository.searchByAttributeIds(attributeIds);
	}

	/**
	 * Returns the attribute series that are subsequent to the given series.
	 * For example, if the given series is o05, odds 1.5, first half, the subsequent series
	 * would be all o05 series with or without of one the attributes of the given series.
	 * Series with other attributes are not considered.
	 *
	 * @param series the series to check for subsequent series.
	 * @return the list of subsequent series.
	 */
	public List<AttributeSeries> subsequent(AttributeSeries series) {
		return null;
	}

	@Override
	public void onApplicationEvent(RepositoriesPopulatedEvent event) {
		if (!(event.getSource() instanceof InitialAttributesPopulator)) {
			return;
		}
		List<AttributeSeries> attributeSeries = loadConfiguredSeries();
		for (AttributeSeries series : attributeSeries) {
			var persistedSeries = attributeSeriesRepository.searchByAttributeIds(series.getAttributeIds());
			if (persistedSeries == null) {
				attributeSeriesRepository.insert(series);
			}
		}
	}

	private List<AttributeSeries> loadConfiguredSeries() {
		var result = new ArrayList<AttributeSeries>();
		try {
			List<String> csvLines = IOUtils.readLines(initialAttributeSeriesResource.getInputStream(), "UTF-8");
			for (String line : csvLines) {
				var uniqueAttrNames = line.split(",");
				List<BaseBetAttribute<?>> attributes = new ArrayList<>(uniqueAttrNames.length);
				for (String uniqueAttrName : uniqueAttrNames) {
					BaseBetAttribute<?> attr = attributeRepository.findByUniqueName(uniqueAttrName);
					if (attr == null) {
						log.error("Failed to find attribute with unique name: {}", uniqueAttrName);
						throw new RuntimeException("Failed to find attribute with unique name: " + uniqueAttrName);
					}
					attributes.add(attr);
				}

				var series = new AttributeSeries();
				series.setAttributes(attributes);
				result.add(series);
			}

		} catch (IOException e) {
			log.error("Failed to load initial attribute series from csv file", e);
			throw new RuntimeException(e);
		}

		return result;
	}
}
