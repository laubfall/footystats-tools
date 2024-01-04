import React from "react";
import { colord } from "colord";
import { PercentageDistributionGraph } from "./PercentageDistributionGraph";
import translate from "../../i18n/translate";
import { Col, Row } from "react-bootstrap";
import { BetPredictionQualityBetAggregate } from "../../footystats-frontendapi";
import { BetPredictionQualityBetEnum } from "../../footystats-frontendapi/models/BetPredictionQuality";

export const PredictionGraphView = ({
	betPredictionPercentDistribution,
	bet,
}: PredictionGraphViewProps) => {
	if (betPredictionPercentDistribution?.length == 0) {
		return null;
	}
	return (
		<Row>
			<Col md={12}>
				<PercentageDistributionGraph
					graphs={[
						{
							name: `${translate(
								`renderer.matchesview.bet.${bet}`,
							)} ${translate(
								"renderer.predictiongraphview.bet",
							)}`,
							data: betPredictionPercentDistribution?.map(
								(bppa) => ({
									xPredictionPercent: bppa.predictionPercent,
									yCount: bppa.betSucceeded,
								}),
							),
							color: colord("rgb(40,200,0)").toRgb(),
						},
						{
							name: `${translate(
								`renderer.matchesview.bet.${bet}`,
							)} ${translate(
								"renderer.predictiongraphview.bet.failed",
							)}`,
							data: betPredictionPercentDistribution?.map(
								(bppa) => ({
									xPredictionPercent: bppa.predictionPercent,
									yCount: bppa.betFailed,
								}),
							),
						},
					]}
				/>
			</Col>
		</Row>
	);
};

export type PredictionGraphViewProps = {
	betPredictionPercentDistribution?: Array<BetPredictionQualityBetAggregate>;
	bet: BetPredictionQualityBetEnum;
};
