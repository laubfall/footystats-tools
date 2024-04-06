package de.footystats.tools.services.prediction;

/**
 * Enum for the analysis result of a completed match for a specific bet.
 */
public enum PredictionAnalyze {
	// Bet was successful (e.g. home team won and bet was on home team)
	SUCCESS,
	// Actually not used. Idea behind this was to show that the bet was not successful, but it was close to be successful.
	CLOSE,
	// Bet was not successful (e.g. home team won and bet was on away team)
	FAILED,
	// Bet was not completed (e.g. match was canceled)
	NOT_COMPLETED,
	// Bet was not analyzed (e.g. match was not played yet)
	NOT_ANALYZED,
	// Bet was not predicted (e.g. prediction was not calculated)
	NOT_PREDICTED;
}
