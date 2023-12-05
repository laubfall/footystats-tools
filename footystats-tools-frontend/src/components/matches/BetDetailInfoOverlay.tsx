import React from "react";
import { ListGroup, ListGroupItem } from "react-bootstrap";
import translate from "../../i18n/translate";
import { uniqueId } from "lodash";
import {
	InfluencerResult,
	InfluencerResultPrecheckResultEnum,
	InfluencerStatisticalResultOutcome,
	Ranking,
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

	function recommendedBetOdd(statisticalOutcome?: number) {
		if (!statisticalOutcome) {
			return 0;
		}

		return (1 / statisticalOutcome).toFixed(2);
	}

	function influencerRanking(
		statisticalResultOutcome: InfluencerStatisticalResultOutcome,
		betOnThis: boolean,
	): string {
		if (!statisticalResultOutcome?.ranking) {
			return "";
		}

		const ranking: Ranking = statisticalResultOutcome.ranking;
		return `Ranking: ${ranking.position}/${ranking.total} ${
			!betOnThis
				? statisticalResultOutcome.statisticalOutcomeBetSuccess
				: ""
		}`;
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
				<ListGroupItem>
					<b>
						{translate(
							"renderer.matchlist.influencer.popup.recommended.odd",
						)}
					</b>{" "}
					{recommendedBetOdd(
						statisticalOutcome?.betStatisticalSuccess,
					)}
				</ListGroupItem>
				{relevantDetails?.map((influencerResult) => {
					const statOutcome = influencerStatisticalOutcome(
						influencerResult.influencerName,
					);
					return (
						<ListGroupItem key={uniqueId()}>
							{influencerResult.influencerName}:{" "}
							{influencerResult.influencerPredictionValue}
							{" / "}
							{humanReadablePercent(
								statOutcome?.statisticalOutcomeBetSuccess,
							)}{" "}
							{influencerRanking(
								statOutcome,
								betPrediction.prediction.betOnThis,
							)}
						</ListGroupItem>
					);
				})}
			</ListGroup>
		</>
	);
};

export type BetDetailInfoOverlayProps = {
	betPrediction: BetPrediction;
	statisticalOutcome: StatisticalResultOutcome;
};
