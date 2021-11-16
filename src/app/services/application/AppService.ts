/**
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
import { csvFileInformationByFileName } from './CsvFileService';
import { CsvFileType } from '../../types/application/CsvFileType';

function startImportDirectoryWatch(config: Configuration): boolean {
  if (config.includes(InvalidConfigurations.IMPORT_DIRECTORY_DOESNOT_EXIST)) {
    return false;
  }

  watchImportDirectory(config.importDirectory, (e, f) => {
    const fi = csvFileInformationByFileName(f);
    switch (fi.type) {
      case CsvFileType.LEAGUE_MATCH_STATS:
        break;
      case CsvFileType.MATCH_STATS:
        break;
      default:
        break;
    }
    return null;
  });
  return true;
}

function loadConfigAndDispatchErrors(): Promise<Configuration> {
  const config = loadConfig();
  const ves = config.validate();
  if (ves.length > 0) {
    // TODO dispatch errors
    return Promise.reject(ves);
  }

  return Promise.resolve(config);
}

export default function startApplication() {
  const config = loadConfigAndDispatchErrors();

  config.then((cfg) => startImportDirectoryWatch(cfg)).catch((reason) => {});
}
