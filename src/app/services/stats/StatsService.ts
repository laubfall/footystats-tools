import Papa from 'papaparse';
import fs from 'fs';
import TeamStats from '../../types/stats/TeamStats';

// eslint-disable-next-line import/prefer-default-export
export function readTeamStats(path: string): TeamStats[] {
  const buffer = fs.readFileSync(`${__dirname}/${path}`);
  const parseResult = Papa.parse<TeamStats>(buffer.toString(), {
    header: true,
    delimiter: ',',
  });
  return parseResult.data;
}
