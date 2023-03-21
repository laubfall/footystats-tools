import React from "react";
import { ListGroup, ListGroupItem, Popover } from "react-bootstrap";
import translate from "../../i18n/translate";
import { uniqueId } from "lodash";
import {
	InfluencerResultPrecheckResultEnum,
	StatisticalResultOutcome,
} from "../../footystats-frontendapi";
import { BetPrediction } from "./MatchList";

export const BetDetailInfoOverlay = ({
	betPrediction,
	statisticalOutcome,
}: BetDetailInfoOverlayProps) => {
	const relevantDetails =
		betPrediction?.prediction.influencerDetailedResult.filter(
			(d) => d.precheckResult === InfluencerResultPrecheckResultEnum.Ok,
		);

	return (
		<>
			<span>
				{translate(
					"renderer.matchlist.influencer.popup.statistical.outcome",
				)}
				<br />
				{betPrediction.prediction.betSuccessInPercent} /{" "}
				{(statisticalOutcome?.betStatisticalSuccess * 100).toFixed(2)}
			</span>
			<br />
			{translate("renderer.matchlist.influencer.popup.influencer.title")}
			<ListGroup>
				{relevantDetails &&
					relevantDetails.map((d) => (
						<ListGroupItem key={uniqueId()}>
							{d.influencerName}: {d.influencerPredictionValue}
						</ListGroupItem>
					))}
			</ListGroup>
		</>
	);
};

export type BetDetailInfoOverlayProps = {
	betPrediction: BetPrediction;
	statisticalOutcome: StatisticalResultOutcome;
};
