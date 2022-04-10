const env = process.env.NODE_ENV || 'development';

// eslint-disable-next-line import/no-dynamic-require
const cfg = require(`./config.${env}.js`);

/**
 * Application configuration that is not modifiable by the user.
 */
export interface Config {
  matchStatsDbFileName: string;
  teamStatsDbFileName: string;
  leagueStatsDbFileName: string;
  window: {
    width: number;
    height: number;
  };
  markCsvFilesAsImported: boolean;
  importEsportMatches: boolean;
  nedb: {
    inMemoryOnly: boolean;
  };
}

export default cfg as Config;
