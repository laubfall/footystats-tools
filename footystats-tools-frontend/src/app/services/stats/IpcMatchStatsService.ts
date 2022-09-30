import {
  NString,
  NDate,
  Result,
  CursorModification,
  PagedResult,
} from "../../types/General";
import { MatchStats } from "../../types/stats/MatchStats";
import { MatchStatsService, IMatchStatsService } from "./MatchStatsService";

class IpcMatchStatsService implements IMatchStatsService {
  matchByUniqueFields(
    date_unix: number,
    League: string,
    homeTeam: string,
    awayTeam: string,
  ): Promise<MatchStats> {
    return Promise.resolve(undefined);
  }

  matchesByFilter(
    country: NString,
    league: NString,
    from: NDate,
    until: NDate,
    cursorModification?: CursorModification[],
  ): Promise<PagedResult<MatchStats>> {
    return Promise.resolve(undefined);
  }

  // eslint-disable-next-line class-methods-use-this
  public matchesByDay(day: Date): Promise<Result<MatchStats>> {
    return Promise.resolve(undefined);
  }
}

export default IpcMatchStatsService;
