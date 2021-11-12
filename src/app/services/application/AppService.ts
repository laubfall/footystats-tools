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
import { watchImportDirectory } from './CsvFileService';

function startImportDirectoryWatch(config: Configuration): boolean {
  if (config.includes(InvalidConfigurations.IMPORT_DIRECTORY_DOESNOT_EXIST)) {
    return false;
  }

  watchImportDirectory();
  return true;
}

export default function startApplication() {
  const config = loadConfig();
  startImportDirectoryWatch(config);
}
