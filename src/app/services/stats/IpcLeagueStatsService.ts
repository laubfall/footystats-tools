import { ipcMain } from 'electron';
import { LeagueStats } from '../../types/stats/LeagueStats';
import { ipcRendererInvoke } from '../application/gui/IpcRenderer2Main';
import LeagueStatsService, { ILeagueStatsService } from './LeagueStatsService';

class IpcLeagueStatsService implements ILeagueStatsService {
  findeLeagueStatsBy(name: string, season: string): Promise<LeagueStats> {
    return ipcRendererInvoke(this.findeLeagueStatsBy.name, name, season);
  }

  public static registerInvokeHandler(leagueStatsService: LeagueStatsService) {
    ipcMain.handle(leagueStatsService.findeLeagueStatsBy.name, (...args) => {
      const params = args[1];
      return leagueStatsService.findeLeagueStatsBy(params[0], params[1]);
    });
  }
}

export default IpcLeagueStatsService;
