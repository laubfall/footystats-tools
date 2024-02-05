import React, { useEffect, useState } from "react";
import {
	LiveAndHotControllerApi,
	LiveAndHotMatches,
} from "../../footystats-frontendapi";
import LoadingOverlayStore from "../../mobx/LoadingOverlayStore";
import { apiCatchReasonHandler } from "../functions";
import NoLiveAndHotMatches from "./NoLiveAndHotMatches";
import MatchTable from "./MatchTable";
import { Alert } from "react-bootstrap";
import translate from "../../i18n/translate";

export const LiveAndHotView = () => {
	const [liveAndHot, setLiveAndHot] = useState<LiveAndHotMatches[]>([]);

	useEffect(() => {
		const api = new LiveAndHotControllerApi();
		LoadingOverlayStore.loadingNow();
		api.list()
			.then(setLiveAndHot)
			.catch(apiCatchReasonHandler)
			.finally(() => LoadingOverlayStore.notLoadingNow());
	}, []);

	if (liveAndHot?.length === 0) {
		return <NoLiveAndHotMatches />;
	}

	return (
		<>
			<Alert variant="success">
				<Alert.Heading>
					{translate("renderer.liveandhot.view.alert.header")}
				</Alert.Heading>
				<p>{translate("renderer.liveandhot.view.alert.text")}</p>
			</Alert>
			<MatchTable matches={liveAndHot} />;
		</>
	);
};

export default LiveAndHotView;
