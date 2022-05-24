// eslint-disable-next-line no-multi-assign
const config = (module.exports = {});
config.matchDbFileName = 'matches.nedb';
config.matchStatsDbFileName = 'matchStats.nedb';
config.teamStatsDbFileName = 'teamStats.nedb';
config.leagueStatsDbFileName = 'leagueStats.nedb';
config.window = { width: 1600, height: 1200 };
config.markCsvFilesAsImported = true;
config.importEsportMatches = false;
config.nedb = {
  inMemoryOnly: false,
};
config.matchView = {
  alwaysCalculatePredictions: false,
};
