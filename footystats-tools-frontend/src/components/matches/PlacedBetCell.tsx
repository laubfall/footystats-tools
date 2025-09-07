import { MatchListEntry } from "./MatchList";
import { OverlayTrigger, Popover } from "react-bootstrap";
import translate from "../../i18n/translate";
import React from "react";
import { PlacedBetOverlay } from "./PlacedBetOverlay";
import { FaBacon } from "react-icons/fa";

export const PlacedBetCell = ({ row }: { row: MatchListEntry }) => {
	return (
		<OverlayTrigger
			placement="right"
			overlay={
				<Popover id="popover-basic">
					<Popover.Header as="h3">
						{translate("renderer.matchlist.influencer.popup.title")}
					</Popover.Header>
					<Popover.Body>
						<PlacedBetOverlay placedBets={[...row.placedBets]} />
					</Popover.Body>
				</Popover>
			}
		>
			<span>
				<FaBacon />
			</span>
		</OverlayTrigger>
	);
};
