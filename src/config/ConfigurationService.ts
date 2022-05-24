import { injectable } from 'inversify';
import cfg, { Config } from './index';

@injectable()
export class ConfigurationService implements IConfigurationService {
  // eslint-disable-next-line class-methods-use-this
  loadConfig(): Promise<Config> {
    return Promise.resolve(cfg);
  }
}

export interface IConfigurationService {
  loadConfig(): Promise<Config>;
}
