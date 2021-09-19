import Papa from 'papaparse';
import fs from 'fs';

export function importFile<T>(path: string, markAsImported: boolean): T[] {
  const buffer = fs.readFileSync(`${__dirname}/${path}`);
  const parseResult = Papa.parse<T>(buffer.toString(), {
    header: true,
    delimiter: ',',
  });
  return parseResult.data;
}

export default importFile;
