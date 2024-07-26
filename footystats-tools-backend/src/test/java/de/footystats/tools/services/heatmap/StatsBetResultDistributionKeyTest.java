package de.footystats.tools.services.heatmap;

import de.footystats.tools.services.domain.Country;
import de.footystats.tools.services.domain.DomainDataService;
import de.footystats.tools.services.domain.Season;
import de.footystats.tools.services.prediction.Bet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = {DomainDataService.class})
class StatsBetResultDistributionKeyTest {

	@Autowired
	private DomainDataService domainDataService;

	@Test
	void broader_season() {
		Country germany = domainDataService.countryByName("germany");
		var statsBetResultDistributionKey = new StatsBetResultDistributionKey.StatsBetResultDistributionKeyBuilder().bet(Bet.OVER_ZERO_FIVE)
			.country(germany)
			.league("Bundesliga")
			.season(new Season("2020/2021"))
			.build();

		Assertions.assertNotNull(statsBetResultDistributionKey.getSeason());
		Assertions.assertNotNull(statsBetResultDistributionKey.getLeague());
		Assertions.assertNotNull(statsBetResultDistributionKey.getCountry());

		var broader = statsBetResultDistributionKey.broader();
		Assertions.assertNull(broader.getSeason());
		Assertions.assertNotNull(broader.getLeague());
		Assertions.assertNotNull(broader.getCountry());
	}

	@Test
	void broader_league() {
		Country germany = domainDataService.countryByName("germany");
		var statsBetResultDistributionKey = new StatsBetResultDistributionKey.StatsBetResultDistributionKeyBuilder().bet(Bet.OVER_ZERO_FIVE)
			.country(germany)
			.league("Bundesliga")
			.build();

		Assertions.assertNull(statsBetResultDistributionKey.getSeason());
		Assertions.assertNotNull(statsBetResultDistributionKey.getLeague());
		Assertions.assertNotNull(statsBetResultDistributionKey.getCountry());

		var broader = statsBetResultDistributionKey.broader();
		Assertions.assertNull(broader.getSeason());
		Assertions.assertNull(broader.getLeague());
		Assertions.assertNotNull(broader.getCountry());
	}

	@Test
	void broader_country() {
		Country germany = domainDataService.countryByName("germany");
		var statsBetResultDistributionKey = new StatsBetResultDistributionKey.StatsBetResultDistributionKeyBuilder().bet(Bet.OVER_ZERO_FIVE)
			.country(germany)
			.build();

		Assertions.assertNull(statsBetResultDistributionKey.getSeason());
		Assertions.assertNull(statsBetResultDistributionKey.getLeague());
		Assertions.assertNotNull(statsBetResultDistributionKey.getCountry());

		var broader = statsBetResultDistributionKey.broader();
		Assertions.assertNull(broader.getSeason());
		Assertions.assertNull(broader.getLeague());
		Assertions.assertNull(broader.getCountry());
	}
}
