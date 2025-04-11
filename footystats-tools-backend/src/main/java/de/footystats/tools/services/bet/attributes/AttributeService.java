package de.footystats.tools.services.bet.attributes;

import de.footystats.tools.services.prediction.Bet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.RepositoriesPopulatedEvent;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates the attribute series and provides methods to access them.
 */
@Slf4j
@Service
public class AttributeService implements ApplicationListener<RepositoriesPopulatedEvent> {

	private final Map<Bet, List<AttributeSeries>> attributeSeriesMap = new HashMap<>();
	private final BetAttributeRepository attributeRepository;
	@Value("classpath:data/initialAttributeSeries.csv")
	private Resource initialAttributeSeriesResource;

	public AttributeService(BetAttributeRepository attributeRepository) {
		this.attributeRepository = attributeRepository;
	}

	public List<AttributeSeries> by(Bet bet) {
		return attributeSeriesMap.get(bet);
	}

	public AttributeSeries by(List<ObjectId> attributeIds) {
		List<List<AttributeSeries>> matchingBySize = attributeSeriesMap.values().stream().filter(
			attrs -> attrs.size() == attributeIds.size()).toList();

		for (List<AttributeSeries> attributeSeries : matchingBySize) {
			for (AttributeSeries series : attributeSeries) {
				if (CollectionUtils.isEqualCollection(series.computeAttributeIds(), attributeIds)) {
					return series;
				}
			}
		}

		return null;
	}

	@Override
	public void onApplicationEvent(RepositoriesPopulatedEvent event) {
		if (!(event.getSource() instanceof InitialAttributesPopulator)) {
			return;
		}
		List<AttributeSeries> attributeSeries = loadConfiguredSeries();
		for (AttributeSeries series : attributeSeries) {
			addSeriesToInMemoryMap(series.findBetAttribute(), series);
		}
	}

	private void addSeriesToInMemoryMap(BetAttribute bet, AttributeSeries series) {
		attributeSeriesMap.computeIfAbsent(bet.value, k -> new ArrayList<>()).add(series);
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

				result.add(new AttributeSeries(attributes));
			}

		} catch (IOException e) {
			log.error("Failed to load initial attribute series from csv file", e);
			throw new RuntimeException(e);
		}

		return result;
	}
}
