/**
 * Only use these functions from the main process!
 *
 * Functions that reflects the application behavior.
 *
 * This is typically the steps that are needed to make in order
 * to provide user functionality.
 */
import { loadConfig } from './ConfigurationService';
import Configuration, {
  InvalidConfigurations,
} from '../../types/application/Configuration';
import watchImportDirectory from './FileSystemService';
import {
  msgInvalidConfigurations,
  msgSimpleMessage,
} from './Ipc2RendererService';
import { MainProcessMessageCodes } from '../../types/application/MessageCodes';
import IpcMatchStatsService from '../stats/IpcMatchStatsService';
import { MatchStatsService } from '../stats/MatchStatsService';
import IpcNativeGuiService from './gui/IpcNativeGuiService';
import inversifyContainer from '../inversify.config';
import CsvDataToDBService from './CsvDataToDBService';
import IpcLeagueStatsService from '../stats/IpcLeagueStatsService';
import LeagueStatsService from '../stats/LeagueStatsService';

function startImportDirectoryWatch(config: Configuration) {
  const csvDataToDBService =
    inversifyContainer.get<CsvDataToDBService>(CsvDataToDBService);

  watchImportDirectory(config.importDirectory, (e) =>
    csvDataToDBService.storeCsvData(e)
  );
  msgSimpleMessage(MainProcessMessageCodes.STARTED_IMPORT_DIRECTORY_WATCH);
}

function registerConfigDependentIpcHandler() {
  IpcMatchStatsService.registerInvokeHandler(
    inversifyContainer.get<MatchStatsService>(MatchStatsService)
  );
  IpcLeagueStatsService.registerInvokeHandler(
    inversifyContainer.get<LeagueStatsService>(LeagueStatsService)
  );
}

function registerConfigIndependetIpcHandler() {
  IpcNativeGuiService.registerInvokeHandler();
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
  inversifyContainer.bind<Configuration>(Configuration).toConstantValue(cfg);

  startImportDirectoryWatch(cfg);
  registerConfigDependentIpcHandler();
}

function onConfigInvalid(ves: InvalidConfigurations[]) {
  msgInvalidConfigurations(ves);
}

export default async function startApplication() {
  msgSimpleMessage(MainProcessMessageCodes.STARTED_IMPORT_DIRECTORY_WATCH);
  const config = loadConfigAndDispatchErrors();
  config.then(onConfigValid).catch(onConfigInvalid);

  registerConfigIndependetIpcHandler();
}
