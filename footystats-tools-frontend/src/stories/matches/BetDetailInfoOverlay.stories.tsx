import React from "react";
import { BetDetailInfoOverlay } from "../../components/matches/BetDetailInfoOverlay";
import { OverlayTrigger, Popover } from "react-bootstrap";
import translate from "../../i18n/translate";

export default {
	title: "Matches/BetDetailInfoOverlay",
};

export const BetDetailInfoOverlayComponent = () => (
	<OverlayTrigger
		defaultShow={true}
		placement={"bottom"}
		overlay={
			<Popover id="popover-basic">
				<Popover.Header as="h3">
					{translate("renderer.matchlist.influencer.popup.title")}
				</Popover.Header>
				<Popover.Body>
					<BetDetailInfoOverlay
						betPrediction={{
							bet: "OVER_ZERO_FIVE",
							prediction: {
								betOnThis: false,
								betSuccessInPercent: 64,
								analyzeResult: "SUCCESS",
								influencerDetailedResult: [
									{
										influencerName: "OverZeroFive",
										precheckResult: "OK",
										influencerPredictionValue: 72,
									},
								],
							},
						}}
						statisticalOutcome={{
							bet: "OVER_ZERO_FIVE",
							betStatisticalSuccess: 0.81,
							influencerStatisticalResultOutcomes: [
								{
									influencerName: "OverZeroFive",
									statisticalOutcomeBetSuccess: 0.31,
									ranking: {
										position: 1,
										total: 40,
										best10Percent: true,
										best20Percent: true,
									},
								},
							],
						}}
					/>
				</Popover.Body>
			</Popover>
		}
	>
		<h3>Bet Details</h3>
	</OverlayTrigger>
);
