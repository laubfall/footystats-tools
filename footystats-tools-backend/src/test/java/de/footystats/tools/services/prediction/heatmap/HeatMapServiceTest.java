package de.footystats.tools.services.prediction.heatmap;

import de.footystats.tools.services.stats.MatchStats;
import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureDataMongo
class HeatMapServiceTest {

	@Autowired
	private HeatMapService heatMapService;

	@Test
	void heatMappedMatchStatsProperties() {
		var matchStats = new MatchStats();
		Collection<StatsBetResultDistribution<?>> statsBetResultDistributions = heatMapService.heatMapRelevant(matchStats);
		Assertions.assertNotNull(statsBetResultDistributions);
		Assertions.assertFalse(statsBetResultDistributions.isEmpty());

		Optional<StatsBetResultDistribution<?>> bttsAverage = statsBetResultDistributions.stream().filter(s -> s.getStatsName().equals("bttsAverage"))
			.findFirst();
		Assertions.assertTrue(bttsAverage.isPresent());
	}
}
