const cfgBase = require('./config.js');

// eslint-disable-next-line no-multi-assign
const config = (module.exports = { ...cfgBase });
config.markCsvFilesAsImported = false;
config.nedb = {
  inMemoryOnly: true,
};
