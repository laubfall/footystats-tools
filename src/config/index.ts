const env = process.env.NODE_ENV || 'development';

// eslint-disable-next-line import/no-dynamic-require
const cfg = require(`./config.${env}.js`);

export interface Config {
  db: string;
}

export default cfg as Config;
