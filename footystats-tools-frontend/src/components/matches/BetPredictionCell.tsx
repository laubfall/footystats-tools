import { MatchListEntry } from "./MatchList";
import { OverlayTrigger, Popover } from "react-bootstrap";
import translate from "../../i18n/translate";
import { BetDetailInfoOverlay } from "./BetDetailInfoOverlay";
import { BetPredictionIcon } from "./BetPredictionIcon";
import React from "react";
import { Bet } from "../../footystats-frontendapi";

export const BetPredictionCell = ({
	row,
	bet,
}: {
	row: MatchListEntry;
	bet: Bet;
}) => {
	const betPrediction = row.betPredictions.find((v) => v.bet === bet);

	// In case of new bet prediction, we don't have a prediction yet.
	if (betPrediction?.prediction === undefined) {
		return null;
	}

	const statisticalOutcome = row.statisticalResultOutcome.find(
		(sro) => sro?.bet === bet,
	);

	return (
		<OverlayTrigger
			placement="right"
			overlay={
				<Popover id="popover-basic">
					<Popover.Header as="h3">
						{translate("renderer.matchlist.influencer.popup.title")}
					</Popover.Header>
					<Popover.Body>
						<BetDetailInfoOverlay
							betPrediction={betPrediction}
							statisticalOutcome={statisticalOutcome}
						/>
					</Popover.Body>
				</Popover>
			}
		>
			<span>
				{betPrediction?.prediction.betSuccessInPercent}
				&nbsp;
				<BetPredictionIcon
					predictionResult={betPrediction?.prediction}
					statisticalResultOutcome={statisticalOutcome}
				/>
			</span>
		</OverlayTrigger>
	);
};
