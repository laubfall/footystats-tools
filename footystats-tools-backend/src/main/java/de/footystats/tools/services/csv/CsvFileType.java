package de.footystats.tools.services.csv;

public enum CsvFileType {
    MATCH_STATS,
    TEAM_STATS,
    TEAM_2_STATS,
    LEAGUE_STATS,
    LEAGUE_MATCH_STATS,
	PLAYER_STATS,
	DOWNLOAD_CONFIG // not a footystats csv file type! This is a csv file containing download ids for team-, league- and player-stats.
    ;
}
