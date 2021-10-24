import Papa from 'papaparse';
import fs from 'fs';
import * as path from 'path';
import lodash, { isNumber, toNumber } from 'lodash';

const IMPORTED_PREFIX = 'imported_';

export function alreadyImported(pathToFile: string): boolean {
  const fileName = path.basename(pathToFile);
  if (fileName.startsWith(IMPORTED_PREFIX)) {
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
  const buffer = fs.readFileSync(`${__dirname}/${pathToFile}`);
  const parseResult = Papa.parse<T>(buffer.toString(), {
    header: true,
    delimiter: ',',
    transformHeader: (header) => lodash.replace(header, '.', 'dot'), // Otherwise objects can not be stored inside nedb.
    dynamicTyping: true, // guess the type of the field (e.g. numbers will be stored without quotes)
  });

  if (markAsImported) {
    const dirName = path.dirname(pathToFile);
    const fileName = path.basename(pathToFile);
    fs.renameSync(
      pathToFile,
      path.format({ dir: dirName, base: fileName, ext: IMPORTED_PREFIX })
    );
  }

  return parseResult.data;
}

export default importFile;
