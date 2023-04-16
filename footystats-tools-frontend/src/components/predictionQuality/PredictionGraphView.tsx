import React from "react";
import { colord } from "colord";
import { PercentageDistributionGraph } from "./PercentageDistributionGraph";
import translate from "../../i18n/translate";
import { Col, Row } from "react-bootstrap";
import { BetPredictionQualityBetAggregate } from "../../footystats-frontendapi";
import { BetPredictionQualityBetEnum } from "../../footystats-frontendapi/models/BetPredictionQuality";

export const PredictionGraphView = ({
	measurement,
	measurementDontBet,
	bet,
}: PredictionGraphViewProps) => {
	if (!measurement) {
		return null;
	}
	return (
		<Row>
			<Col md={5}>
				<PercentageDistributionGraph
					graphs={[
						{
							name: `${translate(
								`renderer.matchesview.bet.${bet}`,
							)} ${translate(
								"renderer.predictiongraphview.bet",
							)}`,
							data: measurement,
							color: colord("rgb(40,200,0)").toRgb(),
						},
						{
							name: `${translate(
								`renderer.matchesview.bet.${bet}`,
							)} ${translate(
								"renderer.predictiongraphview.bet.failed",
							)}`,
							data: measurementDontBet,
						},
					]}
				/>
			</Col>
			<Col md={6}>
				<PercentageDistributionGraph
					graphs={[
						{
							name: `${translate(
								`renderer.matchesview.bet.${bet}`,
							)} ${translate(
								"renderer.predictiongraphview.dontbet",
							)}`,
							data: measurement,
							color: colord("rgb(40,200,0)").toRgb(),
						},
						{
							name: `${translate(
								`renderer.matchesview.bet.${bet}`,
							)} ${translate(
								"renderer.predictiongraphview.dontbet.failed",
							)}`,
							data: measurementDontBet,
						},
					]}
				/>
			</Col>
		</Row>
	);
};

export type PredictionGraphViewProps = {
	measurement?: Array<BetPredictionQualityBetAggregate>;
	measurementDontBet?: Array<BetPredictionQualityBetAggregate>;
	bet: BetPredictionQualityBetEnum;
};
