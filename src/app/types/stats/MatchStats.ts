export type MatchStats = {
  date_unix: number;
  date_GMT: string;
  Country: string;
  League: string;
  'Home Team': string;
  'Away Team': string;
  'Home Team Points Per Game (Pre-Match)': number;
  'Away Team Points Per Game (Pre-Match)': number;
  'Home Team Points Per Game (Current)': number;
  'Away Team Points Per Game (Current)': number;
  'Average Goals': number;
  'BTTS Average': number;
  'Over05 Average': number;
  'Over15 Average': number;
  'Over25 Average': number;
  'Over35 Average': number;
  'Over45 Average': number;
  'Over05 FHG HT Average': number;
  'Over15 FHG HT Average': number;
  'Over05 2HG Average': number;
  'Over15 2HG Average': number;
  'Average Corners': number;
  'Average Cards': number;
  'Average Over 8dot5 Corners': number;
  'Average Over 9dot5 Corners': number;
  'Average Over 10dot5 Corners': number;
  'Match Status': MatchStatus;
  'Result - Home Team Goals': number;
  'Result - Away Team Goals': number;
  'Home Team Corners': number;
  'Away Team Corners': number;
  'Home Team Offsides': number;
  'Away Team Offsides': number;
  'Home Team Yellow Cards': number;
  'Away Team Yellow Cards': number;
  'Home Team Red Cards': number;
  'Away Team Red Cards': number;
  'Home Team Shots': number;
  'Away Team Shots': number;
  'Home Team Shots On Target': number;
  'Away Team Shots On Target': number;
  'Home Team Shots Off Target': number;
  'Away Team Shots Off Target': number;
  'Home Team Possession': number;
  'Away Team Possession': number;
  Odds_Home_Win: number;
  Odds_Draw: number;
  Odds_Away_Win: number;
  Odds_Over15: number;
  Odds_Over25: number;
  Odds_Over35: number;
  Odds_Over45: number;
  Odds_BTTS_Yes: number;
  Odds_BTTS_No: number;
  'Home Team Pre-Match xG': number;
  'Away Team Pre-Match xG': number;
  'Home Team xG': number;
  'Away Team xG': number;
  Odds_Under05: number;
  Odds_Under15: number;
  Odds_Under25: number;
  Odds_Under35: number;
  Odds_Under45: number;
  Odds_DoubleChance_1x: number;
  Odds_DoubleChance_12: number;
  Odds_DoubleChance_x2: number;
  Odds_DrawNoBet_1: number;
  Odds_DrawNoBet_2: number;
  Odds_Corners_Over75: number;
  Odds_Corners_Over85: number;
  Odds_Corners_Over95: number;
  Odds_Corners_Over105: number;
  Odds_Corners_Over115: number;
  Odds_Corners_Under75: number;
  Odds_Corners_Under85: number;
  Odds_Corners_Under95: number;
  Odds_Corners_Under105: number;
  Odds_Corners_Under115: number;
  'Under05 Average': number;
  'Under15 Average': number;
  'Under25 Average': number;
  'Under35 Average': number;
  'Under45 Average': number;
  Odds_1st_Half_Over05: number;
  Odds_1st_Half_Over15: number;
  Odds_1st_Half_Over25: number;
  Odds_1st_Half_Under05: number;
  Odds_1st_Half_Under15: number;
  Odds_1st_Half_Under25: number;
  Odds_2nd_Half_Over05: number;
  Odds_2nd_Half_Over15: number;
  Odds_2nd_Half_Over25: number;
  Odds_2nd_Half_Under05: number;
  Odds_2nd_Half_Under15: number;
  Odds_2nd_Half_Under25: number;
  Odds_1st_Half_BTTS_Yes: number;
  Odds_1st_Half_BTTS_No: number;
  Odds_2nd_Half_BTTS_Yes: number;
  Odds_2nd_Half_BTTS_No: number;
  '1H BTTS Average': number;
  Odds_Home_Team_Had_More_Corners: number;
  Odds_Both_Teams_Same_Corners: number;
  Odds_Away_Team_Had_More_Corners: number;
  'Home Team Overall Points Per Game (Pre-Match)': number;
  'Away Team Overall Points Per Game (Pre-Match)': number;
  'Game Week': number;
  'Match FootyStats URL': string;
};

export type MatchStatus = 'complete' | 'incomplete';
