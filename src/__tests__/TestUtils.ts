import inversifyContainer from '../app/services/inversify.config';
import LeagueStatsService from '../app/services/stats/LeagueStatsService';
import { MatchStatsService } from '../app/services/stats/MatchStatsService';
import Configuration from '../app/types/application/Configuration';

jest.mock('../app/services/application/Ipc2RendererService');

inversifyContainer.bind(Configuration).toConstantValue(new Configuration());
const mss = inversifyContainer.get<MatchStatsService>(MatchStatsService);
const lss = inversifyContainer.get<LeagueStatsService>(LeagueStatsService);

export default {
  matchStatsService: mss,
  leagueStatsService: lss,
};
