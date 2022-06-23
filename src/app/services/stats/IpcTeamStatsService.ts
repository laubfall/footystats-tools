import { ipcMain } from 'electron';
import TeamStatsService, {
  IIpcTeamStatsService,
  UniqueTeamStats,
} from './TeamStatsService';
import { ipcRendererInvoke } from '../application/gui/IpcRenderer2Main';

class IpcTeamStatsService implements IIpcTeamStatsService {
  latestThree(
    team: string,
    country: string,
    year?: number
  ): Promise<UniqueTeamStats[]> {
    return ipcRendererInvoke(
      IpcTeamStatsService.name + this.latestThree.name,
      team,
      country,
      year
    );
  }

  public static registerInvokeHandler(teamStatsService: TeamStatsService) {
    ipcMain.handle(
      IpcTeamStatsService.name + teamStatsService.latestThree.name,
      (...args) => {
        return teamStatsService.latestThree(args[1][0], args[1][1], args[1][2]);
      }
    );
  }
}

export default IpcTeamStatsService;
