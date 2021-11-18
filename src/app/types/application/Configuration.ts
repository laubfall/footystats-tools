import fs from 'fs';
import { includes } from 'lodash';

/**
 * Error codes that show invalid configuration parts.
 */
export enum InvalidConfigurations {
  DATABASE_DIRECTORY_DOESNOT_EXIST,
  IMPORT_DIRECTORY_DOESNOT_EXIST,
}

/**
 * User configuration, everthing a user can modify.
 */
export default class Configuration {
  databaseDirectory = '';

  importDirectory = '';

  public validate(): InvalidConfigurations[] {
    const invalidConfigs = [];
    if (fs.existsSync(this.databaseDirectory) === false) {
      invalidConfigs.push(
        InvalidConfigurations.DATABASE_DIRECTORY_DOESNOT_EXIST
      );
    }

    if (fs.existsSync(this.importDirectory) === false) {
      invalidConfigs.push(InvalidConfigurations.IMPORT_DIRECTORY_DOESNOT_EXIST);
    }

    return invalidConfigs;
  }

  public includes(invalid: InvalidConfigurations): boolean {
    return includes(this.validate(), invalid);
  }
}
