import 'reflect-metadata';
import { Container } from 'inversify';
// eslint-disable-next-line import/no-cycle
import { MatchStatsService } from './stats/MatchStatsService';
import LeagueStatsService from './stats/LeagueStatsService';
import TeamStatsService from './stats/TeamStatsService';
// eslint-disable-next-line import/no-cycle
import CsvDataToDBService from './application/CsvDataToDBService';
import { AppControllService } from './application/AppControllService';

const inversifyContainer = new Container();
inversifyContainer
  .bind<CsvDataToDBService>(CsvDataToDBService)
  .toSelf()
  .inSingletonScope();
inversifyContainer
  .bind<MatchStatsService>(MatchStatsService)
  .toSelf()
  .inSingletonScope();
inversifyContainer
  .bind<LeagueStatsService>(LeagueStatsService)
  .toSelf()
  .inSingletonScope();
inversifyContainer
  .bind<TeamStatsService>(TeamStatsService)
  .toSelf()
  .inSingletonScope();
inversifyContainer
  .bind<AppControllService>(AppControllService)
  .toSelf()
  .inSingletonScope();

export default inversifyContainer;
