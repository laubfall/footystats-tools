import fs from 'fs';

export enum InvalidConfigurations {
  DATABASE_DIRECTORY_DOESNOT_EXIST,
  IMPORT_DIRECTORY_DOESNOT_EXIST,
}

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
}