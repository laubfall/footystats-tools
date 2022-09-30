export type LeagueStats = {
	name: string;
	season: string;
	status: string;
	format: string;
	number_of_clubs: number;
	total_matches: number;
	matches_completed: number;
	game_week: number;
	total_game_week: number;
	progress: number;
	average_goals_per_match: number;
	average_scored_home_team: number;
	average_scored_away_team: number;
	btts_percentage: number;
	clean_sheets_percentage: number;
	prediction_risk: number;
	home_scored_advantage_percentage: number;
	home_defence_advantage_percentage: number;
	home_advantage_percentage: number;
	average_corners_per_match: number;
	average_corners_per_match_home_team: number;
	average_corners_per_match_away_team: number;
	total_corners_for_season: number;
	average_cards_per_match: number;
	average_cards_per_match_home_team: number;
	average_cards_per_match_away_team: number;
	total_cards_for_season: number;
	over_05_percentage: number;
	over_15_percentage: number;
	over_25_percentage: number;
	over_35_percentage: number;
	over_45_percentage: number;
	over_55_percentage: number;
	under_05_percentage: number;
	under_15_percentage: number;
	under_25_percentage: number;
	under_35_percentage: number;
	under_45_percentage: number;
	under_55_percentage: number;
	over_65_corners_percentage: number;
	over_75_corners_percentage: number;
	over_85_corners_percentage: number;
	over_95_corners_percentage: number;
	over_105_corners_percentage: number;
	over_115_corners_percentage: number;
	over_125_corners_percentage: number;
	over_135_corners_percentage: number;
	over_05_cards_percentage: number;
	over_15_cards_percentage: number;
	over_25_cards_percentage: number;
	over_35_cards_percentage: number;
	over_45_cards_percentage: number;
	over_55_cards_percentage: number;
	over_65_cards_percentage: number;
	over_75_cards_percentage: number;
	goals_min_0_to_10: number;
	goals_min_11_to_20: number;
	goals_min_21_to_30: number;
	goals_min_31_to_40: number;
	goals_min_41_to_50: number;
	goals_min_51_to_60: number;
	goals_min_61_to_70: number;
	goals_min_71_to_80: number;
	goals_min_81_to_90: number;
	goals_min_0_to_15: number;
	goals_min_16_to_30: number;
	goals_min_31_to_45: number;
	goals_min_46_to_60: number;
	goals_min_61_to_75: number;
	goals_min_76_to_90: number;
	xg_avg_per_match: number;
};

export default LeagueStats;
