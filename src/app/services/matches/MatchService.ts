import Papa from 'papaparse';
import fs from 'fs';
import Match from '../../types/matches/Match';

export function readMatches(path: string) : Match[]{
  const buffer = fs.readFileSync(`${__dirname}/${path}`);
  const parseResult = Papa.parse<Match>(buffer.toString(), {
    header: true,
    delimiter: ',',
  });
  return parseResult.data;
}
