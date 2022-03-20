import { ipcMain } from 'electron';
import { NString, NDate } from '../../types/General';
import { MatchStats } from '../../types/stats/MatchStats';
import { CursorModification } from '../application/DbStoreService';
import { ipcRendererInvoke } from '../application/gui/IpcRenderer2Main';
import { MatchStatsService, IMatchStatsService } from './MatchStatsService';

class IpcMatchStatsService implements IMatchStatsService {
  matchesByFilter(
    country: NString,
    league: NString,
    from: NDate,
    until: NDate,
    cursorModification?: CursorModification[]
  ): Promise<MatchStats[]> {
    return ipcRendererInvoke(
      this.matchesByFilter.name,
      country,
      league,
      from,
      until,
      cursorModification
    );
  }

  // eslint-disable-next-line class-methods-use-this
  public matchesByDay(day: Date): Promise<MatchStats[]> {
    return ipcRendererInvoke(this.matchesByDay.name, day);
  }

  public static registerInvokeHandler(matchStatsService: MatchStatsService) {
    ipcMain.handle(matchStatsService.matchesByDay.name, (...args) => {
      return matchStatsService.matchesByDay(args[1][0]);
    });

    ipcMain.handle(matchStatsService.matchesByFilter.name, (...args) => {
      return matchStatsService.matchesByFilter(
        args[1][0],
        args[1][1],
        args[1][2],
        args[1][3],
        args[1][4]
      );
    });
  }
}

export default IpcMatchStatsService;
