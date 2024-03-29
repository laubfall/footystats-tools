import { MatchStats, MatchStatus } from "../../types/stats/MatchStats";
import { PredictionQualityRevision } from "../prediction/PredictionQualityService.types";
import {
  PredictionResult,
} from "../prediction/PredictionService.types";

type Match = {
  uniqueIdentifier: string; // combination of date_unix, league, away and home team
  date_unix: number;
  date_GMT: string;
  Country: string;
  League: string;
  "Home Team": string;
  "Away Team": string;
  goalsHomeTeam: number;
  goalsAwayTeam: number;
  state: MatchStatus;
  footyStatsUrl: string;
  o05?: PredictionResult;
  bttsYes?: PredictionResult;
  revision?: PredictionQualityRevision;
};

export default Match;
