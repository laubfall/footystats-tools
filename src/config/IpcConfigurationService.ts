import { ipcMain } from 'electron';
import {
  ConfigurationService,
  IConfigurationService,
} from './ConfigurationService';
import { Config } from './index';
import { ipcRendererInvoke } from '../app/services/application/gui/IpcRenderer2Main';

export class IpcConfigurationService implements IConfigurationService {
  loadConfig(): Promise<Config> {
    const config = ipcRendererInvoke(
      `configurationService.${this.loadConfig.name}`
    );
    return config;
  }

  static registerInvokeHandler(configrationService: ConfigurationService) {
    ipcMain.handle(
      `configurationService.${configrationService.loadConfig.name}`,
      () => configrationService.loadConfig()
    );
  }
}

export default IpcConfigurationService;
