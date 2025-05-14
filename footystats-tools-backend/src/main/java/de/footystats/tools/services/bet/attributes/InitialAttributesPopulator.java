package de.footystats.tools.services.bet.attributes;

import de.footystats.tools.services.prediction.Bet;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.repository.init.RepositoriesPopulatedEvent;
import org.springframework.data.repository.init.RepositoryPopulator;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Populates the database with initial attributes if they are not already present.
 * Fires an event after populating the database so that other components can react to it.
 * This is actual the initialization of the attribute series.
 */
@Component
public class InitialAttributesPopulator implements RepositoryPopulator, ApplicationListener<ContextRefreshedEvent>, ApplicationEventPublisherAware {

	private ApplicationEventPublisher applicationEventPublisher;

	public void populate(Repositories repositories) {
		Optional<Object> repositoryFor = repositories.getRepositoryFor(BaseBetAttribute.class);
		if (repositoryFor.isEmpty()) {
			return;
		}

		var attributesWaveOne = List.of(
			new BetAttribute(Bet.OVER_ZERO_FIVE),
			new BetAttribute(Bet.OVER_ONE_FIVE),
			new BetAttribute(Bet.OVER_TWO_FIVE),
			new BetAttribute(Bet.BTTS_YES),
			new BetAttribute(Bet.HOME_WIN),
			new BetAttribute(Bet.AWAY_WIN),
			new DoubleAttribute(1.0, Attributes.ODDS),
			new DoubleAttribute(2.0, Attributes.ODDS),
			new DoubleAttribute(3.0, Attributes.ODDS),
			new DoubleAttribute(4.0, Attributes.ODDS),
			new DoubleAttribute(5.0, Attributes.ODDS),
			new DoubleAttribute(6.0, Attributes.ODDS),
			new DoubleAttribute(7.0, Attributes.ODDS),
			new DoubleAttribute(8.0, Attributes.ODDS),
			new DoubleAttribute(9.0, Attributes.ODDS),
			new DoubleAttribute(10.0, Attributes.ODDS),
			new MatchHalfAttribute(MatchHalfAttribute.MatchHalf.BOTH),
			new MatchHalfAttribute(MatchHalfAttribute.MatchHalf.FIRST_HALF),
			new MatchHalfAttribute(MatchHalfAttribute.MatchHalf.SECOND_HALF)
		);


		BetAttributeRepository repository = (BetAttributeRepository) repositoryFor.get();
		if (needPopulation(repository, attributesWaveOne.size())) {
			repository.saveAll(attributesWaveOne);
			applicationEventPublisher.publishEvent(new RepositoriesPopulatedEvent(this, repositories));
		}
	}

	private boolean needPopulation(BetAttributeRepository repository, long expectedAttributeCount) {
		return repository.count() != expectedAttributeCount;
	}


	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Repositories repositories = new Repositories(event.getApplicationContext());
		populate(repositories);
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
}
