import React from "react";
import { colord } from "colord";
import { PercentageDistributionGraph } from "./PercentageDistributionGraph";
import translate from "../../i18n/translate";
import { Col, Row } from "react-bootstrap";
import { BetPredictionQualityBetAggregate } from "../../footystats-frontendapi";
import { BetPredictionQualityBetEnum } from "../../footystats-frontendapi/models/BetPredictionQuality";

export const PredictionGraphView = ({
	betPredictionPercentDistribution,
	dontBetPredictionPercentDistribution,
	bet,
}: PredictionGraphViewProps) => {
	if (
		betPredictionPercentDistribution?.length == 0 &&
		dontBetPredictionPercentDistribution?.length == 0
	) {
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
							data: betPredictionPercentDistribution,
							color: colord("rgb(40,200,0)").toRgb(),
						},
						{
							name: `${translate(
								`renderer.matchesview.bet.${bet}`,
							)} ${translate(
								"renderer.predictiongraphview.bet.failed",
							)}`,
							data: dontBetPredictionPercentDistribution,
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
							data: betPredictionPercentDistribution,
							color: colord("rgb(40,200,0)").toRgb(),
						},
						{
							name: `${translate(
								`renderer.matchesview.bet.${bet}`,
							)} ${translate(
								"renderer.predictiongraphview.dontbet.failed",
							)}`,
							data: dontBetPredictionPercentDistribution,
						},
					]}
				/>
			</Col>
		</Row>
	);
};

export type PredictionGraphViewProps = {
	betPredictionPercentDistribution?: Array<BetPredictionQualityBetAggregate>;
	dontBetPredictionPercentDistribution?: Array<BetPredictionQualityBetAggregate>;
	bet: BetPredictionQualityBetEnum;
};
