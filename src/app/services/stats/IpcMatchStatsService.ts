import { ipcMain } from 'electron';
import MatchStats from '../../types/stats/MatchStats';
import { ipcRendererInvoke } from '../application/IpcRenderer2Main';
import { MatchStatsService, IMatchStatsService } from './MatchStatsService';

class IpcMatchStatsService implements IMatchStatsService {
  // eslint-disable-next-line class-methods-use-this
  public matchesByDay(day: Date): Promise<MatchStats[]> {
    return ipcRendererInvoke(this.matchesByDay.name, day);
  }

  public static registerInvokeHandler(matchStatsService: MatchStatsService) {
    ipcMain.handle(matchStatsService.matchesByDay.name, (...args) => {
      return matchStatsService.matchesByDay(args[1][0]);
    });
  }
}

export default IpcMatchStatsService;
