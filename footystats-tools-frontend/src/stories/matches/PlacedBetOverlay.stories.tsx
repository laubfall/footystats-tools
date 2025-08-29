import { OverlayTrigger, Popover } from "react-bootstrap";
import translate, { initialize } from "../../i18n/translate";
import React from "react";
import { PlacedBetOverlay } from "../../components/matches/PlacedBetOverlay";

export default {
	title: "Matches/PlacedBetOverlay",
};

initialize(new Intl.Locale("de"));

export const PlacedBetOverlayStory = () => (
	<OverlayTrigger
		defaultShow={true}
		placement={"bottom"}
		overlay={
			<Popover id="popover-basic">
				<Popover.Header as="h3">
					{translate("renderer.matchlist.influencer.popup.title")}
				</Popover.Header>
				<Popover.Body>
					<PlacedBetOverlay
						placedBets={[
							{
								placedBet: "OVER_ZERO_FIVE",
								won: false,
								evaluated: true,
							},
							{
								placedBet: "OVER_ZERO_FIVE",
								won: false,
								evaluated: false,
							},
							{
								placedBet: "OVER_ZERO_FIVE",
								won: true,
								evaluated: true,
							},
						]}
					/>
				</Popover.Body>
			</Popover>
		}
	>
		<h3>Bet Details</h3>
	</OverlayTrigger>
);
