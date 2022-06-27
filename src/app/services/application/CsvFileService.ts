import Papa from 'papaparse';
import fs from 'fs';
import * as path from 'path';
import lodash, { join, slice, split, startsWith } from 'lodash';
import log from 'electron-log';
import { CsvFileType } from '../../types/application/CsvFileType';
import { Country } from '../../types/application/AppControll';

export const IMPORTED_PREFIX = '.imported';

export interface CsvFileInformation {
  type: CsvFileType;
  country?: Country;
}

function csvFileTypeByName(name: string): CsvFileType | undefined {
  switch (name) {
    case 'matches':
      return CsvFileType.MATCH_STATS;
    case 'league':
      return CsvFileType.LEAGUE_STATS;
    case 'teams':
      return CsvFileType.TEAM_STATS;
    case 'teams2':
      return CsvFileType.TEAM_2_STATS;
    case 'matches_expanded':
      return CsvFileType.LEAGUE_MATCH_STATS;
    default:
      return undefined;
  }
}

export function csvFileInformationByFileName(
  fileName: string
): CsvFileInformation {
  if (startsWith(fileName, 'matches_expanded')) {
    return {
      type: CsvFileType.MATCH_STATS,
    };
  }

  const fileNameWithoutExt = fileName.slice(0, fileName.length - 3);
  const splittedFileName = split(fileNameWithoutExt, '-');
  const { length } = splittedFileName;

  const country = { name: splittedFileName[0] }; // this will fail in case of south korea...
  const ln = () => join(slice(splittedFileName, 1, length - 6 + 1), '-');
  const cvi: CsvFileInformation = {
    type: csvFileTypeByName(splittedFileName[length - 5]),
    country: {
      name: country.name,
      leagues: [
        {
          name: ln(),
          seasons: [
            {
              yearFrom: splittedFileName[length - 4],
              yearTo: splittedFileName[length - 2],
              name: join(slice(splittedFileName, length - 4, length - 1), '-'),
            },
          ],
        },
      ],
    },
  };

  return cvi;
}

export function alreadyImported(pathToFile: string): boolean {
  const fileName = path.basename(pathToFile);
  if (fileName.endsWith(IMPORTED_PREFIX)) {
    log.debug(`${pathToFile} is already imported`);
    return true;
  }

  return false;
}

/**
 * Import a footystats csv file and parse it to the wanted type.
 *
 * @param pathToFile Mandatory. Path to the csv file.
 * @param markAsImported If true file will be marked as imported so it won't be imported again. A prefix is added to the files name.
 * @returns Every row as a json object of the wanted type.
 */
export function importFile<T>(
  pathToFile: string,
  markAsImported: boolean
): T[] {
  const buffer = fs.readFileSync(pathToFile);
  const parseResult = Papa.parse<T>(buffer.toString(), {
    header: true,
    delimiter: ',',
    transformHeader: (header) => lodash.replace(header, '.', 'dot'), // Otherwise objects can not be stored inside nedb.
    dynamicTyping: true, // guess the type of the field (e.g. numbers will be stored without quotes)
    skipEmptyLines: true,
  });
  log.debug(
    `Imported file ${pathToFile} resulting in ${parseResult.data.length} result sets`
  );

  if (markAsImported) {
    const dirName = path.dirname(pathToFile);
    const fileName = path.basename(pathToFile);
    fs.renameSync(
      pathToFile,
      path.format({ dir: dirName, name: fileName, ext: IMPORTED_PREFIX })
    );
    log.debug(`Marked file as imported ${pathToFile}`);
  }

  return parseResult.data;
}

export default importFile;
