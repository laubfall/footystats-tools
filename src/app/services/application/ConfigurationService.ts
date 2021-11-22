import { plainToClass } from 'class-transformer';
import Configuration from '../../types/application/Configuration';
import { load, store } from './StorageService';

const STORAGE_KEY = 'configurationStorageKey';

export const saveConfig = (configuration: Configuration) => {
  store(STORAGE_KEY, JSON.stringify(configuration));
};

export const loadConfig = async (): Promise<Configuration> => {
  const rawConfig = load(STORAGE_KEY);

  const cfgVal = await rawConfig;

  if (cfgVal == null) {
    return Promise.resolve(new Configuration());
  }
  const config = plainToClass(Configuration, JSON.parse(cfgVal));
  return Promise.resolve(config);
};

export default { loadConfig, saveConfig };
