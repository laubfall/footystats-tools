import { injectable } from 'inversify';
import { Country, League, Season } from '../../types/application/AppControll';
import Configuration from '../../types/application/Configuration';
import DbService from './DbStoreService';

export interface IAppControllService extends IIpcAppControllService {
  /**
   * Adds controll docs to the database. Checks all team docs without
   * @param country
   * @param league
   * @param season
   */
  addCountryLeagueAndSeason(
    country: string,
    league: string,
    season: string
  ): Promise<boolean>;

  addTeam(team: string, country: Country): Promise<boolean>;
}

export interface IIpcAppControllService {
  findCountries(): Promise<Country[]>;
}

@injectable()
export class AppControllService implements IAppControllService {
  private appControllDB: DbService<Country>;

  constructor(configuration: Configuration) {
    this.appControllDB = new DbService<Country>(
      `${configuration.databaseDirectory}/_appControll_Country.nedb`
    );
    this.appControllDB.createUniqueIndex('name');
  }

  async addCountryLeagueAndSeason(
    country: string,
    league: string,
    season: string
  ): Promise<boolean> {
    const s: Season = {
      name: season,
      yearFrom: '',
      yearTo: '',
    };

    const l: League = { name: league, seasons: [s] };

    const c: Country = {
      name: country,
      leagues: [l],
    };

    const expectedCountry: Country | undefined =
      await this.appControllDB.DB.asyncFindOne({ name: country });

    if (!expectedCountry || expectedCountry == null) {
      this.appControllDB.insert(c);
      return true;
    }

    const el = expectedCountry.leagues?.find((fl) => fl.name === league);
    if (el) {
      const es = el.seasons.find((fs) => fs.name === season);
      if (!es) {
        el.seasons.push(s);
      }
    } else {
      expectedCountry.leagues?.push(l);
    }

    this.appControllDB.DB.update(
      { name: country },
      { $set: { leagues: expectedCountry.leagues } }
    );

    return true;
  }

  // eslint-disable-next-line class-methods-use-this
  addTeam(team: string, country: Country): Promise<boolean> {
    return Promise.resolve(true);
  }

  findCountries(): Promise<Country[]> {
    return this.appControllDB.loadAll();
  }
}
