import TeamStats from '../../types/stats/TeamStats';
import { importFile } from '../application/CsvFileService';

// eslint-disable-next-line import/prefer-default-export
export function readTeamStats(path: string): TeamStats[] {
  return importFile<TeamStats>(path, false);
}
