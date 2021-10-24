const env = process.env.NODE_ENV || 'development';
// eslint-disable-next-line import/no-dynamic-require
const cfg = require(`./config.${env}.js`);
module.exports = cfg;
