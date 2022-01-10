import inversifyContainer from '../app/services/inversify.config';
import symbols from '../app/services/inversify.symbols';
import { MatchStatsService } from '../app/services/stats/MatchStatsService';
import Configuration from '../app/types/application/Configuration';

jest.mock('../app/services/application/Ipc2RendererService');

inversifyContainer.bind(Configuration).toConstantValue(new Configuration());
const mss = inversifyContainer.get<MatchStatsService>(
  symbols.MatchStatsService
);

export default mss;
