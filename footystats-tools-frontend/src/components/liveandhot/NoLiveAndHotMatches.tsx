import React from "react";
import { Alert } from "react-bootstrap";
import translate from "../../i18n/translate";

export const NoLiveAndHotMatches = () => {
	return (
		<Alert variant="info">
			<Alert.Heading>
				{translate("renderer.liveandhot.noliveandhotmatches.header")}
			</Alert.Heading>
			<p>{translate("renderer.liveandhot.noliveandhotmatches.text")}</p>
		</Alert>
	);
};

export default NoLiveAndHotMatches;
