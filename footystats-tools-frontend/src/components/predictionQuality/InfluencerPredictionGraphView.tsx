import React from "react";
import { uniqueId, keys } from "lodash";
import { Alert } from "react-bootstrap";
import { colord } from "colord";
import { PercentageDistributionGraph } from "./PercentageDistributionGraph";
import { InfluencerName } from "../../app/services/prediction/PredictionService.types";
import translate from "../../i18n/translate";
import { PercentDistribution } from "../../app/services/prediction/PredictionQualityService.types";
import { BetPredictionQualityBetEnum } from "../../footystats-frontendapi/models/BetPredictionQuality";
import { BetPredictionQualityInfluencerAggregate } from "../../footystats-frontendapi";

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
						data: i1Distribution?.map((influencerDist) => ({
							xPredictionPercent:
								influencerDist.predictionPercent,
							yCount: influencerDist.count,
						})),
						color: colord("rgb(40,200,0)").toRgb(),
					},
					{
						name: `${translate(
							`renderer.matchesview.bet.${bet}`,
						)} ${translate(
							"renderer.predictiongraphview.bet.failed",
						)}`,
						data: i2Distribution?.map((influencerDist) => ({
							xPredictionPercent:
								influencerDist.predictionPercent,
							yCount: influencerDist.count,
						})),
					},
				]}
			/>
		</>
	);
};

export const InfluencerPredictionGraphView = ({
	measurementBetInfluencerAggregate,
	bet,
}: InfluencerPredictionGraphViewProps) => {
	const influencerNames = keys(measurementBetInfluencerAggregate);

	return (
		<>
			{influencerNames.map((name, idx) => {
				const g1Data = measurementBetInfluencerAggregate[
					name.toString()
				].map(
					(agg) =>
						({
							count: agg.betSucceeded,
							predictionPercent: agg.predictionPercent,
						} as PercentDistribution),
				);

				const g2Data = measurementBetInfluencerAggregate[
					name.toString()
				].map(
					(agg) =>
						({
							count: agg.betFailed,
							predictionPercent: agg.predictionPercent,
						} as PercentDistribution),
				);

				return (
					<InfluencerPredictionGraph
						i1Distribution={g1Data}
						i2Distribution={g2Data}
						name={name.toString()}
						bet={bet}
						key={uniqueId()}
					/>
				);
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
	bet: BetPredictionQualityBetEnum;
};

export default { InfluencerPredictionGraphView };
