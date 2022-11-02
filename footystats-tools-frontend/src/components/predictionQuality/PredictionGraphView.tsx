import React from "react";
import { colord } from "colord";
import { PercentageDistributionGraph } from "./PercentageDistributionGraph";
import translate from "../../i18n/translate";
import { Bet } from "../../app/types/prediction/BetPredictionContext";
import {
	BetPredictionQuality,
	BetPredictionQualityBetEnum,
} from "../../footystats-frontendapi";

export type PredictionGraphViewProps = {
	measurement?: BetPredictionQuality;
};

export const PredictionGraphView = ({
	measurement,
}: PredictionGraphViewProps) => {
	if (!measurement) {
		return null;
	}
	return (
		<>
			<PercentageDistributionGraph
				graphs={[
					{
						name: `${translate(
							`renderer.matchesview.bet.${
								BetPredictionQualityBetEnum[measurement.bet]
							}`,
						)} ${translate("renderer.predictiongraphview.bet")}`,
						data: measurement.distributionBetOnThis,
						color: colord("rgb(40,200,0)").toRgb(),
					},
					{
						name: `${translate(
							`renderer.matchesview.bet.${
								BetPredictionQualityBetEnum[measurement.bet]
							}`,
						)} ${translate(
							"renderer.predictiongraphview.bet.failed",
						)}`,
						data: measurement.distributionBetOnThisFailed,
					},
				]}
			/>

			<PercentageDistributionGraph
				graphs={[
					{
						name: `${translate(
							`renderer.matchesview.bet.${
								BetPredictionQualityBetEnum[measurement.bet]
							}`,
						)} ${translate(
							"renderer.predictiongraphview.dontbet",
						)}`,
						data: measurement.distributionDontBetOnThis,
					},
					{
						name: `${translate(
							`renderer.matchesview.bet.${
								BetPredictionQualityBetEnum[measurement.bet]
							}`,
						)} ${translate(
							"renderer.predictiongraphview.dontbet.failed",
						)}`,
						data: measurement.distributionDontBetOnThisFailed,
					},
				]}
			/>
		</>
	);
};
