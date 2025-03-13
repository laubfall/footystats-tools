package de.footystats.tools.services.bet.attributes;

import de.footystats.tools.services.prediction.Bet;
import org.springframework.context.ApplicationListener;
import org.springframework.data.repository.init.RepositoriesPopulatedEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttributeService implements ApplicationListener<RepositoriesPopulatedEvent> {
	private final Map<Bet, List<AttributeSeries>> attributeSeriesMap = new HashMap<>();

	private final BetAttributeRepository attributeRepository;

	public AttributeService(BetAttributeRepository attributeRepository) {
		this.attributeRepository = attributeRepository;
	}

	public List<AttributeSeries> by(Bet bet) {
		return attributeSeriesMap.get(bet);
	}

	private void addSeriesToInMemoryMap(BetAttribute bet, List<BaseBetAttribute<?>> attributeSeries) {
		var result = new ArrayList<>(attributeSeries);
		result.add(bet);
		attributeSeriesMap.computeIfAbsent(bet.value, k -> new ArrayList<>()).add(new AttributeSeries(result));
	}

	@Override
	public void onApplicationEvent(RepositoriesPopulatedEvent event) {
		BaseBetAttribute<?> byValueAndName = attributeRepository.findByValueAndName(1.0, Attributes.ODDS);
		BetAttribute betAttribute = attributeRepository.findByValueAndName(Bet.OVER_ZERO_FIVE, Attributes.BET_ATTRIBUTE,
			BetAttribute.class);
		addSeriesToInMemoryMap(betAttribute, List.of(byValueAndName));


	}
}
