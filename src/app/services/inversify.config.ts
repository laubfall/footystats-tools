import 'reflect-metadata';
import { Container } from 'inversify';
// eslint-disable-next-line import/no-cycle
import { MatchStatsService } from './stats/MatchStatsService';
import LeagueStatsService from './stats/LeagueStatsService';
import TeamStatsService from './stats/TeamStatsService';
import Symbols from './inversify.symbols';
// eslint-disable-next-line import/no-cycle
import CsvDataToDBService from './application/CsvDataToDBService';

const inversifyContainer = new Container();
inversifyContainer
  .bind<CsvDataToDBService>(Symbols.CsvDataToDBService)
  .to(CsvDataToDBService);
inversifyContainer
  .bind<MatchStatsService>(Symbols.MatchStatsService)
  .to(MatchStatsService);
inversifyContainer
  .bind<LeagueStatsService>(Symbols.LeagueStatsService)
  .to(LeagueStatsService);
inversifyContainer
  .bind<TeamStatsService>(Symbols.TeamStatsService)
  .to(TeamStatsService);

export default inversifyContainer;
