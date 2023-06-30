import React from "react";
import { ListGroup, ListGroupItem, Popover } from "react-bootstrap";
import translate from "../../i18n/translate";
import { uniqueId } from "lodash";
import {
	InfluencerResultPrecheckResultEnum,
	InfluencerStatisticalResultOutcome,
	StatisticalResultOutcome,
} from "../../footystats-frontendapi";
import { BetPrediction } from "./MatchList";

export const BetDetailInfoOverlay = ({
	betPrediction,
	statisticalOutcome,
}: BetDetailInfoOverlayProps) => {
	const relevantDetails =
		betPrediction?.prediction.influencerDetailedResult.filter(
			(influencerResult) =>
				influencerResult.precheckResult ===
				InfluencerResultPrecheckResultEnum.Ok,
		);

	function influencerStatisticalOutcome(
		influencerName: string,
	): InfluencerStatisticalResultOutcome {
		const result =
			statisticalOutcome?.influencerStatisticalResultOutcomes.filter(
				(iso) => iso.influencerName === influencerName,
			);

		return result?.length === 1 ? result[0] : undefined;
	}

	function humanReadablePercent(statisticalOutcome?: number) {
		if (!statisticalOutcome) {
			return 0;
		}

		return (statisticalOutcome * 100).toFixed(2);
	}

	return (
		<>
			{translate("renderer.matchlist.influencer.popup.help")}
			<ListGroup variant={"flush"}>
				<ListGroupItem>
					<b>
						{translate(
							"renderer.matchlist.influencer.popup.statistical.outcome",
						)}
					</b>
					{betPrediction.prediction.betSuccessInPercent} /{" "}
					{humanReadablePercent(
						statisticalOutcome?.betStatisticalSuccess,
					)}
				</ListGroupItem>
				{relevantDetails &&
					relevantDetails.map((influencerResult) => (
						<ListGroupItem key={uniqueId()}>
							{influencerResult.influencerName}:{" "}
							{influencerResult.influencerPredictionValue}
							{" / "}
							{humanReadablePercent(
								influencerStatisticalOutcome(
									influencerResult.influencerName,
								)?.statisticalOutcomeBetSuccess,
							)}
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
