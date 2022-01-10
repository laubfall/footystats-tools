import 'reflect-metadata';
import { Container } from 'inversify';
// eslint-disable-next-line import/no-cycle
import { MatchStatsService } from './stats/MatchStatsService';
import LeagueStatsService from './stats/LeagueStatsService';
import TeamStatsService from './stats/TeamStatsService';
// eslint-disable-next-line import/no-cycle
import CsvDataToDBService from './application/CsvDataToDBService';

const inversifyContainer = new Container();
inversifyContainer.bind<CsvDataToDBService>(CsvDataToDBService).toSelf();
inversifyContainer.bind<MatchStatsService>(MatchStatsService).toSelf();
inversifyContainer.bind<LeagueStatsService>(LeagueStatsService).toSelf();
inversifyContainer.bind<TeamStatsService>(TeamStatsService).toSelf();

export default inversifyContainer;
