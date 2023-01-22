import React from "react";
import { colord } from "colord";
import { PercentageDistributionGraph } from "./PercentageDistributionGraph";
import translate from "../../i18n/translate";
import {
	BetPredictionQuality,
	BetPredictionQualityBetEnum,
} from "../../footystats-frontendapi";
import { Col, Row } from "react-bootstrap";

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
		<Row>
			<Col md={6}>
				<PercentageDistributionGraph
					graphs={[
						{
							name: `${translate(
								`renderer.matchesview.bet.${measurement.bet}`,
							)} ${translate(
								"renderer.predictiongraphview.bet",
							)}`,
							data: measurement.distributionBetOnThis,
							color: colord("rgb(40,200,0)").toRgb(),
						},
						{
							name: `${translate(
								`renderer.matchesview.bet.${measurement.bet}`,
							)} ${translate(
								"renderer.predictiongraphview.bet.failed",
							)}`,
							data: measurement.distributionBetOnThisFailed,
						},
					]}
				/>
			</Col>
			<Col md={6}>
				<PercentageDistributionGraph
					graphs={[
						{
							name: `${translate(
								`renderer.matchesview.bet.${measurement.bet}`,
							)} ${translate(
								"renderer.predictiongraphview.dontbet",
							)}`,
							data: measurement.distributionDontBetOnThis,
							color: colord("rgb(40,200,0)").toRgb(),
						},
						{
							name: `${translate(
								`renderer.matchesview.bet.${measurement.bet}`,
							)} ${translate(
								"renderer.predictiongraphview.dontbet.failed",
							)}`,
							data: measurement.distributionDontBetOnThisFailed,
						},
					]}
				/>
			</Col>
		</Row>
	);
};
