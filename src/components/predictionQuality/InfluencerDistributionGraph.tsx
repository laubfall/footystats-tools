import React from 'react';
import {
	CartesianGrid,
	Legend,
	Scatter,
	ScatterChart,
	Tooltip,
	XAxis,
	YAxis,
	ZAxis,
} from 'recharts';
import { union, uniq, uniqueId } from 'lodash';
import { BetPredictionDistribution } from '../../app/services/prediction/PredictionQualityService.types';
import { InfluencerName } from '../../app/services/prediction/PredictionService.types';
import translate from '../../i18n/translate';

type GraphData = {
	predictionTotal: number;
	influencerResult: number;
	count: number; // count of predictions with the given predictionResult and influencerResult
	influencerName: InfluencerName;
};

type InfluencerDistributionGraphViewProps = {
	distributionBetSuccess: BetPredictionDistribution; // e.g. prediction for bet on this bet o05 or don't bet on o05
	distributionBetFailed: BetPredictionDistribution; // e.g. prediction for bet on this (failed) bet o05 or don't bet on o05 (failed)
};

type InfluencerDistributionGraphProps = {
	relevantInfluencer: InfluencerName; // only data of this influencer is considered
} & InfluencerDistributionGraphViewProps;

function createGraphData(
	measurement: BetPredictionDistribution,
	influencer: InfluencerName
): GraphData[] {
	let result: GraphData[] = [];
	measurement.forEach((predictionDisribution) => {
		predictionDisribution.influencerDistribution
			.filter(
				(influencerDistribution) =>
					influencerDistribution.influencerName === influencer
			)
			.forEach((influencerDistribution) => {
				result.push({
					influencerName: influencerDistribution.influencerName,
					count: influencerDistribution.count,
					predictionTotal: predictionDisribution.predictionPercent,
					influencerResult: Math.round(
						influencerDistribution.predictionPercent
					),
				});
			});
	});

	result = result.sort((a, b) => {
		return a.predictionTotal - b.predictionTotal;
	});

	return result;
}

function collectInfluencerNames(
	measurement: BetPredictionDistribution
): string[] {
	let names: string[] = [];
	measurement.forEach((m) => {
		const uniqueNames = uniq(
			m.influencerDistribution.map((id) => id.influencerName)
		);
		names.push(...uniqueNames);
		names = uniq(names);
	});

	return names;
}

export const InfluencerDistributionGraph = ({
	distributionBetSuccess,
	distributionBetFailed,
	relevantInfluencer,
}: InfluencerDistributionGraphProps) => {
	if (!distributionBetSuccess || !distributionBetFailed) {
		return null;
	}

	const g1Data = createGraphData(distributionBetSuccess, relevantInfluencer);
	const g2Data = createGraphData(distributionBetFailed, relevantInfluencer);

	return (
		<>
			<h3>{relevantInfluencer}</h3>
			<ScatterChart
				width={1024}
				height={768}
				margin={{ top: 20, right: 20, bottom: 10, left: 10 }}
			>
				<CartesianGrid strokeDasharray="3 3" />
				<XAxis
					type="number"
					domain={[0, 100]}
					dataKey="predictionTotal"
					name="Prediction total"
				/>
				<YAxis
					domain={[0, 100]}
					dataKey="influencerResult"
					name="Influencer result"
				/>
				<ZAxis dataKey="count" name="Count" />
				<Tooltip cursor={{ strokeDasharray: '3 3' }} />
				<Legend />
				<Scatter
					name={translate(
						'renderer.influencerdistributiongraph.scatterdiagram.betsuccess'
					)}
					data={g1Data}
					fill="#00FF00"
					shape="cross"
				/>
				<Scatter
					name={translate(
						'renderer.influencerdistributiongraph.scatterdiagram.betfailed'
					)}
					data={g2Data}
					fill="#FF0000"
					shape="cross"
				/>
			</ScatterChart>
		</>
	);
};

/**
 * This view renders a scatter diagramm for every influencer that was utilized calculating predictions.
 */
export const InfluencerDistributionGraphView = ({
	distributionBetSuccess,
	distributionBetFailed,
}: InfluencerDistributionGraphViewProps) => {
	const influencer = collectInfluencerNames(distributionBetSuccess);
	const influencerOnFailed = collectInfluencerNames(distributionBetFailed);
	const result = union(influencer, influencerOnFailed);
	return (
		<>
			{result.map((inf) => (
				<InfluencerDistributionGraph
					key={uniqueId()}
					relevantInfluencer={inf}
					distributionBetSuccess={distributionBetSuccess}
					distributionBetFailed={distributionBetFailed}
				/>
			))}
		</>
	);
};

export default { InfluencerDistributionGraph };
