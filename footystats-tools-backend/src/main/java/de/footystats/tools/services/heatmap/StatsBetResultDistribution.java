package de.footystats.tools.services.heatmap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a distribution of successful and failed bets for a specific stat value.
 *
 * @param <V> The type of the stat value that is analyzed.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "statsBetResultDistribution")
@CompoundIndex(name = "key", def = "{'key.bet' : 1, 'key.country': 1, 'key.league': 1}")
@CompoundIndex(name = "unique", def = "{'key.bet' : 1, 'key.country': 1, 'key.league': 1, 'key.season': 1, 'statsName': 1, 'value': 1}", unique = true)
public abstract sealed class StatsBetResultDistribution<V> permits DoubleStatsDistribution, FloatStatsDistribution, IntegerStatsDistribution {

	public static final String DISCRIMINATOR_KEY = "_discriminator";

	// Name of the stat that is analyzed. A stat is typically a property of some stats entity like MatchStats.
	private String statsName;

	// Count of successful predictions for a bet with the same stat value.
	private Long betSucceeded = 0L;

	// Count of failed predictions for a bet with the same stat value.
	private Long betFailed = 0L;

	// The value of the stat that is analyzed. E.g. 1.5 goals
	private V value;

	// The key that identifies the stats entity that is analyzed. E.g. the bet, or country and bet.
	private StatsBetResultDistributionKey key;
}
