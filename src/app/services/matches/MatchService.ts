import Match from '../../types/matches/Match';
import { importFile } from '../application/CsvFileService';

export default function readMatches(path: string): Match[] {
  return importFile<Match>(path, false);
}
