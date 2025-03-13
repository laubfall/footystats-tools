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

import java.util.Optional;

@Component
public class InitialAttributesPopulator implements RepositoryPopulator, ApplicationListener<ContextRefreshedEvent>, ApplicationEventPublisherAware {

	private ApplicationEventPublisher applicationEventPublisher;

	public void populate(Repositories repositories) {
		Optional<Object> repositoryFor = repositories.getRepositoryFor(BaseBetAttribute.class);

		if (repositoryFor.isEmpty()) {
			return;
		}

		BetAttributeRepository repository = (BetAttributeRepository) repositoryFor.get();
		repository.save(new BetAttribute(Bet.OVER_ZERO_FIVE));
		repository.save(new DoubleAttribute(1.0, Attributes.ODDS));
		repository.save(new DoubleAttribute(2.0, Attributes.ODDS));
		repository.save(new DoubleAttribute(3.0, Attributes.ODDS));
		repository.save(new DoubleAttribute(4.0, Attributes.ODDS));
		repository.save(new DoubleAttribute(5.0, Attributes.ODDS));
		repository.save(new DoubleAttribute(6.0, Attributes.ODDS));
		repository.save(new DoubleAttribute(7.0, Attributes.ODDS));
		repository.save(new DoubleAttribute(8.0, Attributes.ODDS));
		repository.save(new DoubleAttribute(9.0, Attributes.ODDS));
		repository.save(new DoubleAttribute(10.0, Attributes.ODDS));

		applicationEventPublisher.publishEvent(new RepositoriesPopulatedEvent(this, repositories));
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
