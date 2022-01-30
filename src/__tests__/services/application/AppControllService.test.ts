import { AppControllService } from '../../../app/services/application/AppControllService';
import TestUtils from '../../TestUtils';

describe('Test the appControllService', () => {
  it('Simply save country, league and season', async () => {
    const ioc = TestUtils.inversifyContainer;
    const acs = ioc.get<AppControllService>(AppControllService);
    await acs.addCountryLeagueAndSeason('germany', 'Bundesliga', '2021/2022');

    const cs = await acs.findCountries();
    expect(cs).not.toBeUndefined();
    expect(cs).not.toBeNull();
    expect(cs).toHaveLength(1);
  });

  it('Save a country with league and then add another league and new season to existing one', async () => {
    const ioc = TestUtils.inversifyContainer;
    const acs = ioc.get<AppControllService>(AppControllService);

    await acs.addCountryLeagueAndSeason('germany', 'Bundesliga', '2021/2022');
    await acs.addCountryLeagueAndSeason(
      'germany',
      'Bundesliga 2.',
      '2021/2022'
    );
    await acs.addCountryLeagueAndSeason('germany', 'Bundesliga', '2019/2020');

    const germany = (await acs.findCountries())[0];
    expect(germany.leagues).toHaveLength(2);
    const bundesliga = germany.leagues?.find((l) => l.name === 'Bundesliga');
    expect(bundesliga?.seasons).toHaveLength(2);
  });
});
