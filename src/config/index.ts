const env = process.env.NODE_ENV || 'development';

// eslint-disable-next-line import/no-dynamic-require
const cfg = require(`./config.${env}.js`);

/**
 * Application configuration that is not modifiable by the user.
 */
export interface Config {
  matchDbFileName: string;
  matchStatsDbFileName: string;
  teamStatsDbFileName: string;
  leagueStatsDbFileName: string;
  predictionQualityDbFileName: string;
  window: {
    width: number;
    height: number;
  };
  markCsvFilesAsImported: boolean;
  importEsportMatches: boolean;
  nedb: {
    inMemoryOnly: boolean;
  };
  matchView: {
    alwaysCalculatePredictions: boolean;
  };
  stats: {
    ignoreTeamStatsWithGamesPlayedLowerThen: number; // TeamStats will be ignored for prediction calculation if they represent fewer number of played games then configured.
  };
}

export default cfg as Config;
