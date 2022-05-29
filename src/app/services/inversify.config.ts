import 'reflect-metadata';
import { Container } from 'inversify';
// eslint-disable-next-line import/no-cycle
import MatchService from './match/MatchService';
import { MatchStatsService } from './stats/MatchStatsService';
import LeagueStatsService from './stats/LeagueStatsService';
import TeamStatsService from './stats/TeamStatsService';
// eslint-disable-next-line import/no-cycle
import CsvDataToDBService from './application/CsvDataToDBService';
import { AppControllService } from './application/AppControllService';
import { ConfigurationService } from '../../config/ConfigurationService';
import { PredictionQualityService } from './application/PredictionQualityService';

const inversifyContainer = new Container();
inversifyContainer
  .bind<CsvDataToDBService>(CsvDataToDBService)
  .toSelf()
  .inSingletonScope();
inversifyContainer
  .bind<MatchStatsService>(MatchStatsService)
  .toSelf()
  .inSingletonScope();
inversifyContainer.bind<MatchService>(MatchService).toSelf().inSingletonScope();
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
inversifyContainer
  .bind<ConfigurationService>(ConfigurationService)
  .toSelf()
  .inSingletonScope();
inversifyContainer
  .bind<PredictionQualityService>(PredictionQualityService)
  .toSelf()
  .inSingletonScope();
export default inversifyContainer;
