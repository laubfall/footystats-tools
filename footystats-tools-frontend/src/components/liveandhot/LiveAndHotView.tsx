import React, { useEffect, useState } from "react";
import {
	LiveAndHotControllerApi,
	LiveAndHotMatches,
} from "../../footystats-frontendapi";
import LoadingOverlayStore from "../../mobx/LoadingOverlayStore";
import { apiCatchReasonHandler } from "../functions";
import NoLiveAndHotMatches from "./NoLiveAndHotMatches";
import MatchTable from "./MatchTable";

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

	return <MatchTable matches={liveAndHot} />;
};

export default LiveAndHotView;
