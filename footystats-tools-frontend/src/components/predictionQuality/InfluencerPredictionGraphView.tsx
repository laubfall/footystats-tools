import React from "react";
import { union, uniqueId } from "lodash";
import { Alert } from "react-bootstrap";
import { colord } from "colord";
import { PercentageDistributionGraph } from "./PercentageDistributionGraph";
import { InfluencerName } from "../../app/services/prediction/PredictionService.types";
import translate from "../../i18n/translate";
import { PercentDistribution } from "../../app/services/prediction/PredictionQualityService.types";
import { BetPredictionQualityBetEnum } from "../../footystats-frontendapi/models/BetPredictionQuality";
import { BetPredictionQualityInfluencerAggregate } from "../../footystats-frontendapi";
import { keys } from "mobx";

const InfluencerPredictionGraph = ({
	i1Distribution,
	i2Distribution,
	name,
	bet,
}: InfluencerPredictionGraphProps) => {
	if (i1Distribution.length === 0 && i2Distribution.length === 0) {
		return (
			<>
				<Alert variant="info">
					{translate(
						"renderer.predictionqualitiyview.influencerpredictionquality.nodata",
						[name],
					)}
				</Alert>
			</>
		);
	}

	return (
		<>
			<h3>{name}</h3>
			<PercentageDistributionGraph
				graphs={[
					{
						name: `${translate(
							`renderer.matchesview.bet.${bet}`,
						)} ${translate("renderer.predictiongraphview.bet")}`,
						data: i1Distribution,
						color: colord("rgb(40,200,0)").toRgb(),
					},
					{
						name: `${translate(
							`renderer.matchesview.bet.${bet}`,
						)} ${translate(
							"renderer.predictiongraphview.bet.failed",
						)}`,
						data: i2Distribution,
					},
				]}
			/>
		</>
	);
};

function influencerPercentDistributions(
	distribution: Array<BetPredictionQualityInfluencerAggregate>,
): PercentDistribution[] {
	const aggregatePredictionPercentAndCount: Map<number, PercentDistribution> =
		new Map();

	distribution.forEach((influencerDistributions) => {
		aggregatePredictionPercentAndCount.set(
			influencerDistributions.predictionPercent,
			{
				count: influencerDistributions.count,
				predictionPercent: influencerDistributions.predictionPercent,
			},
		);
	});

	return [...aggregatePredictionPercentAndCount.values()];
}

export const InfluencerPredictionGraphView = ({
	measurementBetInfluencerAggregate,
	measurementDontBetInfluencerAggregate,
	bet,
}: InfluencerPredictionGraphViewProps) => {
	const n1 = keys(measurementBetInfluencerAggregate);
	const n2 = keys(measurementDontBetInfluencerAggregate);
	const relevantInfluencerNames = union(n1, n2);

	return (
		<>
			{relevantInfluencerNames.map((name, idx) => {
				const g1Data = influencerPercentDistributions(
					measurementBetInfluencerAggregate[name.toString()],
				);

				const g2Data = influencerPercentDistributions(
					measurementDontBetInfluencerAggregate[name.toString()],
				);

				const Graph = (
					<InfluencerPredictionGraph
						i1Distribution={g1Data}
						i2Distribution={g2Data}
						name={name.toString()}
						bet={bet}
						key={uniqueId()}
					/>
				);

				return Graph;
			})}
		</>
	);
};

export type InfluencerPredictionGraphProps = {
	i1Distribution: PercentDistribution[];
	i2Distribution: PercentDistribution[];
	name: InfluencerName;
	bet: BetPredictionQualityBetEnum;
};

export type InfluencerPredictionGraphViewProps = {
	measurementBetInfluencerAggregate: {
		[key: string]: Array<BetPredictionQualityInfluencerAggregate>;
	};
	measurementDontBetInfluencerAggregate: {
		[key: string]: Array<BetPredictionQualityInfluencerAggregate>;
	};
	bet: BetPredictionQualityBetEnum;
};

export default { InfluencerPredictionGraphView };
