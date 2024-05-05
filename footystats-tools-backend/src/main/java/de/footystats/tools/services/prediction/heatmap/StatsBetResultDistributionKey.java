package de.footystats.tools.services.prediction.heatmap;

import de.footystats.tools.services.domain.Country;
import de.footystats.tools.services.domain.Season;
import de.footystats.tools.services.prediction.Bet;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * Key that identifies a {@link StatsBetResultDistribution} entity.
 * <p>
 * The key is composed of a {@link Bet}, a {@link Country}, a league and a {@link Season}. Bet is always required, the other fields are optional. This
 * way it is possible to create "broader" keys that group stats entities on different levels.
 */
@Getter
public class StatsBetResultDistributionKey {

	private Bet bet;

	private Country country;

	private String league;

	private Season season;

	private StatsBetResultDistributionKey() {
	}

	public StatsBetResultDistributionKey broader() {
		if (season != null && league != null && country != null) {
			return new StatsBetResultDistributionKeyBuilder()
				.bet(bet)
				.country(country)
				.league(league)
				.season(null)
				.build();
		}

		if (league != null && country != null) {
			return new StatsBetResultDistributionKeyBuilder()
				.bet(bet)
				.country(country)
				.league(null)
				.season(null)
				.build();
		}

		if (country != null) {
			return new StatsBetResultDistributionKeyBuilder()
				.bet(bet)
				.country(null)
				.league(null)
				.season(null)
				.build();
		}

		return this;
	}

	static class StatsBetResultDistributionKeyBuilder {

		private Bet bet;

		private Country country;

		private String league;

		private Season season;

		StatsBetResultDistributionKeyBuilder bet(Bet bet) {
			this.bet = bet;
			return this;
		}

		StatsBetResultDistributionKeyBuilder country(Country country) {
			this.country = country;
			return this;
		}

		StatsBetResultDistributionKeyBuilder league(String league) {
			this.league = league;
			return this;
		}

		StatsBetResultDistributionKeyBuilder season(Season season) {
			this.season = season;
			return this;
		}

		StatsBetResultDistributionKey build() {
			StatsBetResultDistributionKey key = new StatsBetResultDistributionKey();
			key.bet = this.bet;
			key.country = this.country;
			key.league = this.league;
			key.season = this.season;

			if (key.bet == null) {
				throw new IllegalArgumentException("Bet must not be null");
			}

			if (StringUtils.isNotBlank(key.league) && key.country == null) {
				throw new IllegalArgumentException("Country must not be null if league is not null");
			}

			return key;
		}
	}
}
