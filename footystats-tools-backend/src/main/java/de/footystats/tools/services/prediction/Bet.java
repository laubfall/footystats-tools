package de.footystats.tools.services.prediction;

import java.util.EnumSet;

public enum Bet {
    OVER_ZERO_FIVE,
    OVER_ONE_FIVE,
    BTTS_YES,
    BTTS_NO,
    ;

	/**
	 * Method used to control active bets. Active bets are bets we do predictions
	 * and bet prediction quality for.
	 *
	 * @return s. description.
	 */
	public static EnumSet<Bet> activeBets(){
		return EnumSet.of(OVER_ZERO_FIVE, OVER_ONE_FIVE, BTTS_YES);
	}
}
