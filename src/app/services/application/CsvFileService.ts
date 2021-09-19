import Papa from 'papaparse';
import fs from 'fs';

export default function importFile<T>(
  path: string,
  markAsImported: boolean
): T[] {
  const buffer = fs.readFileSync(`${__dirname}/${path}`);
  const parseResult = Papa.parse<T>(buffer.toString(), {
    header: true,
    delimiter: ',',
  });
  return parseResult.data;
}
