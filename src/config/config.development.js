const { assign } = require('lodash');
const cfgBase = require('./config.js');

// eslint-disable-next-line no-multi-assign
const config = (module.exports = {});
assign(config, cfgBase);
config.matchView.alwaysCalculatePredictions = true;
