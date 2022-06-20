import { ipcMain } from 'electron';
import { NString, NDate } from '../../types/General';
import { MatchStats } from '../../types/stats/MatchStats';
import { CursorModification, Result } from '../application/DbStoreService';
import { ipcRendererInvoke } from '../application/gui/IpcRenderer2Main';
import Match, { IMatchService } from './IMatchService';
import MatchService from './MatchService';

class IpcMatchService implements IMatchService {
  // Prefix for the invoke handler to avoid name clashes due to equal method names (e.g. MatchStatsService.matchesByFilter)
  static invokeHandlerPrefix = 'MatchService_';

  writeMatch(matchStats: MatchStats): void {
    ipcRendererInvoke(
      IpcMatchService.invokeHandlerPrefix + this.writeMatch.name,
      matchStats
    );
  }

  matchesByFilter(
    country: NString[],
    league: NString[],
    from: NDate,
    until: NDate,
    cursorModification?: CursorModification[]
  ): Promise<Result<Match>> {
    return ipcRendererInvoke(
      IpcMatchService.invokeHandlerPrefix + this.matchesByFilter.name,
      country,
      league,
      from,
      until,
      cursorModification
    );
  }

  public static registerInvokeHandler(matchService: MatchService) {
    ipcMain.handle(
      this.invokeHandlerPrefix + matchService.writeMatch.name,
      (...args) => {
        return matchService.writeMatch(args[1][0]);
      }
    );

    ipcMain.handle(
      this.invokeHandlerPrefix + matchService.matchesByFilter.name,
      (...args) => {
        return matchService.matchesByFilter(
          args[1][0],
          args[1][1],
          args[1][2],
          args[1][3],
          args[1][4]
        );
      }
    );
  }
}

export default IpcMatchService;
