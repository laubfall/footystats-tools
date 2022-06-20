import { injectable } from 'inversify';
import { Country } from '../../types/application/AppControll';
import { Countries } from './CountryAndLeagueService';

export interface IIpcAppControllService {
  findCountries(): Promise<Country[]>;
}

@injectable()
export class AppControllService implements IIpcAppControllService {
  // eslint-disable-next-line class-methods-use-this
  findCountries(): Promise<Country[]> {
    return Promise.resolve(Countries);
  }
}
