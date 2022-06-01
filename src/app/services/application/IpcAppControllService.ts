import { ipcMain } from 'electron';
import { injectable } from 'inversify';
import { Country } from '../../types/application/AppControll';
import {
  AppControllService,
  IIpcAppControllService,
} from './AppControllService';
import { ipcRendererInvoke } from './gui/IpcRenderer2Main';

@injectable()
export default class IpcAppControllService implements IIpcAppControllService {
  findCountries(): Promise<Country[]> {
    return ipcRendererInvoke(this.findCountries.name);
  }

  public static registerInvokeHandler(appControllService: AppControllService) {
    ipcMain.handle(appControllService.findCountries.name, () => {
      return appControllService.findCountries();
    });
  }
}
