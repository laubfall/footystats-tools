import { format, fromUnixTime } from 'date-fns';
import Match from '../../types/matches/Match';
import { alreadyImported, importFile } from '../application/CsvFileService';
import { load, store } from '../application/StorageService';

const STORE_NAME_PREFIX = 'match_stats_';

const STORE_NAME_DATE_PATTERN = 'ddMMyyyy';

/**
 * Store matches inside the store. Cause we use localstorge everything is
 * stored as json objects, so to identify matches for a day all matches
 * are grouped by the day they are played.
 *
 * @param matches Matches of one day
 * @param day Day when the matches are / were played
 */
function storeMatchPerDay(matches: Match[], day: Date) {
  const date = format(day, STORE_NAME_DATE_PATTERN);
  store(STORE_NAME_PREFIX + date, JSON.stringify(matches));
}

export function readMatches(path: string) {
  if (alreadyImported(path)) {
    return;
  }

  const matches = importFile<Match>(path, false);

  const days = matches.flatMap((m) => m.date_unix);
  days.forEach((d) => {
    const matchesOfCurrentDay = matches.filter((m) => m.date_unix === d);
    storeMatchPerDay(matchesOfCurrentDay, fromUnixTime(d));
  });
}

export function matchesByDay(day: Date): Match[] {
  const date = format(day, STORE_NAME_DATE_PATTERN);
  const raw = load(STORE_NAME_PREFIX + date);
  if (raw) {
    return JSON.parse(raw);
  }

  return [];
}

export default matchesByDay;
