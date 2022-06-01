import inversifyContainer from '../app/services/inversify.config';
import LeagueStatsService from '../app/services/stats/LeagueStatsService';
import { MatchStatsService } from '../app/services/stats/MatchStatsService';
import Configuration from '../app/types/application/Configuration';
import MatchService from '../app/services/match/MatchService';
import { PredictionQualityService } from '../app/services/prediction/PredictionQualityService';

jest.mock('../app/services/application/gui/IpcMain2Renderer');

inversifyContainer.bind(Configuration).toConstantValue(new Configuration());
const mss = inversifyContainer.get<MatchStatsService>(MatchStatsService);
const lss = inversifyContainer.get<LeagueStatsService>(LeagueStatsService);
const matchService = inversifyContainer.get<MatchService>(MatchService);
const predictionQualityService =
  inversifyContainer.get<PredictionQualityService>(PredictionQualityService);

export default {
  matchStatsService: mss,
  leagueStatsService: lss,
  matchService,
  predictionQualityService,
  inversifyContainer,
};
