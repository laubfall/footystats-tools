import React from "react";
import { BetForMatch } from "../../footystats-frontendapi";
import { ListGroup } from "react-bootstrap";
import translate from "../../i18n/translate";

export const PlacedBetOverlay = ({ placedBets }: PlacedBetOverlayProps) => {
	if (placedBets.length === 0) {
		return null;
	}

	function actualBetResult(bet: BetForMatch) {
		if (!bet.evaluated) {
			return "not_evaluated";
		}

		if (bet.won) {
			return "won";
		} else {
			return "lost";
		}
	}

	return (
		<ListGroup title={translate("renderer.placedbetoverlay.title")}>
			{placedBets.map((bet) => (
				<ListGroup.Item key={bet.placedBet}>
					{bet.placedBet}:{" "}
					{translate(
						"renderer.placedbetoverlay.result." +
							actualBetResult(bet),
					)}
				</ListGroup.Item>
			))}
		</ListGroup>
	);
};

export type PlacedBetOverlayProps = {
	placedBets: BetForMatch[];
};
