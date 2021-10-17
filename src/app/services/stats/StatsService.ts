import { Season } from '../../types/application/AppControll';
import TeamStats from '../../types/stats/TeamStats';
import { alreadyImported, importFile } from '../application/CsvFileService';

// eslint-disable-next-line import/prefer-default-export
export function readTeamStats(path: string) {
  if (alreadyImported(path)) {
    return;
  }
  const teamStats = importFile<TeamStats>(path, false);
}
