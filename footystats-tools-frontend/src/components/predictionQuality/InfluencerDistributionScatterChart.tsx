import React from "react";
import {
	CartesianGrid,
	Legend,
	Scatter,
	ScatterChart,
	Tooltip,
	XAxis,
	YAxis,
	ZAxis,
} from "recharts";
import { union, uniqueId } from "lodash";
import { InfluencerName } from "../../app/services/prediction/PredictionService.types";
import translate from "../../i18n/translate";
import { collectInfluencerNames } from "./functions";
import { BetPredictionDistribution, InfluencerPercentDistributionPrecheckResultEnum } from "../../footystats-frontendapi";

type GraphData = {
	predictionTotal: number;
	influencerResult: number;
	count: number; // count of predictions with the given predictionResult and influencerResult
	influencerName: InfluencerName;
};

type InfluencerDistributionGraphViewProps = {
	distributionBetSuccess: BetPredictionDistribution[]; // e.g. prediction for bet on this bet o05 or don't bet on o05
	distributionBetFailed: BetPredictionDistribution[]; // e.g. prediction for bet on this (failed) bet o05 or don't bet on o05 (failed)
};

type InfluencerDistributionGraphProps = {
	relevantInfluencer: InfluencerName; // only data of this influencer is considered
} & InfluencerDistributionGraphViewProps;

function createGraphData(
	measurement: BetPredictionDistribution[],
	influencer: InfluencerName,
): GraphData[] {
	let result: GraphData[] = [];
	measurement.forEach((predictionDisribution) => {
		predictionDisribution.influencerDistribution
			.filter(
				(influencerDistribution) =>
					influencerDistribution.influencerName === influencer &&
					influencerDistribution.precheckResult === InfluencerPercentDistributionPrecheckResultEnum.Ok,
			)
			.forEach((influencerDistribution) => {
				result.push({
					influencerName: influencerDistribution.influencerName,
					count: influencerDistribution.count,
					predictionTotal: predictionDisribution.predictionPercent,
					influencerResult: Math.round(
						influencerDistribution.predictionPercent,
					),
				});
			});
	});

	result = result.sort((a, b) => {
		return a.predictionTotal - b.predictionTotal;
	});

	return result;
}

export const InfluencerDistributionScatterChart = ({
	distributionBetSuccess,
	distributionBetFailed,
	relevantInfluencer,
}: InfluencerDistributionGraphProps) => {
	if (!distributionBetSuccess || !distributionBetFailed) {
		return null;
	}

	const g1Data = createGraphData(distributionBetSuccess, relevantInfluencer);
	const g2Data = createGraphData(distributionBetFailed, relevantInfluencer);

	if (g1Data.length === 0 || g2Data.length === 0) {
		//return null;
	}

	return (
		<>
			<h5>{relevantInfluencer}</h5>
			<ScatterChart
				width={600}
				height={450}
				margin={{ top: 20, right: 20, bottom: 10, left: 10 }}
			>
				<CartesianGrid strokeDasharray="3 3" />
				<YAxis
					type="number"
					domain={[0, 100]}
					dataKey="influencerResult"
					name="Influencer result"
				/>
				<XAxis
					type="number"
					domain={[0, 100]}
					dataKey="predictionTotal"
					name="Prediction total"
				/>
				<ZAxis dataKey="count" name="Count" />
				<Tooltip cursor={{ strokeDasharray: "3 3" }} />
				<Legend />
				<Scatter
					name={translate(
						"renderer.influencerdistributiongraph.scatterdiagram.betsuccess",
					)}
					data={g1Data}
					fill="#00FF00"
				/>
				<Scatter
					name={translate(
						"renderer.influencerdistributiongraph.scatterdiagram.betfailed",
					)}
					data={g2Data}
					fill="#FF0000"
				/>
			</ScatterChart>
		</>
	);
};

/**
 * This view renders a scatter diagramm for every influencer that was utilized calculating predictions.
 */
export const InfluencerDistributionScatterChartView = ({
	distributionBetSuccess,
	distributionBetFailed,
}: InfluencerDistributionGraphViewProps) => {
	const influencer = collectInfluencerNames(distributionBetSuccess);
	const influencerOnFailed = collectInfluencerNames(distributionBetFailed);
	const result = union(influencer, influencerOnFailed);
	return (
		<>
			{result.map((inf) => (
				<InfluencerDistributionScatterChart
					key={uniqueId()}
					relevantInfluencer={inf}
					distributionBetSuccess={distributionBetSuccess}
					distributionBetFailed={distributionBetFailed}
				/>
			))}
		</>
	);
};

export default { InfluencerDistributionGraph: InfluencerDistributionScatterChart };
