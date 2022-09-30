import React from "react";
import { MatchList } from "./MatchList";

export default {
  title: "Components/matches",
};

const matchListEntries = [
  {
    awayTeam: "team away",
    homeTeam: "team home",
    betPredictions: [{ betName: "over1.5", prediction: 40 }],
  },
];

export const Primary = () => <MatchList entries={matchListEntries} />;
