import Configuration from '../../types/application/Configuration';
import { load, store } from './StorageService';

const STORAGE_KEY = 'configurationStorageKey';

export const saveConfig = (configuration: Configuration) => {
  store(STORAGE_KEY, JSON.stringify(configuration));
};

export const loadConfig = (): Configuration => {
  const rawConfig = load(STORAGE_KEY);
  if (!rawConfig || rawConfig === '') {
    return new Configuration();
  }
  return JSON.parse(rawConfig);
};

export default loadConfig;
