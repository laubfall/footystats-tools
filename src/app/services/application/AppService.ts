/**
 * Only use these functions from the main process!
 *
 * Functions that reflects the application behavior.
 *
 * This is typically the steps that are needed to make in order
 * to provide user functionality.
 */

import { BrowserWindow } from 'electron';
import { loadConfig } from './ConfigurationService';
import Configuration, {
  InvalidConfigurations,
} from '../../types/application/Configuration';
import watchImportDirectory from './FileSystemService';
import CsvDataToDBService from './CsvDataToDBService';

let csvDataToDBService: CsvDataToDBService;

function startImportDirectoryWatch(config: Configuration): boolean {
  if (config.includes(InvalidConfigurations.IMPORT_DIRECTORY_DOESNOT_EXIST)) {
    return false;
  }

  watchImportDirectory(config.importDirectory, (e) =>
    csvDataToDBService.storeCsvData(e)
  );
  return true;
}

async function loadConfigAndDispatchErrors(): Promise<Configuration> {
  const config = await loadConfig();
  const ves = config.validate();
  if (ves.length > 0) {
    return Promise.reject(ves);
  }

  return Promise.resolve(config);
}

function onConfigValid(cfg: Configuration) {
  csvDataToDBService = new CsvDataToDBService(cfg);
  startImportDirectoryWatch(cfg);
}

function onConfigInvalid(ves: InvalidConfigurations[]) {
  BrowserWindow.getFocusedWindow()?.webContents.send('on-config');
}

export default async function startApplication() {
  const config = loadConfigAndDispatchErrors();

  config.then(onConfigValid).catch(onConfigInvalid);
}
