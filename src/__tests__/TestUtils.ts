import { MatchStatsService } from '../app/services/stats/MatchStatsService';

const mss = new MatchStatsService('inMemory');
jest.mock('../app/services/application/Ipc2RendererService');

export default mss;
