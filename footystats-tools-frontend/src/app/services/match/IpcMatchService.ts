import {
  CursorModification,
  NDate,
  NString, PagedResult,
  Result,
} from "../../types/General";
import { MatchStats } from "../../types/stats/MatchStats";
import Match from "./IMatchService";
import { Bet } from "../../types/prediction/BetPredictionContext";
import { PredictionResult } from "../prediction/PredictionService.types";
import { MatchControllerApi, Paging } from "../../../footystats-frontendapi";

class IpcMatchService {
  // Prefix for the invoke handler to avoid name clashes due to equal method names (e.g. MatchStatsService.matchesByFilter)
  static invokeHandlerPrefix = "MatchService_";

  async matchesByFilter(
    country: NString,
    league: NString,
    from: NDate,
    until: NDate,
    paging?: Paging,
  ) {
    const api = new MatchControllerApi();
    return api.listMatches({
      listMatchRequest: {
        country,
        league,
        paging,
      },
    });
  }

  calculatePrediction(bet: Bet, ms: MatchStats): Promise<PredictionResult> {
    return Promise.resolve(undefined);
  }
}

export default IpcMatchService;
