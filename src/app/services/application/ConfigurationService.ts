import Configuration from '../../types/application/Configuration';
import { load, store } from './StorageService';

const STORAGE_KEY = 'configurationStorageKey';

export const saveConfig = (configuration: Configuration) => {
  store(STORAGE_KEY, JSON.stringify(configuration));
};

export const loadConfig = () => {
  return load(STORAGE_KEY);
};

export default loadConfig;
