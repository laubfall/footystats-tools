import React from "react";
import { MatchList, MatchListEntry } from "./MatchList";
import { BetPredictionQualityBetEnum } from "../../footystats-frontendapi/models/BetPredictionQuality";

export default {
	title: "Components/matches",
};

const matchListEntries: MatchListEntry[] = [
	{
		awayTeam: "team away",
		homeTeam: "team home",
		betPredictions: [
			{
				bet: BetPredictionQualityBetEnum.OverZeroFive,
				prediction: {
					analyzeResult: "SUCCESS",
					betSuccessInPercent: 40,
				},
			},
		],
		country: "germany",
		footyStatsUrl: "https://footystats.org",
		gameStartsAt: new Date(),
		result: "2:1",
	},
];

export const Primary = () => (
	<MatchList
		entries={matchListEntries}
		totalRows={10}
		sortHandler={() => undefined}
	/>
);
