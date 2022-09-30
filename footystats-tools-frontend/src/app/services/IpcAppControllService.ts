import { Country } from "../types/application/AppControll";

export default class IpcAppControllService {
  findCountries(): Promise<Country[]> {
    return Promise.resolve([]);
  }
}
